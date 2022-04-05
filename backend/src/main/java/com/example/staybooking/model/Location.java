package com.example.staybooking.model;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class Location {
  private Long id;
  private GeoPoint geoPoint;

  public Location(Long id, GeoPoint geoPoint) {
    this.id = id;
    this.geoPoint = geoPoint;
  }

  public Long getId() {
    return id;
  }

  public GeoPoint getGeoPoint() {
    return geoPoint;
  }

}
