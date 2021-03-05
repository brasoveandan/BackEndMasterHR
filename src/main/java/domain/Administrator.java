package domain;

import domain.enums.AdminRole;

public class Administrator {
    private String idEmployee;
    private AdminRole adminRole;

    public Administrator() {
    }

    public Administrator(String idEmployee, AdminRole adminRole) {
        this.idEmployee = idEmployee;
        this.adminRole = adminRole;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public AdminRole getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(AdminRole adminRole) {
        this.adminRole = adminRole;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "idEmployee='" + idEmployee + '\'' +
                ", adminRole=" + adminRole +
                '}';
    }
}
