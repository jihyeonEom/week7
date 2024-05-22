package org.mjulikelion.businessmessenger.authentication.employee;

import lombok.RequiredArgsConstructor;
import org.mjulikelion.businessmessenger.authentication.AuthenticationContext;
import org.mjulikelion.businessmessenger.model.Employee;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticatedEmployeeArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthenticationContext authenticationContext;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedEmployee.class);
    }

    @Override
    public Employee resolveArgument(final MethodParameter parameter,
                                    final ModelAndViewContainer mavContainer,
                                    final NativeWebRequest webRequest,
                                    final WebDataBinderFactory binderFactory) {
        return authenticationContext.getPrincipal();
    }
}
