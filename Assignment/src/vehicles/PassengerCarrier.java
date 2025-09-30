package vehicles;

import exceptions.InvalidOperationException;
import exceptions.OverloadException;

public interface PassengerCarrier {
    public abstract void boardPassengers(int count) throws OverloadException;
    public abstract void disembarkPassengers(int count) throws InvalidOperationException;
    int getPassengerCapacity();
    int getCurrentPassengers();
}
