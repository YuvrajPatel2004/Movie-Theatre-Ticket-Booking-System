package com.moviebooking.model;

import java.sql.Timestamp;

public class Payment {
    private int id;
    private int bookingId;
    private double amount;
    private Timestamp paymentTime;
    private String method;
    private String status;
    // getters/setters
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public int getBookingId(){return bookingId;}
    public void setBookingId(int bookingId){this.bookingId=bookingId;}
    public double getAmount(){return amount;}
    public void setAmount(double amount){this.amount=amount;}
    public Timestamp getPaymentTime(){return paymentTime;}
    public void setPaymentTime(Timestamp paymentTime){this.paymentTime=paymentTime;}
    public String getMethod(){return method;}
    public void setMethod(String method){this.method=method;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}
}
