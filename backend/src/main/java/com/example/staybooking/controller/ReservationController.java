package com.example.staybooking.controller;

import com.example.staybooking.exception.InvalidReservationDateException;
import com.example.staybooking.model.Reservation;
import com.example.staybooking.model.User;
import com.example.staybooking.service.ReservationService;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

  private ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping(value = "/reservations")
  public List<Reservation> listReservations(Principal principal) {
    return reservationService.listByGuest(principal.getName());
  }

  @PostMapping("/reservations")
  public void addReservation(@RequestBody Reservation reservation, Principal principal) {
    LocalDate checkinDate = reservation.getCheckinDate();
    LocalDate checkoutDate = reservation.getCheckoutDate();
    if (checkinDate.equals(checkoutDate) || checkinDate.isAfter(checkoutDate)
        || checkinDate.isBefore(LocalDate.now())) {
      throw new InvalidReservationDateException("Invalid date for reservation");
    }
    reservation.setGuest(new User.Builder().setUsername(principal.getName()).build());
    reservationService.add(reservation);
  }

  @DeleteMapping("/reservations/{reservationId}")
  public void deleteReservation(@PathVariable Long reservationId, Principal principal) {
    reservationService.delete(reservationId, principal.getName());
  }

}
