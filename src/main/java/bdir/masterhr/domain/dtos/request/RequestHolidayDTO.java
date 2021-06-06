package bdir.masterhr.domain.dtos.request;

import java.time.LocalDate;

public class RequestHolidayDTO {
    private String usernameEmployee;
    private String description;
    private String status;
    private LocalDate submittedDate;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String type;
    private String proxyUsername;

    public RequestHolidayDTO() {
        // default constructor
    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDate submittedDate) {
        this.submittedDate = submittedDate;
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

    @Override
    public String toString() {
        return "RequestHolidayDTO{" +
                "usernameEmployee='" + usernameEmployee + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", submittedDate=" + submittedDate +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", type='" + type + '\'' +
                ", proxyUsername='" + proxyUsername + '\'' +
                '}';
    }
}
