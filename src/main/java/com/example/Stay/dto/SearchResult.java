package com.example.Stay.dto;

import com.example.Stay.Entity.Stay;
import com.example.Trip.Entity.Trip;
import org.springframework.data.domain.Page;

public class SearchResult {
    private final Page<Stay> stays;
    private final Page<Trip> trips;

    public SearchResult(Page<Stay> stays, Page<Trip> trips) {
        this.stays = stays;
        this.trips = trips;
    }

    public Page<Stay> getStays() {
        return stays;
    }

    public Page<Trip> getTrips() {
        return trips;
    }
}