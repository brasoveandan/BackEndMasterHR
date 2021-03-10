package domain;

import domain.enums.ContractType;

import java.util.Date;
import java.util.Objects;

public class Contract {
    private String usernameEmployee;
    private String companyName;
    private float grossSalary;
    private Date hireDate;
    private ContractType type;
    private Date expirationDate;
    private String department;
    private String position;

    public Contract() {
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

    public float getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(float grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return Objects.equals(usernameEmployee, contract.usernameEmployee) && Objects.equals(companyName, contract.companyName) && Objects.equals(grossSalary, contract.grossSalary) && Objects.equals(hireDate, contract.hireDate) && type == contract.type && Objects.equals(expirationDate, contract.expirationDate) && Objects.equals(department, contract.department) && Objects.equals(position, contract.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usernameEmployee, companyName, grossSalary, hireDate, type, expirationDate, department, position);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "usernameEmployee='" + usernameEmployee + '\'' +
                ", companyName='" + companyName + '\'' +
                ", grossSalary=" + grossSalary +
                ", hireDate=" + hireDate +
                ", type=" + type +
                ", expirationDate=" + expirationDate +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
