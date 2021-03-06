package com.example.staybooking.service;

import com.example.staybooking.exception.StayDeleteException;
import com.example.staybooking.exception.StayNotExistException;
import com.example.staybooking.model.Location;
import com.example.staybooking.model.Reservation;
import com.example.staybooking.model.Stay;
import com.example.staybooking.model.StayImage;
import com.example.staybooking.model.User;
import com.example.staybooking.repository.LocationRepository;
import com.example.staybooking.repository.ReservationRepository;
import com.example.staybooking.repository.StayRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service  //this layer defines custom crud operations for the stay collection in a higher abstraction level of db
public class StayService {

  private StayRepository stayRepository;
  private LocationRepository locationRepository;
  private ImageStorageService imageStorageService;
  private GeoCodingService geoCodingService;
  private ReservationRepository reservationRepository;

  @Autowired
  public StayService(StayRepository stayRepository, LocationRepository locationRepository,
      ImageStorageService imageStorageService, GeoCodingService geoCodingService, ReservationRepository reservationRepository) {
    this.stayRepository = stayRepository;
    this.locationRepository = locationRepository;
    this.imageStorageService = imageStorageService;
    this.geoCodingService = geoCodingService;
    this.reservationRepository = reservationRepository;

  }

  public List<Stay> listByUser(String username) {
    return stayRepository.findByHost(new User.Builder().setUsername(username).build());
  }

  public Stay findByIdAndHost(Long stayId, String username) throws StayNotExistException {
    Stay stay = stayRepository.findByIdAndHost(stayId,
        new User.Builder().setUsername(username).build());
    if (stay == null) {
      throw new StayNotExistException("Stay doesn't exist");
    }
    return stay;
  }

  public void add(Stay stay, MultipartFile[] images) {
    //foreach images: imageStorageService.save(image)
    //collect url
    //set url to stay object
    //TODO: append function for editing stay
    List<String> mediaLinks = Arrays.stream(images).parallel()
                                    .map(image -> imageStorageService.save(image)).collect(
            Collectors.toList());
    List<StayImage> stayImages = new ArrayList<>();
    
    for (String mediaLink : mediaLinks) {
      stayImages.add(new StayImage(mediaLink, stay));
    }
    stay.setImages(stayImages);
    stayRepository.save(stay);// stay images cascading

    Location location = geoCodingService.getLatLng(stay.getId(), stay.getAddress());
    locationRepository.save(location);
  }


  @Transactional(isolation = Isolation.SERIALIZABLE) //delete stay has cascading operation so need Transactional
  public void delete(Long stayId, String username) throws StayNotExistException {
    Stay stay = stayRepository.findByIdAndHost(stayId,
        new User.Builder().setUsername(username).build());
    if (stay == null) {
      throw new StayNotExistException("Stay doesn't exist");
    }

    //???????????????stay????????????
    List<Reservation> reservations = reservationRepository.findByStayAndCheckoutDateAfter(stay,
        LocalDate.now());

    if (reservations != null && reservations.size() > 0) {
      throw new StayDeleteException("Cannot delete stay with active reservation");
    }

    stayRepository.deleteById(stayId);
  }
}
