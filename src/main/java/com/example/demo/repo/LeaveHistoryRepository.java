package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveHistory;

public interface LeaveHistoryRepository extends JpaRepository<LeaveHistory, Integer>{

	List<LeaveHistory> findByEmployee(Employee employee);

}
