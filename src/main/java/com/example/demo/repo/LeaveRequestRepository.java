package com.example.demo.repo;

import com.example.demo.entity.LeaveRequest;
import com.example.demo.entity.DepartmentEnum;
import com.example.demo.entity.Employee;
import com.example.demo.entity.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    List<LeaveRequest> findByEmployeeAndStatus(Employee employee, LeaveStatus status);
    List<LeaveRequest> findByEmployeeAndStatusIn(Employee employee, List<LeaveStatus> statuses);
	List<LeaveRequest> findByDeptNameAndStatus(DepartmentEnum departmentEnum, LeaveStatus pending);
}

