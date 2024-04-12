package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveHistory;

public interface LeaveHistoryService {
    List<LeaveHistory> getAllLeaveHistories(Employee employee);
    // Other service methods if needed
}

