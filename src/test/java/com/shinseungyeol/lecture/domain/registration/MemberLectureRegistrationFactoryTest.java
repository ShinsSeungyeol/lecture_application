package com.shinseungyeol.lecture.domain.registration;

import static org.mockito.Mockito.when;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.lecture.LectureMetrics;
import com.shinseungyeol.lecture.domain.member.Member;
import com.shinseungyeol.lecture.infrastructure.lecture.LectureMetricsRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberLectureRegistrationFactoryTest {

    @InjectMocks
    private MemberLectureRegistrationFactory memberLectureRegistrationFactory;

    @Mock
    private LectureMetricsRepository lectureMetricsRepository;

    @Mock
    private MemberLectureRegistrationValidator memberLectureRegistrationValidator;

    private Lecture normalLecture;


    private Member member;


    @BeforeEach
    void init() {
        normalLecture = Lecture.create(
            "normal", "bob", LocalDateTime.now(), LocalDateTime.now().plusDays(1),
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        member = Member.create("shinseungyeol");
    }


    @Test
    public void 유저_강의_등록_인스턴스_생성_테스트() {
        when(lectureMetricsRepository.findByLecture(normalLecture)).thenReturn(
            Optional.of(new LectureMetrics()));

        MemberLectureRegistration memberLectureRegistration = memberLectureRegistrationFactory.newInstance(
            normalLecture, member);

        Assertions.assertEquals(normalLecture, memberLectureRegistration.getLecture());
        Assertions.assertEquals(member, memberLectureRegistration.getMember());
    }
}