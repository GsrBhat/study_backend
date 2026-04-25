package com.studyplanner.aistudyplanner.service;

import com.studyplanner.aistudyplanner.dto.ExamDto;
import com.studyplanner.aistudyplanner.entity.Exam;
import com.studyplanner.aistudyplanner.entity.Subject;
import com.studyplanner.aistudyplanner.entity.User;
import com.studyplanner.aistudyplanner.repository.ExamRepository;
import com.studyplanner.aistudyplanner.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository repository;
    private final SubjectRepository subjectRepository;

    public List<ExamDto> getAllExams(User user) {
        return repository.findByUser(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ExamDto addExam(ExamDto dto, User user) {
        Subject subject = subjectRepository.findById(dto.getSubjectId()).orElseThrow();
        Exam exam = Exam.builder()
                .user(user)
                .subject(subject)
                .examDate(dto.getExamDate())
                .weight(dto.getWeight() != null ? dto.getWeight() : 1.0f)
                .build();
        return mapToDto(repository.save(exam));
    }

    private ExamDto mapToDto(Exam exam) {
        return ExamDto.builder()
                .id(exam.getId())
                .subjectId(exam.getSubject().getId())
                .subjectName(exam.getSubject().getName())
                .examDate(exam.getExamDate())
                .weight(exam.getWeight())
                .build();
    }
}
