package com.pflb.education.atexample.tests;

import java.util.ArrayList;

public class UserInfo extends User{
    private ArrayList<Car> cars;
    private Integer houseId;

    public UserInfo(Integer id, String firstName, String secondName, Double money, Integer age, Sex sex, ArrayList<Car> cars, Integer houseId) {
        super(id, firstName, secondName, money, age, sex);
        this.cars = cars;
        this.houseId = houseId;
    }

    public UserInfo(ArrayList<Car> cars, Integer houseId) {
        this.cars = cars;
        this.houseId = houseId;
    }

    public UserInfo(){
    }

}
