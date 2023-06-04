package com.restful.booker.bookinginfo;

import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.junit.annotations.Concurrent;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;

@Concurrent(threads = "8x")
@UseTestDataFrom("src/test/java/resources/testdata/bookinginfo.csv")
@RunWith(SerenityParameterizedRunner.class)
public class CreateBookingDataDrivenTest extends TestBase {
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String checkin;
    private String checkout;
    private String additionalneeds;

    @Steps
    BookingSteps bookingSteps;

    @Title("Data driven test for creating multiple bookings")
    @Test
    public void createMultipleBookings() {
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin(checkin);
        bookingdates.setCheckout(checkout);
        bookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds).statusCode(200);
    }
}


