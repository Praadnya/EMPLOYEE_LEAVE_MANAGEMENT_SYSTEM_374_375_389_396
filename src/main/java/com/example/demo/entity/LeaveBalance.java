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
@Table(name = "LeaveBalance")
public class LeaveBalance{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bal_id;

    @ManyToOne
    @JoinColumn(name = "empID")
    private Employee employee;

    @Column
    private int sick_leave;

    @Column
    private int annual_leave;

    public int getSickLeave()
    {
        return sick_leave;
    }

    public int getAnnualLeave()
    {
        return annual_leave;
    }

    public void setSickLeave(int sick_leave)
    {
        this.sick_leave=sick_leave;
    }

    public void setAnnualLeave(int annual_leave)
    {
        this.annual_leave=annual_leave;
    }
    
}