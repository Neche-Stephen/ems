package com.neche.ems.service;

import com.neche.ems.entity.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee saveEmployee(Employee employee);

    Employee getEmployeeById(Long id) ;

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long id) ;

    Optional<Employee> findByEmail(String email);
}
