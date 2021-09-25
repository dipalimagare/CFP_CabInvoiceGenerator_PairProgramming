package cabInvoiceGenerator;

public class InvoiceGenerator {

    private static final Double MINIMUM_COST_PER_KILOMETER = 10.0;
    private static final int COST_PER_TIME = 1;

    public static double calculateFare(double distance, int time) {
        return distance*MINIMUM_COST_PER_KILOMETER + time*COST_PER_TIME;
    }
}
