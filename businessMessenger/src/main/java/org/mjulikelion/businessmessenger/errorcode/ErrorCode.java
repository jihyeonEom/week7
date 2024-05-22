package org.mjulikelion.businessmessenger.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // 401 Unauthorized
    UNAUTHORIZED_EMPLOYEE("4010", "Unauthorized Employee"),

    // 403 Forbidden
    FORBIDDEN_EMPLOYEE("4030", "employee does not have access"),
    MESSAGE_CANNOT_BE_EDITED("4031", "message cannot be edited"),

    // 404 Not found
    EMPLOYEE_NOT_FOUND("4040", "employee not found"),
    MESSAGE_NOT_FOUND("4041", "message not found"),
    TOKEN_NOT_FOUND("4042", "token not found"),

    // 409 Conflict
    EMAIL_ALREADY_EXISTS("4090", "email already exists"),

    NOT_NULL("9001", "required value is null"),
    NOT_BLANK("9002", "required value is null or empty"),
    NOT_EMAIL("9002", "required value is not email pattern"),
    NOT_SIZE("9003", "required value is not fit in size");

    private final String code;
    private final String message;

    public static ErrorCode resolveValidationErrorCode(String code) {
        return switch (code) {
            case "NotNull" -> NOT_NULL;
            case "NotBlank" -> NOT_BLANK;
            case "Pattern" -> NOT_EMAIL;
            case "Size" -> NOT_SIZE;

            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }
}
