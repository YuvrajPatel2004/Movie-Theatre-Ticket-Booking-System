package com.moviebooking.dao;

import com.moviebooking.model.Screen;
import com.moviebooking.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScreenDAO {
    public Screen create(Screen s) throws SQLException {
        String sql = "INSERT INTO screens (hall_id, name, total_seats) VALUES (?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,s.getHallId());
            ps.setString(2,s.getName());
            ps.setInt(3,s.getTotalSeats());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) s.setId(rs.getInt(1));
            }
            return s;
        }
    }

    public List<Screen> listByHall(int hallId) throws SQLException {
        List<Screen> list = new ArrayList<>();
        String sql = "SELECT * FROM screens WHERE hall_id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, hallId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Screen s = new Screen();
                    s.setId(rs.getInt("id"));
                    s.setHallId(rs.getInt("hall_id"));
                    s.setName(rs.getString("name"));
                    s.setTotalSeats(rs.getInt("total_seats"));
                    list.add(s);
                }
            }
        }
        return list;
    }

public Screen findById(int id) throws SQLException {
    String sql = "SELECT * FROM screens WHERE id=?";
    try (Connection c = DBUtil.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Screen s = new Screen();
                s.setId(rs.getInt("id"));
                s.setHallId(rs.getInt("hall_id"));
                s.setName(rs.getString("name"));
                s.setTotalSeats(rs.getInt("total_seats"));
                return s;
            }
        }
    }
    return null;
}

}
