package com.moviebooking.dao;

import com.moviebooking.model.Movie;
import com.moviebooking.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    public Movie create(Movie m) throws SQLException {
        String sql = "INSERT INTO movies (title, duration_minutes, language, description) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,m.getTitle());
            ps.setInt(2,m.getDurationMinutes());
            ps.setString(3,m.getLanguage());
            ps.setString(4,m.getDescription());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getInt(1));
            }
            return m;
        }
    }

    public List<Movie> listAll() throws SQLException {
        List<Movie> list = new ArrayList<>();
        String sql = "SELECT * FROM movies";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Movie m = new Movie();
                m.setId(rs.getInt("id"));
                m.setTitle(rs.getString("title"));
                m.setDurationMinutes(rs.getInt("duration_minutes"));
                m.setLanguage(rs.getString("language"));
                m.setDescription(rs.getString("description"));
                list.add(m);
            }
        }
        return list;
    }
public Movie findById(int id) throws SQLException {
    String sql = "SELECT * FROM movies WHERE id=?";
    try (Connection c = DBUtil.getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Movie m = new Movie();
                m.setId(rs.getInt("id"));
                m.setTitle(rs.getString("title"));
                m.setDurationMinutes(rs.getInt("duration_minutes"));
                m.setLanguage(rs.getString("language"));
                m.setDescription(rs.getString("description"));
                return m;
            }
        }
    }
    return null;
}


}
