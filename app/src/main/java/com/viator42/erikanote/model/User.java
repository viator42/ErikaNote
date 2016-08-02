package com.viator42.erikanote.model;

/**
 * Created by Administrator on 2016/8/2.
 */
public class User extends BaseModel{
    public Long id;
    public String username;
    public String password;
    public String imie;
    public double balance;
    public double totalIncome;
    public double totalSpend;
    public Long registerTime;
    public Long lastLoginTime;
}
