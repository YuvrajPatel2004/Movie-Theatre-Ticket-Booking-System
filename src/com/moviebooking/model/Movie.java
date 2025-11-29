package com.moviebooking.model;

public class Movie {
    private int id;
    private String title;
    private int durationMinutes;
    private String language;
    private String description;
    // getters/setters
    public int getId(){return id;}
    public void setId(int id){this.id=id;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}
    public int getDurationMinutes(){return durationMinutes;}
    public void setDurationMinutes(int d){this.durationMinutes=d;}
    public String getLanguage(){return language;}
    public void setLanguage(String language){this.language=language;}
    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}
}
