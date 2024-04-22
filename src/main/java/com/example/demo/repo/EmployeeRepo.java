package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employee;
import com.example.demo.entity.DepartmentEnum;

public interface EmployeeRepo extends JpaRepository<Employee,Integer>{
	Employee findByUsername(String username);
	List<Employee> findByDeptName(DepartmentEnum departmentEnum);
}
