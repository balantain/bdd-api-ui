package com.epam.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.epam.stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-reports", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        tags = "@GetPetsTests"
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}