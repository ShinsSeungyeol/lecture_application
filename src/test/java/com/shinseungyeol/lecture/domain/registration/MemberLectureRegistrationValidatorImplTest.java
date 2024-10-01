package com.shinseungyeol.lecture.domain.registration;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.lecture.LectureMetrics;
import com.shinseungyeol.lecture.exception.registration.ExpiredRegistrationTimeException;
import com.shinseungyeol.lecture.exception.registration.MaxRegistrationLimitExceededException;
import com.shinseungyeol.lecture.exception.registration.NotYetRegistrationTimeException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberLectureRegistrationValidatorImplTest {

    private final MemberLectureRegistrationValidator memberLectureRegistrationValidator = new MemberLectureRegistrationValidatorImpl();

    private Lecture possibleRegistrationLecture;

    private Lecture expiredRegistrationLecture;

    private Lecture beforeRegistrationLecture;


    @BeforeEach
    public void init() {
        possibleRegistrationLecture = Lecture.create(
            "normal", "bob", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        expiredRegistrationLecture = Lecture.create(
            "expired", "jery", LocalDateTime.now().minusDays(1),
            LocalDateTime.now().minusMinutes(1),
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        beforeRegistrationLecture = Lecture.create("yet", "tom", LocalDateTime.now().plusMinutes(1),
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(2));
    }


    @Test
    public void 등록_가능한_경우_테스트() {
        memberLectureRegistrationValidator.validate(possibleRegistrationLecture,
            new LectureMetrics());
    }

    @Test
    public void 아직_등록_가능_시간_전인_경우_테스트() {
        Assertions.assertThrows(NotYetRegistrationTimeException.class, () -> {
            memberLectureRegistrationValidator.validate(beforeRegistrationLecture,
                new LectureMetrics());
        });
    }

    @Test
    public void 등록_가능_시간_지난_경우_테스트() {
        Assertions.assertThrows(ExpiredRegistrationTimeException.class, () -> {
            memberLectureRegistrationValidator.validate(expiredRegistrationLecture,
                new LectureMetrics());
        });
    }

    @Test
    public void 이미_수강인원을_꽉_채운_경우_테스트() {
        LectureMetrics lectureMetrics = new LectureMetrics();

        Assertions.assertThrows(MaxRegistrationLimitExceededException.class, () -> {
            memberLectureRegistrationValidator.validate(possibleRegistrationLecture,
                new LectureMetrics(1L, 30, possibleRegistrationLecture));
        });
    }


}