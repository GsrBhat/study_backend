package com.studyplanner.aistudyplanner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "performance_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "efficiency_score")
    private Integer efficiencyScore;

    @Enumerated(EnumType.STRING)
    private PerformanceStatus status;
}
