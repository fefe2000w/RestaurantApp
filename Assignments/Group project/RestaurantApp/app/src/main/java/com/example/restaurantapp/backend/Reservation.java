package com.example.restaurantapp.backend;

public class Reservation {
    private String date;
    private String timeSlot;
    private int numberOfPeople;
    private String note;

    // 构造函数
    public Reservation(String date, String timeSlot, int numberOfPeople, String note) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.numberOfPeople = numberOfPeople;
        this.note = note;
    }

    // Getter 和 Setter 方法
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
