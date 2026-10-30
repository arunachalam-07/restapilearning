package io.restapiframeworks.postman.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import annotations.FrameworkAnnotations;
import apiutil.ApiBuilderUtil;
import enums.Enums;
import extentreport.ExtendLogger;
import io.restapiframework.tests.BaseTests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.FakerUtil;
import util.PropertyUtilReader;

public class PostmanCollections extends BaseTests {

	@FrameworkAnnotations
	@Test(priority = 1)
	public void checkWorkSpace() {
		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN))
				.pathParam("workspace", PropertyUtilReader.getValue(Enums.POSTMAN_WORKSPACE_ID));

		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.get("/workspaces/{workspace}");
		assertThat(response.getStatusCode()).as("issue in workspace").isIn(200, 201);
		ExtendLogger.info("status of " + PropertyUtilReader.getValue(Enums.POSTMAN_WORKSPACE_ID) + " "
				+ response.getStatusCode());
		ExtendLogger.logResponse(response.asPrettyString());
	}

	@FrameworkAnnotations
	@Test(priority = 2, dependsOnMethods = "checkWorkSpace")
	public void createCollection(ITestContext context) {

		HashMap<String, Object> body = new HashMap<String, Object>();
		HashMap<String, Object> info = new HashMap<String, Object>();
		info.put("name", FakerUtil.getName());
		info.put("description", "Collection created via REST Assured Automation");
		info.put("schema", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

		HashMap<String, Object> collection = new HashMap<String, Object>();
		collection.put("info", info);
		collection.put("item", new ArrayList<>());

		body.put("collection", collection);

		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN)).contentType(ContentType.JSON)
				.body(body).queryParam("workspace", PropertyUtilReader.getValue(Enums.POSTMAN_WORKSPACE_ID));

		ExtendLogger.logHeaderresponse(requestSpecification);

		Response response = requestSpecification.post("/collections");
		assertThat(response.getStatusCode()).as("Collection not created").isIn(200, 201);
		ExtendLogger.logResponse(response.asPrettyString());
		ExtendLogger.info("generated collection id is " + response.jsonPath().getString("collection.id"));
		context.setAttribute("currentCollectionId", response.jsonPath().getString("collection.id"));

	}

	@FrameworkAnnotations
	@Test(priority = 3, dependsOnMethods = "createCollection")
	public void updateCollection(ITestContext context) {

		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN))
				.pathParam("collectionid", String.valueOf(context.getAttribute("currentCollectionId")));
		// String.valueOf(context.getAttribute("currentCollectionId"))
		ExtendLogger.logHeaderresponse(requestSpecification);
		ExtendLogger.info("getting collection which to be updated");

		Response response = requestSpecification.get("/collections/{collectionid}");
		assertThat(response.getStatusCode()).as("collection not found").isIn(200, 201);
		ExtendLogger.logResponse(response.asPrettyString());

		JSONObject extenralPayloadObject = new JSONObject(response.asString());
		ExtendLogger.info("json object");
		ExtendLogger.info(extenralPayloadObject.toString());

		JSONObject infoObject = new JSONObject();
		infoObject.put("name", FakerUtil.getName());
		infoObject.put("description",
				extenralPayloadObject.getJSONObject("collection").getJSONObject("info").getString("description")
						+ " Updated via put call");
		infoObject.put("schema",
				extenralPayloadObject.getJSONObject("collection").getJSONObject("info").getString("schema"));

		JSONObject collectionObject = new JSONObject();

		collectionObject.put("info", infoObject);
		collectionObject.put("item", new ArrayList<>());

		JSONObject finalPayload = new JSONObject();
		finalPayload.put("collection", collectionObject);

		ExtendLogger.info("updated collection " + finalPayload.toString());
		RequestSpecification requestSpecification1 = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN))
				.pathParam("collectionid", String.valueOf(context.getAttribute("currentCollectionId")))
				.body(finalPayload.toMap());
		Response response2 = requestSpecification1.put("/collections/{collectionid}");
		assertThat(response2.getStatusCode()).as("update not done as expected").isIn(200, 201);
		ExtendLogger.logResponse(response2.asPrettyString());
	}

	@FrameworkAnnotations
	@Test(priority = 4,dependsOnMethods="updateCollection")
	public void deleteCollection(ITestContext context) {
		RequestSpecification requestSpecification = ApiBuilderUtil.customURLRequestCallBuilder(Enums.POSTMAN_BASE_URI)
				.header("Authorization", "Bearer " + PropertyUtilReader.getValue(Enums.POSTMAN_API_BEARER_TOKEN))
				.pathParam("collectionid", String.valueOf(context.getAttribute("currentCollectionId")));

		ExtendLogger.logHeaderresponse(requestSpecification);
		Response response = requestSpecification.delete("/collections/{collectionid}");
		assertThat(response.getStatusCode()).as("collection not found").isIn(200, 201);
		ExtendLogger.info("collection is deleted");
		ExtendLogger.logResponse(response.asPrettyString());

		ExtendLogger.info("checking the presence of collection after delete");
		response = requestSpecification.get("/collections/{collectionid}");
		assertThat(response.getStatusCode()).as("collection is found please check").isIn(404);
		ExtendLogger.logResponse(response.asPrettyString());

	}

}
