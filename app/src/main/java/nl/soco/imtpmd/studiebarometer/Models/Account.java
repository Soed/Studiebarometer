package nl.soco.imtpmd.studiebarometer.Models;

public class Account {

    private String username = "";
    private String password = "";

    public Account (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername (String username){
        this.username = username;
    }

    public void  setPassword (String password){
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
