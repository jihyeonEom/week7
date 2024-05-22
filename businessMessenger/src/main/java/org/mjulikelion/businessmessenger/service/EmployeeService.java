package org.mjulikelion.businessmessenger.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.mjulikelion.businessmessenger.authentication.PasswordHashEncryption;
import org.mjulikelion.businessmessenger.authentication.token.JwtEncoder;
import org.mjulikelion.businessmessenger.authentication.token.JwtTokenProvider;
import org.mjulikelion.businessmessenger.dto.employee.EmployeeCreateDto;
import org.mjulikelion.businessmessenger.dto.employee.EmployeeUpdateDto;
import org.mjulikelion.businessmessenger.dto.login.LoginDto;
import org.mjulikelion.businessmessenger.dto.responsedata.employee.EmployeeResponseData;
import org.mjulikelion.businessmessenger.dto.responsedata.message.MessageResponseData;
import org.mjulikelion.businessmessenger.errorcode.ErrorCode;
import org.mjulikelion.businessmessenger.exception.ConflictException;
import org.mjulikelion.businessmessenger.exception.NotFoundException;
import org.mjulikelion.businessmessenger.exception.UnauthorizedException;
import org.mjulikelion.businessmessenger.model.Employee;
import org.mjulikelion.businessmessenger.model.Message;
import org.mjulikelion.businessmessenger.repository.EmployeeRepository;
import org.mjulikelion.businessmessenger.repository.MessageRepository;
import org.mjulikelion.businessmessenger.status.Status;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MessageRepository messageRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordHashEncryption passwordHashEncryption;

    // 회원 가입
    public void createEmployee(EmployeeCreateDto employeeCreateDto) {
        this.isEmailExist(employeeCreateDto.getEmail()); // 이매일 중복 검사
        String encryptedPassword = this.passwordHashEncryption.encrypt(employeeCreateDto.getPassword());

        List<Message> messages = new ArrayList<>();
        Employee employee = Employee.builder()
                .name(employeeCreateDto.getName())
                .email(employeeCreateDto.getEmail())
                .password(encryptedPassword)
                .messages(messages)
                .build();
        this.employeeRepository.save(employee);
    }

    // 이메일 중복 검사
    public void isEmailExist(String email) {
        Employee employee = this.employeeRepository.findByEmail(email);
        if (employee != null) {
            throw new ConflictException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    // 로그인 정보로 유저 검증하기
    public Employee validateEmployeeByLoginDto(LoginDto loginDto) {
        Employee employee = this.employeeRepository.findByEmail(loginDto.getEmail());
        if(employee == null) {
            throw new NotFoundException(ErrorCode.EMPLOYEE_NOT_FOUND);
        }
        if (!passwordHashEncryption.matches(loginDto.getPassword(), employee.getPassword())) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_EMPLOYEE);
        }

        return employee;
    }

    // 로그인
    public void login(Employee employee, HttpServletResponse response) {
        String payload = employee.getId().toString();
        String accessToken = jwtTokenProvider.createToken(payload);
        ResponseCookie cookie = ResponseCookie.from("AccessToken", JwtEncoder.encodeJwtBearerToken(accessToken))
                .maxAge(Duration.ofMillis(1800000))
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    // 로그아웃
    public void logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("AccessToken", null)
                .maxAge(0)
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    // 직원 정보 수정하기
    public void updateEmployee(EmployeeUpdateDto employeeUpdateDto, Employee employee) {
        employee.updateEmployee(employeeUpdateDto);
        this.employeeRepository.save(employee);
    }

    // 내 정보와 메시지함 조회하기
    public EmployeeResponseData getEmployeeInfo(Employee employee) {
        List<Message> messages = employee.getMessages();
        List<MessageResponseData> messageResponseDataList = new ArrayList<>(); // 받은 메시지함

        for (Message message : messages) {
            MessageResponseData messageResponseData = MessageResponseData.builder()
                    .id(message.getId())
                    .content(message.getContent())
                    .senderEmail(message.getSenderEmail())
                    .receiverEmail(employee.getEmail())
                    .sentAt(message.getCreatedAt())
                    .build();
            message.setStatus(Status.READ); // 메시지함을 조회해서 메시지를 확인했으므로 READ로 상태를 바꾼다.
            this.messageRepository.save(message);
            messageResponseDataList.add(messageResponseData);
        }

        return EmployeeResponseData.builder()
                .name(employee.getName())
                .email(employee.getEmail())
                .messages(messageResponseDataList)
                .build();
    }

    // 탈퇴하기
    public void deleteEmployee(Employee employee) {
        this.employeeRepository.delete(employee);
    }
}
