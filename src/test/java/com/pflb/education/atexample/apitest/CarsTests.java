package com.pflb.education.atexample.apitest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pflb.education.atexample.tests.Car;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CarsTests extends BaseApiTest{
    @Test
    @Step("Создать авто, используя валидные данные")
    @Description("Создать автомобиль с валидными данными")
    public void createValidCar() throws IOException {

        File testData = new File("/Users/admin/Testing/PLTest/at_practics2023/src/test/resources/carsTestData.json");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(testData)
                .when()
                .post("/car");

        response.then()
                .statusCode(201)
                .body(notNullValue());

        Car responseCar = response.getBody().as(Car.class);
        Car requestCar = new ObjectMapper().readValue(testData, Car.class);

        requestCar.id = responseCar.id;

        Assertions.assertEquals(requestCar, responseCar);
    }

    @Step("Удалить авто, с указнным id")
    public void deleteCar(Integer id) throws IOException {

        Response response = given()
                .contentType(ContentType.JSON)
                .header("accept", "*/*")
                .when()
                .delete("/car/" + id);

        Assertions.assertEquals(204, response.statusCode());
    }
}
