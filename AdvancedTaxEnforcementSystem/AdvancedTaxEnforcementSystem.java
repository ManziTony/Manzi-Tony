import java.time.LocalDate;
import java.util.*;

interface TaxCalculable {
    double calculateTax();
}

interface Receiptable {
    void generateReceipt();
}

abstract class TaxDeclaration implements TaxCalculable, Receiptable {
    protected String declarationId;
    protected String taxpayerName;
    protected String taxpayerTIN;
    protected LocalDate declarationDate;
    protected double taxAmount;
    protected boolean isPaid;

    public TaxDeclaration(String id, String name, String tin, LocalDate date) {
        if (tin.length() != 9 || !tin.matches("\\d{9}")) throw new IllegalArgumentException("TIN must be 9 digits.");
        if (date.isAfter(LocalDate.now())) throw new IllegalArgumentException("Date cannot be in the future.");
        this.declarationId = id;
        this.taxpayerName = name;
        this.taxpayerTIN = tin;
        this.declarationDate = date;
        this.isPaid = false;
    }

    public abstract boolean validateDeclaration();
    public abstract void enforceCompliance();

    public void markAsPaid() {
        this.isPaid = true;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }
}

class PAYEDeclaration extends TaxDeclaration {
    private double grossSalary;

    public PAYEDeclaration(String id, String name, String tin, LocalDate date, double salary) {
        super(id, name, tin, date);
        this.grossSalary = salary;
    }

    @Override
    public double calculateTax() {
        if (grossSalary <= 0) return 0;
        if (grossSalary <= 300000) return 0.1 * grossSalary;
        else if (grossSalary <= 1000000) return 0.2 * grossSalary;
        else return 0.3 * grossSalary;
    }

    @Override
    public boolean validateDeclaration() {
        return grossSalary > 0;
    }

    @Override
    public void enforceCompliance() {
        if (!isPaid && declarationDate.plusMonths(1).withDayOfMonth(15).isBefore(LocalDate.now())) {
            taxAmount += taxAmount * 0.05; // 5% penalty
        }
    }

    @Override
    public void generateReceipt() {
        System.out.printf("PAYE Receipt for %s: Amount: %.2f Paid: %b\n", taxpayerName, taxAmount, isPaid);
    }
}

class VATDeclaration extends TaxDeclaration {
    private double sales;
    private double purchases;

    public VATDeclaration(String id, String name, String tin, LocalDate date, double sales, double purchases) {
        super(id, name, tin, date);
        this.sales = sales;
        this.purchases = purchases;
    }

    @Override
    public double calculateTax() {
        return 0.18 * (sales - purchases);
    }

    @Override
    public boolean validateDeclaration() {
        return sales > purchases;
    }

    @Override
    public void enforceCompliance() {
        if (!isPaid && declarationDate.plusMonths(1).isBefore(LocalDate.now())) {
            taxAmount += 0.1 * taxAmount; // 10% penalty
        }
    }

    @Override
    public void generateReceipt() {
        System.out.printf("VAT Receipt for %s: VAT Due: %.2f Paid: %b\n", taxpayerName, taxAmount, isPaid);
    }
}

class WithholdingTaxDeclaration extends TaxDeclaration {
    private String category;
    private double baseAmount;

    public WithholdingTaxDeclaration(String id, String name, String tin, LocalDate date, String category, double amount) {
        super(id, name, tin, date);
        this.category = category.toLowerCase();
        this.baseAmount = amount;
    }

    @Override
    public double calculateTax() {
        switch (category) {
            case "services": return 0.15 * baseAmount;
            case "rent": return 0.3 * baseAmount;
            case "dividends": return 0.05 * baseAmount;
            default: return 0;
        }
    }

    @Override
    public boolean validateDeclaration() {
        return baseAmount > 0 && (category.equals("services") || category.equals("rent") || category.equals("dividends"));
    }

