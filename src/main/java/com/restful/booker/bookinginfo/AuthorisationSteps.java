package com.restful.booker.bookinginfo;

import com.restful.booker.model.AuthorisationPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class AuthorisationSteps {
    @Step("Getting Access Token")
    public ValidatableResponse getToken() {
        AuthorisationPojo authPojo = new AuthorisationPojo();
        authPojo.setUsername("admin");
        authPojo.setPassword("password123");

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .body(authPojo)
                .post("https://restful-booker.herokuapp.com/auth")
                .then().log().all();
    }
}
