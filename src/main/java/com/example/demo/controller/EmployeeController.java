package com.example.demo.controller;

import com.example.demo.dto.LeaveRequestDto;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveHistory;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.entity.LeaveStatus;
import com.example.demo.factory.LeaveRequestFactory;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.LeaveHistoryService;
import com.example.demo.service.LeaveRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LeaveRequestService leaveRequestService;
    @Autowired
    private final LeaveHistoryService leaveHistoryService;
    private final LeaveRequestFactory leaveRequestFactory;

    public EmployeeController(EmployeeService employeeService, LeaveRequestService leaveRequestService, LeaveHistoryService leaveHistoryService, LeaveRequestFactory leaveRequestFactory) {
        this.employeeService = employeeService;
        this.leaveRequestService = leaveRequestService;
        this.leaveHistoryService = leaveHistoryService;
        this.leaveRequestFactory = leaveRequestFactory;
    }
    @GetMapping("/pending-requests")
    public String getPendingRequests(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee loggedInEmployee = employeeService.findByUsername(username);
        List<LeaveRequest> pendingRequests = leaveRequestService.getPendingRequests(loggedInEmployee);
        model.addAttribute("pendingRequests", pendingRequests);
        return "pending-requests";
    }
    
    @GetMapping("/create-request")
    public String showCreateRequestForm(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee loggedInEmployee = employeeService.findByUsername(username);
        String departmentName = loggedInEmployee.getDeptName().toString();
        System.out.println("Department Name: " + departmentName);
        model.addAttribute("departmentName", departmentName);
        model.addAttribute("leaveRequest", new LeaveRequest());
        return "create-request";
    }

    @PostMapping("/create-request")
    public String createLeaveRequest(@ModelAttribute LeaveRequestDto leaveRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee loggedInEmployee = employeeService.findByUsername(username);
        
        // Use the factory to create the LeaveRequest instance
        LeaveRequest leaveRequest = leaveRequestFactory.createLeaveRequest(loggedInEmployee, leaveRequestDto.getReason(), leaveRequestDto.getStartDate(), leaveRequestDto.getEndDate(), LeaveStatus.PENDING, leaveRequestDto.getType(), loggedInEmployee.getDeptName());
        
        leaveRequestService.createLeaveRequest(leaveRequest);
        return "redirect:/employee/pending-requests";
    }

//    @PostMapping("/create-request")
//    public String createLeaveRequest(@ModelAttribute LeaveRequest leaveRequest) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Employee loggedInEmployee = employeeService.findByUsername(username);
//        leaveRequest.setEmployee(loggedInEmployee);
//        leaveRequestService.createLeaveRequest(leaveRequest);
//        return "redirect:/employee/pending-requests";
//    }


    @GetMapping("/leave-history")
    public String getLeaveHistory(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee loggedInEmployee = employeeService.findByUsername(username);
        List<LeaveHistory> leaveHistoryList = leaveHistoryService.getAllLeaveHistories(loggedInEmployee);
        model.addAttribute("leaveHistory", leaveHistoryList);
        return "leave-history";
    }

    
    @PostMapping("/cancel-request/{requestId}")
    public String cancelLeaveRequest(@PathVariable int requestId) {
        leaveRequestService.cancelLeaveRequest(requestId);
        return "redirect:/employee/pending-requests";
    }
}
