package com.moviebooking.dao;

import com.moviebooking.model.Booking;
import com.moviebooking.util.DBUtil;

import java.sql.*;

public class BookingDAO {

    public Booking create(Booking b) throws SQLException {
        String sql = "INSERT INTO bookings (user_id, show_id, total_amount, status) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,b.getUserId());
            ps.setInt(2,b.getShowId());
            ps.setDouble(3,b.getTotalAmount());
            ps.setString(4,b.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) b.setId(rs.getInt(1));
            }
            return b;
        }
    }

    public void updateStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE bookings SET status=? WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1,status);
            ps.setInt(2,bookingId);
            ps.executeUpdate();
        }
    }
}
