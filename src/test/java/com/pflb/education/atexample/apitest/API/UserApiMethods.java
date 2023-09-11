package com.pflb.education.atexample.apitest.API;

import com.pflb.education.atexample.tests.Car;
import com.pflb.education.atexample.tests.User;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.InputStream;

import static io.restassured.RestAssured.given;

public class UserApiMethods {
    @Step("POST. Создать пользователя (id, firstName, secondName)")
    public static Response createUser(User user){

        return given()
                .body(user)
                .when()
                .post("/user");
    }

    @Step("POST. Создать пользователя, с помощью Json")
    public static Response createUser(InputStream inputStream){

        return given()
                .body(inputStream)
                .when()
                .post("/user");
    }

    @Step("DELETE. Удалить пользователя")
    public static Response deleteUserById(Integer id) {

        return given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/user/" + id);
    }

    @Step("GET. Получить пользователя")
    public static Response getUserById(Integer id) {

        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/user/" + id);
    }

    @Step("POST. Изменение пользователя")
    public static Response editUser(User user){
        return given()
                .body(user)
                .when()
                .post("/user");
    }

    @Step("POST. Покупка пользователем")
    public static Response userBuyCar(User user, Car car){
        return given()
                .body(user)
                .when()
                .post("/user/" + user.id + "/buyCar/" + car.id);
    }

    @Step("GET. Получить список автомобилей пользователя по userId")
    public static Response getUserCars(User user){
        return given()
                .body(user)
                .when()
                .get("/user/" + user.id + "/cars");
    }

    @Step("GET. Получить имущество пользователя")
    public static Response getUserInfo(User user){
        return given()
                .body(user)
                .when()
                .get("/user/" + user.id + "/info");
    }

    @Step("POST. Начисление начисление денег пользователю")
    public static Response addMoneyToUser(User user, Double amount){
        return given()
                .body(user)
                .when()
                .post("/user/" + user.id + "/money/" + amount);
    }

    @Step("POST. Продажа авто пользователя")
    public static Response sellCarByUser(User user, Car car){
        return given()
                .body(user)
                .when()
                .post("/user/" + user.id + "/sellCar/" + car.id);
    }
    @Step("GET. Получить список пользователей")
    public static Response getUsersList(){

        Response response = given()
                .when()
                .get("/users");

        return response;
    }
}