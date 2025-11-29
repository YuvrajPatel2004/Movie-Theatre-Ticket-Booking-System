package com.moviebooking.dao;

import com.moviebooking.model.Payment;
import com.moviebooking.util.DBUtil;

import java.sql.*;

public class PaymentDAO {
    public Payment create(Payment p) throws SQLException {
        String sql = "INSERT INTO payments (booking_id, amount, method, status) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,p.getBookingId());
            ps.setDouble(2,p.getAmount());
            ps.setString(3,p.getMethod());
            ps.setString(4,p.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getInt(1));
            }
            return p;
        }
    }
}
