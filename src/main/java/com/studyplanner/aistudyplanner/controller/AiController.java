package com.studyplanner.aistudyplanner.controller;

import com.studyplanner.aistudyplanner.dto.ChatRequest;
import com.studyplanner.aistudyplanner.dto.ChatResponse;
import com.studyplanner.aistudyplanner.entity.User;
import com.studyplanner.aistudyplanner.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService service;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@AuthenticationPrincipal User user, @RequestBody ChatRequest request) {
        return ResponseEntity.ok(service.getChatResponse(user, request));
    }
}
