package com.moviebooking.model;

import java.sql.Timestamp;

public class Booking {
    private int id;
    private int userId;
    private int showId;
    private Timestamp bookingTime;
    private double totalAmount;
    private String status;
    // getters/setters
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public int getUserId(){return userId;}
    public void setUserId(int userId){this.userId=userId;}
    public int getShowId(){return showId;}
    public void setShowId(int showId){this.showId=showId;}
    public Timestamp getBookingTime(){return bookingTime;}
    public void setBookingTime(Timestamp bookingTime){this.bookingTime=bookingTime;}
    public double getTotalAmount(){return totalAmount;}
    public void setTotalAmount(double totalAmount){this.totalAmount=totalAmount;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}
}
