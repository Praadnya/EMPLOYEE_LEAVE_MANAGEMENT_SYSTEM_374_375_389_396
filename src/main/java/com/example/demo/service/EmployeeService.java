package com.example.demo.service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repo.EmployeeRepo;

@Service
public class EmployeeService implements UserDetailsService {
    private final EmployeeRepo employeeRepository;

    public EmployeeService(EmployeeRepo employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        // Compare plain text passwords during login
        return User.withUsername(employee.getUsername())
                .password(employee.getPassword())
                .roles(employee.getIsManager() ? "MANAGER" : "EMPLOYEE")
                .build();
    }
    
    public Employee findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

	
}
