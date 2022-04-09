package com.example.staybooking.service;

import com.example.staybooking.model.Stay;
import com.example.staybooking.repository.LocationRepository;
import com.example.staybooking.repository.StayRepository;
import com.example.staybooking.repository.StayReservationDateRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class SearchService {

  private StayRepository stayRepository;
  private StayReservationDateRepository stayReservationDateRepository;
  private LocationRepository locationRepository;

  @Autowired
  public SearchService(StayRepository stayRepository,
      StayReservationDateRepository stayReservationDateRepository,
      LocationRepository locationRepository) {
    this.stayRepository = stayRepository;
    this.stayReservationDateRepository = stayReservationDateRepository;
    this.locationRepository = locationRepository;
  }


  //parameters from front end
  //return List<Stay> is front end need
  public List<Stay> search(int guestNumber, LocalDate checkinDate, LocalDate checkoutDate,
      double lat, double lon, String distance) {

    List<Long> stayIds = locationRepository.searchByDistance(lat, lon, distance);
    if (stayIds == null || stayIds.isEmpty()) {
      return new ArrayList<>();
    }

    Set<Long> reservedStayIds = stayReservationDateRepository.findByIdInAndDateBetween(stayIds,
        checkinDate, checkoutDate.minusDays(1));//-1 is due to checkoutDate can be available for others

    List<Long> filteredStayIds = new ArrayList<>();
    for (Long stayId : stayIds) {
      if (!reservedStayIds.contains(stayId)) {
        filteredStayIds.add(stayId);
      }
    }

    return stayRepository.findByIdInAndGuestNumberGreaterThanEqual(filteredStayIds, guestNumber);
  }

}
