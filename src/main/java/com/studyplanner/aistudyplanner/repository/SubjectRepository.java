package com.studyplanner.aistudyplanner.repository;

import com.studyplanner.aistudyplanner.entity.Subject;
import com.studyplanner.aistudyplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByUser(User user);
}
