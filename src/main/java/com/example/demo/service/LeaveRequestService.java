package com.example.demo.service;

import com.example.demo.entity.LeaveRequest;
import com.example.demo.entity.Employee;

import java.util.List;

public interface LeaveRequestService {
    List<LeaveRequest> getPendingRequests(Employee employee);
    List<LeaveRequest> getApprovedOrDeniedRequests(Employee employee);
    LeaveRequest createLeaveRequest(LeaveRequest leaveRequest);
    LeaveRequest cancelLeaveRequest(int requestId);
    
    List<LeaveRequest> getPendingRequestsByDepartment(String department);
    LeaveRequest getRequestById(int requestId);
    LeaveRequest approveRequest(int requestId, String comments);
    LeaveRequest denyRequest(int requestId, String comments);

}

