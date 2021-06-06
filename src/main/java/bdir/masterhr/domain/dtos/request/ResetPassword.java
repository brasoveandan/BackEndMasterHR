package bdir.masterhr.domain.dtos.request;

public class ResetPassword {
    private String token;
    private String password;
    private String password_confirm;

    public ResetPassword() {
        //default constructor
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirm() {
        return password_confirm;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }

    @Override
    public String toString() {
        return "ResetPassword{" +
                "token='" + token + '\'' +
                ", password='" + password + '\'' +
                ", password_confirm='" + password_confirm + '\'' +
                '}';
    }
}
