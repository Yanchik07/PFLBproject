package com.pflb.education.atexample.cucumber;

import com.codeborne.selenide.Configuration;
//import com.pflb.education.atexample.conditions.UserHaveMoneyCondition;
import com.pflb.education.atexample.config.ApplicationConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class CucumberHooks {

    @Before
    public void setUp() {
        ApplicationConfig config = new ApplicationConfig();
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        Configuration.browser = "chrome";
        Configuration.timeout = 10_000;
        Configuration.baseUrl = config.baseUrl;
        Configuration.holdBrowserOpen = true;
    }
}
