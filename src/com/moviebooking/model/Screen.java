package com.moviebooking.model;

public class Screen {
    private int id;
    private int hallId;
    private String name;
    private int totalSeats;
    // getters/setters
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public int getHallId(){return hallId;}
    public void setHallId(int hallId){this.hallId=hallId;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public int getTotalSeats(){return totalSeats;}
    public void setTotalSeats(int totalSeats){this.totalSeats=totalSeats;}
}
