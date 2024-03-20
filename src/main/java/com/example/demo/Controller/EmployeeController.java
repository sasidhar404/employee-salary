package com.example.demo.Controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Controller.DTO.TaxDeduction;
import com.example.demo.Model.Employee;
import com.example.demo.Repository.EmployeeRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
@Validated
public class EmployeeController {
	

    @Autowired
    private EmployeeRepository emplRepo;
	@PostMapping("/employees")
	public ResponseEntity<String> addEmployee(@Valid @RequestBody Employee employee) {  
		emplRepo.save(employee);
		return ResponseEntity.ok("Employee details added successfully"); 
		}
	@GetMapping("/employees/{id}")
	public TaxDeduction calculateTaxDeduction(@PathVariable Long id) {
		Employee employee = emplRepo.findByEmployeeId(id);
		
	        // Get current date
		if(employee != null) {
		Date currentDate=new Date();
	 
	        // Check if employee joined in the current financial year
	        if (employee.getDoj().after(getFinancialYearStartDate(currentDate))) {
	            // Calculate total months worked in the financial year
	            int monthsWorked = calculateMonthsWorked(employee.getDoj(), currentDate);
	 
	            // Calculate total salary for the financial year
	            double totalSalary = employee.getSalary() * monthsWorked / 12.0;
	 
	            // Calculate tax amount based on tax slabs
	            double taxAmount = calculateTaxAmount(totalSalary);
	 
	            // Calculate cess amount if applicable
	            double cessAmount = totalSalary > 2500000 ? (totalSalary - 2500000) * 0.02 : 0;
	 
	            // Return tax deduction details
	            return new TaxDeduction(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
	                                    totalSalary, taxAmount, cessAmount);
	        } else {
	            // Employee joined before the current financial year, return 0 tax deduction
	            return new TaxDeduction(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
	                                    0, 0, 0);
	        }
		}
		 return new TaxDeduction(null, null, null, 0, 0, 0);
		
	    }
	
	private Date getFinancialYearStartDate(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
 
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
 
        // If the current month is April (4th month) or later, the financial year started in the current year
        if (currentMonth >= Calendar.APRIL) {
            calendar.set(currentYear, Calendar.APRIL, 1);
        } else {
            // If the current month is earlier than April, the financial year started in the previous year
            calendar.set(currentYear - 1, Calendar.APRIL, 1);
        }
 
        return calendar.getTime();
    }

	private double calculateTaxAmount(double totalSalary) {
        // Tax slabs
        double tax = 0;
        if (totalSalary > 1000000) {
            tax += (totalSalary - 1000000) * 0.2; // 20% Tax
            totalSalary = 1000000;
        }
        if (totalSalary > 500000) {
            tax += (totalSalary - 500000) * 0.1; // 10% Tax
            totalSalary = 500000;
        }
        if (totalSalary > 250000) {
            tax += (totalSalary - 250000) * 0.05; // 5% Tax
        }
        return tax;
    }

	private int calculateMonthsWorked(Date doj, Date currentDate) {
	        Calendar dojCalendar = Calendar.getInstance();
	        dojCalendar.setTime(doj);
	        
	        Calendar currentCalendar = Calendar.getInstance();
	        currentCalendar.setTime(currentDate);
	        
	        int dojYear = dojCalendar.get(Calendar.YEAR);
	        int dojMonth = dojCalendar.get(Calendar.MONTH);
	        
	        int currentYear = currentCalendar.get(Calendar.YEAR);
	        int currentMonth = currentCalendar.get(Calendar.MONTH);
	        
	        int monthsWorked = 0;
	        
	        if (currentYear > dojYear || (currentYear == dojYear && currentMonth >= dojMonth)) {
	            // Calculate the difference in years and months
	            int diffYears = currentYear - dojYear;
	            int diffMonths = currentMonth - dojMonth;
	            
	            // Calculate total months worked
	            monthsWorked = diffYears * 12 + diffMonths;
	            
	            // Adjust months worked if current month is not complete
	            if (currentCalendar.get(Calendar.DAY_OF_MONTH) < dojCalendar.get(Calendar.DAY_OF_MONTH)) {
	                monthsWorked--;
	            }
	        }
	        
	        return monthsWorked;
	    }
		
}