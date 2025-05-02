public class Truck extends Vehicle {
    private double loadCapacity;

    public Truck(String vehicleId, String ownerName, int year, String regNum, double baseTax, double loadCapacity) {
        super(vehicleId, ownerName, year, regNum, baseTax, "Truck");
        if (loadCapacity <= 0) throw new IllegalArgumentException("Load capacity must be positive.");
        this.loadCapacity = loadCapacity;
    }

    @Override
    public double calculateTax() {
        double tax = baseTaxRate;
        int age = java.time.Year.now().getValue() - getYearOfFabrication();
        if (age > 15) tax *= 1.15;
        if (loadCapacity > 10) tax *= 1.25;
        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println(toString() + " | Load Capacity: " + loadCapacity + " tons | Tax: " + calculateTax());
    }
}