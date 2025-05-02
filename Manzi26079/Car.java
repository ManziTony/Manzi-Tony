public class Car extends Vehicle {
    private boolean isElectric;

    public Car(String vehicleId, String ownerName, int year, String regNum, double baseTax, boolean isElectric) {
        super(vehicleId, ownerName, year, regNum, baseTax, "Car");
        this.isElectric = isElectric;
    }

    @Override
    public double calculateTax() {
        double tax = baseTaxRate;
        int age = java.time.Year.now().getValue() - getYearOfFabrication();
        if (isElectric) tax *= 0.8;
        if (age > 10) tax *= 0.9;
        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println(toString() + " | Tax: " + calculateTax());
    }
}