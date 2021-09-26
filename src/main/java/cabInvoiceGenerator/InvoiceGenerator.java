package cabInvoiceGenerator;

public class InvoiceGenerator {

    private static final Double MINIMUM_COST_PER_KILOMETER = 10.0;
    private static final int COST_PER_TIME = 1;
    private static final Double MINIMUM_FARE = 5.0;
    RideRepository rideRepository;
    public InvoiceGenerator()
    {
        rideRepository = new RideRepository();
    }

    public static double calculateFare(double distance, double time) {
        double totalFare = distance * MINIMUM_COST_PER_KILOMETER + time * COST_PER_TIME;
        return Math.max(totalFare, MINIMUM_FARE );
    }

    public InvoiceSummery calculateFare(Ride[] rides) {
        double totalFare = 0;
        for (Ride ride: rides){
        totalFare+= this.calculateFare(ride.distance, ride.time);
        }
        return new InvoiceSummery(rides.length, totalFare);
    }

    public void addRideToRepositoy(String[] userId, Ride[][] rides) throws InvoiceGeneratorException {
        for (int i = 0; i < userId.length; i++)
        {
            rideRepository.addRideForUser(userId[i], rides[i]);
        }
    }

    public InvoiceSummery invoiceForUser(String userId) {
        return calculateFare(rideRepository.getRidesForUser(userId));
    }
}



