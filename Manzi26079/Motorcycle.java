public class Motorcycle extends Vehicle {
    private int engineCapacity;

    public Motorcycle(String vehicleId, String ownerName, int year, String regNum, double baseTax, int engineCapacity) {
        super(vehicleId, ownerName, year, regNum, baseTax, "Motorcycle");
        if (engineCapacity <= 0) throw new IllegalArgumentException("Engine capacity must be positive.");
        this.engineCapacity = engineCapacity;
    }

    @Override
    public double calculateTax() {
        double tax = baseTaxRate;
        int age = java.time.Year.now().getValue() - getYearOfFabrication();
        if (engineCapacity > 500) tax *= 1.2;
        tax *= (1 - (0.05 * (age / 5)));
        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println(toString() + " | Engine: " + engineCapacity + "cc | Tax: " + calculateTax());
    }
}