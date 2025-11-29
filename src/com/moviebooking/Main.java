package com.moviebooking;

import com.moviebooking.dao.*;
import com.moviebooking.model.*;
import com.moviebooking.util.DBUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final AdminDAO adminDAO = new AdminDAO();
    private static final UserDAO userDAO = new UserDAO();
    private static final MovieDAO movieDAO = new MovieDAO();
    private static final HallDAO hallDAO = new HallDAO();
    private static final ScreenDAO screenDAO = new ScreenDAO();
    private static final ShowDAO showDAO = new ShowDAO();
    private static final SeatDAO seatDAO = new SeatDAO();
    private static final BookingDAO bookingDAO = new BookingDAO();
    private static final PaymentDAO paymentDAO = new PaymentDAO();
    private static final TicketDAO ticketDAO = new TicketDAO();

    public static void main(String[] args) {
        System.out.println("=== Movie/Theatre Ticket Booking System (CLI) ===");
        while (true) {
            try {
                System.out.println("\nMain Menu\n1. Admin Login\n2. User Register\n3. User Login\n4. Exit");
                System.out.print("Choose > ");
                String c = sc.nextLine().trim();
                switch (c) {
                    case "1":
                        adminLogin();
                        break;
                    case "2":
                        userRegister();
                        break;
                    case "3":
                        userLogin();
                        break;
                    case "4":
                        System.out.println("Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (Throwable t) {
                System.err.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }
    }

    // ------------------------------
    // ADMIN
    // ------------------------------
    private static void adminLogin() {
        try {
            System.out.print("Admin Email: ");
            String email = sc.nextLine().trim();
            System.out.print("Password: ");
            String pass = sc.nextLine().trim();
            Admin a = adminDAO.findByEmailAndPassword(email, pass);
            if (a == null) {
                System.out.println("Invalid admin credentials.");
                return;
            }
            System.out.println("Welcome, " + a.getName());
            while (true) {
                System.out.println(
                        "\nAdmin Menu\n1. Add Movie\n2. Add Hall\n3. Add Screen\n4. Add Show\n5. List Movies\n6. List Halls\n7. List Screens by Hall\n8. List Shows\n9. Back\n10. Revenue Dashboard");
                System.out.print("Choose > ");
                String ch = sc.nextLine().trim();
                switch (ch) {
                    case "1":
                        addMovie();
                        break;
                    case "2":
                        addHall();
                        break;
                    case "3":
                        addScreen();
                        break;
                    case "4":
                        addShow();
                        break;
                    case "5":
                        listMovies();
                        break;
                    case "6":
                        listHalls();
                        break;
                    case "7":
                        listScreensByHall();
                        break;
                    case "8":
                        listShows();
                        break;
                    case "9":
                        return;
                    case "10":
                        showRevenueDashboard();
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } catch (Exception e) {
            System.err.println("Admin flow error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addMovie() throws Exception {
        Movie m = new Movie();
        System.out.print("Title: ");
        m.setTitle(sc.nextLine());
        System.out.print("Duration (minutes): ");
        m.setDurationMinutes(Integer.parseInt(sc.nextLine()));
        System.out.print("Language: ");
        m.setLanguage(sc.nextLine());
        System.out.print("Description: ");
        m.setDescription(sc.nextLine());
        movieDAO.create(m);
        System.out.println("Movie added with id: " + m.getId());
    }

    private static void addHall() throws Exception {
        Hall h = new Hall();
        System.out.print("Hall Name: ");
        h.setName(sc.nextLine());
        System.out.print("Location: ");
        h.setLocation(sc.nextLine());
        hallDAO.create(h);
        System.out.println("Hall added id: " + h.getId());
    }

    private static void addScreen() throws Exception {
        listHalls();
        System.out.print("Hall id for screen: ");
        int hid = Integer.parseInt(sc.nextLine());
        Screen s = new Screen();
        s.setHallId(hid);
        System.out.print("Screen name: ");
        s.setName(sc.nextLine());
        System.out.print("Total seats: ");
        s.setTotalSeats(Integer.parseInt(sc.nextLine()));
        screenDAO.create(s);
        seatDAO.autoCreateSeats(s.getId(), s.getTotalSeats());
        System.out.println("Screen created with id: " + s.getId());
        System.out.println("Seats auto-generated successfully!");
    }

    private static void addShow() throws Exception {
        listMovies();
        System.out.print("Movie id: ");
        int mid = Integer.parseInt(sc.nextLine());
        listHalls();
        System.out.print("Hall id: ");
        int hid = Integer.parseInt(sc.nextLine());
        List<Screen> screens = screenDAO.listByHall(hid);
        if (screens.isEmpty()) {
            System.out.println("No screens in hall.");
            return;
        }
        System.out.println("Screens:");
        for (Screen s : screens)
            System.out.println(s.getId() + ". " + s.getName() + " (" + s.getTotalSeats() + " seats)");
        System.out.print("Screen id: ");
        int sid = Integer.parseInt(sc.nextLine());
        System.out.print("Show time (yyyy-MM-dd HH:mm): ");
        String st = sc.nextLine();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date dt = sdf.parse(st);
            Show sh = new Show();
            sh.setMovieId(mid);
            sh.setScreenId(sid);
            sh.setShowTime(new Timestamp(dt.getTime()));
            System.out.print("Ticket price (e.g., 150.00): ");
            sh.setPrice(Double.parseDouble(sc.nextLine()));
            showDAO.create(sh);
            System.out.println("Show created id: " + sh.getId());
        } catch (Exception ex) {
            System.out.println("Invalid date/time format.");
        }
    }

    private static void listMovies() throws Exception {
        List<Movie> movies = movieDAO.listAll();
        System.out.println("--- Movies ---");
        for (Movie m : movies) {
            System.out
                    .println(m.getId() + ": " + m.getTitle() + " (" + m.getDurationMinutes() + "m) " + m.getLanguage());
        }
    }

    private static void listHalls() throws Exception {
        List<Hall> halls = hallDAO.listAll();
        System.out.println("--- Halls ---");
        for (Hall h : halls) {
            System.out.println(h.getId() + ": " + h.getName() + " - " + h.getLocation());
        }
    }

    private static void listScreensByHall() throws Exception {
        listHalls();
        System.out.print("Hall id: ");
        int hid = Integer.parseInt(sc.nextLine());
        List<Screen> screens = screenDAO.listByHall(hid);
        if (screens.isEmpty())
            System.out.println("No screens.");
        for (Screen s : screens) {
            System.out.println(s.getId() + ": " + s.getName() + " (" + s.getTotalSeats() + ")");
        }
    }

    // improved: show movie title + screen name
    private static void listShows() throws Exception {
        List<Show> shows = showDAO.listUpcoming();
        System.out.println("--- Upcoming Shows ---");
        for (Show s : shows) {
            Movie m = movieDAO.findById(s.getMovieId());
            Screen scr = screenDAO.findById(s.getScreenId());
            String title = (m != null) ? m.getTitle() : ("Movie#" + s.getMovieId());
            String screenName = (scr != null) ? scr.getName() : ("Screen#" + s.getScreenId());
            System.out.println(s.getId() + ": " + title + " | " + screenName + " | " + s.getShowTime() + " | Price="
                    + s.getPrice());
        }
    }

    // ------------------------------
    // USER
    // ------------------------------
    private static void userRegister() {
        try {
            User u = new User();
            System.out.print("Name: ");
            u.setName(sc.nextLine());
            System.out.print("Email: ");
            u.setEmail(sc.nextLine());
            System.out.print("Password: ");
            u.setPassword(sc.nextLine());
            System.out.print("Phone: ");
            u.setPhone(sc.nextLine());
            userDAO.create(u);
            System.out.println("Registered. Your user id: " + u.getId());
        } catch (Exception e) {
            System.err.println("Register error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void userLogin() {
        try {
            System.out.print("Email: ");
            String e = sc.nextLine();
            System.out.print("Password: ");
            String p = sc.nextLine();
            User u = userDAO.findByEmailAndPassword(e, p);
            if (u == null) {
                System.out.println("Invalid credentials.");
                return;
            }
            System.out.println("Welcome, " + u.getName());
            userMenu(u);
        } catch (Exception ex) {
            System.err.println("Login error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void userMenu(User u) throws Exception {
        while (true) {
            System.out.println("\nUser Menu\n1. List Shows\n2. Book Tickets\n3. Cancel Booking\n4. Logout\n5. View Booking History");
            System.out.print("Choose > ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1":
                    listShows();
                    break;
                case "2":
                    bookTickets(u);
                    break;
                case "3":
                    cancelBooking(u);
                    break;
                case "4":
                    return;
                case "5":
                    viewBookingHistory(u);
                    break;
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    // Show booking history with tickets and movie title
    private static void viewBookingHistory(User u) {
        try (Connection c = DBUtil.getConnection()) {
            String sql = "SELECT b.id AS booking_id, b.total_amount, b.status, b.booking_time, " +
                    "s.show_time, m.title " +
                    "FROM bookings b " +
                    "JOIN shows s ON b.show_id = s.id " +
                    "JOIN movies m ON s.movie_id = m.id " +
                    "WHERE b.user_id = ? ORDER BY b.booking_time DESC";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, u.getId());
                try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("===== Your Booking History =====");
                    while (rs.next()) {
                        int bid = rs.getInt("booking_id");
                        double amt = rs.getDouble("total_amount");
                        String status = rs.getString("status");
                        Timestamp btime = rs.getTimestamp("booking_time");
                        Timestamp showTime = rs.getTimestamp("show_time");
                        String movieTitle = rs.getString("title");
                        System.out.println("Booking ID: " + bid + " | Movie: " + movieTitle + " | Amount: " + amt
                                + " | Status: " + status + " | BookingTime: " + btime + " | ShowTime: " + showTime);
                        // tickets for this booking
                        String tq = "SELECT t.ticket_label, s2.seat_label FROM tickets t JOIN seats s2 ON t.seat_id = s2.id WHERE t.booking_id = ?";
                        try (PreparedStatement tps = c.prepareStatement(tq)) {
                            tps.setInt(1, bid);
                            try (ResultSet trs = tps.executeQuery()) {
                                List<String> seatLabels = new ArrayList<>();
                                while (trs.next()) {
                                    seatLabels.add(trs.getString("seat_label"));
                                }
                                System.out.println("  Tickets: " + String.join(", ", seatLabels));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("History error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // cancellation with refund calculation
    private static void cancelBooking(User u) {
        try (Connection c = DBUtil.getConnection()) {
            String sql = "SELECT b.id AS booking_id, b.total_amount, b.status, s.show_time " +
                    "FROM bookings b JOIN shows s ON b.show_id=s.id " +
                    "WHERE b.user_id=? AND b.status='CONFIRMED'";
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setInt(1, u.getId());
                try (ResultSet rs = ps.executeQuery()) {
                    List<Integer> bookingIds = new ArrayList<>();
                    Map<Integer, Double> amountMap = new HashMap<>();
                    Map<Integer, Timestamp> showTimeMap = new HashMap<>();
                    while (rs.next()) {
                        int id = rs.getInt("booking_id");
                        bookingIds.add(id);
                        amountMap.put(id, rs.getDouble("total_amount"));
                        showTimeMap.put(id, rs.getTimestamp("show_time"));
                    }
                    if (bookingIds.isEmpty()) {
                        System.out.println("No confirmed bookings to cancel.");
                        return;
                    }
                    System.out.println("--- Your Confirmed Bookings ---");
                    for (int id : bookingIds) {
                        System.out.println("Booking ID: " + id + "  Amount: " + amountMap.get(id));
                    }
                    System.out.print("Enter Booking ID to cancel: ");
                    int bid = Integer.parseInt(sc.nextLine().trim());
                    if (!bookingIds.contains(bid)) {
                        System.out.println("Invalid booking id.");
                        return;
                    }

                    // calculate refund percent based on time difference
                    Timestamp showTs = showTimeMap.get(bid);
                    Instant now = Instant.now();
                    Instant showInstant = showTs.toInstant();
                    long hoursBefore = Duration.between(now, showInstant).toHours();

                    double refundPercent;
                    if (hoursBefore > 24) refundPercent = 1.0;
                    else if (hoursBefore >= 3) refundPercent = 0.5;
                    else refundPercent = 0.0;

                    double total = amountMap.get(bid);
                    double refundAmount = Math.round(total * refundPercent * 100.0) / 100.0;

                    // update booking status
                    bookingDAO.updateStatus(bid, "CANCELLED");

                    // cancel tickets
                    String cancelTicketsSQL = "UPDATE tickets SET status='CANCELLED' WHERE booking_id=?";
                    try (PreparedStatement tps = c.prepareStatement(cancelTicketsSQL)) {
                        tps.setInt(1, bid);
                        tps.executeUpdate();
                    }

                    // simulate refund by inserting a payment with method=REFUND and negative amount (or separate refund record)
                    String insertRefundSql = "INSERT INTO payments (booking_id, amount, method, status) VALUES (?,?,?,?)";
                    try (PreparedStatement rps = c.prepareStatement(insertRefundSql)) {
                        rps.setInt(1, bid);
                        rps.setDouble(2, -refundAmount); // negative to indicate money returned
                        rps.setString(3, "REFUND");
                        rps.setString(4, refundAmount > 0 ? "SUCCESS" : "FAILED");
                        rps.executeUpdate();
                    }

                    System.out.println("Booking cancelled successfully.");
                    if (refundAmount > 0) {
                        System.out.println("Refund: ₹" + refundAmount + " (" + (int) (refundPercent * 100) + "%)");
                    } else {
                        System.out.println("No refund available (too close to showtime).");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Cancel error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------
    // BOOKING
    // ------------------------------
    private static void bookTickets(User u) {
        try {
            listShows();
            System.out.print("Show id to book: ");
            int showId = Integer.parseInt(sc.nextLine());
            Show show = showDAO.findById(showId);
            if (show == null) {
                System.out.println("Show not found.");
                return;
            }
            List<Seat> seats = seatDAO.listByScreen(show.getScreenId());

            // find already booked seats for this show (via tickets->bookings->show_id)
            Set<Integer> takenSeatIds = new HashSet<>();
            String q = "SELECT t.seat_id FROM tickets t JOIN bookings b ON t.booking_id=b.id WHERE b.show_id=? AND b.status='CONFIRMED' AND t.status='ACTIVE'";
            try (Connection conn = DBUtil.getConnection();
                    PreparedStatement ps = conn.prepareStatement(q)) {
                ps.setInt(1, showId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        takenSeatIds.add(rs.getInt("seat_id"));
                }
            }

            System.out.println("Available seats:");
            for (Seat s : seats) {
                String mark = takenSeatIds.contains(s.getId()) ? "[X]" : "[ ]";
                System.out.println(s.getId() + " " + mark + " " + s.getSeatLabel() + " (" + s.getSeatType() + ")");
            }
            System.out.print("Enter seat ids to book (comma separated): ");
            String line = sc.nextLine();
            String[] parts = line.split(",");
            List<Seat> chosen = new ArrayList<>();
            for (String p : parts) {
                p = p.trim();
                if (p.isEmpty())
                    continue;
                try {
                    int sid = Integer.parseInt(p);
                    if (takenSeatIds.contains(sid)) {
                        System.out.println("Seat id " + sid + " already taken. Skipping.");
                        continue;
                    }
                    Seat seat = seatDAO.findById(sid);
                    if (seat == null) {
                        System.out.println("Seat id " + sid + " not found.");
                        continue;
                    }
                    chosen.add(seat);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid id: " + p);
                }
            }
            if (chosen.isEmpty()) {
                System.out.println("No seats chosen.");
                return;
            }

            double total = chosen.size() * show.getPrice(); // simple price calc
            System.out.println("You chose " + chosen.size() + " seats. Total = " + total);
            System.out.print("Confirm booking? (y/n): ");
            if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.println("Cancelled.");
                return;
            }

            // create booking
            Booking b = new Booking();
            b.setUserId(u.getId());
            b.setShowId(showId);
            b.setTotalAmount(total);
            b.setStatus("PENDING");
            bookingDAO.create(b);
            System.out.println("Booking created with id: " + b.getId());

            // simulate payment
            System.out.println("---- Payment ----");
            System.out.print("Method (CARD/UPI/CASH): ");
            String method = sc.nextLine().trim();
            Payment pmt = new Payment();
            pmt.setBookingId(b.getId());
            pmt.setAmount(total);
            pmt.setMethod(method);
            pmt.setStatus("SUCCESS"); // simulation
            paymentDAO.create(pmt);
            // confirm booking
            bookingDAO.updateStatus(b.getId(), "CONFIRMED");
            System.out.println("Payment success, booking confirmed.");

            // create tickets
            List<String> ticketSeatLabels = new ArrayList<>();
            for (Seat s : chosen) {
                Ticket t = new Ticket();
                t.setBookingId(b.getId());
                t.setSeatId(s.getId());
                t.setStatus("ACTIVE");
                ticketDAO.create(t);
                ticketSeatLabels.add(s.getSeatLabel());
            }

            // Print enhanced booking confirmation (movie/hall/screen)
            Movie mov = movieDAO.findById(show.getMovieId());
            Screen scr = screenDAO.findById(show.getScreenId());
            Hall hall = (scr != null) ? hallDAO.listAll().stream().filter(h -> h.getId() == scr.getHallId()).findFirst().orElse(null) : null;

            System.out.println("\n=== Booking Confirmed! ===");
            System.out.println("Movie: " + (mov != null ? mov.getTitle() : "Movie#" + show.getMovieId()));
            System.out.println("Hall: " + (hall != null ? hall.getName() : "Hall#" + (scr != null ? scr.getHallId() : "-")));
            System.out.println("Screen: " + (scr != null ? scr.getName() : "Screen#" + show.getScreenId()));
            System.out.println("Show Time: " + show.getShowTime());
            System.out.println("Amount Paid: ₹" + total);
            System.out.println("Tickets: ");
            for (String sl : ticketSeatLabels) System.out.println("  " + sl);
            System.out.println("===========================");
            System.out.println("Enjoy your show!");
        } catch (Exception e) {
            System.err.println("Booking error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------------
    // ADMIN REPORTS
    // ------------------------------
    private static void showRevenueDashboard() {
        try (Connection c = DBUtil.getConnection()) {
            System.out.println("===== Revenue Dashboard =====");
            // total revenue (sum of payments where amount>0 and status SUCCESS)
            try (PreparedStatement ps = c.prepareStatement("SELECT IFNULL(SUM(amount),0) AS total_rev FROM payments WHERE status='SUCCESS'")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        double total = rs.getDouble("total_rev");
                        System.out.println("Total Revenue: ₹" + total);
                    }
                }
            }

            // total tickets sold (count of tickets with status ACTIVE for confirmed bookings)
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT COUNT(*) AS sold FROM tickets t JOIN bookings b ON t.booking_id=b.id WHERE t.status='ACTIVE' AND b.status='CONFIRMED'")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Total Tickets Sold: " + rs.getInt("sold"));
                    }
                }
            }

            // movie-wise revenue
            System.out.println("\nMovie-wise Revenue:");
            String movSql = "SELECT m.title, IFNULL(SUM(p.amount),0) AS revenue " +
                    "FROM movies m " +
                    "LEFT JOIN shows sh ON sh.movie_id = m.id " +
                    "LEFT JOIN bookings b ON b.show_id = sh.id " +
                    "LEFT JOIN payments p ON p.booking_id = b.id AND p.status='SUCCESS' " +
                    "GROUP BY m.id ORDER BY revenue DESC";
            try (PreparedStatement ps = c.prepareStatement(movSql);
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  " + rs.getString("title") + " → ₹" + rs.getDouble("revenue"));
                }
            }

            // show-wise revenue
            System.out.println("\nShow-wise Revenue:");
            String showSql = "SELECT sh.id AS show_id, scr.name AS screen_name, sh.show_time, IFNULL(SUM(p.amount),0) AS revenue " +
                    "FROM shows sh " +
                    "LEFT JOIN screens scr ON scr.id = sh.screen_id " +
                    "LEFT JOIN bookings b ON b.show_id = sh.id " +
                    "LEFT JOIN payments p ON p.booking_id = b.id AND p.status='SUCCESS' " +
                    "GROUP BY sh.id ORDER BY sh.show_time";
            try (PreparedStatement ps = c.prepareStatement(showSql);
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("  Show ID " + rs.getInt("show_id") + " – " + rs.getString("screen_name") + " – " + rs.getTimestamp("show_time") + " → ₹" + rs.getDouble("revenue"));
                }
            }

            // booking status counts
            try (PreparedStatement ps = c.prepareStatement("SELECT status, COUNT(*) cnt FROM bookings GROUP BY status")) {
                try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("\nBooking Status:");
                    while (rs.next()) {
                        System.out.println("  " + rs.getString("status") + ": " + rs.getInt("cnt"));
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Revenue error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
