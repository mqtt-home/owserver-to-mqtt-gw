package de.rnd7.owservermqttgw.config;

public class MqttCredentials {
    private String username = "";
    private String password = "";

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
