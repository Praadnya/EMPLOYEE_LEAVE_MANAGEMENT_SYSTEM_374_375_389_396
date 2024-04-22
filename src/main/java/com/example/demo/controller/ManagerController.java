package com.example.demo.controller;

import com.example.demo.command.ApproveLeaveRequestCommand;
import com.example.demo.command.DenyLeaveRequestCommand;
import com.example.demo.entity.DepartmentEnum;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveBalance;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.entity.LeaveHistory;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.LeaveRequestService;
import com.example.demo.service.LeaveHistoryService;
import com.example.demo.service.LeaveBalanceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private final LeaveRequestService leaveRequestService;
    private final EmployeeService employeeService;
    private final LeaveHistoryService leaveHistoryService;
    private final LeaveBalanceService leaveBalanceService;

    public ManagerController(LeaveRequestService leaveRequestService, EmployeeService employeeService, LeaveHistoryService leaveHistoryService, LeaveBalanceService leaveBalanceService) {
        this.leaveRequestService = leaveRequestService;
        this.employeeService = employeeService;
        this.leaveHistoryService = leaveHistoryService;
        this.leaveBalanceService = leaveBalanceService;
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
        System.out.println("abs:"+pendingRequests);
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

    @GetMapping("/leave-history")
    public String getLeaveHistory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String managerUsername = authentication.getName();
        Employee manager = employeeService.findByUsername(managerUsername);
        if (manager == null || !manager.getIsManager()) {
            // Handle error: Manager not found or not authorized
            return "error"; // You can redirect to an error page
        }

        // Retrieve the manager's department name
        DepartmentEnum managerDepartment = manager.getDeptName();//List<String> usernames = leaveHistoryService.getPendingRequestsByDepartment("TECH");
        List<Employee> usernames = leaveHistoryService.getAllUsernames(managerDepartment);
        List<LeaveHistory> leaveHistoryAll = new ArrayList<LeaveHistory>();
        for (Employee string : usernames) {
            System.out.println("abc"+string.getName());
            Employee loggedInEmployee = employeeService.findByUsername(string.getUsername());
            List<LeaveHistory> leaveHistoryListEmp = leaveHistoryService.getAllLeaveHistories(loggedInEmployee);
            for (LeaveHistory leaveHistoryEmp : leaveHistoryListEmp) {
                System.out.println("xyz"+leaveHistoryEmp.getComments());
                leaveHistoryAll.add(leaveHistoryEmp);
            }

            model.addAttribute("leaveHistory", leaveHistoryAll);
        }
        return "leave-history-m";
    }

    @PostMapping("/generate-report")
    public String getReport(@RequestParam String username, Model model) {
        System.out.println(username);
        Employee loggedInEmployee = employeeService.findByUsername(username);
        List<LeaveHistory> leaveHistoryList = leaveHistoryService.getAllLeaveHistories(loggedInEmployee);
        List<LeaveBalance> leaveBalanceList = leaveBalanceService.getAllLeaveBalances(loggedInEmployee);
        model.addAttribute("leaveHistory", leaveHistoryList);
        model.addAttribute("leaveBalance", leaveBalanceList);
        return "view-report";
    }
}
