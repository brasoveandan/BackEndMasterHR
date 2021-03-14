package domain;

import java.util.Objects;

public class Timesheet {
    private String idTimesheet;
    private String usernameEmployee;
    private int year;
    private int month;
    private float workedHours;
    private float homeOfficeHours;
    private float requiredHours;
    private float overtimeHours;
    private float totalOvertimeLeave;

    public Timesheet() {
        //default constructor
    }

    public String getIdTimesheet() {
        return idTimesheet;
    }

    public void setIdTimesheet(String idTimesheet) {
        this.idTimesheet = idTimesheet;
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

    public float getTotalOvertimeLeave() {
        return totalOvertimeLeave;
    }

    public void setTotalOvertimeLeave(float totalOvertimeLeave) {
        this.totalOvertimeLeave = totalOvertimeLeave;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timesheet timesheet = (Timesheet) o;
        return year == timesheet.year && month == timesheet.month && Float.compare(timesheet.workedHours, workedHours) == 0 && Float.compare(timesheet.homeOfficeHours, homeOfficeHours) == 0 && Float.compare(timesheet.requiredHours, requiredHours) == 0 && Float.compare(timesheet.overtimeHours, overtimeHours) == 0 && Float.compare(timesheet.totalOvertimeLeave, totalOvertimeLeave) == 0 && Objects.equals(idTimesheet, timesheet.idTimesheet) && Objects.equals(usernameEmployee, timesheet.usernameEmployee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTimesheet, usernameEmployee, year, month, workedHours, homeOfficeHours, requiredHours, overtimeHours, totalOvertimeLeave);
    }

    @Override
    public String toString() {
        return "Timesheet{" +
                "idTimesheet='" + idTimesheet + '\'' +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", workedHours=" + workedHours +
                ", homeOfficeHours=" + homeOfficeHours +
                ", requiredHours=" + requiredHours +
                ", overtimeHours=" + overtimeHours +
                ", totalOvertimeLeave=" + totalOvertimeLeave +
                '}';
    }
}
