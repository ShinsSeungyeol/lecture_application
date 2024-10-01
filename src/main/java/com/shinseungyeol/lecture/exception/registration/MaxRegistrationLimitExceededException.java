package com.shinseungyeol.lecture.exception.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaxRegistrationLimitExceededException extends RuntimeException {

    private String message = "수강 인원을 초과했습니다.";

}
