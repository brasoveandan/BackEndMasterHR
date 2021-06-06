package bdir.masterhr.domain.dtos.response;

import java.time.LocalDate;

public class ContractDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String personalNumber;
    private String mail;
    private String phoneNumber;
    private String socialSecurityNumber;
    private String companyName;
    private float baseSalary;
    private String currency;
    private LocalDate hireDate;
    private String type;
    private LocalDate expirationDate;
    private String department;
    private String position;
    private String birthday;
    private String gender;
    private String bankName;
    private String bankAccountNumber;
    private int overtimeIncreasePercent;
    private boolean taxExempt;
    private float ticketValue;
    private int daysOff;

    public ContractDTO() {
    }

    public ContractDTO(String firstName, String lastName, String personalNumber, String mail, String phoneNumber, String socialSecurityNumber,
                       String companyName, float baseSalary, String currency, LocalDate hireDate, LocalDate expirationDate, String department,
                       String position, String birthday, String gender, String bankName, String bankAccountNumber, int overtimeIncreasePercent,
                       boolean taxExempt, float ticketValue, int daysOff) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.socialSecurityNumber = socialSecurityNumber;
        this.companyName = companyName;
        this.baseSalary = baseSalary;
        this.currency = currency;
        this.hireDate = hireDate;
        this.expirationDate = expirationDate;
        this.department = department;
        this.position = position;
        this.birthday = birthday;
        this.gender = gender;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.overtimeIncreasePercent = overtimeIncreasePercent;
        this.taxExempt = taxExempt;
        this.ticketValue = ticketValue;
        this.daysOff = daysOff;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(float baseSalary) {
        this.baseSalary = baseSalary;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
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
    public String toString() {
        return "ContractDTO{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", baseSalary=" + baseSalary +
                ", currency='" + currency + '\'' +
                ", hireDate=" + hireDate +
                ", type='" + type + '\'' +
                ", expirationDate=" + expirationDate +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", overtimeIncreasePercent=" + overtimeIncreasePercent +
                ", taxExempt=" + taxExempt +
                ", ticketValue=" + ticketValue +
                ", daysOff=" + daysOff +
                '}';
    }
}
