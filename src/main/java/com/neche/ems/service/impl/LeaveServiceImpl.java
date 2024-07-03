package com.neche.ems.service.impl;

import com.neche.ems.entity.model.Employee;
import com.neche.ems.entity.model.Leave;
import com.neche.ems.repository.LeaveRepository;
import com.neche.ems.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private final LeaveRepository leaveRepository;

    public Leave applyLeave(Leave leave) {
        return leaveRepository.save(leave);
    }

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public Leave getLeaveById(Long id) {
        return leaveRepository.findById(id).orElse(null);
    }

    public void updateLeave(Leave leave) {
        leaveRepository.save(leave);
    }

    public List<Leave> findByEmployee(Employee employee) {
        return leaveRepository.findByEmployee(employee);
    }

}
