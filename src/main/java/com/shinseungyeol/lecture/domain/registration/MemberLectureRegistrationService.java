package com.shinseungyeol.lecture.domain.registration;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.lecture.LectureRepository;
import com.shinseungyeol.lecture.domain.member.Member;
import com.shinseungyeol.lecture.domain.member.MemberRepository;
import com.shinseungyeol.lecture.exception.lecture.NotFoundLectureException;
import com.shinseungyeol.lecture.exception.member.NotFoundMemberException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberLectureRegistrationService {

    final MemberRepository memberRepository;
    final LectureRepository lectureRepository;
    final MemberLectureRegistrationRepository memberLectureRegistrationRepository;

    final MemberLectureRegistrationFactory memberLectureRegistrationFactory;


    /**
     * 사용자 강의 등록
     *
     * @param memberId
     * @param lectureId
     */
    public void register(Long memberId, Long lectureId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundMemberException());

        Lecture lecture = lectureRepository.findById(lectureId)
            .orElseThrow(() -> new NotFoundLectureException());

        MemberLectureRegistration memberLectureRegistration = memberLectureRegistrationFactory.newInstance(
            lecture, member);

        memberLectureRegistrationRepository.save(memberLectureRegistration);

    }


    /**
     * 사용자가 등록 신청 내역 목록 반환
     *
     * @param memberId
     * @return
     */
    public List<MemberLectureRegistration> findAllByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundMemberException());

        return memberLectureRegistrationRepository.findAllByMember(member);

    }

}
