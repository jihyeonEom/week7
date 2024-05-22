package org.mjulikelion.businessmessenger.exception;

import org.mjulikelion.businessmessenger.errorcode.ErrorCode;

public class ConflictException extends CustomException {
    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
