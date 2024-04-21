package com.example.demo.factory;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Employee;
import com.example.demo.repo.LeaveBalanceRepository;
import com.example.demo.service.AnnualLeave;
import com.example.demo.service.LeaveTypeService;
import com.example.demo.service.SickLeave;

@Component
public class LeaveFactory {
    private final LeaveBalanceRepository leaveBalanceRepository;
    public LeaveFactory(LeaveBalanceRepository leaveBalanceRepository)
    {
        this.leaveBalanceRepository=leaveBalanceRepository;
    }
    public LeaveTypeService applyLeave(Employee employee,int days,String type)
    {
        if (type.equalsIgnoreCase("annual"))
        {
            return new AnnualLeave(leaveBalanceRepository);
        }
        else if(type.equalsIgnoreCase("sick"))
        {
            return new SickLeave(leaveBalanceRepository);
        }
        return null;
    }
    
}
