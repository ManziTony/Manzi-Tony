public abstract class Vehicle {
    protected String vehicleId;
    protected String ownerName;
    protected int yearOfFabrication;
    protected String registrationNumber;
    protected double baseTaxRate;
    protected String vehicleType;

    public Vehicle(String vehicleId, String ownerName, int yearOfFabrication, String registrationNumber, double baseTaxRate, String vehicleType) {
        if (yearOfFabrication > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Year of fabrication cannot be in the future.");
        }
        this.vehicleId = vehicleId;
        this.ownerName = ownerName;
        this.yearOfFabrication = yearOfFabrication;
        this.registrationNumber = registrationNumber;
        this.baseTaxRate = baseTaxRate;
        this.vehicleType = vehicleType;
    }

    public abstract double calculateTax();
    public abstract void generateTaxReport();

    @Override
    public String toString() {
        return "Vehicle ID: " + vehicleId + ", Owner: " + ownerName + ", Year: " + yearOfFabrication +
               ", Reg#: " + registrationNumber + ", Base Tax: " + baseTaxRate + ", Type: " + vehicleType;
    }

    public int getYearOfFabrication() { return yearOfFabrication; }
    public String getRegistrationNumber() { return registrationNumber; }
}