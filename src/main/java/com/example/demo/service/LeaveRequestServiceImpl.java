// LeaveRequestServiceImpl.java
package com.example.demo.service;

import com.example.demo.entity.LeaveRequest;
import com.example.demo.entity.DepartmentEnum;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveHistory;
import com.example.demo.entity.LeaveStatus;
import com.example.demo.repo.LeaveHistoryRepository;
import com.example.demo.repo.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    
    @Autowired
    private LeaveHistoryRepository leaveHistoryRepository;
    
    @Override
    public List<LeaveRequest> getPendingRequests(Employee employee) {
        return leaveRequestRepository.findByEmployeeAndStatus(employee, LeaveStatus.PENDING);
    }

    @Override
    public List<LeaveRequest> getApprovedOrDeniedRequests(Employee employee) {
        return leaveRequestRepository.findByEmployeeAndStatusIn(employee, 
                List.of(LeaveStatus.APPROVED, LeaveStatus.DENIED));
    }

    @Override
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        // Set status to PENDING before saving
        leaveRequest.setStatus(LeaveStatus.PENDING);
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public LeaveRequest cancelLeaveRequest(int requestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId).orElse(null);
        if (leaveRequest != null) {
            leaveRequest.setStatus(LeaveStatus.CANCELED);
            return leaveRequestRepository.save(leaveRequest);
        }
        return null;
    }
    
    
    
    @Override
    public List<LeaveRequest> getPendingRequestsByDepartment(String department) {
        // Convert the department String to DepartmentEnum
        DepartmentEnum departmentEnum = DepartmentEnum.valueOf(department.toUpperCase());

        // Call the repository method with the converted department enum
        return leaveRequestRepository.findByDeptNameAndStatus(departmentEnum, LeaveStatus.PENDING);
    }


    @Override
    public LeaveRequest getRequestById(int requestId) {
        Optional<LeaveRequest> request = leaveRequestRepository.findById(requestId);
        return request.orElse(null);
    }

    @Override
    public LeaveRequest approveRequest(int requestId, String comments) {
        Optional<LeaveRequest> optionalRequest = leaveRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            LeaveRequest request = optionalRequest.get();
            request.setStatus(LeaveStatus.APPROVED);
            leaveRequestRepository.save(request);

            // Save the comments in LeaveHistory
            LeaveHistory history = new LeaveHistory();
            history.setLeaveRequest(request);
            history.setEmployee(request.getEmployee());
            history.setComments(comments);
            leaveHistoryRepository.save(history);

            return request;
        }
        return null;
    }

    @Override
    public LeaveRequest denyRequest(int requestId, String comments) {
        Optional<LeaveRequest> optionalRequest = leaveRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            LeaveRequest request = optionalRequest.get();
            request.setStatus(LeaveStatus.DENIED);
            leaveRequestRepository.save(request);

            // Save the comments in LeaveHistory
            LeaveHistory history = new LeaveHistory();
            history.setLeaveRequest(request);
            history.setEmployee(request.getEmployee());
            history.setComments(comments);
            leaveHistoryRepository.save(history);

            return request;
        }
        return null;
    }
    
}
