package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;
import exceptions.OverloadException;

public class Car extends LandVehicle implements FuelConsumable, PassengerCarrier, Maintainable {
    private double fuelLevel;
    private int passengerCapacity;
    private int currentPassengers;
    private boolean maintenanceNeeded;

    public Car(String id, String model, double maxSpeed, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed, numWheels);
        this.fuelLevel = 0.0;
        this.passengerCapacity = 5;
        this.currentPassengers = 0;
        this.maintenanceNeeded = false;
    }

    public Car(String id, String model, double maxSpeed, double mileage, double fuel, int wheels, int capacity, int curr) throws InvalidOperationException {
        super(id, model, maxSpeed, wheels);
        this.fuelLevel = fuel;
        this.passengerCapacity = capacity;
        this.currentPassengers = curr;
        this.maintenanceNeeded = false;
        setCurrentMileage(mileage);
    }

    public double calculateFuelEfficiency() { return 15.0; }

    public void move(double distance) throws InvalidOperationException {
        if (distance < 0) throw new InvalidOperationException("Distance negative");
        try {
            double fuelConsumed = consumeFuel(distance);
            System.out.println("Consumed " + fuelConsumed + " liters of fuel");
        } catch (InsufficientFuelException e) {
            System.out.println("Cannot move: " + e.getMessage());
        }

        updateMileage(distance);

    }

    public double estimateJourneyTime(double distance) {
        return super.estimateJourneyTime(distance);
    }

    public void refuel(double amount) throws InvalidOperationException {
        if (amount <= 0) throw new InvalidOperationException("Refuel amount should be positive");
        fuelLevel += amount;
        System.out.println("Refueled ");
    }

    public int getPassengerCapacity() { return passengerCapacity; }
    public int getCurrentPassengers() { return currentPassengers; }

    public double getFuelLevel() { return fuelLevel; }

    public double consumeFuel(double distance) throws InsufficientFuelException {
        double requiredFuel = distance / calculateFuelEfficiency();
        if (fuelLevel < requiredFuel) {
            throw new InsufficientFuelException("Insufficient fuel");
        }
        fuelLevel -= requiredFuel;
        return requiredFuel;
    }

    public void disembarkPassengers(int count) throws InvalidOperationException {
        if (count > currentPassengers) {
            throw new InvalidOperationException("Cannot disembark ");
        }
        currentPassengers -= count;
        System.out.println("Disembarked " + count + " passengers.");
    }

    public void boardPassengers(int count) throws OverloadException {
        if (currentPassengers + count > passengerCapacity) {
            throw new OverloadException("Overloading");
        }
        currentPassengers += count;
        System.out.println("Boarded");
    }


    public void performMaintenance() {
        maintenanceNeeded = false;
        System.out.println("Maintenance done on car " + getId());
    }

    public void scheduleMaintenance() {
        maintenanceNeeded = true;
        System.out.println("Maintenance for car " + getId());
    }

    public boolean needsMaintenance() {
        return getCurrentMileage() > 10000 || maintenanceNeeded;
    }

    public String toCSV() {
        return "Car," + getId() + "/" + getModel() + "/" + getMaxSpeed() + "/" + getCurrentMileage() +
               "/" + fuelLevel + "/" + 4 + "/" + passengerCapacity + "/" + currentPassengers;
    }
}

