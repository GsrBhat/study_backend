package com.studyplanner.aistudyplanner.dto;

import com.studyplanner.aistudyplanner.entity.Difficulty;
import com.studyplanner.aistudyplanner.entity.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    private Long id;
    private String name;
    private Difficulty difficulty;
    private Priority priority;
    private Integer revisionCount;
}
