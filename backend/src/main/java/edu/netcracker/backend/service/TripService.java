package edu.netcracker.backend.service;

import edu.netcracker.backend.message.response.TripDTO;
import edu.netcracker.backend.model.Trip;
import edu.netcracker.backend.model.User;

import java.util.List;

public interface TripService {

    Trip updateTrip(User requestUser, TripDTO tripDTO);
    List<Trip> findCarrierTripsByStatus(User requestUser, Integer status, Long offset, Long limit);
    List<Trip> findCarrierTrips(User requestUser, Long offset, Long limit);
    List<Trip> findApproverTrips(User requestUser, Integer status, Long offset, Long limit);
}