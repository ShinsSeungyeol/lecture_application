package com.shinseungyeol.lecture.domain.registration;

import static org.mockito.Mockito.when;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.lecture.LectureRepository;
import com.shinseungyeol.lecture.domain.member.Member;
import com.shinseungyeol.lecture.domain.member.MemberRepository;
import com.shinseungyeol.lecture.exception.lecture.NotFoundLectureException;
import com.shinseungyeol.lecture.exception.member.NotFoundMemberException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberLectureRegistrationServiceTest {

    @InjectMocks
    private MemberLectureRegistrationService memberLectureRegistrationService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private MemberLectureRegistrationRepository memberLectureRegistrationRepository;

    @Mock
    private MemberLectureRegistrationFactory memberLectureRegistrationFactory;

    private Member member;

    private Lecture firstLecture;

    private Lecture secondLecture;

    private Lecture thirdLecture;

    @BeforeEach
    void init() {
        String memberName = "shinseungyeol";
        member = Member.create(memberName);

        String lectureName = "tdd";
        String speakerName = "bob";
        LocalDateTime registrationStartTime = LocalDateTime.now();
        LocalDateTime registrationEndTime = registrationStartTime.plusHours(1);
        LocalDateTime lectureStartTime = LocalDateTime.now().plusDays(1);
        LocalDateTime lectureEndTime = lectureStartTime.plusHours(1).plusHours(1);

        firstLecture = Lecture.create(
            lectureName, speakerName, registrationStartTime, registrationEndTime,
            lectureStartTime, lectureEndTime);

        lectureName = "opp";
        speakerName = "toby";
        secondLecture = Lecture.create(
            lectureName, speakerName, registrationStartTime, registrationEndTime,
            lectureStartTime, lectureEndTime);

        lectureName = "clean code";
        speakerName = "ken";
        thirdLecture = Lecture.create(lectureName, speakerName, registrationStartTime,
            registrationEndTime,
            lectureStartTime, lectureEndTime);
    }

    /**
     * 특강신청 정상 등록 테스트, 에러 없는 경우 테스트 통과
     */
    @Test
    public void 수강신청_정상_등록_테스트() {
        Long memberId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(
            Optional.of(member));

        Long lectureId = 1L;

        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(firstLecture));

        MemberLectureRegistration memberLectureRegistration = MemberLectureRegistration.create(
            firstLecture, member);

        when(memberLectureRegistrationFactory.newInstance(firstLecture, member)).thenReturn(
            memberLectureRegistration);

        memberLectureRegistrationService.register(memberId, lectureId);
    }

    @Test
    public void 수강신청_사용자_찾을_수_없는_경우_테스트() {
        Long memberId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundMemberException.class,
            () -> memberLectureRegistrationService.register(memberId, 1L));
    }

    @Test
    public void 수강신청_강의를_찾을_수_없는_경우_테스트() {
        Long memberId = 1L;
        Long lectureId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(
            Optional.of(Member.create("shinseungyeol")));
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundLectureException.class,
            () -> memberLectureRegistrationService.register(memberId, lectureId));
    }

    @Test
    public void 수강목록_정상_동작_테스트() {
        Long memberId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        MemberLectureRegistration first = MemberLectureRegistration.create(firstLecture, member);
        MemberLectureRegistration second = MemberLectureRegistration.create(secondLecture, member);
        MemberLectureRegistration third = MemberLectureRegistration.create(thirdLecture, member);

        when(memberLectureRegistrationRepository.findAllByMember(member)).thenReturn(
            List.of(first, second, third));

        List<MemberLectureRegistration> actualRegistrations = memberLectureRegistrationService.findAllByMemberId(
            memberId);

        Assertions.assertEquals(actualRegistrations.size(), 3);
        Assertions.assertEquals(actualRegistrations.get(0), first);
        Assertions.assertEquals(actualRegistrations.get(1), second);
        Assertions.assertEquals(actualRegistrations.get(2), third);

    }

    @Test
    public void 수강목록_사용자_찾을_수_없는_경우_테스트() {
        Long memberId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundMemberException.class,
            () -> memberLectureRegistrationService.findAllByMemberId(memberId));
    }
}