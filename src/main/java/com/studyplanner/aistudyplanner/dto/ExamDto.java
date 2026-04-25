package com.studyplanner.aistudyplanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDto {
    private Long id;
    private Long subjectId;
    private String subjectName;
    private LocalDate examDate;
    private Float weight;
    private String notes;
}
