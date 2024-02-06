package com.epam.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.epam.cucumber.stepdefinitions","com.epam.cucumber.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        tags = "@GetPetsTests"
)
public class CucumberRunnerTests extends AbstractTestNGCucumberTests {
}