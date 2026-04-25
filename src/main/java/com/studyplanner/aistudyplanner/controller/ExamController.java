package com.studyplanner.aistudyplanner.controller;

import com.studyplanner.aistudyplanner.dto.ExamDto;
import com.studyplanner.aistudyplanner.entity.User;
import com.studyplanner.aistudyplanner.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService service;

    @GetMapping
    public ResponseEntity<List<ExamDto>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.getAllExams(user));
    }

    @PostMapping
    public ResponseEntity<ExamDto> add(@RequestBody ExamDto dto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.addExam(dto, user));
    }
}
