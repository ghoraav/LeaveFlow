package com.leaveflow.controller;

import com.leaveflow.model.LeaveRequest;
import com.leaveflow.model.User;
import com.leaveflow.service.LeaveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    /**
     * Helper method to check if a user is logged in.
     * @return The logged-in User, or null if not logged in.
     */
    private User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    /**
     * Main dashboard. Redirects user based on their role.
     */
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return "redirect:login"; // Not logged in
        }

        if ("ROLE_ADMIN".equals(user.getRole())) {
            return "redirect:admin/dashboard";
        } else {
            return "redirect:employee/dashboard";
        }
    }

    // --- Employee Routes ---

    /**
     * Shows the employee's personal dashboard with their past leave requests.
     */
    @GetMapping("/employee/dashboard")
    public String showEmployeeDashboard(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return "redirect:login";
        }

        List<LeaveRequest> requests = leaveService.getRequestsForEmployee(user.getId());
        model.addAttribute("requests", requests);
        model.addAttribute("username", user.getUsername());
        return "employee_dashboard"; // -> /WEB-INF/views/employee_dashboard.jsp
    }

    /**
     * Shows the form to apply for new leave.
     */
    @GetMapping("/apply")
    public String showApplyForm(HttpSession session, Model model) {
        if (getLoggedInUser(session) == null) {
            return "redirect:login";
        }
        // Add an empty request object to the model for the form
        model.addAttribute("leaveRequest", new LeaveRequest());
        return "apply_leave"; // -> /WEB-INF/views/apply_leave.jsp
    }

    /**
     * Processes the new leave application form.
     */
    @PostMapping("/apply")
    public String handleApplyForm(
            @ModelAttribute LeaveRequest leaveRequest,
            HttpSession session) {
        
        User user = getLoggedInUser(session);
        if (user == null) {
            return "redirect:login";
        }

        // The 'leaveRequest' object is automatically populated by Spring
        // from the form fields (e.g., startDate, endDate, reason, language)
        leaveService.applyForLeave(leaveRequest, user);

        return "redirect:employee/dashboard"; // Show success by redirecting
    }

    // --- Admin Routes ---

    /**
     * Shows the admin dashboard with all PENDING requests.
     */
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(HttpSession session, Model model) {
        User user = getLoggedInUser(session);
        if (user == null) {
            return "redirect:login";
        }
        if (!"ROLE_ADMIN".equals(user.getRole())) {
            return "redirect:dashboard"; // Not an admin
        }

        List<LeaveRequest> pendingRequests = leaveService.getAllPendingRequests();
        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("username", user.getUsername());
        return "admin_dashboard"; // -> /WEB-INF/views/admin_dashboard.jsp
    }

    /**
     * Approves a request.
     * We use @PathVariable to get the ID from the URL.
     */
    @PostMapping("/admin/approve/{requestId}")
    public String approveRequest(@PathVariable("requestId") int requestId, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null || !"ROLE_ADMIN".equals(user.getRole())) {
            return "redirect:login"; // Not an authorized admin
        }

        leaveService.approveRequest(requestId);
        return "redirect:../dashboard";
    }

    /**
     * Rejects a request.
     */
    @PostMapping("/admin/reject/{requestId}")
    public String rejectRequest(@PathVariable("requestId") int requestId, HttpSession session) {
        User user = getLoggedInUser(session);
        if (user == null || !"ROLE_ADMIN".equals(user.getRole())) {
            return "redirect:login"; // Not an authorized admin
        }

        leaveService.rejectRequest(requestId);
        return "redirect:../dashboard";
    }
}