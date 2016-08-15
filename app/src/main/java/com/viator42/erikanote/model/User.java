package com.viator42.erikanote.model;

/**
 * Created by Administrator on 2016/8/2.
 */
public class User extends BaseModel{
    //------offline------
    public String imie;
    public String name;
    public double balance;
    public double totalIncome;
    public double totalSpend;
    public long openCount;
    public long lastOpenTime;
    //------online------
    public long id;
    public String username;
    public String password;
    public long registerTime;
    public long lastLoginTime;
}
