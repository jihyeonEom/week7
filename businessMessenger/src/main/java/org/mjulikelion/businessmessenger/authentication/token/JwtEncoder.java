package org.mjulikelion.businessmessenger.authentication.token;

import org.mjulikelion.businessmessenger.errorcode.ErrorCode;
import org.mjulikelion.businessmessenger.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class JwtEncoder {
    private static final String TOKEN_TYPE = "Bearer ";

    public static String decodeJwtBearerToken(final String token) {
        String decodedToken = token.replace("+", " ");

        if (decodedToken.startsWith(TOKEN_TYPE)) {
            return decodedToken.substring(TOKEN_TYPE.length());
        }
        throw new NotFoundException(ErrorCode.TOKEN_NOT_FOUND);
    }

    public static String encodeJwtBearerToken(final String accessToken) {
        return URLEncoder.encode("Bearer " + accessToken, StandardCharsets.UTF_8);
    }
}
