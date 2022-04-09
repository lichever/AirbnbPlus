package com.example.staybooking.repository;

import com.example.staybooking.model.Reservation;
import com.example.staybooking.model.Stay;
import com.example.staybooking.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  //for guest
  List<Reservation> findByGuest(User guest);

  //for host
  List<Reservation> findByStay(Stay stay);

  //id is the reservation_id 可以不需要guest。直接用JPA默认get
  Reservation findByIdAndGuest(Long id, User guest);

}
