package io.restapiframework.tests;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.testng.annotations.Test;

import apiutil.ApiBuilderUtil;
import extentreport.ExtendLogger;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;


public class DeleteTests extends BaseTests {
	
	@Test
	public void deleteEmployeebyId()
	{
		//get id of random employee
		Response response = ApiBuilderUtil.requestCallBuilder().get("/employees");
		List<Map<String, Object>> responseList = response.jsonPath().get("$");
		int size=responseList.size();
//		System.out.println("employee count before deletion "+responseList.size());
		ExtendLogger.info("employee count before deletion "+responseList.size());
		int randomNumber=ThreadLocalRandom.current().nextInt(0, size);
		String targetId = String.valueOf( responseList.get(randomNumber).get("id"));
		int empId=(int)(responseList.get(randomNumber).get("empid"));
//		System.out.println("target employee id --> " + targetId);
		ExtendLogger.info("target employee id --> " + targetId);
//		System.out.println("target Employee --> " + response.prettyPrint());
		
		Response deleteResponse=ApiBuilderUtil.requestCallBuilder().pathParam("id", targetId).delete("/employees/{id}");
		System.out.println(deleteResponse.statusCode());
		System.out.println(deleteResponse.prettyPrint());
		assertThat(deleteResponse.getStatusCode()).isIn(200,201);
		ExtendLogger.info("delete status code "+deleteResponse.statusCode());
		ExtendLogger.info("delete response code ");
		ExtendLogger.logResponse(deleteResponse.prettyPrint());

		
		Response getResponse=ApiBuilderUtil.requestCallBuilder().pathParam("id", targetId).get("/employees/{id}");
//		System.out.println("status code for deleted employee in get "+getResponse.statusCode());
		assertThat(getResponse.getStatusCode()).isEqualTo(404);
		ExtendLogger.info("status code for deleted employee in get "+getResponse.statusCode());
//		System.out.println(((List<Object>)getResponse.jsonPath().get("$")).size());
//		System.out.println("response after delete "+getResponse.prettyPrint());
		ExtendLogger.info("response after delete ");
		ExtendLogger.logResponse(getResponse.prettyPrint());
		
	}

}
