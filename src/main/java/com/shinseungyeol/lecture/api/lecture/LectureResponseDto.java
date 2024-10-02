package com.shinseungyeol.lecture.api.lecture;

import com.shinseungyeol.lecture.domain.lecture.Lecture;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureResponseDto {

    private Long lectureId;
    private String speakerName;
    private String lectureName;
    private LocalDateTime lectureStartAt;
    private LocalDateTime lectureEndAt;
    private LocalDateTime createAt;

    public static LectureResponseDto of(Lecture lecture) {
        LectureResponseDto dto = new LectureResponseDto();
        dto.setLectureId(lecture.getId());
        dto.setSpeakerName(lecture.getSpeakerName());
        dto.setLectureName(lecture.getName());
        dto.setLectureStartAt(lecture.getStartAt());
        dto.setLectureEndAt(lecture.getEndAt());

        return dto;
    }

}
