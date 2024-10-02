package com.shinseungyeol.lecture.domain.registration;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.lecture.LectureMetrics;

public interface MemberLectureRegistrationValidator {

    void validate(Lecture lecture, LectureMetrics lectureMetrics);

    int getMaxRegistrationLimit();

}
