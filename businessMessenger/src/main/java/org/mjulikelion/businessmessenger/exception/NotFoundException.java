package org.mjulikelion.businessmessenger.exception;

import org.mjulikelion.businessmessenger.errorcode.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
