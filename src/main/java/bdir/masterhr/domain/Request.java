package bdir.masterhr.domain;

import bdir.masterhr.domain.enums.RequestStatus;

import java.time.LocalDate;
import java.util.Objects;

public class Request {
    private Integer idRequest;
    private String usernameEmployee;
    private String description;
    private RequestStatus status;
    private LocalDate submittedDate;
    private String idTimesheet;

    public Request() {
        //default constructor
    }

    public Request(String usernameEmployee, String description, RequestStatus status, LocalDate submittedDate, String idTimesheet) {
        this.usernameEmployee = usernameEmployee;
        this.description = description;
        this.status = status;
        this.submittedDate = submittedDate;
        this.idTimesheet = idTimesheet;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
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

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDate date) {
        this.submittedDate = date;
    }

    public String getIdTimesheet() {
        return idTimesheet;
    }

    public void setIdTimesheet(String idTimesheet) {
        this.idTimesheet = idTimesheet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return idRequest.equals(request.idRequest) && Objects.equals(usernameEmployee, request.usernameEmployee) && Objects.equals(description, request.description) && status == request.status && Objects.equals(submittedDate, request.submittedDate) && Objects.equals(idTimesheet, request.idTimesheet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRequest, usernameEmployee, description, status, submittedDate, idTimesheet);
    }

    @Override
    public String toString() {
        return "Request{" +
                "idRequest=" + idRequest +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", submittedDate=" + submittedDate +
                ", idTimesheet='" + idTimesheet + '\'' +
                '}';
    }
}
