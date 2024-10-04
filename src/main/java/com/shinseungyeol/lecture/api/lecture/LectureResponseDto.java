package com.shinseungyeol.lecture.api.lecture;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import java.time.LocalDateTime;

public record LectureResponseDto(
    Long lectureId,
    String speakerName,
    String lectureName,
    LocalDateTime lectureStartAt,
    LocalDateTime lectureEndAt,
    LocalDateTime createAt
) {

    public static LectureResponseDto of(Lecture lecture) {
        LectureResponseDto dto = new LectureResponseDto(lecture.getId(), lecture.getSpeakerName(),
            lecture.getName(), lecture.getStartAt(), lecture.getEndAt(), lecture.getCreateAt());

        return dto;
    }

}
