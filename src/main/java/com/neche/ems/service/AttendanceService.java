package com.neche.ems.service;

import com.neche.ems.entity.model.Attendance;

import java.util.List;

public interface AttendanceService {

    Attendance saveAttendance(Attendance attendance);

    List<Attendance> getAllAttendances();
}
