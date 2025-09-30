package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;

public interface FuelConsumable {
    public abstract void refuel(double amount) throws InvalidOperationException;
    public abstract double getFuelLevel();
    public abstract double consumeFuel(double distance) throws InsufficientFuelException;
}
