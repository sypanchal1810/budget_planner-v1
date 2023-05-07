package com.planner.entities;

import java.sql.Date;
import java.sql.Timestamp;

public class PlanExpense {

    private int expense_id;
    private int plan_id;
    private int user_id;
    private String expense_title;
    private java.sql.Date expense_date;
    private int expense_amount;
    private Timestamp expense_added_on;

    public PlanExpense(int expense_id, int plan_id, int user_id, String expense_title, Date expense_date, int expense_amount, Timestamp expense_added_on) {
        this.expense_id = expense_id;
        this.plan_id = plan_id;
        this.user_id = user_id;
        this.expense_title = expense_title;
        this.expense_date = expense_date;
        this.expense_amount = expense_amount;
        this.expense_added_on = expense_added_on;
    }

    public PlanExpense(int plan_id, int user_id, String expense_title, Date expense_date, int expense_amount, Timestamp expense_added_on) {
        this.plan_id = plan_id;
        this.user_id = user_id;
        this.expense_title = expense_title;
        this.expense_date = expense_date;
        this.expense_amount = expense_amount;
        this.expense_added_on = expense_added_on;
    }

    public PlanExpense(int plan_id, int user_id, String expense_title, Date expense_date, int expense_amount) {
        this.plan_id = plan_id;
        this.user_id = user_id;
        this.expense_title = expense_title;
        this.expense_date = expense_date;
        this.expense_amount = expense_amount;
    }

    public PlanExpense() {
    }

    // Getters
    public int getExpense_id() {
        return expense_id;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getExpense_title() {
        return expense_title;
    }

    public Date getExpense_date() {
        return expense_date;
    }

    public int getExpense_amount() {
        return expense_amount;
    }

    public Timestamp getExpense_added_on() {
        return expense_added_on;
    }

    // Setters
    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setExpense_title(String expense_title) {
        this.expense_title = expense_title;
    }

    public void setExpense_date(Date expense_date) {
        this.expense_date = expense_date;
    }

    public void setExpense_amount(int expense_amount) {
        this.expense_amount = expense_amount;
    }

    public void setExpense_added_on(Timestamp expense_added_on) {
        this.expense_added_on = expense_added_on;
    }
}
