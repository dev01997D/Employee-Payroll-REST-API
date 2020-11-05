package com.blz.addressbookrest.tester;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.blz.addressbookrest.controller.EmpPayrollRestMain;
import com.blz.addressbookrest.controller.EmpPayrollRestMain.IOService;
import com.blz.addressbookrest.model.Employee;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class EmpPayrollRestTest {
	EmpPayrollRestMain empPayrollService = null;
	private static Logger log = Logger.getLogger(EmpPayrollRestTest.class.getName());

	@Before
	public void setUp() {
		empPayrollService = new EmpPayrollRestMain();
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	public Employee[] getEmployeeList() {
		Response response = RestAssured.get("/employees");
		log.info("Employee payroll entries in JSON Server :\n" + response.asString());
		Employee[] arrayOfEmps = new Gson().fromJson(response.asString(), Employee[].class);
		return arrayOfEmps;
	}

	private Response addEmployeeToJSONServer(Employee employeeData) {
		String empJson = new Gson().toJson(employeeData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		return request.post("/employees");
	}

	@Test
	public void givenEmployeeDataInJSONServer_WhenRetrieved_ShouldmatchTheCount() {
		Employee[] arrayOfEmps = getEmployeeList();
		empPayrollService = new EmpPayrollRestMain(Arrays.asList(arrayOfEmps));
		long entries = empPayrollService.countEntries(IOService.REST_IO);
		Assert.assertEquals(2, entries);
	}

	@Test
	public void givenNewEmployee_WhenAddedInJsonServer_ShouldMatchResponseAndCount() {
		Employee[] arrayOfEmps = getEmployeeList();
		empPayrollService = new EmpPayrollRestMain(Arrays.asList(arrayOfEmps));
		Employee employeeData = new Employee(0, "Mark ZukerBerg", "M", 3000000, LocalDate.now());
		Response response = addEmployeeToJSONServer(employeeData);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);

		employeeData = new Gson().fromJson(response.asString(), Employee.class);
		empPayrollService.addEmployeeToPayroll(employeeData, IOService.REST_IO);
		long entries = empPayrollService.countEntries(IOService.REST_IO);
		Assert.assertEquals(3, entries);
	}
}
