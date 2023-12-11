package com.example.Stay.dto;

import com.example.Eatery.Dto.EateryItemDto;
import com.example.Eatery.Entity.Eatery;
import com.example.Stay.Entity.Stay;
import com.example.Trip.Dto.MainItemDto;
import com.example.Trip.Entity.Trip;
import org.springframework.data.domain.Page;

public class SearchResult {
    private final Page<StayItemDto> stays;
    private final Page<MainItemDto> trips;
    private final Page<EateryItemDto> eaterys;

    public SearchResult(Page<StayItemDto> stays, Page<MainItemDto> trips, Page<EateryItemDto> eaterys) {
        this.stays = stays;
        this.trips = trips;
        this.eaterys = eaterys;
    }

    public Page<StayItemDto> getStays() {
        return stays;
    }

    public Page<MainItemDto> getTrips() {
        return trips;
    }

    public Page<EateryItemDto> getEaterys() { return eaterys; }
}