package com.shinseungyeol.lecture.exception.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpiredRegistrationTimeException extends RuntimeException {

    private String message = "수강 신청 기간이 지났습니다.";

}
