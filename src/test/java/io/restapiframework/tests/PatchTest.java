package io.restapiframework.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import annotations.FrameworkAnnotations;
import apiutil.ApiBuilderUtil;
import extentreport.ExtendLogger;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.EmployeePojo;
import util.FakerUtil;

public class PatchTest extends BaseTests {

	@FrameworkAnnotations
	@Test
	public void patchEmployeename() {
		
		Response response = ApiBuilderUtil.requestCallBuilder().get("/employees");
		List<Map<String, Object>> responseList = response.jsonPath().get("$");
		int randomNumber=ThreadLocalRandom.current().nextInt(0, responseList.size());
		String targetId =(String)responseList.get(randomNumber).get("id");
				//((List<String>) response.jsonPath().get("id")).get(0);
		int targetEmployeeId = (int)responseList.get(randomNumber).get("empid");
//				((List<Integer>) response.jsonPath().get("empid")).get(0);
//		System.out.println("target employee id --> " + targetId);
//		System.out.println("target Employee --> " + response.prettyPrint());
		
		ExtendLogger.info("target employee id --> " + targetId);
		ExtendLogger.logResponse(response.prettyPrint());
		
		assertThat(response.getStatusCode()).isIn(200,201).as("expected status code didnt come for get ");
		// update name in Employee pojo
		String targetUpdatedname = FakerUtil.getName();
		EmployeePojo employee = EmployeePojo.builder().setName(targetUpdatedname).build();
//		System.out.println("target employee updated --> " + employee);
		ExtendLogger.info("target employee updated --> " + employee);

		// updating employee
		RequestSpecification requestSpecification= ApiBuilderUtil.requestCallBuilder().contentType(ContentType.JSON).body(employee)
		.pathParam("id", targetId);
		ExtendLogger.logHeaderresponse(requestSpecification);
		Response updateEmployee = requestSpecification.patch("/employees/{id}");
//		System.out.println("updated employee response --> " + updateEmployee.prettyPrint());
		ExtendLogger.info("updated employee response below");
		ExtendLogger.logResponse( updateEmployee.prettyPrint());
		assertThat(updateEmployee.getStatusCode()).isIn(200,201)
				.as("expected status code didnt come for patch ");

		// validating after update
		requestSpecification=ApiBuilderUtil.requestCallBuilder().queryParam("empid", targetEmployeeId);
		ExtendLogger.logHeaderresponse(requestSpecification);
		Response employeeCheck = requestSpecification
				.get("/employees");
//		System.out.println("employee fetched -->" + employeeCheck);
		ExtendLogger.info("employee fetched -->" );
		ExtendLogger.logResponse(employeeCheck.prettyPrint());
		assertThat(employeeCheck.getStatusCode()).isIn(200,201)
				.as("update employee not came as expected after fetching");
		assertThat(((List<String>) employeeCheck.jsonPath().get("name")).get(0))
				.isEqualToIgnoringCase(targetUpdatedname);
	}

	@FrameworkAnnotations
	@Test
	public void patchEmployeeJob() {
		
		
		
		
		Response response=ApiBuilderUtil.requestCallBuilder().get("/employees");
		List<Map<String, Object>> responseList = response.jsonPath().get("$");
		int randomNumber=ThreadLocalRandom.current().nextInt(0, responseList.size());
		String id=(String)responseList.get(randomNumber).get("id");
		String currentJob=(String)responseList.get(randomNumber).get("job");
//		System.out.println("Current id and current job "+id+" "+currentJob);
		int targetEmployeeId = (int)responseList.get(randomNumber).get("empid");
		ExtendLogger.info("Current id and current job "+id+" "+currentJob);
		String updatedJob=FakerUtil.getJob();
		EmployeePojo employee=EmployeePojo.builder().setJob(updatedJob).build();
		
		RequestSpecification requestSpecification= ApiBuilderUtil.requestCallBuilder().pathParam("id", id).contentType(ContentType.JSON).body(employee);
		Response updateResponse=requestSpecification.patch("/employees/{id}");
		updateResponse.prettyPrint();
		
		
		requestSpecification=ApiBuilderUtil.requestCallBuilder().queryParam("empid", targetEmployeeId);
		ExtendLogger.logHeaderresponse(requestSpecification);
		Response employeeCheck=requestSpecification.get("/employees");
		String updatedJob1=((List<String>)employeeCheck.jsonPath().get("job")).get(0);
		ExtendLogger.info("employee check --> ");
		ExtendLogger.logResponse(employeeCheck.prettyPrint());
		assertThat(updatedJob1).isEqualTo(updatedJob).as("job is not updated");
		assertThat(updatedJob1).isNotEqualTo(currentJob);
	

	}

}
