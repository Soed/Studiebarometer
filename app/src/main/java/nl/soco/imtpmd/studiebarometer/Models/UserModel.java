package nl.soco.imtpmd.studiebarometer.Models;

public class UserModel {

    private String name = "";
    private String email = "";

    public UserModel() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
