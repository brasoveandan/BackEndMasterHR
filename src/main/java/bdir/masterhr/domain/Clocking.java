package bdir.masterhr.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Clocking {
    private String idClocking;
    private String idTimesheet;
    private String usernameEmployee;
    private LocalDateTime fromHour;
    private LocalDateTime toHour;
    private String reason;
    private String type;

    public Clocking() {
        //default constructor
    }

    public String getIdClocking() {
        return idClocking;
    }

    public void setIdClocking(String idClocking) {
        this.idClocking = idClocking;
    }

    public String getIdTimesheet() {
        return idTimesheet;
    }

    public void setIdTimesheet(String idTimesheet) {
        this.idTimesheet = idTimesheet;
    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public LocalDateTime getFromHour() {
        return fromHour;
    }

    public void setFromHour(LocalDateTime fromHour) {
        this.fromHour = fromHour;
    }

    public LocalDateTime getToHour() {
        return toHour;
    }

    public void setToHour(LocalDateTime toHour) {
        this.toHour = toHour;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clocking clocking = (Clocking) o;
        return Objects.equals(idClocking, clocking.idClocking) && Objects.equals(idTimesheet, clocking.idTimesheet) && Objects.equals(usernameEmployee, clocking.usernameEmployee) && Objects.equals(fromHour, clocking.fromHour) && Objects.equals(toHour, clocking.toHour) && Objects.equals(reason, clocking.reason) && Objects.equals(type, clocking.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClocking, idTimesheet, usernameEmployee, fromHour, toHour, reason, type);
    }

    @Override
    public String toString() {
        return "Clocking{" +
                "idClocking='" + idClocking + '\'' +
                ", idTimesheet='" + idTimesheet + '\'' +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", fromHour=" + fromHour +
                ", toHour=" + toHour +
                ", reason='" + reason + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
