package com.pflb.education.atexample.apitest.API;

import com.pflb.education.atexample.tests.Car;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.InputStream;

import static io.restassured.RestAssured.given;

public class CarApiMethods {
    @Step("GET. Получить список автомобилей")
    public static Response getCarsList(){

        Response response = given()
                .when()
                .get("/cars");

        return response;
    }

    @Step("POST. Создать автомобиль (id, model, mark)")
    public static Response createCar(Car car){

        return given()
                .body(car)
                .when()
                .post("/car");
    }

    @Step("POST. Создать автомобиль, с помощью Json")
    public static Response createCar(InputStream inputStream){

        return given()
                .body(inputStream)
                .when()
                .post("/car");
    }

    @Step("PUT. Изменение автомобиль (user.id, user.firstName, user.secondName)")
    public static Response editCar(Integer carId, Car car){

        return given()
                .contentType(ContentType.JSON)
                .body(car)
                .when()
                .put("/car/" + carId);
    }

    @Step("DELETE. Удалить автомобиль")
    public static Response deleteCarById(Integer carId) {

        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/car/" + carId);
    }

    @Step("GET. Получить автомобиль")
    public static Response getCarById(Integer carid) {

        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/car/" + carid);
    }
}
