package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;
import exceptions.OverloadException;

public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable {
    private double fuelLevel;
    private double cargoCapacity;
    private double currentCargo;
    private boolean maintenanceNeeded;

    public Truck(String id, String model, double maxSpeed, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed, numWheels);
        this.fuelLevel = 0.0;
        this.cargoCapacity = 5000.0;
        this.currentCargo = 0.0;
        this.maintenanceNeeded = false;
    }

    public Truck(String id, String model, double maxSpeed, double mileage, double fuel, int wheels, double currentCargo) throws InvalidOperationException {
        super(id, model, maxSpeed, wheels);
        this.fuelLevel = fuel;
        this.cargoCapacity = 5000.0;
        this.currentCargo = currentCargo;
        this.maintenanceNeeded = false;
        setCurrentMileage(mileage);
    }


    public double calculateFuelEfficiency() {
        double baseEfficiency = 8.0;
        if (currentCargo > cargoCapacity * 0.5) {
            return baseEfficiency * 0.9;
        }
        return baseEfficiency;
    }

    public void move(double distance) throws InvalidOperationException {
        if (distance < 0) throw new InvalidOperationException("Distance cannot be negative");

        try {
            double fuelConsumed = consumeFuel(distance);
            System.out.println("Consumed " + fuelConsumed + " liters of fuel");
        } catch (InsufficientFuelException e) {
            System.out.println("Cannot move: " + e.getMessage());
            return;
        }

        updateMileage(distance);
    }

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
            throw new InsufficientFuelException("Insufficient fuel ");
        }
        fuelLevel -= requiredFuel;
        return requiredFuel;
    }

    public double getCargoCapacity() { return cargoCapacity; }
    public double getCurrentCargo() { return currentCargo; }

    public void unloadCargo(double weight) throws InvalidOperationException {
        if (weight > currentCargo) {
            throw new InvalidOperationException("Check input weight" );
        }
        currentCargo -= weight;
        System.out.println("Unloaded " + weight);
    }
    public void loadCargo(double weight) throws OverloadException {
        if (currentCargo + weight > cargoCapacity) {
            throw new OverloadException("Cannot load " + weight  + "Because of overloading!");
        }
        currentCargo += weight;
        System.out.println("Loaded " + weight + " Total cargo: " + currentCargo );
    }


    public boolean needsMaintenance() {
        return getCurrentMileage() > 10000 || maintenanceNeeded;
    }

    public void scheduleMaintenance() {
        maintenanceNeeded = true;
        System.out.println("Maintenance for truck " + getId());
    }

    public void performMaintenance() {
        maintenanceNeeded = false;
        System.out.println("Maintenance done on truck " + getId());
    }

    public String toString() {
        return super.toString() + " [Fuel: " + fuelLevel + "L, Cargo: " + currentCargo + "/" + cargoCapacity + " kg]";
    }

    public String toCSV() {
        return "Truck," + getId() + "/" + getModel() + "/" + getMaxSpeed() + "/" + getCurrentMileage() +
               "/" + fuelLevel + "/" + 6 + "/" + cargoCapacity + "/" + currentCargo;
    }
}