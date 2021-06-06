package bdir.masterhr.domain.dtos.response;

public class ClockingDTO {
    private int day;
    private String fromHour;
    private String toHour;
    private String workedHours;
    private String overtimeHours;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getFromHour() {
        return fromHour;
    }

    public void setFromHour(String fromHour) {
        this.fromHour = fromHour;
    }

    public String getToHour() {
        return toHour;
    }

    public void setToHour(String toHour) {
        this.toHour = toHour;
    }

    public String getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(String workedHours) {
        this.workedHours = workedHours;
    }

    public String getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(String overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    @Override
    public String toString() {
        return "ClockingDTO{" +
                "day=" + day +
                ", fromHour=" + fromHour +
                ", toHour=" + toHour +
                ", workedHours=" + workedHours +
                ", overtimeHours=" + overtimeHours +
                '}';
    }
}
