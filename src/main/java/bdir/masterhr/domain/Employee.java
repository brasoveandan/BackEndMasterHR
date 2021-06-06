package bdir.masterhr.domain;

import bdir.masterhr.domain.enums.AdminRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.Set;

public class Employee {
    private String username;
    private String password;
    private String personalNumber;
    private AdminRole adminRole;
    private String firstName;
    private String lastName;
    private String mail;
    private String phoneNumber;
    private String socialSecurityNumber;
    private String birthday;
    private String gender;
    private String bankName;
    private String bankAccountNumber;

    @JsonIgnore
    private Contract contract;
    @JsonIgnore
    private Set<Payslip> payslips;
    @JsonIgnore
    private Set<Holiday> holidays;
    @JsonIgnore
    private Set<Timesheet> timesheets;
    @JsonIgnore
    private Set<Request> requests;
    @JsonIgnore
    private Set<Clocking> clockings;

    public Employee() {
        // default constructor
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public AdminRole getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(AdminRole adminRole) {
        this.adminRole = adminRole;
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

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Set<Payslip> getPayslips() {
        return payslips;
    }

    public void setPayslips(Set<Payslip> payslips) {
        this.payslips = payslips;
    }

    public Set<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(Set<Holiday> holidays) {
        this.holidays = holidays;
    }

    public Set<Timesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(Set<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public Set<Clocking> getClockings() {
        return clockings;
    }

    public void setClockings(Set<Clocking> clockings) {
        this.clockings = clockings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(username, employee.username) && Objects.equals(password, employee.password) && Objects.equals(personalNumber, employee.personalNumber) && adminRole == employee.adminRole && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(mail, employee.mail) && Objects.equals(phoneNumber, employee.phoneNumber) && Objects.equals(socialSecurityNumber, employee.socialSecurityNumber) && Objects.equals(birthday, employee.birthday) && Objects.equals(gender, employee.gender) && Objects.equals(bankName, employee.bankName) && Objects.equals(bankAccountNumber, employee.bankAccountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, personalNumber, adminRole, firstName, lastName, mail, phoneNumber, socialSecurityNumber, birthday, gender, bankName, bankAccountNumber);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", adminRole=" + adminRole +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                '}';
    }
}
