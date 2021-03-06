package domain;

import domain.enums.AdminRole;

import java.util.Objects;

public class Administrator {
    private String usernameEmployee;
    private AdminRole adminRole;

    public Administrator() {
        // Do nothing
    }

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public AdminRole getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(AdminRole adminRole) {
        this.adminRole = adminRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return Objects.equals(usernameEmployee, that.usernameEmployee) && adminRole == that.adminRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usernameEmployee, adminRole);
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "usernameEmployee='" + usernameEmployee + '\'' +
                ", adminRole=" + adminRole +
                '}';
    }
}
