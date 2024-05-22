package org.mjulikelion.businessmessenger.exception;

import org.mjulikelion.businessmessenger.errorcode.ErrorCode;

public class DtoValidationException extends CustomException {
    public DtoValidationException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
