package com.example.staybooking.repository;

import com.example.staybooking.model.Reservation;
import com.example.staybooking.model.Stay;
import com.example.staybooking.model.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  /*
  要用springboot JPA data，2个条件
  1. extends JpaRepository
  2. @Repository

   */

  //for guest
  List<Reservation> findByGuest(User guest);

  //for host
  List<Reservation> findByStay(Stay stay);

  //id is the reservation_id 可以不需要guest。直接用JPA默认get；加上是为了万一id泄露，有user验证是不是这个人
  Reservation findByIdAndGuest(Long id, User guest);

  //Stay, CheckoutDate 这些关键词都是 Reservation class里的 field 不是db里的field
  List<Reservation> findByStayAndCheckoutDateAfter(Stay stay, LocalDate date);
}
