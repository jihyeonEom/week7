package org.mjulikelion.businessmessenger.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.mjulikelion.businessmessenger.dto.response.ErrorResponseDto;
import org.mjulikelion.businessmessenger.errorcode.ErrorCode;
import org.mjulikelion.businessmessenger.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    // UnauthorizedException 핸들러
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedException(UnauthorizedException unauthorizedException) {
        this.writeLog(unauthorizedException);
        return new ResponseEntity<>(ErrorResponseDto.res(unauthorizedException), HttpStatus.UNAUTHORIZED);
    }

    // ForbiddenException 핸들러
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDto> handleForbiddenException(ForbiddenException forbiddenException) {
        this.writeLog(forbiddenException);
        return new ResponseEntity<>(ErrorResponseDto.res(forbiddenException), HttpStatus.FORBIDDEN);
    }

    //NotFoundException 핸들러
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException notFoundException) {
        this.writeLog(notFoundException);
        return new ResponseEntity<>(ErrorResponseDto.res(notFoundException), HttpStatus.NOT_FOUND);
    }

    // ConflictException 핸들러
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflictException(ConflictException conflictException) {
        this.writeLog(conflictException);
        return new ResponseEntity<>(ErrorResponseDto.res(conflictException), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        if (fieldError == null) {
            return new ResponseEntity<>(ErrorResponseDto.res(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    methodArgumentNotValidException), HttpStatus.BAD_REQUEST);
        }
        ErrorCode validationErrorCode = ErrorCode.resolveValidationErrorCode(fieldError.getCode());
        String detail = fieldError.getDefaultMessage();
        DtoValidationException dtoValidationException = new DtoValidationException(validationErrorCode, detail);
        this.writeLog(dtoValidationException);
        return new ResponseEntity<>(ErrorResponseDto.res(dtoValidationException), HttpStatus.BAD_REQUEST);
    }

    private void writeLog(CustomException customException) {
        String exceptionName = customException.getClass().getSimpleName();
        ErrorCode errorCode = customException.getErrorCode();
        String detail = customException.getDetail();

        log.error("({}){}: {}", exceptionName, errorCode.getMessage(), detail);
    }
}
