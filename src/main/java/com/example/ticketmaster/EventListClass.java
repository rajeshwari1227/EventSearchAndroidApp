package com.example.ticketmaster;

public class EventListClass {
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "EventListClass{" +
                "date='" + date + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventId='" + eventId + '\'' +
                ", category='" + category + '\'' +
                ", venue='" + venue + '\'' +
                ", venueId='" + venueId + '\'' +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    private String eventName;
    private  String eventId;
    private String category;
    private String venue;
    private String venueId;

    public EventListClass() {
    }

    public EventListClass(String date, String eventName, String eventId, String category, String venue, String venueId) {
        this.date = date;
        this.eventName = eventName;
        this.eventId = eventId;
        this.category = category;
        this.venue = venue;
        this.venueId = venueId;
    }
}
