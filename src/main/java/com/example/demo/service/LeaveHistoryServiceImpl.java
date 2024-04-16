package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveHistory;
import com.example.demo.repo.LeaveHistoryRepository;

@Service
public class LeaveHistoryServiceImpl implements LeaveHistoryService {

    @Autowired
    private LeaveHistoryRepository leaveHistoryRepository;

    @Override
    public List<LeaveHistory> getAllLeaveHistories(Employee employee) {
        return leaveHistoryRepository.findByEmployee(employee);
    }

	    

}

