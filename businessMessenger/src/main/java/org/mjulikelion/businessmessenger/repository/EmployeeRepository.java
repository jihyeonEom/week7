package org.mjulikelion.businessmessenger.repository;

import org.mjulikelion.businessmessenger.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    public Employee findByEmail(String email);
}
