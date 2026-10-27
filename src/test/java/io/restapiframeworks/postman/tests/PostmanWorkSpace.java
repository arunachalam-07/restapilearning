package io.restapiframeworks.postman.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import annotations.FrameworkAnnotations;
import apiutil.ApiBuilderUtil;
import enums.Enums;
import extentreport.ExtendLogger;
import io.restapiframework.tests.BaseTests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.PropertyUtilReader;

public class PostmanWorkSpace extends BaseTests {

	@FrameworkAnnotations(author = { "messi" })
	@Test(priority = 1)
	public void getWorkspaces() {
		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN));
		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.get("/workspaces");
		ExtendLogger.logResponse(response.asPrettyString());

		assertThat(response.getStatusCode()).as("un expected status code").isIn(200, 201);
	}

	@FrameworkAnnotations
	@Test(priority = 2)
	public void createWorkSpaces(ITestContext context) {
		String payload = "{\r\n" + "  \"workspace\": {\r\n" + "    \"name\": \"My Automation Workspace\",\r\n"
				+ "    \"type\": \"team\",\r\n"
				+ "    \"description\": \"Workspace created via REST Assured test automation\"\r\n" + "  }\r\n" + "}";

		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.contentType(ContentType.JSON).body(payload)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN));
		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.post("/workspaces");
		ExtendLogger.logResponse(response.asPrettyString());

		context.setAttribute("currentWorkSpaceId", response.jsonPath().getString("workspace.id"));

		assertThat(response.getStatusCode()).as("un expected status code").isIn(200, 201);
	}

	@FrameworkAnnotations
	@Test(priority = 3)

	public void getWorkSpace(ITestContext context) {
		// 9d62b798-1ce2-4c72-82ef-e3288e8b49aa
		String workspaceId = String.valueOf(context.getAttribute("currentWorkSpaceId"));
		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.pathParam("workspace", workspaceId)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN));
		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.get("/workspaces/{workspace}");
		ExtendLogger.logResponse(response.asPrettyString());

		assertThat(response.getStatusCode()).as("un expected status code").isIn(200, 201);
	}

	@FrameworkAnnotations
	@Test(priority = 4, dependsOnMethods = "createWorkSpaces")
	public void updateWorkSpaceDetails(ITestContext context) {
		String payload = "{\r\n" + "  \"workspace\": {\r\n" + "    \"name\": \"My Automation Workspace updated\",\r\n"
				+ "    \"type\": \"team\",\r\n"
				+ "    \"description\": \"Workspace created via REST Put Assured test automation\"\r\n" + "  }\r\n"
				+ "}";

		String workspaceId = String.valueOf(context.getAttribute("currentWorkSpaceId"));
		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.pathParam("workspace", workspaceId)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN));
		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.get("/workspaces/{workspace}");

		ExtendLogger.info("workspace details before update");
		ExtendLogger.logResponse(response.asPrettyString());

		String workSpaceName = response.jsonPath().getString("$.workspace.name");
		String workSpacetype = response.jsonPath().getString("$.workspace.type");
		String workSpaceDescription = response.jsonPath().getString("$.workspace.description");

		RequestSpecification requestSpecification1 = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.contentType(ContentType.JSON).body(payload).pathParam("workspace", workspaceId)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN));
		ExtendLogger.logHeaderresponse(requestSpecification1);

		Response response1 = requestSpecification1.put("/workspaces/{workspace}");
		ExtendLogger.info("updated after put call");
		ExtendLogger.logResponse(response1.asPrettyString());
		ExtendLogger.info(response1.jsonPath().getString("workspace.name"));

		assertThat(response1.getStatusCode()).as("unexpected status code").isIn(200, 201);
		SoftAssertions softAssertion = new SoftAssertions();
		softAssertion.assertThat(response1.jsonPath().getString("workspace.id")).as("workspace id is not correct").isEqualTo(workspaceId);
		softAssertion.assertThat(response1.jsonPath().getString("workspace.name")).as("Workspace name is not correct").isNotEqualTo(workSpaceName);
		softAssertion.assertAll();

		ExtendLogger.info("getting updated workspace");
		RequestSpecification requestSpecification2 = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.pathParam("workspace", workspaceId)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN));
		ExtendLogger.logHeaderresponse(requestSpecification2);

		Response response2 = requestSpecification2.get("/workspaces/{workspace}");
		ExtendLogger.logResponse(response2.asPrettyString());

		JsonPath jsonPath = new JsonPath(payload);
		assertThat(response2.jsonPath().getString("workspace.name")).as("workspace name is not correct").isEqualTo(jsonPath.getString("workspace.name"))
				;
		assertThat(response2.jsonPath().getString("workspace.type")).as("workspace type is not correct").isEqualTo(jsonPath.getString("workspace.type"))
				;
		assertThat(response2.jsonPath().getString("workspace.description"))
		.as("workspace description is not correct").isEqualTo(jsonPath.getString("workspace.description"));

	}

}
