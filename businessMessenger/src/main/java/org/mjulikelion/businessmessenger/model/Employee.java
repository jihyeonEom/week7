package org.mjulikelion.businessmessenger.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mjulikelion.businessmessenger.dto.employee.EmployeeUpdateDto;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "employee")
public class Employee extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Message> messages;

    public void updateEmployee(EmployeeUpdateDto employeeUpdateDto) {
        this.name = employeeUpdateDto.getName();
        this.email = employeeUpdateDto.getEmail();
    }
}
