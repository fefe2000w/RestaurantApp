package com.example.restaurantapp.backend;

import java.util.List;

public class Account {
    private String userName;
    private String nickName;
    private String profileId;

    private List<String> favoriteRestaurants;
    private List<Comment> comments;
    private List<String> bookingHistory;

    public Account(String userName) {
        this.userName = userName; // TODO: ? other attributes
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<String> getFavoriteRestaurants() {
        return favoriteRestaurants;
    }

    // Add restaurant to favorites
    public void addFavoriteRestaurant(String restaurantId) {
        if (!favoriteRestaurants.contains(restaurantId)) {
            favoriteRestaurants.add(restaurantId);
        }
    }

    // Remove restaurant from favorites
    public void removeFavoriteRestaurant(String restaurantId) {
        favoriteRestaurants.remove(restaurantId);
    }

    public List<String> getBookingHistory() {
        return bookingHistory;
    }

    // Add restaurant to favorites
    public void addBookingHistory(String reservationId) {
        if (!bookingHistory.contains(reservationId)) {
            bookingHistory.add(reservationId);
        }
    }

    // Remove restaurant from favorites
    public void removBookingHistory(String reservationId) {
        bookingHistory.remove(reservationId);
    }

}
