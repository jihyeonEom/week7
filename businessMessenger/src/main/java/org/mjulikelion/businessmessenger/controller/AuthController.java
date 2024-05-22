package org.mjulikelion.businessmessenger.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.businessmessenger.dto.employee.EmployeeCreateDto;
import org.mjulikelion.businessmessenger.dto.login.LoginDto;
import org.mjulikelion.businessmessenger.dto.response.ResponseDto;
import org.mjulikelion.businessmessenger.model.Employee;
import org.mjulikelion.businessmessenger.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final EmployeeService employeeService;

    // 회원 가입
    @PostMapping("/auth/sign-in")
    public ResponseEntity<ResponseDto<Void>> createEmployee(@Valid @RequestBody EmployeeCreateDto employeeCreateDto) {
        this.employeeService.createEmployee(employeeCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "created Employee"), HttpStatus.CREATED);
    }

    // 로그인
    @GetMapping("/auth/login")
    public ResponseEntity<ResponseDto<Void>> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        Employee employee = this.employeeService.validateEmployeeByLoginDto(loginDto);

        this.employeeService.login(employee, response);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "login"), HttpStatus.OK);
    }

    // 로그아웃
    @GetMapping("/auth/logout")
    public ResponseEntity<ResponseDto<Void>> logout(HttpServletResponse response) {
        this.employeeService.logout(response);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "logout"), HttpStatus.OK);
    }
}
