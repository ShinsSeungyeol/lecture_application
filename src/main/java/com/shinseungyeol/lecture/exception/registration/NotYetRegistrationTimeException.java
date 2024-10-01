package com.shinseungyeol.lecture.exception.registration;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotYetRegistrationTimeException extends RuntimeException {

    private final String message = "아직 수강 신청을 신청할 수 없습니다.";

}
