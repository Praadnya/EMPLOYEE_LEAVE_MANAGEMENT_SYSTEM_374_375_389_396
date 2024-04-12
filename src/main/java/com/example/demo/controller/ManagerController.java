package com.example.demo.controller;

import com.example.demo.command.ApproveLeaveRequestCommand;
import com.example.demo.command.DenyLeaveRequestCommand;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.LeaveRequestService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private final LeaveRequestService leaveRequestService;
    private final EmployeeService employeeService;

    public ManagerController(LeaveRequestService leaveRequestService, EmployeeService employeeService) {
        this.leaveRequestService = leaveRequestService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String showPendingRequests(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String managerUsername = authentication.getName();
        Employee manager = employeeService.findByUsername(managerUsername);

        if (manager == null || !manager.getIsManager()) {
            // Handle error: Manager not found or not authorized
            return "error"; // You can redirect to an error page
        }

        // Retrieve the manager's department name
        String managerDepartment = manager.getDeptName().toString();

        // Retrieve all pending leave requests for the manager's department
        List<LeaveRequest> pendingRequests = leaveRequestService.getPendingRequestsByDepartment(managerDepartment);

        model.addAttribute("pendingRequests", pendingRequests);
        return "manager_dashboard";
    }

    @GetMapping("/request/{reqId}")
    public String showRequestDetails(@PathVariable int reqId, Model model) {
        LeaveRequest request = leaveRequestService.getRequestById(reqId);
        model.addAttribute("request", request);
        return "request_details";
    }

    @PostMapping("/request/{reqId}/approve")
    public String approveRequest(@PathVariable int reqId, @RequestParam String comments) {
        ApproveLeaveRequestCommand command = new ApproveLeaveRequestCommand(leaveRequestService, reqId, comments);
        command.execute();
        return "redirect:/manager/";
    }

    @PostMapping("/request/{reqId}/deny")
    public String denyRequest(@PathVariable int reqId, @RequestParam String comments) {
        DenyLeaveRequestCommand command = new DenyLeaveRequestCommand(leaveRequestService, reqId, comments);
        command.execute();
        return "redirect:/manager/";
    }
}
