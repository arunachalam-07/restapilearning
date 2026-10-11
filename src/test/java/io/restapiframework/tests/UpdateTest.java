package io.restapiframework.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;
import org.testng.annotations.Test;

import apiutil.ApiBuilderUtil;
import extentreport.ExtendLogger;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.EmployeePojo;
import util.FakerUtil;

public class UpdateTest extends BaseTests {

	@Test
	public void updateEmployee() {
		ExtendLogger.info("inside update employee method");
		Response response = ApiBuilderUtil.requestCallBuilder().get("/employees");
//		System.out.println(response.jsonPath().get("$").toString());		
		List<Map<String, Object>> responseList = response.jsonPath().get("$");
//		System.out.println("current employee List--> " + responseList.size());
		ExtendLogger.info("current employee List--> " + responseList.size());
		int randomInt = ThreadLocalRandom.current().nextInt(0, responseList.size());
		Map<String, Object> randomEmployee = responseList.get(randomInt);
		String targetId = String.valueOf(randomEmployee.get("id"));
		int empId=Integer.valueOf(String.valueOf(randomEmployee.get("empid")));
//		System.out.println("target id " + targetId);
//		System.out.println("before job update " + randomEmployee);
		ExtendLogger.info("target id " + targetId);
		ExtendLogger.info("before job update " + randomEmployee);
		String targetJob=FakerUtil.getJob();
		EmployeePojo employee = EmployeePojo.builder().setJob(targetJob)
				.setEmpid(empId).setName((String) randomEmployee.get("name")).build();
//		System.out.println(employee);
		ExtendLogger.info("employee updated "+employee);
		Response updateRequest = ApiBuilderUtil.requestCallBuilder().contentType(ContentType.JSON)
				.pathParam("id", targetId).body(employee).put("/employees/{id}");
		
		assertThat(updateRequest.getStatusCode()).isIn(200,201);
		
		Response requestAfterUpdate=ApiBuilderUtil.requestCallBuilder().queryParam("id", targetId).get("/employees");
		String newJob=requestAfterUpdate.jsonPath().get("[0].job");
		assertThat(newJob).isEqualTo(targetJob);
 
		ExtendLogger.logResponse(updateRequest.prettyPrint());

	}

}
