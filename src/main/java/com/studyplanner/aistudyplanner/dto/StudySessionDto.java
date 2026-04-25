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
public class StudySessionDto {
    private Long id;
    private Long subjectId;
    private String subjectName;
    private LocalDate sessionDate;
    private Integer durationMinutes;
    private Integer completionPercentage;
    private Integer focusScore;
    private String notes;
}
