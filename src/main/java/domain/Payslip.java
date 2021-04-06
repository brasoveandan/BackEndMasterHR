package domain;

import java.util.Objects;

public class Payslip {
    private String idPayslip;
    private String usernameEmployee;
    private int year;
    private int month;
    private float grossSalary;
    private float netSalary;
    private float workedHours;
    private float homeOfficeHours;
    private float requiredHours;

    public Payslip() {
        //default constructor
    }

    public String getIdPayslip() {
        return idPayslip;
    }

    public void setIdPayslip(String idPayslip) {
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

    public float getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(float brutSalary) {
        this.grossSalary = brutSalary;
    }

    public float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(float netSalary) {
        this.netSalary = netSalary;
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
        return year == payslip.year && month == payslip.month && Float.compare(payslip.grossSalary, grossSalary) == 0 && Float.compare(payslip.netSalary, netSalary) == 0 && Float.compare(payslip.workedHours, workedHours) == 0 && Float.compare(payslip.homeOfficeHours, homeOfficeHours) == 0 && Float.compare(payslip.requiredHours, requiredHours) == 0 && Objects.equals(idPayslip, payslip.idPayslip) && Objects.equals(usernameEmployee, payslip.usernameEmployee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPayslip, usernameEmployee, year, month, grossSalary, netSalary, workedHours, homeOfficeHours, requiredHours);
    }

    @Override
    public String toString() {
        return "Payslip{" +
                "idPayslip=" + idPayslip +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", brutSalary=" + grossSalary +
                ", netSalary=" + netSalary +
                ", workedHours=" + workedHours +
                ", homeOfficeHours=" + homeOfficeHours +
                ", requiredHours=" + requiredHours +
                '}';
    }
}
