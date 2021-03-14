package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Clocking {
    private String idClocking;
    private String idTimesheet;
    private String usernameEmployee;
    private LocalDateTime fromHour;
    private LocalDateTime toHour;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clocking clocking = (Clocking) o;
        return idClocking == clocking.idClocking && idTimesheet == clocking.idTimesheet && Objects.equals(usernameEmployee, clocking.usernameEmployee) && Objects.equals(fromHour, clocking.fromHour) && Objects.equals(toHour, clocking.toHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idClocking, idTimesheet, usernameEmployee, fromHour, toHour);
    }

    @Override
    public String toString() {
        return "Clocking{" +
                "idClocking=" + idClocking +
                ", idTimesheet=" + idTimesheet +
                ", usernameEmployee='" + usernameEmployee + '\'' +
                ", fromHour=" + fromHour +
                ", toHour=" + toHour +
                '}';
    }
}
