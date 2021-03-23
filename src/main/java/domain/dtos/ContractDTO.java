package domain.dtos;

import java.time.LocalDate;

public class ContractDTO {
    private String firstName;
    private String lastName;
    private String personalNumber;
    private String mail;
    private String phoneNumber;
    private String socialSecurityNumber;
    private String companyName;
    private float grossSalary;
    private LocalDate hireDate;
    private String type;
    private LocalDate expirationDate;
    private String department;
    private String position;

    public ContractDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
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

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    @Override
    public String toString() {
        return "ContractDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", grossSalary=" + grossSalary +
                ", hireDate=" + hireDate +
                ", type='" + type + '\'' +
                ", expirationDate=" + expirationDate +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
