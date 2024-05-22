package org.mjulikelion.businessmessenger.authentication;

import lombok.Getter;
import lombok.Setter;
import org.mjulikelion.businessmessenger.model.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class AuthenticationContext {
    private Employee principal;
}
