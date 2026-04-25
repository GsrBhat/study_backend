package com.studyplanner.aistudyplanner.service;

import com.studyplanner.aistudyplanner.dto.SubjectDto;
import com.studyplanner.aistudyplanner.entity.Subject;
import com.studyplanner.aistudyplanner.entity.User;
import com.studyplanner.aistudyplanner.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository repository;

    public List<SubjectDto> getAllSubjects(User user) {
        return repository.findByUser(user).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public SubjectDto addSubject(SubjectDto dto, User user) {
        Subject subject = Subject.builder()
                .user(user)
                .name(dto.getName())
                .difficulty(dto.getDifficulty())
                .priority(dto.getPriority())
                .revisionCount(0)
                .build();
        return mapToDto(repository.save(subject));
    }

    public void deleteSubject(Long id, User user) {
        Subject subject = repository.findById(id).orElseThrow();
        if (subject.getUser().getId().equals(user.getId())) {
            repository.delete(subject);
        }
    }

    private SubjectDto mapToDto(Subject subject) {
        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .difficulty(subject.getDifficulty())
                .priority(subject.getPriority())
                .revisionCount(subject.getRevisionCount())
                .build();
    }
}
