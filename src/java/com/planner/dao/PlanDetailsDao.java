package com.planner.dao;

import com.planner.entities.PlanDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PlanDetailsDao {

    private Connection con;

    public PlanDetailsDao(Connection con) {
        this.con = con;
    }

    // Insert Plan to database
    public boolean savePlan(PlanDetails plan) {
        boolean p = false;
        try {
            String queary = "insert into plan_details (user_id, initial_budget, total_person, trip_title, from_date, to_date) values(?,?,?,?,?,?)";

            PreparedStatement pstmt = this.con.prepareStatement(queary);

            pstmt.setInt(1, plan.getUser_id());
            pstmt.setInt(2, plan.getInitial_budget());
            pstmt.setInt(3, plan.getTotal_person());
            pstmt.setString(4, plan.getTrip_title());
            pstmt.setDate(5, plan.getFrom_date());
            pstmt.setDate(6, plan.getTo_date());

            pstmt.executeUpdate();

            p = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    // Get all plans of logged in user
    public ArrayList<PlanDetails> getAllPlan(int user_id) {

        ArrayList<PlanDetails> planList = new ArrayList<>();

        try {
            String q = "select * from plan_details where user_id = ?";
            PreparedStatement pstmt = this.con.prepareStatement(q);
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int plan_id = rs.getInt("plan_id");
                int initial_budget = rs.getInt("initial_budget");
                int total_person = rs.getInt("total_person");
                String trip_title = rs.getString("trip_title");
                java.sql.Date from_date = rs.getDate("from_date");
                java.sql.Date to_date = rs.getDate("to_date");
                Timestamp createdAt = rs.getTimestamp("createdAt");

                PlanDetails plan = new PlanDetails(plan_id, initial_budget, total_person, trip_title, from_date, to_date, createdAt);
                planList.add(plan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planList;
    }

    public PlanDetails getPlan(int user_id, int plan_id) {

        PlanDetails fetchedPlan = null;

        try {
            String q = "select * from plan_details where user_id = ? and plan_id = ?";
            PreparedStatement pstmt = this.con.prepareStatement(q);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, plan_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int initial_budget = rs.getInt("initial_budget");
                int total_person = rs.getInt("total_person");
                String trip_title = rs.getString("trip_title");
                java.sql.Date from_date = rs.getDate("from_date");
                java.sql.Date to_date = rs.getDate("to_date");
                Timestamp createdAt = rs.getTimestamp("createdAt");

                fetchedPlan = new PlanDetails(initial_budget, total_person, trip_title, from_date, to_date, createdAt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fetchedPlan;
    }
}
