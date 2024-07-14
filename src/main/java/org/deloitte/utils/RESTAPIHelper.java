package org.deloitte.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RESTAPIHelper {

    public static RequestSpecification prepareBaseRequest() {
        RequestSpecification specification = RestAssured.given();
        specification.baseUri(TestData.testConfig.getApiBaseURI());
        specification.accept(ContentType.JSON);
        specification.contentType(ContentType.JSON);
        return specification;
    }

    public static Response post(RequestSpecification specification) {
        return specification.post().then().extract().response();
    }

    public static Response get(RequestSpecification specification) {
        return specification.get().then().extract().response();
    }

}
