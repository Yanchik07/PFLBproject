package com.pflb.education.atexample.tests;

import java.util.Objects;

public class User {
    public Integer id;
    public String firstName;
    public String secondName;
    public Double money;
    public Integer age;
    public Sex sex;

    public enum Sex {
        MALE, FEMALE
    }

    public User(Integer id, String firstName, String secondName, Double money, Integer age, User.Sex sex) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.money = money;
        this.age = age;
        this.sex = sex;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getAge() {
        return age;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", money=" + money +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Integer.compare(user.id, id) == 0
                && Double.compare(user.money, money) == 0
                && Integer.compare(user.age, age) == 0
                && firstName.equals(user.firstName)
                && secondName.equals(user.secondName)
                && sex == user.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, money, age, sex);
    }

}
