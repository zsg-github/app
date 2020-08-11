package com.paas.app.entity;

import java.io.Serializable;

public class Employee implements Serializable {

    private static final long serialVersionUID = -2218847443881359359L;
    private int id;
    private String employeeName;
    private String department;
    private String group;
    private String post;
    private String identityCard;
    private String bank;
    private String bankAccount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return "employee{" +
                "id=" + id +
                ", employeeName='" + employeeName + '\'' +
                ", department='" + department + '\'' +
                ", group='" + group + '\'' +
                ", post='" + post + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", bank='" + bank + '\'' +
                ", bankAccount=" + bankAccount +
                '}';
    }
}
