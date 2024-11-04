package LLDPracctice.UberRideSharingProblem;

public class Driver {
    private String id;
    private Car car;

    public Driver(String id) {
        this.id = id;
        this.car = new Car(0, 4);
    }

    public String getId() {
        return id;
    }


    public long currentLocation() {
        return this.car.getCurrentLocation();
    }
    private class Car {
        private long currentLocation;
        private long totalSeats;
        private long freeSeats;

        public Car(long currentLocation, long totalSeats) {
            this.currentLocation = currentLocation;
            this.totalSeats = totalSeats;
            this.freeSeats = totalSeats;
        }

        public long getCurrentLocation() {
            return currentLocation;
        }
    }
}
