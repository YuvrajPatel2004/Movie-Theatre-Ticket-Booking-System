package com.moviebooking.dao;

import com.moviebooking.model.Ticket;
import com.moviebooking.util.DBUtil;

import java.sql.*;
import java.util.UUID;

public class TicketDAO {
    public Ticket create(Ticket t) throws SQLException {
        String generatedLabel = "TICKET-" + UUID.randomUUID().toString().substring(0,8);
        String sql = "INSERT INTO tickets (booking_id, seat_id, ticket_label, status) VALUES (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,t.getBookingId());
            ps.setInt(2,t.getSeatId());
            ps.setString(3, generatedLabel);
            ps.setString(4, t.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));
                    t.setTicketLabel(generatedLabel);
                }
            }
            return t;
        }
    }
}
