package cabInvoiceGenerator;

import org.junit.jupiter.api.*;

import java.text.*;


public class InvoiceServiceTest {

    InvoiceServices invoiceService =new InvoiceServices();

    @Test
    public void givenDistanceAndTime_shouldReturnTotalFare() {
        double distance = 2.0;
        int time = 5;
        double fare = invoiceService.calculateFareForNormal(distance, time);
        Assertions.assertEquals(25, fare, 0.0);
    }

    @Test
    public void givenLessDistanceAndTime_shouldReturnMinFare() {
        double distance = 0.1;
        int time = 1;
        double fare = invoiceService.calculateFareForNormal(distance, time);
        Assertions.assertEquals(5, fare, 0.0);
    }


    @Test
    public void givenMultipleRide_ShouldReturnTotalFare() {
        Ride[] rides = {new Ride(2.0, 5, InvoiceServices.RideMode.NORMAL),
                new Ride(0.1, 1,InvoiceServices.RideMode.NORMAL),
        };
        InvoiceSummary summary = invoiceService.calculateFareForNormal(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnInvoiceSummary() throws InvoiceGeneratorException {
        String[] userId = {"user1", "user2", "user3"};
        Ride[][] rides ={
                {new Ride(5.0, 12, InvoiceServices.RideMode.NORMAL), new Ride(2.5, 6, InvoiceServices.RideMode.NORMAL)},
                {new Ride(3.0, 5, InvoiceServices.RideMode.NORMAL), new Ride(0.01, 1, InvoiceServices.RideMode.NORMAL)},
                {new Ride(10.0, 15, InvoiceServices.RideMode.NORMAL), new Ride(2, 30, InvoiceServices.RideMode.NORMAL)} };
        invoiceService.addRides(userId, rides);
        InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 93.0);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenDistanceAndTime_shouldReturnPremiumTotalFare() {
        double distance = 2.0;
        int time = 5;
        double fare = invoiceService.calculateFareForPremium(distance, time);
        Assertions.assertEquals(40, fare, 0.0);
    }

    @Test
    public void givenMultipleRide_ShouldReturnPremiumTotalFare() {
        Ride[] rides = {new Ride(2.0, 5, InvoiceServices.RideMode.PREMIUM),
                new Ride(0.1, 1,InvoiceServices.RideMode.PREMIUM),
        };
        InvoiceSummary summary = invoiceService.calculateFareForNormal(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 50);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnBothInvoiceSummary() throws InvoiceGeneratorException {
        String[] userId = {"user1", "user2", "user3"};
        Ride[][] rides ={
                {new Ride(5.0, 12, InvoiceServices.RideMode.NORMAL), new Ride(2.5, 6, InvoiceServices.RideMode.PREMIUM)},
                {new Ride(3.0, 5, InvoiceServices.RideMode.PREMIUM), new Ride(0.01, 1, InvoiceServices.RideMode.NORMAL)},
                {new Ride(10.0, 15, InvoiceServices.RideMode.NORMAL), new Ride(2, 30, InvoiceServices.RideMode.PREMIUM)} };
        invoiceService.addRides(userId, rides);
        InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 111.5);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }
}