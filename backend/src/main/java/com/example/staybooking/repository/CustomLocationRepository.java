package com.example.staybooking.repository;

import java.util.List;

public interface CustomLocationRepository {

  //return desired stay id
  List<Long> searchByDistance(double lat, double lon, String distance);

}
