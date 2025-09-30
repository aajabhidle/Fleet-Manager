package vehicles;

import exceptions.InvalidOperationException;
import exceptions.OverloadException;

public interface CargoCarrier {
    public abstract void loadCargo(double weight) throws OverloadException;
    public abstract void unloadCargo(double weight) throws InvalidOperationException;
    double getCargoCapacity();
    double getCurrentCargo();
}
