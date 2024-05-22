package org.mjulikelion.businessmessenger.exception;

import org.mjulikelion.businessmessenger.errorcode.ErrorCode;

public class ForbiddenException extends CustomException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
