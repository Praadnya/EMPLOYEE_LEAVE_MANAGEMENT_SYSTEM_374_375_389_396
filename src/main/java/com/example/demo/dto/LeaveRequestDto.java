package com.example.demo.dto;

import java.sql.Date;

public class LeaveRequestDto {
    private String reason;
    private Date startDate;
    private Date endDate;
    private String type;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

    // Constructors, getters, and setters
    
}

