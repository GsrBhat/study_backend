package com.studyplanner.aistudyplanner.repository;

import com.studyplanner.aistudyplanner.entity.PerformanceLog;
import com.studyplanner.aistudyplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PerformanceLogRepository extends JpaRepository<PerformanceLog, Long> {
    List<PerformanceLog> findByUser(User user);
}
