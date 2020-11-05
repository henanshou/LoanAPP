package com.swufe.loanapp;

public class Purchase {
    private String username;//用户名
    private String purchase;//购买的贷款名称

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public Purchase(){
        super();
        username = "";
        purchase = "";
    }

    public Purchase(String username, String purchase) {
        this.username = username;
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "username='" + username + '\'' +
                ", purchase='" + purchase + '\'' +
                '}';
    }
}
