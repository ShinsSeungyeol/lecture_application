package com.shinseungyeol.lecture.domain.lecture;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureMetricsRepository extends JpaRepository<LectureMetrics, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureMetrics> findByLecture(Lecture lecture);

}
