package com.studyplanner.aistudyplanner.controller;

import com.studyplanner.aistudyplanner.entity.User;
import com.studyplanner.aistudyplanner.service.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/timetable")
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService service;

    @GetMapping("/generate")
    public ResponseEntity<Map<String, Object>> generate(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "6") int hours) {
        return ResponseEntity.ok(service.generateTimetable(user, hours));
    }
}
