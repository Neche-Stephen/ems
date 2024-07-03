package com.neche.ems.service;

import com.neche.ems.entity.model.Employee;
import com.neche.ems.entity.model.Leave;

import java.util.List;

public interface LeaveService {

    Leave applyLeave(Leave leave);

    List<Leave> getAllLeaves();

    Leave getLeaveById(Long id);

    void updateLeave(Leave leave);

    List<Leave> findByEmployee(Employee employee);
}
