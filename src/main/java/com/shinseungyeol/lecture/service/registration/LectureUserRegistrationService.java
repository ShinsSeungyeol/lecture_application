package com.shinseungyeol.lecture.service.registration;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.member.Member;
import com.shinseungyeol.lecture.domain.registration.MemberLectureRegistration;
import com.shinseungyeol.lecture.domain.registration.MemberLectureRegistrationFactory;
import com.shinseungyeol.lecture.exception.lecture.NotFoundLectureException;
import com.shinseungyeol.lecture.exception.member.NotFoundMemberException;
import com.shinseungyeol.lecture.infrastructure.lecture.LectureRepository;
import com.shinseungyeol.lecture.infrastructure.member.MemberRepository;
import com.shinseungyeol.lecture.infrastructure.registration.MemberLectureRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureUserRegistrationService {

    final MemberRepository memberRepository;
    final LectureRepository lectureRepository;
    final MemberLectureRegistrationRepository memberLectureRegistrationRepository;

    final MemberLectureRegistrationFactory memberLectureRegistrationFactory;


    public void register(Long memberId, Long lectureId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundMemberException());

        Lecture lecture = lectureRepository.findById(lectureId)
            .orElseThrow(() -> new NotFoundLectureException());

        MemberLectureRegistration memberLectureRegistration = memberLectureRegistrationFactory.newInstance(
            lecture, member);

        memberLectureRegistrationRepository.save(memberLectureRegistration);

    }

}
