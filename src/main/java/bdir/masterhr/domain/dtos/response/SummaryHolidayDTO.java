package bdir.masterhr.domain.dtos.response;

public class SummaryHolidayDTO {
    private int daysTaken;
    private int daysAvailable;
    private int medicalLeave;
    private int otherLeave;
    private int overtimeLeave;

    public int getDaysTaken() {
        return daysTaken;
    }

    public void setDaysTaken(int daysTaken) {
        this.daysTaken = daysTaken;
    }

    public int getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(int daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public int getMedicalLeave() {
        return medicalLeave;
    }

    public void setMedicalLeave(int medicalLeave) {
        this.medicalLeave = medicalLeave;
    }

    public int getOtherLeave() {
        return otherLeave;
    }

    public void setOtherLeave(int otherLeave) {
        this.otherLeave = otherLeave;
    }

    public int getOvertimeLeave() {
        return overtimeLeave;
    }

    public void setOvertimeLeave(int overtimeLeaveAvailable) {
        this.overtimeLeave = overtimeLeaveAvailable;
    }

    @Override
    public String toString() {
        return "SummaryHolidayDTO{" +
                "daysTaken=" + daysTaken +
                ", daysAvailable=" + daysAvailable +
                ", medicalLeave=" + medicalLeave +
                ", otherLeave=" + otherLeave +
                ", overtimeLeave=" + overtimeLeave +
                '}';
    }
}
