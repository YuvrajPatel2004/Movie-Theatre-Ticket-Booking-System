package com.moviebooking.model;

public class Seat {
    private int id;
    private int screenId;
    private String seatLabel;
    private String seatType;
    private boolean isActive;
    // getters/setters
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public int getScreenId(){return screenId;}
    public void setScreenId(int screenId){this.screenId=screenId;}
    public String getSeatLabel(){return seatLabel;}
    public void setSeatLabel(String seatLabel){this.seatLabel=seatLabel;}
    public String getSeatType(){return seatType;}
    public void setSeatType(String seatType){this.seatType=seatType;}
    public boolean isActive(){return isActive;}
    public void setActive(boolean active){this.isActive=active;}
}
