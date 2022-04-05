package com.example.staybooking.service;


import com.example.staybooking.exception.GeoCodingException;
import com.example.staybooking.exception.InvalidStayAddressException;
import com.example.staybooking.model.Location;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

@Service
public class GeoCodingService {

  private GeoApiContext context;

  @Autowired
  public GeoCodingService(GeoApiContext context) {
    this.context = context;
  }

  public Location getLatLng(Long id, String address) throws GeoCodingException {
    try {
      GeocodingResult result = GeocodingApi.geocode(context, address).await()[0];
      if (result.partialMatch) {
        throw new InvalidStayAddressException("Failed to find stay address");
      }
      return new Location(id,
                          new GeoPoint(result.geometry.location.lat, result.geometry.location.lng));
    } catch (IOException | ApiException | InterruptedException e) {
      e.printStackTrace();
      throw new GeoCodingException("Failed to encode stay address");
    }
  }


}
