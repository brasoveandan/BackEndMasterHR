package domain.dtos;

import java.time.LocalDateTime;

public class TimesheetDTO {
    private int year;
    private int month;
    private float workedHours;
    private float homeOfficeHours;
    private float requiredHours;
    private float overtimeHours;
    private float totalOvertimeLeave;

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
    public String toString() {
        return "TimesheetDTO{" +
                "year=" + year +
                ", month=" + month +
                ", workedHours=" + workedHours +
                ", homeOfficeHours=" + homeOfficeHours +
                ", requiredHours=" + requiredHours +
                ", overtimeHours=" + overtimeHours +
                ", totalOvertimeLeave=" + totalOvertimeLeave +
                '}';
    }
}
