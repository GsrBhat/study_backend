package com.studyplanner.aistudyplanner.service;

import com.studyplanner.aistudyplanner.dto.ChatRequest;
import com.studyplanner.aistudyplanner.dto.ChatResponse;
import com.studyplanner.aistudyplanner.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${huggingface.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    // Using Mistral-7B-Instruct as a reliable default for study guidance
    private final String API_URL = "https://api-inference.huggingface.co/models/mistralai/Mistral-7B-Instruct-v0.2";

    public ChatResponse getChatResponse(User user, ChatRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            // Constructing a prompt for Hugging Face Inference API
            String prompt = String.format("<s>[INST] You are a helpful study assistant for %s. Answer the following query concisely: %s [/INST]", 
                    user.getUsername(), request.getMessage());

            Map<String, Object> body = new HashMap<>();
            body.put("inputs", prompt);
            body.put("parameters", Map.of("max_new_tokens", 250, "return_full_text", false));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            
            // Hugging Face returns a List of Maps for many models
            List<Map<String, Object>> response = restTemplate.postForObject(API_URL, entity, List.class);
            
            String reply = "I'm here to help you study better! Based on your performance, you should focus on your weak subjects today.";
            if (response != null && !response.isEmpty()) {
                reply = (String) response.get(0).get("generated_text");
            }

            return ChatResponse.builder().reply(reply).build();
        } catch (Exception e) {
            System.err.println("AI Service Error: " + e.getMessage());
            e.printStackTrace();
            return ChatResponse.builder()
                    .reply("AI Service (Hugging Face) is temporarily unavailable. Tip: Focus on your highest priority subjects!")
                    .build();
        }
    }
}
