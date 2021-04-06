package domain.dtos;

public class PayslipDTO {
    private String usernameEmployee;
    private int year;
    private int month;
    private float baseSalary;
    private float grossSalary;
    private float netSalary;
    private float workedHours;
    private float homeOfficeHours;
    private float requiredHours;
    private float CAS;
    private float CASS;
    private float IV;
    private float increases;
    private boolean taxExempt;
    private float ticketsValue;

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

    public float getTicketsValue() {
        return ticketsValue;
    }

    public void setTicketsValue(float ticketsValue) {
        this.ticketsValue = ticketsValue;
    }
}
