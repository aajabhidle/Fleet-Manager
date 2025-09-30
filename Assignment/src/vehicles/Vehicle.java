package vehicles;

import exceptions.InvalidOperationException;

public abstract class Vehicle implements Comparable<Vehicle> {
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    public Vehicle(String id, String model, double maxSpeed) throws InvalidOperationException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidOperationException("Invalid");
        }
        this.id = id;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.currentMileage = 0.0;
    }

    public String getId() { return id; }
    public String getModel() { return model; }
    public double getMaxSpeed() { return maxSpeed; }
    public double getCurrentMileage() { return currentMileage; }

    public void updateMileage(double distance) {
        this.currentMileage += distance;
    }

    public void setCurrentMileage(double mileage) {
        this.currentMileage = mileage;
    }

    public abstract void move(double distance) throws InvalidOperationException;
    public abstract double calculateFuelEfficiency();
    public abstract double estimateJourneyTime(double distance);

    public void displayInfo() {
        System.out.println("Vehicle ID "+ id);
        System.out.println("Model "+ model);
        System.out.println("Max Speed "+ maxSpeed);
        System.out.println("Current Mileage "+ currentMileage);
    }

    public int compareTo(Vehicle other) {
        return Double.compare(this.calculateFuelEfficiency(), other.calculateFuelEfficiency());
    }

    public String toString() {
        return getClass().getSimpleName() + " [ID: " + id + ", Model: " + model + 
               ", Max Speed: " + maxSpeed + ",Mileage: " + currentMileage + "]";
    }

    public String toCSV() {
        return getClass().getSimpleName() + "/" + id + "/"+ model + "/" + maxSpeed + "/" + currentMileage;
    }
}