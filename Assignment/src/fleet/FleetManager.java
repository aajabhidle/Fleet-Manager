package fleet;

import java.io.*;
import java.util.*;
import vehicles.*;
import exceptions.InvalidOperationException;

public class FleetManager {
    private List<Vehicle> fleet = new ArrayList<>();
    public static String DEFAULT_CSV = "Assignment/src/fleet_demo.csv";


    public String generateReport() {
        int total = fleet.size();
        int cars = 0, trucks = 0, buses = 0, airplanes = 0, cargoships = 0;
        double totalEff = 0.0;
        double totalMileage = 0.0;
        int needsMaint = 0;

        for (Vehicle v : fleet) {
            totalEff += v.calculateFuelEfficiency();
            totalMileage += v.getCurrentMileage();

            if (v instanceof Car) cars++;
            else if (v instanceof Truck) trucks++;
            else if (v instanceof Bus) buses++;
            else if (v instanceof Airplane) airplanes++;
            else if (v instanceof CargoShip) cargoships++;

            if (v instanceof Maintainable) {
                if (((Maintainable) v).needsMaintenance()) needsMaint++;
            }
        }
        double avgEff = (total > 0) ? (totalEff / total) : 0.0;
        StringBuilder sb = new StringBuilder();
        sb.append("Vehicles: ").append(total).append(" [Cars: ").append(cars)
                .append(", Trucks: ").append(trucks)
                .append(", Buses: ").append(buses)
                .append(", Airplanes: ").append(airplanes)
                .append(", CargoShips: ").append(cargoships).append("]\n");
        sb.append(String.format("Average efficiency: %.2f km/l%n", avgEff));
        sb.append(String.format("Cumulative mileage: %.2f km%n", totalMileage));
        sb.append("Maintenance due: ").append(needsMaint).append("\n");
        return sb.toString();
    }

    private Vehicle findById(String id) {
        for (Vehicle v : fleet) {
            if (v.getId().equals(id)) return v;
        }
        return null;
    }


