package org.mjulikelion.businessmessenger.exception;

import org.mjulikelion.businessmessenger.errorcode.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