    @Override
    public void enforceCompliance() {
        if (!isPaid) taxAmount += 0.1 * taxAmount;
    }

    @Override
    public void generateReceipt() {
        System.out.printf("Withholding Tax Receipt (%s) for %s: Amount: %.2f Paid: %b\n", category, taxpayerName, taxAmount, isPaid);
    }
}

class Taxpayer {
    private String tin;
    private String name;
    private String type;
    private int complianceScore = 100;

    public Taxpayer(String tin, String name, String type) {
        if (!tin.matches("\\d{9}")) throw new IllegalArgumentException("TIN must be 9 digits.");
        this.tin = tin;
        this.name = name;
        this.type = type;
    }

    public String getTIN() { return tin; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getComplianceScore() { return complianceScore; }
    public void reduceScore(int penalty) { complianceScore -= penalty; }
}

class TaxOfficer {
    private String officerId;
    private String fullName;
    private String assignedRegion;
    private List<TaxDeclaration> auditsConducted = new ArrayList<>();

    public TaxOfficer(String id, String name, String region) {
        this.officerId = id;
        this.fullName = name;
        this.assignedRegion = region;
    }

    public void auditDeclaration(TaxDeclaration declaration) {
        auditsConducted.add(declaration);
        System.out.println("Audit completed for: " + declaration.taxpayerName);
    }

    public void generateAuditSummary() {
        System.out.println("Audit Summary for Officer " + fullName);
        for (TaxDeclaration d : auditsConducted) {
            System.out.println("- " + d.getType() + " by " + d.taxpayerName + ", Amount: " + d.taxAmount);
        }
    }
}

public class AdvancedTaxEnforcementSystem {
    private static List<TaxDeclaration> declarations = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Declare PAYE\n2. Declare VAT\n3. Declare Withholding Tax\n4. View Unpaid Taxes\n5. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) handlePAYE();
            else if (choice == 2) handleVAT();
            else if (choice == 3) handleWithholding();
            else if (choice == 4) showUnpaidTaxes();
            else break;
        }
    }

    private static void handlePAYE() {
        System.out.print("Enter ID, Name, TIN, Salary: ");
        String id = sc.next(), name = sc.next(), tin = sc.next();
        double salary = sc.nextDouble();
        TaxDeclaration paye = new PAYEDeclaration(id, name, tin, LocalDate.now(), salary);
        if (paye.validateDeclaration()) {
            paye.taxAmount = paye.calculateTax();
            paye.enforceCompliance();
            paye.generateReceipt();
            declarations.add(paye);
        }
    }

    private static void handleVAT() {
        System.out.print("Enter ID, Name, TIN, Sales, Purchases: ");
        String id = sc.next(), name = sc.next(), tin = sc.next();
        double sales = sc.nextDouble(), purchases = sc.nextDouble();
        TaxDeclaration vat = new VATDeclaration(id, name, tin, LocalDate.now(), sales, purchases);
        if (vat.validateDeclaration()) {
            vat.taxAmount = vat.calculateTax();
            vat.enforceCompliance();
            vat.generateReceipt();
            declarations.add(vat);
        }
    }

    private static void handleWithholding() {
        System.out.print("Enter ID, Name, TIN, Category (services/rent/dividends), Amount: ");
        String id = sc.next(), name = sc.next(), tin = sc.next(), category = sc.next();
        double amount = sc.nextDouble();
        TaxDeclaration wh = new WithholdingTaxDeclaration(id, name, tin, LocalDate.now(), category, amount);
        if (wh.validateDeclaration()) {
            wh.taxAmount = wh.calculateTax();
            wh.enforceCompliance();
            wh.generateReceipt();
            declarations.add(wh);
        }
    }

    private static void showUnpaidTaxes() {
        for (TaxDeclaration d : declarations) {
            if (!d.isPaid) {
                System.out.printf("Unpaid: %s - %s (%.2f)\n", d.taxpayerName, d.getType(), d.taxAmount);
            }
        }
    }
}