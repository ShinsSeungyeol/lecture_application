package com.shinseungyeol.lecture.exception.lecture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundLectureException extends RuntimeException {

    private String message = "강의를 찾을 수 없습니다.";
}
