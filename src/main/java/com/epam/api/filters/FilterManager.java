package com.epam.api.filters;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.Filter;

public class FilterManager {
    public static Filter getAllureLoggingFilter() {
        return new AllureRestAssured()
                .setResponseAttachmentName("Response");
    }
}
