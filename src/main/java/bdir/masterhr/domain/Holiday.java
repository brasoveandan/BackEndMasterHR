package bdir.masterhr.domain;

import bdir.masterhr.domain.enums.HolidayType;

import java.time.LocalDate;
import java.util.Objects;

public class Holiday {
    private String idHoliday;
    private String usernameEmployee;
    private Request request;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int overtimeLeave;
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

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request idRequest) {
        this.request = idRequest;
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

    public int getOvertimeLeave() {
        return overtimeLeave;
    }

    public void setOvertimeLeave(int daysOff) {
        this.overtimeLeave = daysOff;
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
        return request == holiday.request && overtimeLeave == holiday.overtimeLeave && Objects.equals(idHoliday, holiday.idHoliday) && Objects.equals(usernameEmployee, holiday.usernameEmployee) && Objects.equals(fromDate, holiday.fromDate) && Objects.equals(toDate, holiday.toDate) && type == holiday.type && Objects.equals(proxyUsername, holiday.proxyUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idHoliday, usernameEmployee, request, fromDate, toDate, overtimeLeave, type, proxyUsername);
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "idHoliday='" + idHoliday + '\'' +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", request=" + request +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", overtimeLeave=" + overtimeLeave +
                ", type=" + type +
                ", proxyUsername='" + proxyUsername + '\'' +
                '}';
    }
}
