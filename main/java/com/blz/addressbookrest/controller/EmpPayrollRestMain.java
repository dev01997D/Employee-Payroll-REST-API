package com.blz.addressbookrest.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.blz.addressbookrest.model.Employee;

@SuppressWarnings("unused")
public class EmpPayrollRestMain {
	private List<Employee> empPayrollList;

	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO;
	}

	public EmpPayrollRestMain() {
	}

	public EmpPayrollRestMain(List<Employee> employeeList) {
		this();
		this.empPayrollList = new ArrayList<>(employeeList); // Use new memory not the same as provided by client to
																// avoid confusion

	}

	public long countEntries(IOService ioService) {
		if (ioService.equals(IOService.REST_IO))
			return empPayrollList.size();
		return 0;
	}

	public void addEmployeeToPayroll(Employee employeeData, IOService ioService) {
		empPayrollList.add(employeeData);
	}
}
