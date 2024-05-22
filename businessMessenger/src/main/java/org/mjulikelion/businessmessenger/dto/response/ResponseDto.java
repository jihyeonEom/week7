package org.mjulikelion.businessmessenger.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private final String statusCode; // HTTP 상태 코드
    private final String message; // API 요청 결과
    private final T data; // API 요청 결과 데이터

    // 반환 데이터가 없는 경우
    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String message) {
        return new ResponseDto<>(String.valueOf(statusCode.value()), message, null);
    }

    // 반환 데이터가 있는 경우
    public static <T> ResponseDto<T> res(final HttpStatusCode statusCode, final String message, final T data) {
        return new ResponseDto<>(String.valueOf(statusCode.value()), message, data);
    }
}