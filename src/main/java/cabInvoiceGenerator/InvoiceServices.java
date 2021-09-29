package cabInvoiceGenerator;

public class InvoiceServices {
    private static final int COST_PER_TIME = 1;
    private static final double MINIMUM_COST_PER_KM = 10.0;
    private static final double MINIMUM_FARE = 5;
    private static final int COST_PER_TIME_PREMIUM = 2;
    private static final double MINIMUM_COST_PER_KM_PREMIUM = 15.0;
    private static final double MINIMUM_FARE_PREMIUM = 10;

    int normal = 0;
    int premium = 0;

    public enum RideMode {NORMAL, PREMIUM}

    RideRepository rideRepository;

    public InvoiceServices(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public double calculateFareForNormal(double distance, int time) {
        double totalFare = (distance * MINIMUM_COST_PER_KM) + (time * COST_PER_TIME);
        return Math.max(totalFare, MINIMUM_FARE);
    }

    public double calculateFareForPremium(double distance, int time) {
        double totalFare = (distance * MINIMUM_COST_PER_KM_PREMIUM) + (time * COST_PER_TIME_PREMIUM);
        return Math.max(totalFare, MINIMUM_FARE_PREMIUM);
    }

    public InvoiceSummary calculateFareForNormal(Ride[] rides) {
        double totalFare = 0.0;
        System.out.println("Total Rides- " + rides.length);
        for (Ride ride : rides) {
            if (ride.rideMode.equals(RideMode.NORMAL)) {
                totalFare += this.calculateFareForNormal(ride.distance, ride.time);
                this.normal++;
            } else {
                totalFare += this.calculateFareForPremium(ride.distance, ride.time);
                this.premium++;
            }
        }
        System.out.println("NORMAL Ride- " + this.normal);
        System.out.println("PREMIUM Ride- " + this.premium);
        return new InvoiceSummary(rides.length, totalFare);
    }

    public void addRides(String[] userId, Ride[] rides) throws InvoiceGeneratorException {
        for (int i = 0; i < userId.length; i++) {
            rideRepository.addRides(userId[i], rides);
        }
    }

    public InvoiceSummary getInvoiceSummary(String[] userId) {
        return this.calculateFareForNormal(rideRepository.getRides(userId));
    }
}



