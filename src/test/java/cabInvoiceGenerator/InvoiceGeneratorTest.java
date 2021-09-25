package cabInvoiceGenerator;

import org.junit.jupiter.api.*;

public class InvoiceGeneratorTest {
    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare(){
        InvoiceGenerator  invoiceGenerator = new InvoiceGenerator();
        double distance = 2.0;
        int time = 5;
        invoiceGenerator.calculateFare(distance, time);
        double Fare = InvoiceGenerator.calculateFare(distance, time);
        Assertions.assertEquals(25, Fare, 0.0);
    }

    

    
}
