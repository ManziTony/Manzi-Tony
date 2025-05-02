public class SUV extends Vehicle {
    private boolean fourWheelDrive;

    public SUV(String vehicleId, String ownerName, int year, String regNum, double baseTax, boolean fourWheelDrive) {
        super(vehicleId, ownerName, year, regNum, baseTax, "SUV");
        this.fourWheelDrive = fourWheelDrive;
    }

    @Override
    public double calculateTax() {
        double tax = baseTaxRate;
        int age = java.time.Year.now().getValue() - getYearOfFabrication();
        if (fourWheelDrive) tax *= 1.10;
        if (age > 10) tax *= 0.95;
        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println(toString() + " | 4WD: " + fourWheelDrive + " | Tax: " + calculateTax());
    }
}