package domain.dtos.response;

import java.time.LocalDate;

public class HolidayDTO {
    private LocalDate fromDate;
    private LocalDate toDate;
    private int numberOfDays;
    private String type;
    private String proxyUsername;
    private String status;

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

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return "HolidayDTO{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", numberOfDays=" + numberOfDays +
                ", type='" + type + '\'' +
                ", proxyUsername='" + proxyUsername + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
