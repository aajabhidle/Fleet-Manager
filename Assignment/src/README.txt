# Fleet Management System

This is my assignment for Advanced Programming for the Project of a transportation fleet management system in Java.

## What it does

Manages different types of vehicles like cars, trucks, buses, airplanes and ships. You can:
• Add/remove vehicles
• Make them travel distances
• Refuel them
• Do maintenance
• Save/load data from files
• Search for specific types

## How to run (Windows)

cd src
javac -d ..\out\production\Assignment -cp . *.java vehicles\*.java fleet\*.java exceptions\*.java
cd ..\out\production\Assignment
java Main

## Alternative compilation (if above doesn't work)
cd src
javac -d ..\bin -cp . *.java vehicles\*.java fleet\*.java exceptions\*.java
cd ..\bin
java Main

## Code structure

• vehicles/ - All the vehicle interfaces and classes
• fleet/ - The FleetManager that manages everything
• exceptions/ - Exceptions for errors done specifically
• Main.java - The main program with menu

## Vehicle types

• Car: 5 passengers, 15 km/l fuel economy
• Truck: 5000kg load, 8 km/l (lower when loaded)
• Bus: 50 passengers + 500kg load, 10 km/l
• Airplane: 200 passengers + 10000kg load, 5 km/l
• Ship: 50000kg of cargo, 4 km/l (or sail-powered without fuel)

## Implemented Features

• Inheritance structure (Vehicle -> LandVehicle/AirVehicle/WaterVehicle -> concrete classes)
• Interfaces for fuel, cargo, passengers, maintenance
• Exception handling for overloads and invalid actions
• File I/O with CSV format
• Polymorphism - can call move() on any type of vehicle
• CLI menu system

## Interactive Menu

Following the demo, you will find yourself presented with this menu:

1. Add Vehicle          - Add new vehicles to the fleet
2. Remove Vehicle       - Remove vehicles by ID
3. Start Journey        - Simulate travel for all vehicles
4. Refuel All          - Add fuel to all fuel-powered vehicles
5. Perform Maintenance  - Maintain service vehicles requiring maintenance
6. Create Report     - Observe detailed fleet statistics
7. Save Fleet          - Export fleet data to CSV file
8. Load Fleet          - Import fleet data from CSV file
9. Search by Type      - Search by type or interface
10. List Vehicles Needing Maintenance - Display maintenance queue
11. Exit               - Shutdown the application

## Adding Vehicles

When adding a vehicle, you will be asked for:
• Type: car, truck, bus, airplane, cargoship
• ID: Unique identifier (may not be empty)
• Model: Vehicle model name
• Max Speed: Max speed in km/h
• Additional: Wheels (land vehicles), altitude (aircraft), sail (ships)

## File Operations

• Save: Saves current fleet to fleet_demo.csv
• Load: Loads vehicles from CSV file
• Format: CSV employs comma-separated values with vehicle-specific data

## UML Diagram

A PlantUML class diagram is provided in class_diagram.puml

## Notes

The program first executes a demo with all features, then an interactive menu is opened. Cars require fuel in order to travel (apart from sail ships). Cars require maintenance after 10000km.

CSV files save all the data of vehicles and can be loaded back. The sorting functionality arranges vehicles based on fuel efficiency