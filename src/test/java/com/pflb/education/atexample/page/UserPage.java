package com.pflb.education.atexample.page;

import java.util.ArrayList;
import java.util.List;

import com.pflb.education.atexample.tests.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserPage extends AbstractPage {
    @FindBy(css = "table tbody tr")
    private List<WebElement> userRows;

    @FindBy(css = "#root table")
    private WebElement usersTable; //user table for vanilla Selenium example

    private static final int ID_COL = 0;
    private static final int FIRST_NAME_COL = 1;
    private static final int LAST_NAME_COL = 2;
    private static final int AGE_COL = 3;
    private static final int SEX_COL = 4;
    private static final int MONEY_COL = 5;

    public UserPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    private List<WebElement> getUsersTable() {
        return usersTable.findElements(By.cssSelector("tbody tr"));
    }

    public List<User> parseUsers() {
        List<WebElement> usersTrs = getUsersTable();
        List<User> userList = new ArrayList<>();
        for (WebElement usersTr : usersTrs) {
            WebElement idCol = usersTr.findElements(By.cssSelector("td")).get(ID_COL);
            WebElement fnCol = usersTr.findElements(By.cssSelector("td")).get(FIRST_NAME_COL);
            WebElement lnCol = usersTr.findElements(By.cssSelector("td")).get(LAST_NAME_COL);
            WebElement ageCol = usersTr.findElements(By.cssSelector("td")).get(AGE_COL);
            WebElement sexCol = usersTr.findElements(By.cssSelector("td")).get(SEX_COL);
            WebElement moneyCol = usersTr.findElements(By.cssSelector("td")).get(MONEY_COL);

            Integer id = Integer.parseInt(idCol.getText());
            Integer age = Integer.parseInt(ageCol.getText());
            Double money = Double.parseDouble(moneyCol.getText());

            User user = new User(id, fnCol.getText(),lnCol.getText(),  money, age, User.Sex.valueOf(sexCol.getText()));
            userList.add(user);
        }
        return userList;
    }
}