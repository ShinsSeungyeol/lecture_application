package com.shinseungyeol.lecture.api.registration;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.member.Member;
import com.shinseungyeol.lecture.domain.registration.MemberLectureRegistration;
import java.time.LocalDateTime;

public record MemberLectureRegistrationResponseDto(
    Long memberId,
    Long lectureId,
    String speakerName,
    String lectureName,
    LocalDateTime lectureStartAt,
    LocalDateTime lectureEndAt,
    LocalDateTime registrationDt
) {

    public static MemberLectureRegistrationResponseDto of(
        MemberLectureRegistration memberLectureRegistration) {
        Lecture lecture = memberLectureRegistration.getLecture();
        Member member = memberLectureRegistration.getMember();

        return new MemberLectureRegistrationResponseDto(member.getId(), lecture.getId(),
            lecture.getSpeakerName(), lecture.getName(), lecture.getStartAt(), lecture.getEndAt(),
            memberLectureRegistration.getCreateAt());

    }


}
