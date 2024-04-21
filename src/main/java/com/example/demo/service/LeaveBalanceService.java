package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveBalance;

public interface LeaveBalanceService {
List<LeaveBalance> getAllLeaveBalances(Employee employee);
}