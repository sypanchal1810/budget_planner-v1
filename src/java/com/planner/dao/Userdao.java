package com.planner.dao;

import com.planner.entities.User;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class Userdao {

    private Connection con;

    public Userdao(Connection con) {
        this.con = con;
    }

    //  Insert user to DB
    public boolean saveUser(User user) {
        boolean u = false;
        try {
            String queary = "insert into users (name, email, password, phone) values(?,?,?,?)";

            PreparedStatement pstmt = this.con.prepareStatement(queary);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getPhone());
            pstmt.executeUpdate();

            u = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    // Fetch user from DB using EMAIL to remove duplicacy
    public boolean isEmailExist(String email) {
        try {
            String query = "select * from users where email = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Passwor Authentication
    public boolean isPasswordCorrect(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    // Fetch user from DB
    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;
        try {
            String query = "select * from users where email = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                if (!isPasswordCorrect(password, hashedPassword)) {
                    return user;
                } else {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setPhone(rs.getString("phone"));
                    user.setReg_date(rs.getTimestamp("rgdate"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
