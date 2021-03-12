package domain;

import java.util.Date;
import java.util.Objects;

public class Timesheet {
    private int idTimesheet;
    private String usernameEmployee;
    private int idRequest;
    private Date toDate;
    private float workedHours;
    private float homeOfficeHours;
    private float requiredHours;
    private float overtimeHours;
    private float overtimeLeave;

    public Timesheet() {
        //default constructor
    }

    public int getIdTimesheet() {
        return idTimesheet;
    }

    public void setIdTimesheet(int idTimesheet) {
        this.idTimesheet = idTimesheet;
    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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

    public float getOvertimeLeave() {
        return overtimeLeave;
    }

    public void setOvertimeLeave(float overtimeLeave) {
        this.overtimeLeave = overtimeLeave;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timesheet timesheet = (Timesheet) o;
        return idRequest == timesheet.idRequest && Float.compare(timesheet.workedHours, workedHours) == 0 && Float.compare(timesheet.homeOfficeHours, homeOfficeHours) == 0 && Float.compare(timesheet.requiredHours, requiredHours) == 0 && Float.compare(timesheet.overtimeHours, overtimeHours) == 0 && Float.compare(timesheet.overtimeLeave, overtimeLeave) == 0 && Objects.equals(idTimesheet, timesheet.idTimesheet) && Objects.equals(usernameEmployee, timesheet.usernameEmployee) && Objects.equals(toDate, timesheet.toDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTimesheet, usernameEmployee, idRequest, toDate, workedHours, homeOfficeHours, requiredHours, overtimeHours, overtimeLeave);
    }

    @Override
    public String toString() {
        return "Timesheet{" +
                "idTimesheet='" + idTimesheet + '\'' +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", idRequest=" + idRequest +
                ", toDate=" + toDate +
                ", workedHours=" + workedHours +
                ", homeOfficeHours=" + homeOfficeHours +
                ", requiredHours=" + requiredHours +
                ", overtimeHours=" + overtimeHours +
                ", overtimeLeave=" + overtimeLeave +
                '}';
    }
}
