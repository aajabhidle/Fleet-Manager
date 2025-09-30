
import fleet.FleetManager;
import vehicles.*;
import vehicles.FuelConsumable;

import java.util.Scanner;
import java.util.List;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static FleetManager fleetManager = new FleetManager();

    public static void main(String[] args) {
        System.out.println("FLEET MANAGEMENT SYSTEM");
        runDemo();
        
        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Enter option: ");
            String choice = input.nextLine();


            switch (choice) {
                case "1": addVehicle(); break;
                case "2": removeVehicle(); break;
                case "3": startJourneys(); break;
                case "4": refuelAll(); break;
                case "5": performMaintenance(); break;
                case "6": generateReport(); break;
                case "7": saveFleet(); break;
                case "8": loadFleet(); break;
                case "9": searchByType(); break;
                case "10": listMaintenance(); break;
                case "11":
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

   private static void runDemo() {
       java.io.PrintStream originalOut = System.out;
       java.io.PrintStream silent = new java.io.PrintStream(new java.io.OutputStream() { public void write(int b) {} });
       System.setOut(silent);

       try {
           Car car = new Car("C001", "Toyota Camry", 120.0, 4);
           Truck truck = new Truck("T001", "Ford F-150", 100.0, 6);
           Bus bus = new Bus("B001", "Mercedes Bus", 90.0, 6);
           Airplane airplane = new Airplane("A001", "Boeing 737", 800.0, 12000.0);
           CargoShip ship = new CargoShip("S001", "Cargo Vessel", 30.0, false);

           fleetManager.addVehicle(car);
           fleetManager.addVehicle(truck);
           fleetManager.addVehicle(bus);
           fleetManager.addVehicle(airplane);
            fleetManager.addVehicle(ship);

           fleetManager.startAllJourneys(100.0);
           fleetManager.generateReport();
            try { fleetManager.saveToFile(); } catch (Exception ignore) {}
       } catch (Exception ignore) {
       } finally {
           System.setOut(originalOut);
       }
        System.out.println("// Demo CSV created //");
   }


    private static void showMenu() {
        System.out.println("MENU FOR VEHICLES");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Remove Vehicle");
        System.out.println("3. Start Journey");
        System.out.println("4. Refuel All");
        System.out.println("5. Perform Maintenance");
        System.out.println("6. Generate Report");
        System.out.println("7. Save Fleet");
        System.out.println("8. Load Fleet");
        System.out.println("9. Search by Type");
        System.out.println("10. List Vehicles Needing Maintenance");
        System.out.println("11. Exit");
    }


    private static void saveFleet() {
        try {
            fleetManager.saveToFile();
            System.out.println("Saved to demo CSV");
            System.out.println("File: " + fleet.FleetManager.DEFAULT_CSV);
        } catch (Exception e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    private static void loadFleet() {
        try {
            // deliberately verbose: show before/after counts
            int before = fleetManager.getFleet().size();
            int loaded = fleetManager.loadFromFile();
            int after = fleetManager.getFleet().size();
            System.out.println("Loaded " + loaded + " (" + before + " -> " + after + ")");
        } catch (Exception e) {
            System.out.println("Error Occured! " + e.getMessage());
        }
    }

    private static void addVehicle() {
        System.out.print("Type: ");
        String type = input.nextLine();
        System.out.print("ID: ");
        String id = input.nextLine();
        System.out.print("Model: ");
        String model = input.nextLine();
        System.out.print("Max Speed: ");
        double maxSpeed = Double.parseDouble(input.nextLine());

        try {
            Vehicle vehicle = null;
            if (type.equalsIgnoreCase("car")) {
                vehicle = new Car(id, model, maxSpeed, 4);
            } else if (type.equalsIgnoreCase("truck")) {
                vehicle = new Truck(id, model, maxSpeed, 6);
            } else if (type.equalsIgnoreCase("bus")) {
                vehicle = new Bus(id, model, maxSpeed, 6);
            } else if (type.equalsIgnoreCase("airplane")) {
                System.out.print("Altitude: ");
                double altitude = Double.parseDouble(input.nextLine());
                vehicle = new Airplane(id, model, maxSpeed, altitude);
            } else if (type.equalsIgnoreCase("cargoship")) {
                System.out.print("Has Sail? (y/n): ");
                boolean hasSail = input.nextLine().toLowerCase().startsWith("y");
                vehicle = new CargoShip(id, model, maxSpeed, hasSail);
            } else {
                System.out.println("Unknown type");
                return;
            }
            fleetManager.addVehicle(vehicle);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void removeVehicle() {
        System.out.print("Vehicle ID: ");
        String id = input.nextLine();
        try {
            fleetManager.removeVehicle(id);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void startJourneys() {
        System.out.print("Distance: ");
        try {
            double distance = Double.parseDouble(input.nextLine());
            fleetManager.startAllJourneys(distance);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void refuelAll() {
        System.out.print("Fuel amount: ");

        try {
            double amount = Double.parseDouble(input.nextLine());
            int count = 0;
            for (Vehicle vehicle : fleetManager.getFleet()) {
                if (vehicle instanceof FuelConsumable) {
                    try {
                        ((FuelConsumable) vehicle).refuel(amount);
                        count++;
                    } catch (Exception e) {
                        System.out.println("Error refueling " + vehicle.getId());
                    }
                }
            }
            System.out.println("Refueled " + count + " vehicles");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void performMaintenance() {
        try {
            fleetManager.performMaintenance();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listMaintenance() {
        try {
            List<Vehicle> vehicles = fleetManager.getVehiclesNeedingMaintenance();
            if (vehicles.isEmpty()) {
                System.out.println("No vehicles need maintenance");
            } else {
                System.out.println("Vehicles needing maintenance:");
                for (Vehicle vehicle : vehicles) {
                    System.out.println("  " + vehicle);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void generateReport() {
        try {
            System.out.println(fleetManager.generateReport());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static void searchByType() {
        System.out.print("Type to search: ");
        String type = input.nextLine();
        
        try {
            Class<?> searchType = null;
            if (type.equalsIgnoreCase("car")) searchType = Car.class;
            else if (type.equalsIgnoreCase("truck")) searchType = Truck.class;
            else if (type.equalsIgnoreCase("bus")) searchType = Bus.class;
            else if (type.equalsIgnoreCase("airplane")) searchType = Airplane.class;
            else if (type.equalsIgnoreCase("cargoship")) searchType = CargoShip.class;
            else if (type.equalsIgnoreCase("fuelconsumable")) searchType = FuelConsumable.class;
            else if (type.equalsIgnoreCase("cargocarrier")) searchType = CargoCarrier.class;
            else if (type.equalsIgnoreCase("passengercarrier")) searchType = PassengerCarrier.class;
            else if (type.equalsIgnoreCase("maintainable")) searchType = Maintainable.class;
            else {
                System.out.println("Invalid type");
                return;
            }
            
            List<Vehicle> results = fleetManager.searchByType(searchType);
            System.out.println("Found " + results.size() + " vehicles");
            for (Vehicle vehicle : results) {
                System.out.println("  " + vehicle);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}