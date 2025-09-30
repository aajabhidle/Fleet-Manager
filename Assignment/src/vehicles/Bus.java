package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;
import exceptions.OverloadException;

public class Bus extends LandVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable {
    private double fuelLevel;
    private int passengerCapacity;
    private int currentPassengers;
    private double cargoCapacity;
    private double currentCargo;
    private boolean maintenanceNeeded;

    public Bus(String id, String model, double maxSpeed, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed, numWheels);
        this.fuelLevel = 0.0;
        this.passengerCapacity = 50;
        this.currentPassengers = 0;
        this.cargoCapacity = 500.0;
        this.currentCargo = 0.0;
        this.maintenanceNeeded = false;
    }

    public Bus(String id, String model, double maxSpeed, double mileage, double fuel, int wheels, int currPassengers, double currCargo) throws InvalidOperationException {
        super(id, model, maxSpeed, wheels);
        this.fuelLevel = fuel;
        this.passengerCapacity = 50;
        this.currentPassengers = currPassengers;
        this.cargoCapacity = 500.0;
        this.currentCargo = currCargo;
        this.maintenanceNeeded = false;
        setCurrentMileage(mileage);
    }

    public void move(double distance) throws InvalidOperationException {
        if (distance < 0) throw new InvalidOperationException("Distance cannot be negative");
        
        System.out.println("Transporting passengers and cargo...");
        
        try {
            double fuelConsumed = consumeFuel(distance);
            System.out.println("Consumed " + fuelConsumed + " liters of fuel");
        } catch (InsufficientFuelException e) {
            System.out.println("Cannot move: " + e.getMessage());
            return;
        }
        
        updateMileage(distance);
    }

    public double calculateFuelEfficiency() { return 10.0; }

    public double estimateJourneyTime(double distance) {
        return super.estimateJourneyTime(distance);
    }

    public void refuel(double amount) throws InvalidOperationException {
        if (amount <= 0) throw new InvalidOperationException("Refuel amount must be positive");
        fuelLevel += amount;
        System.out.println("Refueled " + amount + " liters. Current fuel level: " + fuelLevel);
    }

    public double getFuelLevel() { return fuelLevel; }

    public double consumeFuel(double distance) throws InsufficientFuelException {
        double requiredFuel = distance / calculateFuelEfficiency();
        if (fuelLevel < requiredFuel) {
            throw new InsufficientFuelException("Insufficient fuel. Required: " + requiredFuel + 
                                              " liters, Available: " + fuelLevel + " liters");
        }
        fuelLevel -= requiredFuel;
        return requiredFuel;
    }

    public void boardPassengers(int count) throws OverloadException {
        if (currentPassengers + count > passengerCapacity) {
            throw new OverloadException("Cannot board " + count + " passengers. " +
                                      "Current: " + currentPassengers + ", Capacity: " + passengerCapacity);
        }
        currentPassengers += count;
        System.out.println("Boarded " + count + " passengers. Total passengers: " + currentPassengers);
    }

    public void disembarkPassengers(int count) throws InvalidOperationException {
        if (count > currentPassengers) {
            throw new InvalidOperationException("Cannot disembark " + count + " passengers. " +
                                              "Current passengers: " + currentPassengers);
        }
        currentPassengers -= count;
        System.out.println("Disembarked " + count + " passengers. Remaining passengers: " + currentPassengers);
    }

    public int getPassengerCapacity() { return passengerCapacity; }
    public int getCurrentPassengers() { return currentPassengers; }

    public void loadCargo(double weight) throws OverloadException {
        if (currentCargo + weight > cargoCapacity) {
            throw new OverloadException("Cannot load " + weight + " kg cargo. " +
                                      "Current: " + currentCargo + " kg, Capacity: " + cargoCapacity + " kg");
        }
        currentCargo += weight;
        System.out.println("Loaded " + weight + " kg cargo. Total cargo: " + currentCargo + " kg");
    }

    public void unloadCargo(double weight) throws InvalidOperationException {
        if (weight > currentCargo) {
            throw new InvalidOperationException("Cannot unload " + weight + " kg cargo. " +
                                              "Current cargo: " + currentCargo + " kg");
        }
        currentCargo -= weight;
        System.out.println("Unloaded " + weight + " kg cargo. Remaining cargo: " + currentCargo + " kg");
    }

    public double getCargoCapacity() { return cargoCapacity; }
    public double getCurrentCargo() { return currentCargo; }

    public void scheduleMaintenance() {
        maintenanceNeeded = true;
        System.out.println("Maintenance scheduled for bus " + getId());
    }

    public boolean needsMaintenance() {
        return getCurrentMileage() > 10000 || maintenanceNeeded;
    }

    public void performMaintenance() {
        maintenanceNeeded = false;
        System.out.println("Maintenance performed on bus " + getId());
    }

    public String toString() {
        return super.toString() + " [Fuel: " + fuelLevel + "L, Passengers: " + currentPassengers + "/" + passengerCapacity + 
               ", Cargo: " + currentCargo + "/" + cargoCapacity + " kg]";
    }

    public String toCSV() {
        return "Bus," + getId() + "/" + getModel() + "/" + getMaxSpeed() + "/" + getCurrentMileage() +
               "/" + fuelLevel + "/" + 6 + "/" + currentPassengers + "/" + currentCargo;
    }
}