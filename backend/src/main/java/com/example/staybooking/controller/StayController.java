package com.example.staybooking.controller;

import com.example.staybooking.model.Stay;
import com.example.staybooking.model.User;
import com.example.staybooking.service.StayService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StayController {

  private StayService stayService;

  @Autowired
  public StayController(StayService stayService) {
    this.stayService = stayService;
  }

//  @GetMapping(value = "/stays")
//  public List<Stay> listStays(@RequestParam(name = "host") String hostName) {
//    return stayService.listByUser(hostName);
//  }

  @GetMapping(value = "/stays")
  public List<Stay> listStays(Principal principal) {
    return stayService.listByUser(principal.getName());
  }


  //  @GetMapping(value = "/stays/id")
//  public Stay getStay(
//      @RequestParam(name = "stay_id") Long stayId,
//      @RequestParam(name = "host") String hostName) {
//    return stayService.findByIdAndHost(stayId, hostName);
//  }
  @GetMapping(value = "/stays/{stayId}")
  public Stay getStay(@PathVariable Long stayId, Principal principal) {
    return stayService.findByIdAndHost(stayId, principal.getName());
  }


//  @PostMapping("/stays")
//  public void addStay(
//      @RequestParam("name") String name,
//      @RequestParam("address") String address,
//      @RequestParam("description") String description,
//      @RequestParam("host") String host,
//      @RequestParam("guest_number") int guestNumber,
//      @RequestParam("images") MultipartFile[] images) {
//    Stay stay = new Stay.Builder().setName(name)
//                                  .setAddress(address)
//                                  .setDescription(description)
//                                  .setGuestNumber(guestNumber)
//                                  .setHost(new User.Builder().setUsername(host).build())
//                                  .build();
//    stayService.add(stay, images);
//  }

  @PostMapping("/stays")
  public void addStay(
      @RequestParam("name") String name,
      @RequestParam("address") String address,
      @RequestParam("description") String description,
      @RequestParam("guest_number") int guestNumber,
      @RequestParam(value = "images", required = false) MultipartFile[] images,
      Principal principal) {

    Stay stay = new Stay.Builder()
        .setName(name)
        .setAddress(address)
        .setDescription(description)
        .setGuestNumber(guestNumber)
        .setHost(new User.Builder().setUsername(principal.getName()).build())
        .build();
    stayService.add(stay, images);
  }


//  @DeleteMapping("/stays")
//  public void deleteStay(
//      @RequestParam(name = "stay_id") Long stayId,
//      @RequestParam(name = "host") String hostName) {
//    stayService.delete(stayId, hostName);
//  }

  @DeleteMapping("/stays/{stayId}")
  public void deleteStay(@PathVariable Long stayId, Principal principal) {
    stayService.delete(stayId, principal.getName());
  }


}

