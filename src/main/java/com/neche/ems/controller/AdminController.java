package com.neche.ems.controller;

import com.neche.ems.entity.model.Admin;
import com.neche.ems.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/signin")
    public String showSignInForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin-signin";
    }

    @PostMapping("/signin")
    public String signIn(@ModelAttribute("admin") Admin admin, HttpSession session) {
        Admin foundAdmin = adminService.findByEmail(admin.getEmail());
        if (foundAdmin != null && foundAdmin.getPassword().equals(admin.getPassword())) {
            session.setAttribute("admin", foundAdmin);
            return "redirect:/employees"; // Redirect to admin dashboard
        } else {
            return "redirect:/admin/signin"; // Redirect back to sign in page if authentication fails
        }
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin-signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute("admin") Admin admin) {
        adminService.save(admin);
        return "redirect:/admin/signin"; // Redirect to sign in page after successful sign up
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session, effectively logging out the admin
        return "redirect:/admin/signin"; // Redirect to the sign-in page after logout
    }

//    @GetMapping("/all-employees")
//    public String showDashboard(HttpSession session) {
//        Admin admin = (Admin) session.getAttribute("admin");
//        if (admin == null) {
//            return "redirect:/admin/signin"; // Redirect to sign in page if not authenticated
//        }
//        return "redirect:/employees";
//    }
}
