package com.example.staybooking.repository;

import com.example.staybooking.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {

  Authority findAuthorityByUsername(String username);//customize the sql according to the JPA rules
}