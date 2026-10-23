package io.restapiframework.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import annotations.FrameworkAnnotations;
import apiutil.ApiBuilderUtil;
import constants.FrameworkConstantSingleton;
import extentreport.ExtendLogger;
import extentreport.ExtentReportConfig;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import jdk.internal.org.jline.utils.Log;

public class GetTests extends BaseTests {

	@FrameworkAnnotations(author = { "cougar" })
	@Test
	public void getEmployee(ITestContext context) throws Exception {
		
		
		RequestSpecification requestSpecification= ApiBuilderUtil.requestCallBuilder();
		ExtendLogger.logHeaderresponse(requestSpecification);
		Response response = requestSpecification.get("/employees");
		
		List<Map<String, Object>> responseListmap = response.jsonPath().get("$");
		response.then().body(JsonSchemaValidator
				.matchesJsonSchema(new FileInputStream(FrameworkConstantSingleton.getJsonSchemaPath())));
		ExtendLogger.logResponse(response.prettyPrint());
		
		int randomNumber=ThreadLocalRandom.current().nextInt(0, responseListmap.size());
		int targetEmpid=(int)(responseListmap.get(randomNumber).get("empid"));
		
		ExtendLogger.info("target employee --> "+targetEmpid);
		
		requestSpecification= ApiBuilderUtil.requestCallBuilder().queryParam("empid", targetEmpid);
		ExtendLogger.logHeaderresponse(requestSpecification);
		
		Response response1=requestSpecification.get("/employees");
		
		if(((List<Object>)response1.jsonPath().get("$")).size()==0)
		{
			throw new Exception(targetEmpid+" "+"employee id is not found");
		}
		else
		{
		ExtendLogger.info("employee found");
		ExtendLogger.logResponse(response1.prettyPrint());
		}
		

	}

	@FrameworkAnnotations
	@Test
	public void getEmployees(ITestContext context) throws FileNotFoundException {
		RequestSpecification requestSpecification= ApiBuilderUtil.requestCallBuilder();
		ExtendLogger.logHeaderresponse(requestSpecification);
		Response response = requestSpecification.get("/employees");
//		response.prettyPrint();
		ValidatableResponse schemaResponse = response.then().body(JsonSchemaValidator
				.matchesJsonSchema(new FileInputStream(FrameworkConstantSingleton.getJsonSchemaPath())));

		int responseSize = ((List<Object>) response.jsonPath().get("$")).size();
		context.setAttribute("initialSize", responseSize);

		ExtendLogger.logResponse(response.prettyPrint());
		ExtendLogger.info("current employee :" + String.valueOf(responseSize));

	}

}
