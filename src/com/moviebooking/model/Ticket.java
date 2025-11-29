package com.moviebooking.model;

public class Ticket {
    private int id;
    private int bookingId;
    private int seatId;
    private String ticketLabel;
    private String status;
    // getters/setters
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public int getBookingId(){return bookingId;}
    public void setBookingId(int bookingId){this.bookingId=bookingId;}
    public int getSeatId(){return seatId;}
    public void setSeatId(int seatId){this.seatId=seatId;}
    public String getTicketLabel(){return ticketLabel;}
    public void setTicketLabel(String ticketLabel){this.ticketLabel=ticketLabel;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}
}
