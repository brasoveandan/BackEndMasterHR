package domain;

import domain.enums.RequestStatus;

import java.util.Date;
import java.util.Objects;

public class Request {
    private int idRequest;
    private String usernameEmployee;
    private String description;
    private RequestStatus status;
    private Date date;

    public Request() {
        //default constructor
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return idRequest == request.idRequest && Objects.equals(usernameEmployee, request.usernameEmployee) && Objects.equals(description, request.description) && status == request.status && Objects.equals(date, request.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRequest, usernameEmployee, description, status, date);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + idRequest +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", date=" + date +
                '}';
    }
}
