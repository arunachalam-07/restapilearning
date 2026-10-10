package apiutil;

import static io.restassured.RestAssured.given;

import enums.Enums;
import io.restassured.specification.RequestSpecification;
import util.PropertyUtilReader;

public class ApiBuilderUtil {
	
	
	public static RequestSpecification requestCallBuilder()
	{
		return given().baseUri(PropertyUtilReader.getValue(Enums.BASEURL)).log().all();
	}

}
