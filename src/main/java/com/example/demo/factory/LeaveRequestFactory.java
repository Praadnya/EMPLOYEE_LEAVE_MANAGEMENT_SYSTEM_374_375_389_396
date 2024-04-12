package com.example.demo.factory;

import java.sql.Date;

import com.example.demo.entity.DepartmentEnum;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.entity.LeaveStatus;

public interface LeaveRequestFactory {
    LeaveRequest createLeaveRequest(Employee employee, String reason, Date startDate, Date endDate, LeaveStatus status, String type, DepartmentEnum deptName);
}

