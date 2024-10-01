package com.shinseungyeol.lecture.exception.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundMemberException extends RuntimeException {

    private String message = "사용자를 찾을 수 없습니다.";

}
