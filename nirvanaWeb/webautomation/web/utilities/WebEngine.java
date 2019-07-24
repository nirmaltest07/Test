package web.utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.MalformedURLException;
import java.util.Map;

public class WebEngine {

	static RequestSpecification Request;

	public WebEngine() {
		// Arrange
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri("https://www.flipkart.com");
		builder.setContentType(ContentType.JSON);
		// var requestSpec = builder.build();
		Request = RestAssured.given().spec(builder.build());
	}

	public static void getOpsWithPathParameter(String URL, Map<String, String> pathParams)
			throws MalformedURLException {
		// Act
		try {
			Request.pathParams(pathParams);
			Request.get(new URI(URL));
		} catch (URISyntaxException e) {
			System.out.println(e.getMessage());
		}
	}

	public static ResponseOptions<Response> getOps(String URL) {
		// Act
		try {
			return Request.get(new URI(URL));
		} catch (URISyntaxException e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	public static ResponseOptions<Response> PostOpsWithBodyAndPathParams(String url, Map<String, String> pathParams,
			Map<String, String> body) {

		Request.pathParams(pathParams);
		Request.body(body); // body parameter
		return Request.post(url);

	}

	public static ResponseOptions<Response> PostOpsWithBody(String url, Map<String, String> body) {
		Request.body(body);
		return Request.post(url);
	}

	public static ResponseOptions<Response> DeleteOpsWithPathParams(String url, Map<String, String> pathParams) {
		Request.pathParams(pathParams);
		return Request.delete(url);
	}

	public static ResponseOptions<Response> GetWithPathParams(String url, Map<String, String> pathParams) {
		Request.pathParams(pathParams);
		return Request.get(url);
	}

	public static ResponseOptions<Response> GetOpsWithToken(String url, String token) {
		// Act
		try {
			Request.header(new Header("Authorization", "Bearer " + token));
			return Request.get(new URI(url));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
