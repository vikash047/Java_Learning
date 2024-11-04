package LLDPracctice.UberRideSharingProblem;

public class Trip implements DriverActions, RiderActions{
    private Request request;

    private Driver driver;
    private Rider rider;
    private int fare;

    private FairCalculation fairCalculation;

    private Status currentTripStatus;

    public Trip(Request request, Driver driver, Rider rider, int fare, FairCalculation fairCalculation) {
        this.request = request;
        this.driver = driver;
        this.rider = rider;
        this.fare = fare;
        this.fairCalculation = fairCalculation;
    }

    public Request getRequest() {
        return request;
    }

    public long getFare() {
        if(fare == 0) {
            return fairCalculation.fare(this);
        }
        return fare;
    }

    public FairCalculation getFairCalculation() {
        return fairCalculation;
    }

    @Override
    public void startTrip() {

    }

    @Override
    public void endTrip() {

    }

    @Override
    public void createRide(Request request) {

    }

    @Override
    public void updateRide(Request request) {

    }

    @Override
    public void withdrawRide(Request request) {

    }

    enum Status {
        IDLE,
        STARTED,
        COMPLETED,
    }
}
