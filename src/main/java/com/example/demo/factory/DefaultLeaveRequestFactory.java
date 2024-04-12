package com.example.demo.factory;

import java.sql.Date;

import org.springframework.stereotype.Component;

import com.example.demo.entity.DepartmentEnum;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.entity.LeaveStatus;

@Component // Assuming Spring managed bean
public class DefaultLeaveRequestFactory implements LeaveRequestFactory {
    @Override
    public LeaveRequest createLeaveRequest(Employee employee, String reason, Date startDate, Date endDate, LeaveStatus status, String type, DepartmentEnum deptName) {
        // Create and return a LeaveRequest instance
        LeaveRequest leaveRequest = new LeaveRequest();
        // Set properties
        leaveRequest.setEmployee(employee);
        leaveRequest.setReason(reason);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setStatus(status);
        leaveRequest.setType(type);
        leaveRequest.setDeptName(deptName);
        return leaveRequest;
    }
}
