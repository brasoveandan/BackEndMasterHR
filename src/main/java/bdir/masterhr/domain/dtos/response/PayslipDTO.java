package bdir.masterhr.domain.dtos.response;

public class PayslipDTO {
    private int year;
    private int month;
    private String idPayslip;
    private String firstName;
    private String lastName;
    private String companyName;
    private String personalNumber;
    private String department;
    private String position;
    private String currency;
    private float baseSalary;
    private float grossSalary;
    private float overtimeIncreases;
    private float increases;
    private float ticketsValue;
    private float workedHours;
    private float homeOfficeHours;
    private float requiredHours;
    private float overtimeHours;
    private float CAS;
    private float CASS;
    private float IV;
    private boolean taxExempt;
    private float netSalary;

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

    public String getIdPayslip() {
        return idPayslip;
    }

    public void setIdPayslip(String idPayslip) {
        this.idPayslip = idPayslip;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(float baseSalary) {
        this.baseSalary = baseSalary;
    }

    public float getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(float grossSalary) {
        this.grossSalary = grossSalary;
    }

    public float getIncreases() {
        return increases;
    }

    public void setIncreases(float increases) {
        this.increases = increases;
    }

    public float getTicketsValue() {
        return ticketsValue;
    }

    public void setTicketsValue(float ticketsValue) {
        this.ticketsValue = ticketsValue;
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

    public float getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(float overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public float getCAS() {
        return CAS;
    }

    public void setCAS(float CAS) {
        this.CAS = CAS;
    }

    public float getCASS() {
        return CASS;
    }

    public void setCASS(float CASS) {
        this.CASS = CASS;
    }

    public float getIV() {
        return IV;
    }

    public void setIV(float IV) {
        this.IV = IV;
    }

    public boolean isTaxExempt() {
        return taxExempt;
    }

    public void setTaxExempt(boolean taxExempt) {
        this.taxExempt = taxExempt;
    }

    public float getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(float netSalary) {
        this.netSalary = netSalary;
    }

    public float getOvertimeIncreases() {
        return overtimeIncreases;
    }

    public void setOvertimeIncreases(float overtimeIncreases) {
        this.overtimeIncreases = overtimeIncreases;
    }

    @Override
    public String toString() {
        return "PayslipDTO{" +
                "year=" + year +
                ", month=" + month +
                ", idPayslip='" + idPayslip + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", currency='" + currency + '\'' +
                ", baseSalary=" + baseSalary +
                ", grossSalary=" + grossSalary +
                ", overtimeIncreases=" + overtimeIncreases +
                ", increases=" + increases +
                ", ticketsValue=" + ticketsValue +
                ", workedHours=" + workedHours +
                ", homeOfficeHours=" + homeOfficeHours +
                ", requiredHours=" + requiredHours +
                ", overtimeHours=" + overtimeHours +
                ", CAS=" + CAS +
                ", CASS=" + CASS +
                ", IV=" + IV +
                ", taxExempt=" + taxExempt +
                ", netSalary=" + netSalary +
                '}';
    }
}
