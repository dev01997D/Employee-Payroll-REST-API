package com.blz.addressbookrest.tester;

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
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class EmpPayrollRestTest {
	private static Logger log = Logger.getLogger(EmpPayrollRestTest.class.getName());
	@Before
	public void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}
	public Employee[] getEmployeeList() {
		Response response=RestAssured.get("/employees");
		log.info("Employee payroll entries in JSON Server :\n"+ response.asString());
		Employee[] arrayOfEmps=new Gson().fromJson(response.asString(), Employee[].class);
		return arrayOfEmps;
	}
	
	@Test
	public void givenEmployeeDataInJSONServer_WhenRetrieved_ShouldmatchTheCount() {
		Employee[] arrayOfEmps=getEmployeeList();
		EmpPayrollRestMain empPayrollService;
		empPayrollService=new EmpPayrollRestMain(Arrays.asList(arrayOfEmps));
		long entries=empPayrollService.countEntries(IOService.REST_IO);
		Assert.assertEquals(2, entries);
	}
}
