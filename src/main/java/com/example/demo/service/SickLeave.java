package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveBalance;
import com.example.demo.repo.LeaveBalanceRepository;

@Service
public class SickLeave implements LeaveTypeService {
    private final LeaveBalanceRepository leaveBalanceRepository;
    public SickLeave(LeaveBalanceRepository leaveBalanceRepository)
    {
        this.leaveBalanceRepository=leaveBalanceRepository;
    }

    @Override
    public String changeLeaveBalance(Employee employee,int days)
    {
        int empid=employee.getEmpID();
        Optional<LeaveBalance> optionalBalance = leaveBalanceRepository.findById(empid);
        LeaveBalance leaveBalance=optionalBalance.get();
        int curr=leaveBalance.getSickLeave();
        if(curr-days<0)
        {
            return "invalid";
        }
        leaveBalance.setSickLeave(curr-days);
        leaveBalanceRepository.save(leaveBalance); 
        return "valid";
    }

    
}
