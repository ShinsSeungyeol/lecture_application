package com.shinseungyeol.lecture.service.registration;

import static org.mockito.Mockito.when;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.member.Member;
import com.shinseungyeol.lecture.domain.registration.MemberLectureRegistration;
import com.shinseungyeol.lecture.domain.registration.MemberLectureRegistrationFactory;
import com.shinseungyeol.lecture.exception.lecture.NotFoundLectureException;
import com.shinseungyeol.lecture.exception.member.NotFoundMemberException;
import com.shinseungyeol.lecture.infrastructure.lecture.LectureRepository;
import com.shinseungyeol.lecture.infrastructure.member.MemberRepository;
import com.shinseungyeol.lecture.infrastructure.registration.MemberLectureRegistrationRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LectureUserRegistrationServiceTest {

    @InjectMocks
    private LectureUserRegistrationService lectureUserRegistrationService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private MemberLectureRegistrationRepository memberLectureRegistrationRepository;

    @Mock
    private MemberLectureRegistrationFactory memberLectureRegistrationFactory;

    /**
     * 특강신청 정상 등록 테스트, 에러 없는 경우 테스트 통과
     */
    @Test
    public void 정상_등록_테스트() {
        Long memberId = 1L;
        String memberName = "shinseungyeol";
        Member member = Member.create(memberName);

        when(memberRepository.findById(memberId)).thenReturn(
            Optional.of(member));

        Long lectureId = 1L;
        String lectureName = "tdd";
        String speakerName = "bob";
        LocalDateTime registrationStartTime = LocalDateTime.now();
        LocalDateTime registrationEndTime = registrationStartTime.plusHours(1);
        LocalDateTime lectureStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime lectureEndTime = lectureStartTime.plusHours(1).plusHours(1);
        Lecture lecture = Lecture.create(
            lectureName, speakerName, registrationStartTime, registrationEndTime,
            lectureStartTime, lectureEndTime);

        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(lecture));

        MemberLectureRegistration memberLectureRegistration = MemberLectureRegistration.create(
            lecture, member);

        when(memberLectureRegistrationFactory.newInstance(lecture, member)).thenReturn(
            memberLectureRegistration);

        lectureUserRegistrationService.register(memberId, lectureId);
    }

    @Test
    public void 수강신청_사용자_찾을_수_없는_경우_테스트() {
        Long memberId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundMemberException.class,
            () -> lectureUserRegistrationService.register(memberId, 1L));
    }

    @Test
    public void 수강신청_강의를_찾을_수_없는_경우_테스트() {
        Long memberId = 1L;
        Long lectureId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(
            Optional.of(Member.create("shinseungyeol")));
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundLectureException.class,
            () -> lectureUserRegistrationService.register(memberId, lectureId));
    }
}