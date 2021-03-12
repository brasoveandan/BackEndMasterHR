package domain;

import java.util.Objects;

public class Payslip {
    private int idPayslip;
    private String usernameEmployee;
    private int year;
    private int month;
    private float brutSalary;
    private float netSalary;
    private float realizedSalary;
    private float workedHours;
    private float homeOfficeHours;
    private float requiredHours;

    public Payslip() {
    }

    public int getIdPayslip() {
        return idPayslip;
    }

    public void setIdPayslip(int idPayslip) {
        this.idPayslip = idPayslip;
    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getBrutSalary() {
        return brutSalary;
    }

    public void setBrutSalary(float brutSalary) {
        this.brutSalary = brutSalary;
    }

    public float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(float netSalary) {
        this.netSalary = netSalary;
    }

    public float getRealizedSalary() {
        return realizedSalary;
    }

    public void setRealizedSalary(float realizedSalary) {
        this.realizedSalary = realizedSalary;
    }

    public float getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(float workedHours) {
        this.workedHours = workedHours;
    }

    public float getHomeOfficeHours() {
        return homeOfficeHours;
    }

    public void setHomeOfficeHours(float homeOfficeHours) {
        this.homeOfficeHours = homeOfficeHours;
    }

    public float getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(float requiredHours) {
        this.requiredHours = requiredHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payslip payslip = (Payslip) o;
        return idPayslip == payslip.idPayslip && year == payslip.year && month == payslip.month && Float.compare(payslip.brutSalary, brutSalary) == 0 && Float.compare(payslip.netSalary, netSalary) == 0 && Float.compare(payslip.realizedSalary, realizedSalary) == 0 && Float.compare(payslip.workedHours, workedHours) == 0 && Float.compare(payslip.homeOfficeHours, homeOfficeHours) == 0 && Float.compare(payslip.requiredHours, requiredHours) == 0 && Objects.equals(usernameEmployee, payslip.usernameEmployee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPayslip, usernameEmployee, year, month, brutSalary, netSalary, realizedSalary, workedHours, homeOfficeHours, requiredHours);
    }

    @Override
    public String toString() {
        return "Payslip{" +
                "idPayslip=" + idPayslip +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", brutSalary=" + brutSalary +
                ", netSalary=" + netSalary +
                ", realizedSalary=" + realizedSalary +
                ", workedHours=" + workedHours +
                ", homeOfficeHours=" + homeOfficeHours +
                ", requiredHours=" + requiredHours +
                '}';
    }
}
