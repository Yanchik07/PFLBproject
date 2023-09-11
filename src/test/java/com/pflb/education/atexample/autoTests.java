package com.pflb.education.atexample;

import com.pflb.education.atexample.config.ApplicationConfig;
import com.pflb.education.atexample.tests.User;
import com.pflb.education.atexample.page.LoginPage;
import com.pflb.education.atexample.page.NavBar;
import com.pflb.education.atexample.page.UserPage;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v115.performance.Performance;
import org.openqa.selenium.devtools.v115.performance.model.Metric;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;


@TestInstance(Lifecycle.PER_CLASS)
    public class autoTests {
    private ChromeDriver driver;

    private WebDriverWait wait;

    private ApplicationConfig config;

    private DevTools devTools;

    @BeforeAll
    public void configInit() {
        config = new ApplicationConfig();
    }

    @BeforeEach
    public void init() throws MalformedURLException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        devTools = driver.getDevTools();
        devTools.createSession();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void loginTest() {
        driver.get(config.baseUrl);
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.fillLoginInput(config.userName);
        loginPage.fillPasswordInput(config.userPassword);
        loginPage.submitForm();
        String alertText = loginPage.getAlertText();
        loginPage.dismissAlert();
        Assertions.assertTrue(alertText.contains("Successful"), "Alert text doesn't contains info about successful auth");
    }

    @Test
    public void sortByAgeAsc() {
        devTools.send(Performance.enable(Optional.empty()));
        List<Metric> perfMetrics = devTools.send(Performance.getMetrics());

        driver.get(config.baseUrl + "/read/users");
        UserPage userPage = new UserPage(driver, wait);

        sendPerfMetricsToReport(perfMetrics);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr")));

        NavBar navBar = new NavBar(driver, wait);

        navBar.sortByAge();

        List<Integer> age = new ArrayList<>(userPage.parseUsers().stream().map(User::getAge).toList());

        List<Integer> age_2 = new ArrayList<>(age);

        Collections.sort(age_2);

        Assertions.assertEquals(age_2, age, "sort by age(ASC) is incorrect");
    }

    @Test
    public void sortByAgeDesc() {
        driver.get(config.baseUrl + "/read/users");
        UserPage userPage = new UserPage(driver, wait);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table tbody tr")));

        NavBar navBar = new NavBar(driver, wait);

        //даблклик для сортировки по убыванию
        navBar.sortById();
        navBar.sortById();

        List<Integer> usersIds = userPage.parseUsers().stream().map(User::getId).toList();

        List<Integer> userIdsCopy = new ArrayList<>(usersIds);

        Collections.sort(userIdsCopy, Collections.reverseOrder());

        Assertions.assertEquals(userIdsCopy, usersIds, "sort by age(Desc) is incorrect");
    }

    @Attachment(value = "Perfomance metrics:", type = "text/html")
    public String sendPerfMetricsToReport(List<Metric> perfMetrics) {

        List<String> perfMetricsStringList = new ArrayList<>();

        for (Metric metric : perfMetrics) {
            perfMetricsStringList.add(metric.getName() + "=" + metric.getValue());
        }

        return String.join("\n", perfMetricsStringList);
    }
}