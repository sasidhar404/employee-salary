package com.example.demo.Model;

import java.util.Date;
import java.util.List;

import com.example.demo.Controller.DTO.TaxDeduction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name="Employee")
public class Employee {
    @Id
    @NotNull(message = "Employee ID is mandatory")
    private Long employeeId;        
    @NotBlank(message = "First name is mandatory")
    private String firstName;         
    @NotBlank(message = "Last name is mandatory")
    private String lastName;        
    @NotBlank(message = "Email is mandatory")
    private String email;       
    @NotNull(message = "Phone number is mandatory")
    private List<String> phoneNumbers;         
   // @NotBlank(message = "Date of joining is mandatory")
    private Date doj;         
    @NotNull(message = "Salary is mandatory")
    private Double salary;
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public Date getDoj() {
		return doj;
	}
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
				+ email + ", phoneNumbers=" + phoneNumbers + ", doj=" + doj + ", salary=" + salary + "]";
	} 
    
    
  
		
}
