package com.neche.ems.controller;

import com.neche.ems.entity.enums.Department;
import com.neche.ems.entity.enums.Gender;
import com.neche.ems.entity.enums.Rank;
import com.neche.ems.entity.model.Admin;
import com.neche.ems.entity.model.Attendance;
import com.neche.ems.entity.model.Employee;
import com.neche.ems.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    // Get all Employees

    @GetMapping
    public String listEmployees(Model model, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/signin"; // Redirect to sign in page if not authenticated
        }
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "all-employees";
    }

    @GetMapping("/create")
    public String createEmployeeForm(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("ranks", Rank.values());
        model.addAttribute("departments", Department.values());

        return "create-employee";

    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute("employee") Employee employee) {
        // Save the employee to the database
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("departments", Department.values());
        model.addAttribute("ranks", Rank.values());
        return "update-employee";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Long id, @ModelAttribute("employee") Employee employee) {
        Employee existingEmployee = employeeService.getEmployeeById(id);

        if (existingEmployee != null) {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setJobRank(employee.getJobRank());
            existingEmployee.setSalary(employee.getSalary());
//            existingEmployee.setPassword(employee.getPassword());

            employeeService.updateEmployee(existingEmployee);
        } else {
            // Handle case where employee with given ID is not found
            throw new RuntimeException("Employee not found with id: " + id);
        }
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }


    // Endpoints for Employee

    // Endpoint to show signin form
    @GetMapping("/signin")
    public String showEmployeeSignInPage(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/signin";
    }

    @PostMapping("/signin")
    public String signIn(@ModelAttribute("employee") Employee employee, HttpSession session) {
        Optional<Employee> foundEmployee = employeeService.findByEmail(employee.getEmail());
        if (foundEmployee.isPresent() && foundEmployee.get().getPassword().equals(employee.getPassword())) {
            // Store employee information in session
            session.setAttribute("employee", foundEmployee.get());
            return "redirect:/employees/welcome"; // Redirect to welcome page after successful sign in
        } else {
            return "redirect:/employees/signin"; // Redirect back to sign in page if authentication fails
        }
    }

    @GetMapping("/welcome")
    public String showWelcomePage(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee != null) {
            model.addAttribute("employee", employee);
            return "employee/welcome"; // Return the HTML file name for welcome page
        } else {
            return "redirect:/employees/signin"; // Redirect to sign in page if not logged in
        }
    }

    // Endpoint to sign user out
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Retrieve the current session, or create a new one if none exists
        if (session != null) {
            session.invalidate(); // Invalidate the session, effectively logging out the user
        }
        return "redirect:/employees/signin"; // Redirect to the sign-in page after logout
    }

}
