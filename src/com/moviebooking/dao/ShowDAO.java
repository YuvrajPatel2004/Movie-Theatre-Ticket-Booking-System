package com.moviebooking.dao;

import com.moviebooking.model.Show;
import com.moviebooking.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShowDAO {
    public Show create(Show s) throws SQLException {
        String sql = "INSERT INTO shows (movie_id, screen_id, show_time, price) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,s.getMovieId());
            ps.setInt(2,s.getScreenId());
            ps.setTimestamp(3,s.getShowTime());
            ps.setDouble(4,s.getPrice());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) s.setId(rs.getInt(1));
            }
            return s;
        }
    }

    public List<Show> listUpcoming() throws SQLException {
        List<Show> list = new ArrayList<>();
        String sql = "SELECT * FROM shows WHERE status='SCHEDULED' AND show_time >= NOW() ORDER BY show_time";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Show s = new Show();
                s.setId(rs.getInt("id"));
                s.setMovieId(rs.getInt("movie_id"));
                s.setScreenId(rs.getInt("screen_id"));
                s.setShowTime(rs.getTimestamp("show_time"));
                s.setPrice(rs.getDouble("price"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }
        }
        return list;
    }

    public Show findById(int id) throws SQLException {
        String sql = "SELECT * FROM shows WHERE id=?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Show s = new Show();
                    s.setId(rs.getInt("id"));
                    s.setMovieId(rs.getInt("movie_id"));
                    s.setScreenId(rs.getInt("screen_id"));
                    s.setShowTime(rs.getTimestamp("show_time"));
                    s.setPrice(rs.getDouble("price"));
                    s.setStatus(rs.getString("status"));
                    return s;
                }
            }
        }
        return null;
    }
}
