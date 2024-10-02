package com.shinseungyeol.lecture.api.registration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLectureRegistrationRequestDto {

    private Long memberId;

    private Long lectureId;

}
