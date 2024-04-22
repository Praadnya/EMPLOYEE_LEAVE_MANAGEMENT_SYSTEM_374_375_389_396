package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.DepartmentEnum;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveHistory;

public interface LeaveHistoryService {
    List<LeaveHistory> getAllLeaveHistories(Employee employee);
    List<Employee> getAllUsernames(DepartmentEnum deptname);
    // Other service methods if needed
}

