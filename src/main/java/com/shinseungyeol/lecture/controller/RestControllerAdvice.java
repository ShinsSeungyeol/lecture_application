package com.shinseungyeol.lecture.controller;

import com.shinseungyeol.lecture.exception.lecture.NotFoundLectureException;
import com.shinseungyeol.lecture.exception.member.NotFoundMemberException;
import com.shinseungyeol.lecture.exception.registration.ExpiredRegistrationTimeException;
import com.shinseungyeol.lecture.exception.registration.MaxRegistrationLimitExceededException;
import com.shinseungyeol.lecture.exception.registration.NotYetRegistrationTimeException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundMemberException.class, NotFoundLectureException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("404", ex.getMessage()));
    }

    @ExceptionHandler(value = {ExpiredRegistrationTimeException.class,
        MaxRegistrationLimitExceededException.class,
        NotYetRegistrationTimeException.class})
    public ResponseEntity<ErrorResponse> handleExpiredRegistrationException(
        ExpiredRegistrationTimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("400", ex.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("500", "에러가 발생했습니다."));
    }

}
