package com.blz.addressbookrest.model;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {
	public int emp_id;
	public String name;
	public String gender;
	public double salary;
	public LocalDate startDate;
	
	public Employee(int emp_id, String name, String gender, double salary, LocalDate startDate) {
		super();
		this.emp_id = emp_id;
		this.name = name;
		this.gender = gender;
		this.salary = salary;
		this.startDate = startDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(emp_id, gender, name, salary, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Employee))
			return false;
		Employee other = (Employee) obj;
		return emp_id == other.emp_id && Objects.equals(gender, other.gender) && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary)
				&& Objects.equals(startDate, other.startDate);
	}

	@Override
	public String toString() {
		return "Employee [emp_id=" + emp_id + ", name=" + name + ", gender=" + gender + ", salary=" + salary
				+ ", startDate=" + startDate + "]";
	}
}
