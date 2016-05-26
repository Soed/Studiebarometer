package nl.soco.imtpmd.studiebarometer.Models;

public class UserModel {

    private String name;
    private String email;
    private int id;

    public UserModel() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return this.email;
    }

    public int getId() {
        return this.id;
    }

}
