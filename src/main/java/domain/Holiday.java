package domain;

import domain.enums.HolidayType;

import java.time.LocalDate;
import java.util.Objects;

public class Holiday {
    private String idHoliday;
    private String usernameEmployee;
    private int idRequest;
    private String idTimesheet;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int daysLeft;
    private HolidayType type;
    private String proxyUsername;

    public Holiday() {
        //default constructor
    }

    public String getIdHoliday() {
        return idHoliday;
    }

    public void setIdHoliday(String idHoliday) {
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

    public String getIdTimesheet() {
        return idTimesheet;
    }

    public void setIdTimesheet(String idTimesheet) {
        this.idTimesheet = idTimesheet;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysOff) {
        this.daysLeft = daysOff;
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
        return idRequest == holiday.idRequest && daysLeft == holiday.daysLeft && Objects.equals(idHoliday, holiday.idHoliday) && Objects.equals(usernameEmployee, holiday.usernameEmployee) && Objects.equals(idTimesheet, holiday.idTimesheet) && Objects.equals(fromDate, holiday.fromDate) && Objects.equals(toDate, holiday.toDate) && type == holiday.type && Objects.equals(proxyUsername, holiday.proxyUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHoliday, usernameEmployee, idRequest, idTimesheet, fromDate, toDate, daysLeft, type, proxyUsername);
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "idHoliday='" + idHoliday + '\'' +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", idRequest=" + idRequest +
                ", idTimesheet='" + idTimesheet + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", daysLeft=" + daysLeft +
                ", type=" + type +
                ", proxyUsername='" + proxyUsername + '\'' +
                '}';
    }
}
