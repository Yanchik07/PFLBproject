package com.pflb.education.atexample.apitest;

import com.pflb.education.atexample.tests.Auth;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests extends BaseApiTest {

    @Test
    @Description("Post. Вход в УЗ с валидными данными")
    public void loginTestSuc(){

        Response loginResponse = loginWith(config.userName, config.userPassword);

        loginResponse.then()
                .assertThat()
                .statusCode(202)
                .body(notNullValue())
                .body(containsString("access_token"));

        Auth.AuthResponse authResponse = loginResponse.getBody().as(Auth.AuthResponse.class);

        sendMessageToReport(authResponse.accessToken);
    }

    @Test
    @Description("Вход в УЗ с невалидными данными")
    public void unSuccessLogin_Test(){
        Response loginResponse = loginWith("testtest", config.userPassword);

        loginResponse.then()
                .assertThat()
                .statusCode(403);
    }

    @Step("Логин в систему с username = {username}, password = {password}")
    public Response loginWith(String username, String password) {
        Auth.AuthRequest authRequest = new Auth.AuthRequest(username, password);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when()
                .post( "/login");

        return response;
    }

    @Attachment(value = "Bearer token:", type = "text/html")
    public String sendMessageToReport(String message) {
        return message;
    }

}