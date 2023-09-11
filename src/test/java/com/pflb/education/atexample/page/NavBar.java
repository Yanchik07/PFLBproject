package com.pflb.education.atexample.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavBar extends AbstractPage{

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(3)")
    private WebElement idSort;

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(4)")
    private WebElement firstNameSort;

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(5)")
    private WebElement lastNameSort;

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(6)")
    private WebElement ageSort;

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(7)")
    private WebElement sexSort;

    @FindBy(css = "button[class='btn btn-secondary']:nth-child(8)")
    private WebElement moneySort;



    public NavBar(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void sortById(){
        idSort.click();
    }

    public void sortByFN(){
        firstNameSort.click();
    }

    public void sortByLN(){
        lastNameSort.click();
    }

    public void sortByAge(){
        ageSort.click();
    }

    public void sortBySex(){
        sexSort.click();
    }

    public void sortByMoney(){
        lastNameSort.click();
    }
}
