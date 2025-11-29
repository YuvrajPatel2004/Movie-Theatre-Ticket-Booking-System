package com.moviebooking.dao;

import com.moviebooking.model.Admin;
import com.moviebooking.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    public Admin findByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT * FROM admins WHERE email=? AND password=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,email);
            ps.setString(2,password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Admin a = new Admin();
                    a.setId(rs.getInt("id"));
                    a.setName(rs.getString("name"));
                    a.setEmail(rs.getString("email"));
                    a.setPassword(rs.getString("password"));
                    return a;
                }
            }
        }
        return null;
    }
}
