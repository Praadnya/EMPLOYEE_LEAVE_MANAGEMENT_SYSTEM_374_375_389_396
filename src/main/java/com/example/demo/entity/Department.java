package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Department")
public class Department {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deptID;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private DepartmentEnum deptName;

    @Column(nullable = false)
    private int size;

    @ManyToOne
    @JoinColumn(name = "managerID")
    private Employee manager;

	public int getDeptID() {
		return deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

	public DepartmentEnum getDeptName() {
		return deptName;
	}

	public void setDeptName(DepartmentEnum deptName) {
		this.deptName = deptName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	
}

