package com.restful.booker.bookinginfo;

import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class BookingCURDTestWithSteps extends TestBase {
    static String firstname = "Prime" + TestUtils.getRandomValue();
    static String lastname = "Tester" + TestUtils.getRandomValue();
    static int totalprice = Integer.parseInt(1 + TestUtils.getRandomValue());
    static boolean depositpaid = true;
    static String additionalneeds = "Lunch";
    static String token;
    static int id;

    @Steps
    BookingSteps bookingSteps;
    @Steps
    AuthorisationSteps authorisationSteps;

    @Title("This method will create a Token")
    @Test
    public void test001() {
        ValidatableResponse response = authorisationSteps.getToken().statusCode(200);
        token = response.extract().path("token");
    }

    @Title("This method will create and verify a booking")
    @Test
    public void test002() {
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2023-07-01");
        bookingdates.setCheckout("2023-08-01");
        ValidatableResponse response = bookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds).statusCode(200);
        id = response.extract().path("bookingid");

        ValidatableResponse validate = bookingSteps.getAllBookingIDs();
        ArrayList<?> booking = validate.extract().path("bookingid");
        Assert.assertTrue(booking.contains(id));
    }

    @Title("This method will get booking with Id")
    @Test
    public void test003() {
        bookingSteps.getSingleBookingIDs(id).statusCode(200);
    }

    @Title("This method will updated a booking with ID")
    @Test
    public void test004() {
        additionalneeds = "Breakfast";
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2023-09-01");
        bookingdates.setCheckout("2023-10-01");
        bookingSteps.updateBookingWithID(id, token, firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);
        ValidatableResponse response = bookingSteps.getSingleBookingIDs(id);
        HashMap<String, ?> update = response.extract().path("");
        Assert.assertThat(update, hasValue(firstname));
    }

    @Title("This method will delete a booking with ID")
    @Test
    public void test005() {
        bookingSteps.deleteABookingID(id, token).statusCode(201);
        bookingSteps.getSingleBookingIDs(id).statusCode(404);
    }

}
