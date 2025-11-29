package com.moviebooking.dao;

import com.moviebooking.model.Seat;
import com.moviebooking.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {

    public List<Seat> listByScreen(int screenId) throws SQLException {
        List<Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE screen_id=? ORDER BY seat_label";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, screenId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Seat s = new Seat();
                    s.setId(rs.getInt("id"));
                    s.setScreenId(rs.getInt("screen_id"));
                    s.setSeatLabel(rs.getString("seat_label"));
                    s.setSeatType(rs.getString("seat_type"));
                    s.setActive(rs.getInt("is_active") == 1);
                    list.add(s);
                }
            }
        }
        return list;
    }

    public Seat findById(int id) throws SQLException {
        String sql = "SELECT * FROM seats WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Seat s = new Seat();
                    s.setId(rs.getInt("id"));
                    s.setScreenId(rs.getInt("screen_id"));
                    s.setSeatLabel(rs.getString("seat_label"));
                    s.setSeatType(rs.getString("seat_type"));
                    s.setActive(rs.getInt("is_active") == 1);
                    return s;
                }
            }
        }
        return null;
    }

    // ===========================
    // AUTO GENERATE SEATS METHOD
    // ===========================
    public void autoCreateSeats(int screenId, int totalSeats) throws SQLException {
        String[] rows = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        int seatsPerRow = 6;
        int created = 0;

        try (Connection c = DBUtil.getConnection()) {
            String sql = "INSERT INTO seats (screen_id, seat_label, seat_type) VALUES (?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql);

            int rowIndex = 0;

            while (created < totalSeats) {
                String row = rows[rowIndex];

                for (int i = 1; i <= seatsPerRow && created < totalSeats; i++) {
                    String seatLabel = row + i;

                    // First 3 rows = REGULAR, rest = PREMIUM
                    String seatType = (rowIndex >= 3) ? "PREMIUM" : "REGULAR";

                    ps.setInt(1, screenId);
                    ps.setString(2, seatLabel);
                    ps.setString(3, seatType);
                    ps.addBatch();

                    created++;
                }
                rowIndex++;
            }

            ps.executeBatch();
        }
    }
}
