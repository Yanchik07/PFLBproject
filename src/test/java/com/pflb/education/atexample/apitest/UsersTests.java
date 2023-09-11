package com.pflb.education.atexample.apitest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pflb.education.atexample.tests.Car;
import com.pflb.education.atexample.tests.User;
import io.qameta.allure.Description;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.pflb.education.atexample.apitest.API.CarApiMethods.createCar;
import static com.pflb.education.atexample.apitest.API.CarApiMethods.deleteCarById;
import static com.pflb.education.atexample.apitest.API.UserApiMethods.*;
import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class UsersTests extends BaseApiTest{

    @Test
    @Description("Получение списка пользователей из JSON и вывод в консоль")
    public void responseUsersListTest() {
        List<User> responseBody;

        Response getUsersResponse = getUsersList();

        responseBody = getUsersResponse
                .then()
                .extract()
                .body()
                .as(new TypeRef<List<User>>() {});

        List<Integer> ids = responseBody.stream()
                .map(User::getId).toList();
        ids.forEach(System.out::println);
    }

    @Test
    @Description("""
            1. POST. Создание пользователя, проверка на статус кода и на непустое тело запроса,
            2. GET. Получить созданного пользователя по id, проверка на статус кода и на непустое тело запроса,
            3. Сравнение пользователей из тела запроса и ответа на равенство
            4. Удалиние созданного пользователя по id,  проверка на статус кода и на непустое тело запроса,""")
    //CRD - create, read, delete
    public void CRDwithValidData() throws IOException {

        InputStream testData = BaseApiTest.class.getClassLoader().getResourceAsStream("usersTestData1.json");
        User requestUser = new ObjectMapper().readValue(testData, User.class);

        Response response = createUser(requestUser);

        response.then()
                .statusCode(201)
                .body(notNullValue());

        requestUser.id = response.path("id");

        Response getResponse =  getUserById(requestUser.id);

        getResponse.then()
                .statusCode(200)
                .body(notNullValue());

        User userFromGetResponse = getResponse.getBody().as(User.class);

        Assertions.assertEquals(requestUser, userFromGetResponse);

        Response deleteResponse = deleteUserById(userFromGetResponse.id);

        deleteResponse.then()
                .statusCode(204)
                .body(notNullValue());
    }

    @Test
    @Description("Создание пользователя (money - String, 400 Bad Request)")
    public void newUserNeg_Test() throws IOException {

        InputStream testData = BaseApiTest.class.getClassLoader().getResourceAsStream("usersTestData2.json");

        Response createUser = createUser(testData);

        createUser.then()
                .statusCode(400);
    }

    @Test
    @Description(""" 
            Проверить возможность покупки пользователем автомобиля
            1. Создать юзера с кол-вом денег 35000;
            2. Создать авто со ценой, меньше 35000;
            3. Купить юзером созданный авто;
            4. Проверить ответ сервера на статус ответа    
            5. Получение списка авто и проверка наличия авто у юзера
            6. Невозможность удаления юзера, т.к. за ним прикреплена другая сущность(авто)""")
    public void userBuyCar_Test() throws IOException {

        User requestUser = new User(43, "Юзер", "сБабками", 35000.0, 45, User.Sex.MALE);
        Car requestCar = new ObjectMapper().readValue(BaseApiTest.class.getClassLoader().getResourceAsStream("carsTestData.json"), Car.class);

        Integer newUserId  = createUser(requestUser)
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        Integer newCarId  = createCar(requestCar)
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        requestUser.id = newUserId;
        requestCar.id = newCarId;

        Response userBuyCarResponse = userBuyCar(requestUser, requestCar);

        userBuyCarResponse.then()
                .statusCode(200);

        User userFromBuyRes = userBuyCarResponse.getBody().as(User.class);

        Assertions.assertNotEquals(requestUser.money, userFromBuyRes.money);

        ArrayList<Car> carsFromUser = getUserCars(requestUser)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(new TypeRef<ArrayList<Car>>() {});

        deleteUserById(newUserId)
                .then()
                .statusCode(409);

        deleteCarById(newCarId)
                .then()
                .statusCode(409);
    }
}
