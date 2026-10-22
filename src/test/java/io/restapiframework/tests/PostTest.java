package io.restapiframework.tests;

import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import annotations.FrameworkAnnotations;
import apiutil.ApiBuilderUtil;
import extentreport.ExtendLogger;
import extentreport.ExtentReportConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.EmployeePojo;
import util.FakerUtil;
import static org.assertj.core.api.Assertions.assertThat;

public class PostTest extends BaseTests {

	@FrameworkAnnotations
	@Test
	public void createEmployee(ITestContext context)
	{
		EmployeePojo employeePojo=EmployeePojo.builder().setEmpid(FakerUtil.getEmpid()).setJob(FakerUtil.getJob()).setName(FakerUtil.getName()).build();
		System.out.println(employeePojo);
		ExtendLogger.info("pojo object created "+employeePojo.toString());
		Response response=ApiBuilderUtil.requestCallBuilder().contentType(ContentType.JSON).body(employeePojo).post("/employees");
		ExtendLogger.logResponse(response.prettyPrint());
		
		Response response1=ApiBuilderUtil.requestCallBuilder().get("/employees");
		
		int currentSize=response1.jsonPath().getList("$").size();
		
		int previousSize= Integer.valueOf(context.getAttribute("initialSize").toString());
		ExtendLogger.info("current size is "+currentSize);
		ExtendLogger.info("response for addition of new employee "+response1.prettyPrint());
		
		assertThat(currentSize).isGreaterThan(previousSize);
		
		
	}
}
