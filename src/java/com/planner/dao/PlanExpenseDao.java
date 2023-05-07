package com.planner.dao;

import com.planner.entities.PlanExpense;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PlanExpenseDao {

    private Connection con;

    public PlanExpenseDao(Connection con) {
        this.con = con;
    }

    //  Insert Plan Expense to DB
    public boolean saveExpense(PlanExpense expense) {
        boolean pe = false;
        try {
            String queary = "insert into plan_expense_of_user (plan_id, user_id, expense_title, expense_date, expense_amount) values(?,?,?,?,?)";

            PreparedStatement pstmt = this.con.prepareStatement(queary);
            pstmt.setInt(1, expense.getPlan_id());
            pstmt.setInt(2, expense.getUser_id());
            pstmt.setString(3, expense.getExpense_title());
            pstmt.setDate(4, expense.getExpense_date());
            pstmt.setInt(5, expense.getExpense_amount());
            pstmt.executeUpdate();

            pe = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pe;
    }

    // Get all expense of plan of the user from DB
    public ArrayList<PlanExpense> getAllExpense(int plan_id, int user_id) {

        ArrayList<PlanExpense> expenseList = new ArrayList<>();

        try {
            String q = "select * from plan_expense_of_user where plan_id = ? and user_id = ?";
            PreparedStatement pstmt = this.con.prepareStatement(q);
            pstmt.setInt(1, plan_id);
            pstmt.setInt(2, user_id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int expense_id = rs.getInt("expense_id");
                String expense_title = rs.getString("expense_title");
                java.sql.Date expense_date = rs.getDate("expense_date");
                int expense_amount = rs.getInt("expense_amount");
                Timestamp expense_added_on = rs.getTimestamp("expense_added_on");

                PlanExpense expense = new PlanExpense(expense_id, plan_id, user_id, expense_title, expense_date, expense_amount, expense_added_on);
                expenseList.add(expense);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expenseList;
    }
}
