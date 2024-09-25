package com.example.restaurantapp.backend;

import java.util.List;

public class Restaurant {
    private String id; // unique identifier
    private String name; // not unique, but necessary

    // Used for search (switch city on HomeFragment)
    private String country;
    private String city;

    // Basic info. Used on SearchFragment
    private String averageCost;
    private float distance;
    private float rating;
    private int iconResId; // necessary????

    // Detailed info. Used on InfoFragment
    private String openingTime;
    private String address;
    private String phoneNumber;
    private List<String> promotionActivities;
    private List<String> menuItems;
    private List<String> comments;

    // Constructor for restaurants searched after confirming location
    public Restaurant(String id, String name, String country, String city) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
    }

    // Constructor for restaurants with basic info
    public Restaurant(String id, String name, float rating, float distance, int iconResId) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.distance = distance;
        this.iconResId = iconResId;
    }

    // Constructor for restaurants with detailed info
    public Restaurant(String id, String name, String averageCost, float distance, float rating, int iconResId,
                      String address, String openingTime, String phoneNumber,
                      List<String> promotionActivities, List<String> menuItems, List<String> comments) {
        this.id = id;
        this.name = name;
        this.averageCost = averageCost;
        this.distance = distance;
        this.rating = rating;
        this.iconResId = iconResId;
        this.openingTime = openingTime;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.promotionActivities = promotionActivities;
        this.menuItems = menuItems;
        this.comments = comments;
    }


    public String getId() { return id; }
    public String getName() { return name; }
    public String getAverageCost() { return averageCost; }
    public float getDistance() { return distance; }
    public float getRating() { return rating; }
    public int getIconResId() { return iconResId; }
    public String getOpeningTime() { return openingTime; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public List<String> getPromotionActivities() { return promotionActivities; }
    public List<String> getMenuItems() { return menuItems; }
    public List<String> getComments() { return comments; }

    public void setName(String name) { this.name = name; }
    public void setAverageCost(String averageCost) { this.averageCost = averageCost; }
    public void setDistance(float distance) { this.distance = distance; }
    public void setRating(float rating) { this.rating = rating; }
    public void setIconResId(int iconResId) { this.iconResId = iconResId; }
    public void setOpeningTime(String openingTime) { this.openingTime = openingTime; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPromotionActivities(List<String> promotionActivities) { this.promotionActivities = promotionActivities; }
    public void setMenuItems(List<String> menuItems) { this.menuItems = menuItems; }
    public void setComments(List<String> comments) { this.comments = comments; }
}
