package bdir.masterhr.domain;

import bdir.masterhr.domain.enums.ContractType;

import java.time.LocalDate;
import java.util.Objects;

public class Contract {
    private String usernameEmployee;
    private String companyName;
    private float baseSalary;
    private String currency;
    private LocalDate hireDate;
    private ContractType type;
    private LocalDate expirationDate;
    private String department;
    private String position;
    private int overtimeIncreasePercent;
    private boolean taxExempt;
    private float ticketValue;
    private int daysOff;
    private Employee employee;

    public Contract() {
        //default constructor
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(float grossSalary) {
        this.baseSalary = grossSalary;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getOvertimeIncreasePercent() {
        return overtimeIncreasePercent;
    }

    public void setOvertimeIncreasePercent(int overtimeIncreasePercent) {
        this.overtimeIncreasePercent = overtimeIncreasePercent;
    }

    public boolean isTaxExempt() {
        return taxExempt;
    }

    public void setTaxExempt(boolean taxExempt) {
        this.taxExempt = taxExempt;
    }

    public float getTicketValue() {
        return ticketValue;
    }

    public void setTicketValue(float ticketValue) {
        this.ticketValue = ticketValue;
    }

    public int getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(int daysOff) {
        this.daysOff = daysOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return Float.compare(contract.baseSalary, baseSalary) == 0 && overtimeIncreasePercent == contract.overtimeIncreasePercent && taxExempt == contract.taxExempt && Float.compare(contract.ticketValue, ticketValue) == 0 && daysOff == contract.daysOff && Objects.equals(usernameEmployee, contract.usernameEmployee) && Objects.equals(companyName, contract.companyName) && Objects.equals(currency, contract.currency) && Objects.equals(hireDate, contract.hireDate) && type == contract.type && Objects.equals(expirationDate, contract.expirationDate) && Objects.equals(department, contract.department) && Objects.equals(position, contract.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usernameEmployee, companyName, baseSalary, currency, hireDate, type, expirationDate, department, position, overtimeIncreasePercent, taxExempt, ticketValue, daysOff);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "employee=" + usernameEmployee +
                ", companyName='" + companyName + '\'' +
                ", baseSalary=" + baseSalary +
                ", currency='" + currency + '\'' +
                ", hireDate=" + hireDate +
                ", type=" + type +
                ", expirationDate=" + expirationDate +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", overtimeIncreasePercent=" + overtimeIncreasePercent +
                ", taxExempt=" + taxExempt +
                ", ticketValue=" + ticketValue +
                ", daysOff=" + daysOff +
                '}';
    }
}
