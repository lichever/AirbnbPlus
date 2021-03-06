package com.example.staybooking.service;

import com.example.staybooking.exception.ReservationCollisionException;
import com.example.staybooking.exception.ReservationNotFoundException;
import com.example.staybooking.model.Reservation;
import com.example.staybooking.model.Stay;
import com.example.staybooking.model.StayReservedDate;
import com.example.staybooking.model.StayReservedDateKey;
import com.example.staybooking.model.User;
import com.example.staybooking.repository.ReservationRepository;
import com.example.staybooking.repository.StayReservationDateRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

  private ReservationRepository reservationRepository;
  private StayReservationDateRepository stayReservationDateRepository;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository,
      StayReservationDateRepository stayReservationDateRepository) {
    this.reservationRepository = reservationRepository;
    this.stayReservationDateRepository = stayReservationDateRepository;
  }

  public List<Reservation> listByGuest(String username) {
    return reservationRepository.findByGuest(new User.Builder().setUsername(username).build());
  }

  public List<Reservation> listByStay(Long stayId) {
    return reservationRepository.findByStay(new Stay.Builder().setId(stayId).build());
  }


  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void add(Reservation reservation) throws ReservationCollisionException {

    Set<Long> stayIds = stayReservationDateRepository.findByIdInAndDateBetween(
        Arrays.asList(reservation.getStay().getId()), reservation.getCheckinDate(),
        reservation.getCheckoutDate().minusDays(1));

    if (!stayIds.isEmpty()) {
      throw new ReservationCollisionException("Duplicate reservation");
    }

    List<StayReservedDate> reservedDates = new ArrayList<>();
    for (LocalDate date = reservation.getCheckinDate();
         date.isBefore(reservation.getCheckoutDate()); date = date.plusDays(1)) {
      reservedDates.add(
          new StayReservedDate(new StayReservedDateKey(reservation.getStay().getId(), date),
              reservation.getStay()));
    }
    stayReservationDateRepository.saveAll(reservedDates);
    reservationRepository.save(reservation);

  }

  //TODO ??????????????????reservationId  and  findById??? ????????????????????????username
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public void delete(Long reservationId, String username) {
    Reservation reservation = reservationRepository.findByIdAndGuest(reservationId,
        new User.Builder().setUsername(
            username).build());
    if (reservation == null) {
      throw new ReservationNotFoundException("Reservation is not available");
    }
    for (LocalDate date = reservation.getCheckinDate();
         date.isBefore(reservation.getCheckoutDate()); date = date.plusDays(1)) {

      stayReservationDateRepository.deleteById(
          new StayReservedDateKey(reservation.getStay().getId(), date));
    }
    reservationRepository.deleteById(reservationId);
  }


}
