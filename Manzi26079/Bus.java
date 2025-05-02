public class Bus extends Vehicle {
    private int passengerCapacity;

    public Bus(String vehicleId, String ownerName, int year, String regNum, double baseTax, int passengerCapacity) {
        super(vehicleId, ownerName, year, regNum, baseTax, "Bus");
        if (passengerCapacity <= 0) throw new IllegalArgumentException("Passenger capacity must be positive.");
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public double calculateTax() {
        double tax = baseTaxRate;
        int age = java.time.Year.now().getValue() - getYearOfFabrication();
        tax *= 1 + (0.02 * (passengerCapacity / 10));
        if (age > 20) tax *= 1.10;
        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println(toString() + " | Passengers: " + passengerCapacity + " | Tax: " + calculateTax());
    }
}