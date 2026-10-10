package io.restapiframework.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import apiutil.ApiBuilderUtil;
import constants.FrameworkConstantSingleton;
import extentreport.ExtendLogger;
import extentreport.ExtentReportConfig;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class GetTests extends BaseTests {

	public void getEmployee(ITestContext context) throws Exception {
		Response response = ApiBuilderUtil.requestCallBuilder().queryParam("empid", 31).get("/employees");
		response.prettyPrint();
		response.then().body(JsonSchemaValidator
				.matchesJsonSchema(new FileInputStream(FrameworkConstantSingleton.getJsonSchemaPath())));
		ExtendLogger.logResponse(response.prettyPrint());
		System.out.println(((List<String>) response.jsonPath().get("$")).size());
		if (((List<String>) response.jsonPath().get("$")).size() == 0) {
			throw new Exception("emp id is not present");
		}

	}

	@Test
	public void getEmployees(ITestContext context) throws FileNotFoundException {
		Response response = ApiBuilderUtil.requestCallBuilder().get("/employees");
//		response.prettyPrint();
		ValidatableResponse schemaResponse = response.then().body(JsonSchemaValidator
				.matchesJsonSchema(new FileInputStream(FrameworkConstantSingleton.getJsonSchemaPath())));

		int responseSize = ((List<Object>) response.jsonPath().get("$")).size();
		context.setAttribute("initialSize", responseSize);

		ExtendLogger.logResponse(response.prettyPrint());
		ExtendLogger.info("current employee :" + String.valueOf(responseSize));

	}

}
