package vehicles;

public abstract class LandVehicle extends Vehicle {
    private int numWheels;

    public LandVehicle(String id, String model, double maxSpeed, int numWheels) throws exceptions.InvalidOperationException {
        super(id, model, maxSpeed);
        this.numWheels = numWheels;
    }
    public int getNumWheels() {
        return numWheels;
    }
    public double estimateJourneyTime(double distance) {
        double baseTime = distance / getMaxSpeed();
        return baseTime * 1.1; // As given
    }

}
