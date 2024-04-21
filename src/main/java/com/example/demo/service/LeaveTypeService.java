package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;

@Service
public interface LeaveTypeService {
    String changeLeaveBalance(Employee employee,int days);
}
