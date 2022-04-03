package com.example.staybooking.repository;

import com.example.staybooking.model.Stay;
import com.example.staybooking.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {
  List<Stay> findByHost(User user);

  Stay findByIdAndHost(Long id, User host);
}