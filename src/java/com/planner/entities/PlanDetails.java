package com.planner.entities;

import java.sql.Date;
import java.sql.Timestamp;

public class PlanDetails {

    private int plan_id;
    private int user_id;
    private int initial_budget;
    private int total_person;
    private String trip_title;
    private java.sql.Date from_date;
    private java.sql.Date to_date;
    private Timestamp createdAt;

    public PlanDetails(int plan_id, int user_id, int initial_budget, int total_person, String trip_title, java.sql.Date from_date, java.sql.Date to_date, Timestamp createdAt) {
        this.plan_id = plan_id;
        this.user_id = user_id;
        this.initial_budget = initial_budget;
        this.total_person = total_person;
        this.trip_title = trip_title;
        this.from_date = from_date;
        this.to_date = to_date;
        this.createdAt = createdAt;
    }

    public PlanDetails(int user_id, int initial_budget, int total_person, String trip_title, java.sql.Date from_date, java.sql.Date to_date) {
        this.user_id = user_id;
        this.initial_budget = initial_budget;
        this.total_person = total_person;
        this.trip_title = trip_title;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public PlanDetails(int plan_id, int initial_budget, int total_person, String trip_title, java.sql.Date from_date, java.sql.Date to_date, Timestamp createdAt) {
        this.plan_id = plan_id;
        this.initial_budget = initial_budget;
        this.total_person = total_person;
        this.trip_title = trip_title;
        this.from_date = from_date;
        this.to_date = to_date;
        this.createdAt = createdAt;
    }

    public PlanDetails(int initial_budget, int total_person, String trip_title, Date from_date, Date to_date, Timestamp createdAt) {
        this.initial_budget = initial_budget;
        this.total_person = total_person;
        this.trip_title = trip_title;
        this.from_date = from_date;
        this.to_date = to_date;
        this.createdAt = createdAt;
    }

    public PlanDetails() {
    }

    // Getters
    public int getPlan_id() {
        return plan_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getInitial_budget() {
        return initial_budget;
    }

    public int getTotal_person() {
        return total_person;
    }

    public String getTrip_title() {
        return trip_title;
    }

    public java.sql.Date getFrom_date() {
        return from_date;
    }

    public java.sql.Date getTo_date() {
        return to_date;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setInitial_budget(int initial_budget) {
        this.initial_budget = initial_budget;
    }

    public void setTotal_person(int total_person) {
        this.total_person = total_person;
    }

    public void setTrip_title(String trip_title) {
        this.trip_title = trip_title;
    }

    public void setFrom_date(java.sql.Date from_date) {
        this.from_date = from_date;
    }

    public void setTo_date(java.sql.Date to_date) {
        this.to_date = to_date;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
