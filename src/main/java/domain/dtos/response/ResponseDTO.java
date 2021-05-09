package domain.dtos.response;

public class ResponseDTO {
    private String adminRole;
    private String name;

    public ResponseDTO(String adminRole, String name) {
        this.adminRole = adminRole;
        this.name = name;
    }

    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "adminRole='" + adminRole + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
