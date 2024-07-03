package com.neche.ems.repository;

import com.neche.ems.entity.model.Employee;
import com.neche.ems.entity.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployee(Employee employee);
}
