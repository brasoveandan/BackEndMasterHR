package bdir.masterhr.domain.dtos.response;

public class AuthenticationResponse {
    private String adminRole;
    private String name;
    private String jwt;

    public AuthenticationResponse() {
        //default constructor
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

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "adminRole='" + adminRole + '\'' +
                ", name='" + name + '\'' +
                ", jwt='" + jwt + '\'' +
                '}';
    }
}
