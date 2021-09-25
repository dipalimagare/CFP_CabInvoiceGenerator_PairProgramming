package cabInvoiceGenerator;

import org.junit.jupiter.api.*;

public class InvoiceGeneratorTest {
    @Test
    public void givenDistanceAndTime_ShouldReturnTotalFare(){
        InvoiceGenerator  invoiceGenerator = new InvoiceGenerator();
        double distance = 2.0;
        int time = 5;
        invoiceGenerator.calculateFare(distance, time);
        double fare = InvoiceGenerator.calculateFare(distance, time);
        Assertions.assertEquals(25, fare, 0.0);
    }

    @Test
    public void givenDistanceAndTime_ShouldReturnMinimumFare(){
        InvoiceGenerator  invoiceGenerator = new InvoiceGenerator();
        double distance = 0.1;
        int time = 1;
        invoiceGenerator.calculateFare(distance, time);
        double fare = InvoiceGenerator.calculateFare(distance, time);
        Assertions.assertEquals(5, fare, 0.0);
    }
    

    
}