    public void performMaintenance() {
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable) {
                Maintainable m = (Maintainable) v;
                if (m.needsMaintenance()) m.performMaintenance();
            }
        }
    }

    public void addVehicle(Vehicle v) throws InvalidOperationException {
        if (findById(v.getId()) != null) {
            throw new InvalidOperationException("Duplicate ID: " + v.getId());
        }
        fleet.add(v);
        System.out.println("Added " + v.getId());
    }

    public void removeVehicle(String id) throws InvalidOperationException {
        Vehicle target = findById(id);
        if (target == null) {
            throw new InvalidOperationException("ID not found: " + id);
        }
        fleet.remove(target);
        System.out.println("Removed " + id);
    }

    public void startAllJourneys(double distance) {
        for (Vehicle v : fleet) {
            try {
                v.move(distance);
                System.out.println("Journey done for " + v.getId());
            } catch (Exception e) {
                System.out.println("Error for " + v.getId() + ": " + e.getMessage());
            }
        }
    }

    public List<Vehicle> searchByType(Class<?> type) {
        List<Vehicle> res = new ArrayList<>();
        for (Vehicle v : fleet) if (type.isInstance(v)) res.add(v);
        return res;
    }


    public List<Vehicle> getVehiclesNeedingMaintenance() {
        List<Vehicle> res = new ArrayList<>();
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable) {
                Maintainable m = (Maintainable) v;
                if (m.needsMaintenance()) res.add(v);
            }
        }
        return res;
    }

    public List<Vehicle> getFleet() { return fleet; }

    public void saveToFile(String filename) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Vehicle v : fleet) {
                pw.println(v.toCSV());
            }
        }
    }

    public void saveToFile() throws IOException {
        saveToFile(DEFAULT_CSV);
    }

    public void sortFleetByEfficiency() {
        Collections.sort(fleet);
    }

    public int loadFromFile(String filename) throws IOException {
        int loaded = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                String type = p[0].trim();
                try {
                    if ("Car".equalsIgnoreCase(type)) {
                        String id = p[1];
                        String model = p[2];
                        double maxSpeed = Double.parseDouble(p[3]);
                        double mileage = Double.parseDouble(p[4]);
                        double fuel = Double.parseDouble(p[5]);
                        int wheels = Integer.parseInt(p[6]);
                        int capacity = Integer.parseInt(p[7]);
                        int curr = Integer.parseInt(p[8]);
                        Car c = new Car(id, model, maxSpeed, mileage, fuel, wheels, capacity, curr);
                        addVehicle(c);
                        loaded++;
                    } else if ("Truck".equalsIgnoreCase(type)) {
                        Truck t = getTruck(p); //made a helper method to get the truck
                        addVehicle(t);
                        loaded++;
                    } else if ("Bus".equalsIgnoreCase(type)) {
                        String id = p[1];
                        String model = p[2];
                        double maxSpeed = Double.parseDouble(p[3]);
                        double mileage = Double.parseDouble(p[4]);
                        double fuel = Double.parseDouble(p[5]);
                        int wheels = Integer.parseInt(p[6]);
                        int currPassengers = Integer.parseInt(p[7]);
                        double currCargo = Double.parseDouble(p[8]);
                        Bus b = new Bus(id, model, maxSpeed, mileage, fuel, wheels, currPassengers, currCargo);
                        addVehicle(b);
                        loaded++;
                    } else if ("Airplane".equalsIgnoreCase(type)) {
                        String id = p[1];
                        String model = p[2];
                        double maxSpeed = Double.parseDouble(p[3]);
                        double mileage = Double.parseDouble(p[4]);
                        double fuel = Double.parseDouble(p[5]);
                        double altitude = Double.parseDouble(p[6]);
                        int passCap = Integer.parseInt(p[7]);
                        int currPass = Integer.parseInt(p[8]);
                        double cargoCap = Double.parseDouble(p[9]);
                        double currCargo = Double.parseDouble(p[10]);
                        Airplane a = new Airplane(id, model, maxSpeed, mileage, fuel, altitude, passCap, currPass, cargoCap, currCargo);
                        addVehicle(a);
                        loaded++;
                    } else if ("CargoShip".equalsIgnoreCase(type)) {
                        String id = p[1];
                        String model = p[2];
                        double maxSpeed = Double.parseDouble(p[3]);
                        double mileage = Double.parseDouble(p[4]);
                        double fuel = Double.parseDouble(p[5]);
                        boolean hasSail = Boolean.parseBoolean(p[6]);
                        double currCargo = Double.parseDouble(p[7]);
                        CargoShip s = new CargoShip(id, model, maxSpeed, mileage, hasSail, fuel, currCargo);
                        addVehicle(s);
                        loaded++;
                    } else {
                        System.out.println("Unknown type: " + type);
                    }
                } catch (Exception e) {
                    System.out.println(line);
                }
            }
        }
        return loaded;
    }
    private static Truck getTruck(String[] p) throws InvalidOperationException {
        String id = p[1];
        String model = p[2];
        double maxSpeed = Double.parseDouble(p[3]);
        double mileage = Double.parseDouble(p[4]);
        double fuel = Double.parseDouble(p[5]);
        int wheels = Integer.parseInt(p[6]);
        double currentCargo = Double.parseDouble(p[8]);
        Truck t = new Truck(id, model, maxSpeed, mileage, fuel, wheels, currentCargo);
        return t;
    }


    public int loadFromFile() throws IOException {
        return loadFromFile(DEFAULT_CSV);
    }

    public double getTotalFuelConsumption(double distance) {
        double total = 0.0;
        for (Vehicle v : fleet) {
            if (v instanceof FuelConsumable) {
                double eff = v.calculateFuelEfficiency();
                if (eff > 0) total += distance / eff;
            }
        }
        return total;
    }
}