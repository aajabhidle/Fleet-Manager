package vehicles;

public interface Maintainable {
    public abstract void scheduleMaintenance();
    public abstract boolean needsMaintenance(); // condition given used when overriding
    void performMaintenance();
}
