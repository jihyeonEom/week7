package org.mjulikelion.businessmessenger.authentication.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.mjulikelion.businessmessenger.authentication.AuthenticationContext;
import org.mjulikelion.businessmessenger.authentication.AuthenticationExtractor;
import org.mjulikelion.businessmessenger.authentication.token.JwtTokenProvider;
import org.mjulikelion.businessmessenger.errorcode.ErrorCode;
import org.mjulikelion.businessmessenger.exception.NotFoundException;
import org.mjulikelion.businessmessenger.model.Employee;
import org.mjulikelion.businessmessenger.repository.EmployeeRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;
    private final EmployeeRepository employeeRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = AuthenticationExtractor.extractTokenFromRequest(request);
        UUID empId = UUID.fromString(jwtTokenProvider.getPayload(accessToken));
        Employee employee = this.employeeRepository.findById(empId).orElseThrow(() -> new NotFoundException(ErrorCode.EMPLOYEE_NOT_FOUND));
        authenticationContext.setPrincipal(employee);
        return true;
    }
}
