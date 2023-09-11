package com.pflb.education.atexample.cucumber.stepdefinition;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.pflb.education.atexample.config.ApplicationConfig;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import com.codeborne.selenide.Configuration;


public class LoginStepDef {

    ApplicationConfig config = new ApplicationConfig();

    @Дано("Открытие главное страницы")
    public void openPage() {
        open("/");
    }

    @Когда("^Ввод логина \"(.+)\"$")
    public void inputLogin(String login) {
        $("input[name=email]").val(login);
    }

    @И("Ввод пароля")
    public void inputPassword(String password) {
        $("input[name=password]").val(password);
    }

    @И("Нажать на кнопку")
    public void clickSubmit(String arg0) {
        $("button[type=submit]")
                .should(Condition.enabled)
                .click();
    }

    @Тогда("^появляется окно со словом \"(.+)\"$")
    public void checkAlert(String string) {

        String alertText = Selenide.Wait().until(d -> {
            Alert alert = Selenide.switchTo().alert();
            String text = alert.getText();
            return text;
        });

        Assertions.assertTrue(alertText.contains(string), "Alert text doesn't contains info about successful auth");
    }

    @Тогда("нажать кнопку согласия в появившемся окне")
    public void clickYesButtonOnAlert() {
        Alert alert = Selenide.switchTo().alert();
        alert.accept();
    }
}
