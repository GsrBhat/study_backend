package com.studyplanner.aistudyplanner.service;

import com.studyplanner.aistudyplanner.dto.SubjectDto;
import com.studyplanner.aistudyplanner.entity.*;
import com.studyplanner.aistudyplanner.repository.ExamRepository;
import com.studyplanner.aistudyplanner.repository.PerformanceLogRepository;
import com.studyplanner.aistudyplanner.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimetableService {
    private final SubjectRepository subjectRepository;
    private final ExamRepository examRepository;
    private final PerformanceLogRepository performanceLogRepository;

    public Map<String, Object> generateTimetable(User user, int dailyHours) {
        List<Subject> subjects = subjectRepository.findByUser(user);
        List<Exam> exams = examRepository.findByUser(user);
        List<PerformanceLog> logs = performanceLogRepository.findByUser(user);

        if (subjects.isEmpty()) {
            return Map.of("message", "No subjects found. Please add subjects first.");
        }

        Map<Long, Double> weights = new HashMap<>();
        double totalWeight = 0;

        for (Subject s : subjects) {
            double weight = getDifficultyWeight(s.getDifficulty()) + getPriorityWeight(s.getPriority());
            
            // Performance adjustment
            Optional<PerformanceLog> lastLog = logs.stream()
                    .filter(l -> l.getSubject().getId().equals(s.getId()))
                    .max(Comparator.comparing(PerformanceLog::getLogDate));
            
            if (lastLog.isPresent()) {
                if (lastLog.get().getStatus() == PerformanceStatus.WEAK) weight += 2.0;
                else if (lastLog.get().getStatus() == PerformanceStatus.MODERATE) weight += 1.0;
            }

            // Exam proximity adjustment
            Optional<Exam> nextExam = exams.stream()
                    .filter(e -> e.getSubject().getId().equals(s.getId()) && e.getExamDate().isAfter(LocalDate.now()))
                    .min(Comparator.comparing(Exam::getExamDate));

            if (nextExam.isPresent()) {
                long daysToExam = ChronoUnit.DAYS.between(LocalDate.now(), nextExam.get().getExamDate());
                if (daysToExam <= 7) weight *= 2.0;
                else if (daysToExam <= 14) weight *= 1.5;
            }

            weights.put(s.getId(), weight);
            totalWeight += weight;
        }

        List<Map<String, Object>> schedule = new ArrayList<>();
        for (Subject s : subjects) {
            double subjectWeight = weights.get(s.getId());
            double allocatedHours = (subjectWeight / totalWeight) * dailyHours;
            
            Map<String, Object> item = new HashMap<>();
            item.put("subject", s.getName());
            item.put("hours", Math.round(allocatedHours * 10.0) / 10.0);
            item.put("reason", getReason(s, weights.get(s.getId())));
            schedule.add(item);
        }

        return Map.of(
            "dailyHours", dailyHours,
            "schedule", schedule,
            "generatedAt", LocalDate.now()
        );
    }

    private double getDifficultyWeight(Difficulty d) {
        return switch (d) {
            case DIFFICULT -> 3.0;
            case MODERATE -> 2.0;
            case EASY -> 1.0;
        };
    }

    private double getPriorityWeight(Priority p) {
        return switch (p) {
            case HIGH -> 3.0;
            case MEDIUM -> 2.0;
            case LOW -> 1.0;
        };
    }

    private String getReason(Subject s, double weight) {
        if (weight > 5.0) return "High priority due to difficulty and upcoming exam.";
        if (weight > 3.0) return "Balanced focus based on subject difficulty.";
        return "Regular maintenance session.";
    }
}
