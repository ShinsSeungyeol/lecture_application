package com.shinseungyeol.lecture.api.registration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shinseungyeol.lecture.domain.lecture.Lecture;
import com.shinseungyeol.lecture.domain.member.Member;
import com.shinseungyeol.lecture.domain.registration.MemberLectureRegistration;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberLectureRegistrationResponseDto {

    private Long memberId;
    private Long lectureId;
    private String speakerName;
    private String lectureName;
    private LocalDateTime lectureStartTime;
    private LocalDateTime lectureEndTime;
    private LocalDateTime createDt;

    public static MemberLectureRegistrationResponseDto of(
        MemberLectureRegistration memberLectureRegistration) {
        Lecture lecture = memberLectureRegistration.getLecture();
        Member member = memberLectureRegistration.getMember();

        MemberLectureRegistrationResponseDto dto = new MemberLectureRegistrationResponseDto();
        dto.setMemberId(member.getId());
        dto.setLectureId(lecture.getId());
        dto.setSpeakerName(lecture.getSpeakerName());
        dto.setLectureName(lecture.getName());
        dto.setLectureStartTime(lecture.getStartTime());
        dto.setLectureEndTime(lecture.getEndTime());
        dto.setCreateDt(memberLectureRegistration.getCreateDt());

        return dto;
    }

}
