package cabInvoiceGenerator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class InvoiceServiceTest {

    @Mock
    RideRepository rideRepository;

    @InjectMocks
    InvoiceServices invoiceService;


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
                new Ride(0.1, 1, InvoiceServices.RideMode.NORMAL),
        };
        InvoiceSummary summary = invoiceService.calculateFareForNormal(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30.0);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnInvoiceSummary() throws InvoiceGeneratorException {
        String[] userId = {"user1", "user2", "user3"};
        Ride[]rides =
                {new Ride(5.0, 12, InvoiceServices.RideMode.NORMAL), new Ride(2.5, 6, InvoiceServices.RideMode.NORMAL)};
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
                new Ride(0.1, 1, InvoiceServices.RideMode.PREMIUM),
        };
        InvoiceSummary summary = invoiceService.calculateFareForNormal(rides);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 50);
        Assertions.assertEquals(expectedInvoiceSummary, summary);
    }

    @Test
    public void givenUserIdAndRides_ShouldReturnBothInvoiceSummary() throws InvoiceGeneratorException {
        String[] userId = {"user1", "user2", "user3"};
        Ride[] rides =
                {new Ride(5.0, 12, InvoiceServices.RideMode.NORMAL), new Ride(2.5, 6, InvoiceServices.RideMode.PREMIUM)};

        when(rideRepository.getRides(userId)).thenReturn(rides);
        InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
        InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 111.5);
        Assertions.assertEquals(expectedInvoiceSummary, summary);

    }

    @Test
    public void givenSameUserId_ShouldThrowException() {
        try {
            String[] userId = {"user1", "user1", "user3"};
            Ride[] rides =
                    {new Ride(5.0, 12, InvoiceServices.RideMode.NORMAL), new Ride(2.5, 6, InvoiceServices.RideMode.NORMAL)};
//            ExpectedException exceptionRule = ExpectedException.none();
//            exceptionRule.expect(InvoiceGeneratorException.class);
            invoiceService.addRides(userId, rides);
        } catch (InvoiceGeneratorException e) {
            e.printStackTrace();
        }
    }
}