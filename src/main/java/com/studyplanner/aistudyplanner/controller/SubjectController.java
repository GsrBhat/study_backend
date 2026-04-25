package com.studyplanner.aistudyplanner.controller;

import com.studyplanner.aistudyplanner.dto.SubjectDto;
import com.studyplanner.aistudyplanner.entity.User;
import com.studyplanner.aistudyplanner.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService service;

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubjects(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.getAllSubjects(user));
    }

    @PostMapping
    public ResponseEntity<SubjectDto> addSubject(@RequestBody SubjectDto dto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.addSubject(dto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id, @AuthenticationPrincipal User user) {
        service.deleteSubject(id, user);
        return ResponseEntity.noContent().build();
    }
}
