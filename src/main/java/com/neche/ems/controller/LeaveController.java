package com.neche.ems.controller;

import com.neche.ems.entity.enums.Approved;
import com.neche.ems.entity.model.Employee;
import com.neche.ems.entity.model.Leave;
import com.neche.ems.service.LeaveService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    LeaveService leaveService;

    @GetMapping("/apply")
    public String showApplyLeaveForm(Model model) {
        model.addAttribute("leave", new Leave());
        return "employee/apply-leave";
    }

    @PostMapping("/apply")
    public String applyLeave(@ModelAttribute("leave") Leave leave, HttpSession session) {

        // Retrieve logged-in employee from session
        Employee loggedInEmployee = (Employee) session.getAttribute("employee");

        // Check if logged-in employee exists in session
        if (loggedInEmployee == null) {
            // Redirect to sign-in page if employee is not logged in
            return "redirect:/signin";
        }
        // Associate logged-in employee with the leave using setEmployee method
        leave.setEmployee(loggedInEmployee);

        leave.setApprovalStatus(Approved.PENDING);
        leaveService.applyLeave(leave);
        return "redirect:/leave/apply";
    }


    @GetMapping("/requests")
    public String showLeaveRequests(Model model) {
        List<Leave> leaves = leaveService.getAllLeaves();
        model.addAttribute("leaves", leaves);
        return "leave-requests";
    }

    @GetMapping("/approve/{id}")
    public String getLeaveApprovalPage(@PathVariable Long id, Model model) {
        Leave leave = leaveService.getLeaveById(id);
        if (leave != null) {
            model.addAttribute("leave", leave);
            return "approve-request";
        } else {
            return "redirect:/leave/requests";
        }
    }


    @PostMapping("/update/{id}")
    public String updateLeave(@PathVariable("id") Long id, @ModelAttribute("leave") Leave leave) {
        Leave existingLeave = leaveService.getLeaveById(id);

        if (existingLeave != null) {
            existingLeave.setApprovalStatus(leave.getApprovalStatus());

            leaveService.updateLeave(existingLeave);
        } else {
            // Handle case where leave with given ID is not found
            throw new RuntimeException("Leave request not found with id: " + id);
        }
        return "redirect:/leave/requests";
    }

    @GetMapping("/view-applied-leaves")
    public String viewAppliedLeaves(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employees/signin"; // Redirect to sign in page if not authenticated
        }
        List<Leave> leaves = leaveService.findByEmployee(employee);
        model.addAttribute("leaves", leaves);
        return "employee/view-applied-leaves"; // Return the view-applied-leaves view
    }
}
