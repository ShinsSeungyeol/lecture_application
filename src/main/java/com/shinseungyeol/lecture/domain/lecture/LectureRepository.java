package com.shinseungyeol.lecture.domain.lecture;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("SELECT l FROM Lecture l inner join LectureMetrics lm on lm.lecture = l and lm.registeredCount < :limit WHERE l.registrationStartAt <= :now AND l.registrationEndAt >= :now")
    List<Lecture> findAllPossibleLectures(@Param("now") LocalDateTime now,
        @Param("limit") Integer limit);

}
