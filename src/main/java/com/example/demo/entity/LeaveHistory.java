package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "LeaveHistory")
public class LeaveHistory {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int HistID;

    @ManyToOne
    @JoinColumn(name = "reqID")
    private LeaveRequest leaveRequest;

    @ManyToOne
    @JoinColumn(name = "empID")
    private Employee employee;

    @Column
    private String comments;

	public int getHistID() {
		return HistID;
	}

	public void setHistID(int histID) {
		HistID = histID;
	}

	public LeaveRequest getLeaveRequest() {
		return leaveRequest;
	}

	public void setLeaveRequest(LeaveRequest leaveRequest) {
		this.leaveRequest = leaveRequest;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
    
    
}
