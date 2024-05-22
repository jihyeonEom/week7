package org.mjulikelion.businessmessenger.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.mjulikelion.businessmessenger.authentication.token.JwtEncoder;
import org.mjulikelion.businessmessenger.errorcode.ErrorCode;
import org.mjulikelion.businessmessenger.exception.NotFoundException;

public class AuthenticationExtractor {
    private static final String TOKEN_COOKIE_NAME = "AccessToken";

    public static String extractTokenFromRequest(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return JwtEncoder.decodeJwtBearerToken(cookie.getValue());
                }
            }
        }
        throw new NotFoundException(ErrorCode.TOKEN_NOT_FOUND);
    }
}

