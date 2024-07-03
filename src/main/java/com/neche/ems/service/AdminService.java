package com.neche.ems.service;

import com.neche.ems.entity.model.Admin;

public interface AdminService {
    Admin findByEmail(String email);
    void save(Admin admin);
}
