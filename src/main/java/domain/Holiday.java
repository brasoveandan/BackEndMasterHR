package domain;

import domain.enums.HolidayType;

import java.util.Date;
import java.util.Objects;

public class Holiday {
    public int idHoliday;
    private String usernameEmployee;
    private int idRequest;
    private Date fromDate;
    private Date toDate;
    private int daysOff;
    private HolidayType type;
    private String proxyUsername;

    public Holiday() {
        //default constructor
    }

    public int getIdHoliday() {
        return idHoliday;
    }

    public void setIdHoliday(int idHoliday) {
        this.idHoliday = idHoliday;
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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getDaysOff() {
        return daysOff;
    }

    public void setDaysOff(int daysOff) {
        this.daysOff = daysOff;
    }

    public HolidayType getType() {
        return type;
    }

    public void setType(HolidayType type) {
        this.type = type;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return idHoliday == holiday.idHoliday && idRequest == holiday.idRequest && daysOff == holiday.daysOff && Objects.equals(usernameEmployee, holiday.usernameEmployee) && Objects.equals(fromDate, holiday.fromDate) && Objects.equals(toDate, holiday.toDate) && type == holiday.type && Objects.equals(proxyUsername, holiday.proxyUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHoliday, usernameEmployee, idRequest, fromDate, toDate, daysOff, type, proxyUsername);
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "idHoliday=" + idHoliday +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", idRequest=" + idRequest +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", daysOff=" + daysOff +
                ", type=" + type +
                ", proxyUsername='" + proxyUsername + '\'' +
                '}';
    }
}
