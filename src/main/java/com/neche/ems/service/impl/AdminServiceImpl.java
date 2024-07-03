package com.neche.ems.service.impl;

import com.neche.ems.entity.model.Admin;
import com.neche.ems.repository.AdminRepository;
import com.neche.ems.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public void save(Admin admin) {
        adminRepository.save(admin);
    }
}
