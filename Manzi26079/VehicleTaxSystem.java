import java.util.*;

public class VehicleTaxSystem {
    static Scanner sc = new Scanner(System.in);
    static List<Vehicle> vehicleList = new ArrayList<>();
    static Set<String> regNumbers = new HashSet<>();
    static Set<String> vehicleIds = new HashSet<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Register Vehicle\n2. View Vehicles\n3. Calculate Taxes\n4. Generate Reports\n5. Exit");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: registerVehicle(); break;
                case 2: vehicleList.forEach(System.out::println); break;
                case 3: vehicleList.forEach(v -> System.out.println("Tax: " + v.calculateTax())); break;
                case 4: vehicleList.forEach(Vehicle::generateTaxReport); break;
                case 5: return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    static void registerVehicle() {
        System.out.print("Enter Vehicle Type (Car/Truck/Motorcycle/Bus/SUV): ");
        String type = sc.nextLine();

        System.out.print("Vehicle ID: "); String vid = sc.nextLine();
        if (vehicleIds.contains(vid)) { System.out.println("Duplicate Vehicle ID!"); return; }

        System.out.print("Owner Name: "); String owner = sc.nextLine();
        System.out.print("Year of Fabrication: "); int year = sc.nextInt(); sc.nextLine();
        System.out.print("Registration Number: "); String reg = sc.nextLine();
        if (regNumbers.contains(reg)) { System.out.println("Duplicate Registration!"); return; }

        System.out.print("Base Tax Rate: "); double tax = sc.nextDouble(); sc.nextLine();

        Vehicle vehicle = null;

        switch (type.toLowerCase()) {
            case "car":
                System.out.print("Is Electric (true/false): ");
                boolean electric = sc.nextBoolean(); sc.nextLine();
                vehicle = new Car(vid, owner, year, reg, tax, electric);
                break;
            case "truck":
                System.out.print("Enter Load Capacity (tons): ");
                double load = sc.nextDouble(); sc.nextLine();
                vehicle = new Truck(vid, owner, year, reg, tax, load);
                break;
            case "motorcycle":
                System.out.print("Enter Engine Capacity (cc): ");
                int cc = sc.nextInt(); sc.nextLine();
                vehicle = new Motorcycle(vid, owner, year, reg, tax, cc);
                break;
            case "bus":
                System.out.print("Enter Passenger Capacity: ");
                int passengers = sc.nextInt(); sc.nextLine();
                vehicle = new Bus(vid, owner, year, reg, tax, passengers);
                break;
            case "suv":
                System.out.print("Is 4-Wheel Drive (true/false): ");
                boolean fourWD = sc.nextBoolean(); sc.nextLine();
                vehicle = new SUV(vid, owner, year, reg, tax, fourWD);
                break;
            default:
                System.out.println("Unsupported vehicle type.");
                return;
        }

        vehicleList.add(vehicle);
        regNumbers.add(reg);
        vehicleIds.add(vid);
        System.out.println("Vehicle registered successfully!");
    }
}