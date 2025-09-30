package vehicles;

public abstract class AirVehicle extends Vehicle {
    private double maxAltitude;

    public AirVehicle(String id, String model, double maxSpeed, double maxAltitude) throws exceptions.InvalidOperationException {
        super(id, model, maxSpeed);
        this.maxAltitude = maxAltitude;
    }
    public double getMaxAltitude() {
        return maxAltitude;
    }
    public double estimateJourneyTime(double distance) {
        return (distance / getMaxSpeed())* 0.95;
    }


}
