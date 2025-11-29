package com.moviebooking.model;

import java.sql.Timestamp;

public class Show {
    private int id;
    private int movieId;
    private int screenId;
    private Timestamp showTime;
    private double price;
    private String status;
    // getters/setters
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public int getMovieId(){return movieId;}
    public void setMovieId(int movieId){this.movieId=movieId;}
    public int getScreenId(){return screenId;}
    public void setScreenId(int screenId){this.screenId=screenId;}
    public Timestamp getShowTime(){return showTime;}
    public void setShowTime(Timestamp showTime){this.showTime=showTime;}
    public double getPrice(){return price;}
    public void setPrice(double price){this.price=price;}
    public String getStatus(){return status;}
    public void setStatus(String status){this.status=status;}
}
