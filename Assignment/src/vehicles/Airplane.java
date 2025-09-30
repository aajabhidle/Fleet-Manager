package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;
import exceptions.OverloadException;

public class Airplane extends AirVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable {
    private double fuelLevel;
    private int passengerCapacity;
    private int currentPassengers;
    private double cargoCapacity;
    private double currentCargo;
    private boolean maintenanceNeeded;

    public Airplane(String id, String model, double maxSpeed, double maxAltitude) throws InvalidOperationException {
        super(id, model, maxSpeed, maxAltitude);
        this.fuelLevel = 0.0;
        this.passengerCapacity = 200;
        this.currentPassengers = 0;
        this.cargoCapacity = 10000.0;
        this.currentCargo = 0.0;
        this.maintenanceNeeded = false;
    }

    public Airplane(String id, String model, double maxSpeed, double mileage, double fuel, double altitude, int passCap, int currPass, double cargoCap, double currCargo) throws InvalidOperationException {
        super(id, model, maxSpeed, altitude);
        this.fuelLevel = fuel;
        this.passengerCapacity = passCap;
        this.currentPassengers = currPass;
        this.cargoCapacity = cargoCap;
        this.currentCargo = currCargo;
        this.maintenanceNeeded = false;
        setCurrentMileage(mileage);
    }

    public void move(double distance) throws InvalidOperationException {
        if (distance < 0) throw new InvalidOperationException("Distance cannot be negative");
        
        System.out.println("Flying at " + getMaxAltitude());
        
        try {
            double fuelConsumed = consumeFuel(distance);
            System.out.println("Consumed " + fuelConsumed + " liters of fuel");
        } catch (InsufficientFuelException e) {
            System.out.println("Cannot move: " + e.getMessage());
            return;
        }
        
        updateMileage(distance);
    }

    public double calculateFuelEfficiency() { return 5.0; }

    public double estimateJourneyTime(double distance) {
        return super.estimateJourneyTime(distance);
    }
    public double consumeFuel(double distance) throws InsufficientFuelException {
        double requiredFuel = distance / calculateFuelEfficiency();
        if (fuelLevel < requiredFuel) {
            throw new InsufficientFuelException("Insufficient fuel");
        }
        fuelLevel -= requiredFuel;
        return requiredFuel;
    }
    public void boardPassengers(int count) throws OverloadException {
        if (currentPassengers + count > passengerCapacity) {
            throw new OverloadException("Full limit reached");
        }
        currentPassengers += count;
        System.out.println("Boarded ");
    }

    public void refuel(double amount) throws InvalidOperationException {
        if (amount <= 0) throw new InvalidOperationException("Refuel amount must be positive");
        fuelLevel += amount;
        System.out.println("Refueled ");
    }

    public double getFuelLevel() { return fuelLevel; }

    public void disembarkPassengers(int count) throws InvalidOperationException {
        if (count > currentPassengers) {
            throw new InvalidOperationException("Cannot disembark " + count + " passengers. " + "Current passengers: " + currentPassengers);
        }
        currentPassengers -= count;
    }


    public void loadCargo(double weight) throws OverloadException {
        if (currentCargo + weight > cargoCapacity) {
            throw new OverloadException("Cannot load as out of bound");
        }
        currentCargo += weight;
        System.out.println("Loaded ");
    }

    public void unloadCargo(double weight) throws InvalidOperationException {
        if (weight > currentCargo) {
            throw new InvalidOperationException("Cannot unload ");
        }
        currentCargo -= weight;
    }

    public double getCargoCapacity() { return cargoCapacity; }
    public double getCurrentCargo() { return currentCargo; }
    public int getPassengerCapacity() { return passengerCapacity; }
    public int getCurrentPassengers() { return currentPassengers; }

    public void scheduleMaintenance() {
        maintenanceNeeded = true;
        System.out.println("Maintenance needed for airplane " + getId());
    }

    public boolean needsMaintenance() {
        return getCurrentMileage() > 10000 || maintenanceNeeded;
    }

    public void performMaintenance() {
        maintenanceNeeded = false;
        System.out.println("Maintenance performed on airplane " + getId());
    }

    public String toString() {
        return super.toString() + " [Fuel: " + fuelLevel + "L, Passengers: " + currentPassengers + "/" + passengerCapacity + 
               ", Cargo: " + currentCargo + "/" + cargoCapacity + " kg, Altitude: " + getMaxAltitude() + "m]";
    }

    public String toCSV() {
        return "Airplane," + getId() + "/" + getModel() + "/" + getMaxSpeed() + "/" + getCurrentMileage() +
               "/" + fuelLevel + "/" + getMaxAltitude() + "/" + passengerCapacity + "/" + currentPassengers +
               "/" + cargoCapacity + "/" + currentCargo;
    }
}