package com.neche.ems.controller;

import com.neche.ems.entity.model.Attendance;
import com.neche.ems.entity.model.Employee;
import com.neche.ems.service.AttendanceService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    // Endpoint to show mark attendance form
    @GetMapping
    public String showMarkAttendancePage(Model model) {
        System.out.println("attendance get called");
        model.addAttribute("attendance", new Attendance());
        return "employee/mark-attendance";
    }

    @PostMapping("/mark")
    public String submitAttendance(@ModelAttribute("attendance") Attendance attendance, HttpSession session) {
        // Retrieve logged-in employee from session
        Employee loggedInEmployee = (Employee) session.getAttribute("employee");

        // Check if logged-in employee exists in session
        if (loggedInEmployee == null) {
            // Redirect to sign-in page if employee is not logged in
            return "redirect:/employees/signin";
        }

        // Associate logged-in employee with the attendance using setEmployee method
        attendance.setEmployee(loggedInEmployee);

        // Save the attendance
        attendanceService.saveAttendance(attendance);

        return "redirect:/employees/welcome";
    }


    @GetMapping("/analytics")
    public String showAttendanceAnalytics(Model model) {
        List<Attendance> attendanceList = attendanceService.getAllAttendances();

//        attendances.forEach(attendance -> logger.info("Attendance: {}, Employee: {}", attendance.getId(), attendance.getEmployee().getFirstName()));
        // attendanceList.forEach(attendance -> System.out.println("Attendance "+  attendance ));
      //  attendanceList.forEach(attendance -> System.out.println("Attendance ID " + attendance.getId() ));
      //  attendanceList.forEach(attendance -> System.out.println("Employee name " + attendance.getEmployee().getFirstName() ));



        // Group attendance records by date
        Map<LocalDate, List<Attendance>> attendanceByDate = attendanceList.stream()
                .collect(Collectors.groupingBy(attendance -> attendance.getCreatedAt().toLocalDate()));

        model.addAttribute("attendanceByDate", attendanceByDate);

        return "attendance-analytics";
    }

}
