package org.mjulikelion.businessmessenger.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.businessmessenger.authentication.employee.AuthenticatedEmployee;
import org.mjulikelion.businessmessenger.dto.employee.EmployeeCreateDto;
import org.mjulikelion.businessmessenger.dto.employee.EmployeeUpdateDto;
import org.mjulikelion.businessmessenger.dto.response.ResponseDto;
import org.mjulikelion.businessmessenger.dto.responsedata.employee.EmployeeResponseData;
import org.mjulikelion.businessmessenger.model.Employee;
import org.mjulikelion.businessmessenger.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


    // 직원 정보 수정하기
    @PatchMapping("/employees")
    public ResponseEntity<ResponseDto<Void>> updateEmployee(@Valid @RequestBody EmployeeUpdateDto employeeUpdateDto,
                                                            @AuthenticatedEmployee Employee employee) {
        this.employeeService.updateEmployee(employeeUpdateDto, employee);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "updated Employee"), HttpStatus.OK);
    }

    // 내 정보와 메시지함 조회하기
    @GetMapping("/employees")
    public ResponseEntity<ResponseDto<EmployeeResponseData>> getEmployeeInfo(@AuthenticatedEmployee Employee employee) {
        EmployeeResponseData employeeResponseData = this.employeeService.getEmployeeInfo(employee);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get Employee info", employeeResponseData), HttpStatus.OK);
    }

    // 탈퇴하기
    @DeleteMapping("/employees")
    public ResponseEntity<ResponseDto<Void>> deleteEmployee(@AuthenticatedEmployee Employee employee) {
        this.employeeService.deleteEmployee(employee);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "deleted Employee"), HttpStatus.OK);
    }

}
