package com.shinseungyeol.lecture.domain.registration;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.lecture.LectureMetrics;
import com.shinseungyeol.lecture.exception.registration.ExpiredRegistrationTimeException;
import com.shinseungyeol.lecture.exception.registration.MaxRegistrationLimitExceededException;
import com.shinseungyeol.lecture.exception.registration.NotYetRegistrationTimeException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class MemberLectureRegistrationValidatorImpl implements
    MemberLectureRegistrationValidator {

    public final static int MAX_REGISTRATION_LIMIT = 30;

    /**
     * 등록 가능한지 체크하는 함수
     *
     * @param lecture
     */
    @Override
    public void validate(Lecture lecture, LectureMetrics lectureMetrics) {
        validateRegistrationTime(lecture);
        validateRegistrationLimit(lectureMetrics);
    }

    @Override
    public int getMaxRegistrationLimit() {
        return MAX_REGISTRATION_LIMIT;
    }

    /**
     * 현재 등록 시작 시간이 적절한지 체크하는 함수
     *
     * @param lecture
     */
    private void validateRegistrationTime(Lecture lecture) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime registrationStartTime = lecture.getRegistrationStartAt();
        LocalDateTime registrationEndTime = lecture.getRegistrationEndAt();

        if (now.isBefore(registrationStartTime)) {
            throw new NotYetRegistrationTimeException();
        }

        if (now.isAfter(registrationEndTime)) {
            throw new ExpiredRegistrationTimeException();
        }
    }

    /**
     * 등록 제한을 넘지 않는 것을 체크하는 함수
     *
     * @param lectureMetrics
     */
    private void validateRegistrationLimit(LectureMetrics lectureMetrics) {
        Integer currentRegisteredUserCount = lectureMetrics.getRegisteredCount();

        if (currentRegisteredUserCount >= MAX_REGISTRATION_LIMIT) {
            throw new MaxRegistrationLimitExceededException();
        }
    }
}
