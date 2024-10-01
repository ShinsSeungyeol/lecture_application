package com.shinseungyeol.lecture.infrastructure.lecture;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

}
