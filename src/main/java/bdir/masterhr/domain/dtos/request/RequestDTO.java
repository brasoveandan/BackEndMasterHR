package bdir.masterhr.domain.dtos.request;

import java.time.LocalDate;

public class RequestDTO {
    private String description;
    private String status;
    private LocalDate submittedDate;
    private String type;

    public RequestDTO() {
        // default constructor
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", submittedDate=" + submittedDate +
                ", type='" + type + '\'' +
                '}';
    }
}
