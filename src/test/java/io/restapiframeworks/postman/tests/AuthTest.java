package io.restapiframeworks.postman.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import annotations.FrameworkAnnotations;
import apiutil.ApiBuilderUtil;
import enums.Enums;
import extentreport.ExtendLogger;
import io.restapiframework.tests.BaseTests;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import util.PropertyUtilReader;
import static io.restassured.RestAssured.*;

public class AuthTest extends BaseTests {

	@FrameworkAnnotations(author = { "messi" })
	@Test
	public void basicAuth() {

		//preparing request call
		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_ECHO_URL)
				.auth().preemptive().basic(PropertyUtilReader.getValue(Enums.BASIC_AUTH_USERNAME),
						PropertyUtilReader.getValue(Enums.BASIC_AUTH_PASSWORD));

		ExtendLogger.logHeaderresponse(requestSpecification);
		//calling request call
		Response response = requestSpecification.get("/basic-auth");
		ExtendLogger.logResponse(response.asPrettyString());
		//assert response
		assertThat(response.getStatusCode()).isIn(200,201);
	}

}
