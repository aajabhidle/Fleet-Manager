package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;
import exceptions.OverloadException;

public class CargoShip extends WaterVehicle implements CargoCarrier, Maintainable, FuelConsumable {
    private double cargoCapacity;
    private double currentCargo;
    private boolean maintenanceNeeded;
    private double fuelLevel;

    public CargoShip(String id, String model, double maxSpeed, boolean hasSail) throws InvalidOperationException {
        super(id, model, maxSpeed, hasSail);
        this.cargoCapacity = 50000.0;
        this.currentCargo = 0.0;
        this.maintenanceNeeded = false;
        this.fuelLevel = 0.0;
    }

    public CargoShip(String id, String model, double maxSpeed, double mileage, boolean hasSail, double fuel, double currCargo) throws InvalidOperationException {
        super(id, model, maxSpeed, hasSail);
        this.cargoCapacity = 50000.0;
        this.currentCargo = currCargo;
        this.maintenanceNeeded = false;
        this.fuelLevel = fuel;
        setCurrentMileage(mileage);
    }


    public double calculateFuelEfficiency() {
        if (hasSail()) return 0.0;
        return 4.0;
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



    public void move(double distance) throws InvalidOperationException {
        if (distance < 0) throw new InvalidOperationException("Distance cannot be negative");

        if (!hasSail()) {
            try {
                double fuelConsumed = consumeFuel(distance);
                System.out.println("Consumed " + fuelConsumed + " liters of fuel");
            } catch (InsufficientFuelException e) {
                System.out.println("Cannot move: " + e.getMessage());
                return;
            }
        } else {
            System.out.println("Using sails");
        }
        updateMileage(distance);
    }

    public double consumeFuel(double distance) throws InsufficientFuelException {
        if (hasSail()) return 0.0;
        double requiredFuel = distance / calculateFuelEfficiency();
        if (fuelLevel < requiredFuel) {
            throw new InsufficientFuelException("Insufficient fuel");
        }
        fuelLevel -= requiredFuel;
        return requiredFuel;
    }
    public void loadCargo(double weight) throws OverloadException {
        if (currentCargo + weight > cargoCapacity) {
            throw new OverloadException("more than limit ");

        }
        currentCargo += weight;
        System.out.println("Loaded ");
    }
    public void unloadCargo(double weight) throws InvalidOperationException {
        if (weight > currentCargo) {
            throw new InvalidOperationException("invalid input");
        }
        currentCargo -= weight;
        System.out.println("Unloaded ");
    }

    public double getCargoCapacity() { return cargoCapacity; }
    public double getCurrentCargo() { return currentCargo; }

    public void scheduleMaintenance() {
        maintenanceNeeded = true;
        System.out.println("Maintenance required for cargo ship " + getId());
    }

    public boolean needsMaintenance() {
        return getCurrentMileage() > 10000 || maintenanceNeeded;
    }

    public void performMaintenance() {
        maintenanceNeeded = false;
        System.out.println("Maintenance done on ship " + getId());
    }

    public String toString() {
        String fuelInfo = hasSail() ? "Sail-powered" : "Fuel: " + fuelLevel;
        return super.toString() + " [" + fuelInfo + ", Cargo: " + currentCargo + "/" + cargoCapacity + " kg]";
    }

    public String toCSV() {
        return "CargoShip," + getId() + "/" + getModel() + "/" + getMaxSpeed() + "/" + getCurrentMileage() +
               "/" + fuelLevel + "/" + hasSail() + "/" + currentCargo;
    }
}