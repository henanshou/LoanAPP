package com.swufe.loanapp;

public class User {
    private String username;//用户名
    private String password;//密码

    public User(){
        super();
        username = "";
        password = "";
    }

    public User(String name, String password) {
        this.username = name;
        this.password = password;
    }
    public String getName() {
        return username;
    }
    public void setName(String name) {
        this.username = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
