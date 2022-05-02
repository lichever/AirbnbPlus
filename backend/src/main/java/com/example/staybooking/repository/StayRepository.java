package com.example.staybooking.repository;

import com.example.staybooking.model.Stay;
import com.example.staybooking.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//this layer defines custum query to operate with stay table in db,extend the CRUD operations on the database
public interface StayRepository extends JpaRepository<Stay, Long> {

  List<Stay> findByHost(User user);

  Stay findByIdAndHost(Long id, User host);//不加id，也没事，加上是为了万一id泄露，有user验证是不是这个人

  List<Stay> findByIdInAndGuestNumberGreaterThanEqual(List<Long> ids, int guestNumber);

}