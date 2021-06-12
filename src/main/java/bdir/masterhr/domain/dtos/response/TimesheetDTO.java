package bdir.masterhr.domain.dtos.response;


import bdir.masterhr.domain.enums.TimesheetStatus;

public class TimesheetDTO {
    private String usernameEmployee;
    private String personalNumber;
    private String department;
    private int year;
    private int month;
    private float workedHours;
    private float homeOfficeHours;
    private float requiredHours;
    private float overtimeHours;
    private float totalOvertimeHours;
    private TimesheetStatus status;
    private int numberOfHoursContract;

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
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

    public float getTotalOvertimeHours() {
        return totalOvertimeHours;
    }

    public void setTotalOvertimeHours(float totalOvertimeHours) {
        this.totalOvertimeHours = totalOvertimeHours;
    }

    public TimesheetStatus getStatus() {
        return status;
    }

    public void setStatus(TimesheetStatus status) {
        this.status = status;
    }

    public int getNumberOfHoursContract() {
        return numberOfHoursContract;
    }

    public void setNumberOfHoursContract(int numberOfHoursContract) {
        this.numberOfHoursContract = numberOfHoursContract;
    }

    @Override
    public String toString() {
        return "TimesheetDTO{" +
                "usernameEmployee='" + usernameEmployee + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", department='" + department + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", workedHours=" + workedHours +
                ", homeOfficeHours=" + homeOfficeHours +
                ", requiredHours=" + requiredHours +
                ", overtimeHours=" + overtimeHours +
                ", totalOvertimeHours=" + totalOvertimeHours +
                ", status=" + status +
                ", numberOfHoursContract=" + numberOfHoursContract +
                '}';
    }
}
