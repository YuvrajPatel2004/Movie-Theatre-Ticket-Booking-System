package com.moviebooking.dao;

import com.moviebooking.model.Hall;
import com.moviebooking.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HallDAO {
    public Hall create(Hall h) throws SQLException {
        String sql = "INSERT INTO halls (name, location) VALUES (?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,h.getName());
            ps.setString(2,h.getLocation());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) h.setId(rs.getInt(1));
            }
            return h;
        }
    }

    public List<Hall> listAll() throws SQLException {
        List<Hall> list = new ArrayList<>();
        String sql = "SELECT * FROM halls";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Hall h = new Hall();
                h.setId(rs.getInt("id"));
                h.setName(rs.getString("name"));
                h.setLocation(rs.getString("location"));
                list.add(h);
            }
        }
        return list;
    }
}
