package io.restapiframeworks.github.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.ResponseCache;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.internal.annotations.ITest;

import com.aventstack.extentreports.model.Log;

import annotations.FrameworkAnnotations;
import apiutil.ApiBuilderUtil;
import enums.Enums;
import extentreport.ExtendLogger;
import io.restapiframework.tests.BaseTests;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.FakerUtil;
import util.PropertyUtilReader;

public class GithubRepoCRUDTests extends BaseTests {

	@FrameworkAnnotations
	@Test(priority = 1)
	public void validateUser() {
		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.GITHUB_BASE_URI)
				.accept("application/vnd.github+json")
				.header("Authorization", PropertyUtilReader.getValue(Enums.GITHUB_KEY));
		ExtendLogger.info("header content ");
		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.get("/user");

		assertThat(response.statusCode()).as("error in fetching user").isIn(200, 201);
		ExtendLogger.info("response body");
		ExtendLogger.logResponse(response.asPrettyString());

	}

	@FrameworkAnnotations
	@Test(priority = 2, dependsOnMethods = "validateUser")
	public void createRepository(ITestContext context) {

		Integer randomInt = Math.abs( ThreadLocalRandom.current().nextInt());

		HashMap<String, Object> bodyContent = new HashMap<String, Object>();
		bodyContent.put("name", "test-" + randomInt + "-repo");
		bodyContent.put("description", "git hub repo created via Post API call");
		bodyContent.put("homepage", "https://github.com");

		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.GITHUB_BASE_URI)
				.accept("application/vnd.github+json")
				.header("Authorization", PropertyUtilReader.getValue(Enums.GITHUB_KEY)).body(bodyContent);
		ExtendLogger.info("header content");
		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.post("/user/repos");
		assertThat(response.statusCode()).as("repository not created successfully").isIn(200, 201);
		assertThat(response.jsonPath().getString("name")).isEqualTo(bodyContent.get("name"));
		context.setAttribute("currentRepoName", response.jsonPath().getString("name"));
		ExtendLogger.info("response body");
		ExtendLogger.logResponse(response.asPrettyString());

	}

	@FrameworkAnnotations
	@Test(priority = 3, dependsOnMethods = "createRepository")
	public void getRepository(ITestContext context) {
		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.GITHUB_BASE_URI)
				.accept("application/vnd.github+json")
				.header("Authorization", PropertyUtilReader.getValue(Enums.GITHUB_KEY))
				.pathParam("owner", PropertyUtilReader.getValue(Enums.GITHUB_OWNER))
				.pathParam("repo", context.getAttribute("currentRepoName"));
		ExtendLogger.info("header content for " + context.getAttribute("currentRepoName"));
		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.get("/repos/{owner}/{repo}");

		assertThat(response.statusCode()).as("error in fetching repo").isIn(200, 201);
		ExtendLogger.info("response body for " + context.getAttribute("currentRepoName"));
		ExtendLogger.logResponse(response.asPrettyString());

	}

	@FrameworkAnnotations
	@Test(priority = 4, dependsOnMethods = "getRepository")
	public void patchRepository(ITestContext context) {
		HashMap<String, Object> bodyContent = new HashMap<String, Object>();
		bodyContent.put("description", "github repo updated via patch call");
		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.GITHUB_BASE_URI)
				.accept("application/vnd.github+json")
				.header("Authorization", PropertyUtilReader.getValue(Enums.GITHUB_KEY))
				.pathParam("owner", PropertyUtilReader.getValue(Enums.GITHUB_OWNER))
				.pathParam("repo", context.getAttribute("currentRepoName")).body(bodyContent);

		ExtendLogger.info("header content for patch call" + context.getAttribute("currentRepoName"));
		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.patch("/repos/{owner}/{repo}");

		ExtendLogger.info("response body for " + context.getAttribute("currentRepoName"));
		ExtendLogger.logResponse(response.asPrettyString());
		assertThat(response.statusCode()).as("error in updating repo").isIn(200, 201);
		assertThat(response.jsonPath().getString("description")).as("description not updated")
				.isEqualTo(bodyContent.get("description"));

	}

	@FrameworkAnnotations
	@Test(priority = 5, dependsOnMethods = "patchRepository")
	public void deleteRepository(ITestContext context) {
		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.GITHUB_BASE_URI)
				.accept("application/vnd.github+json")
				.header("Authorization", PropertyUtilReader.getValue(Enums.GITHUB_KEY))
				.pathParam("owner", PropertyUtilReader.getValue(Enums.GITHUB_OWNER))
				.pathParam("repo", context.getAttribute("currentRepoName"));

		ExtendLogger.info("header content for delete call" + context.getAttribute("currentRepoName"));
		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.delete("/repos/{owner}/{repo}");
		assertThat(response.statusCode()).as("error in delete repo").isIn(204);
		ExtendLogger.info("response body for " + context.getAttribute("currentRepoName"));
		ExtendLogger.logResponse(response.asPrettyString());

		ExtendLogger.info("checking the deleted repo");

		RequestSpecification requestSpecification2 = ApiBuilderUtil.customURLRequestCallBuilder(Enums.GITHUB_BASE_URI)
				.accept("application/vnd.github+json")
				.header("Authorization", PropertyUtilReader.getValue(Enums.GITHUB_KEY))
				.pathParam("owner", PropertyUtilReader.getValue(Enums.GITHUB_OWNER))
				.pathParam("repo", context.getAttribute("currentRepoName"));
		
		
		ExtendLogger.info("header content for get call after delete call" + context.getAttribute("currentRepoName"));
		ExtendLogger.logHeaderresponse(requestSpecification2);
		
		
		

		Response response2 = requestSpecification2.get("/repos/{owner}/{repo}");
		assertThat(response2.getStatusCode()).as("issue in deleted repo fetching").isIn(403, 404);
		ExtendLogger.info("response body for get call after delete call");
		ExtendLogger.logResponse(response2.asPrettyString());
	}

}
