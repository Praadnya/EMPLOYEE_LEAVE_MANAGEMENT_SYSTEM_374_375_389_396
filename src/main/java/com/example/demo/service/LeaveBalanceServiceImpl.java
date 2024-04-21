package com.example.demo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveBalance;
import com.example.demo.repo.LeaveBalanceRepository;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService{
    @Autowired LeaveBalanceRepository leaveBalanceRepository;

    @Override
    public List<LeaveBalance> getAllLeaveBalances(Employee employee){
        return leaveBalanceRepository.findByEmployee(employee);
    }
    
}
