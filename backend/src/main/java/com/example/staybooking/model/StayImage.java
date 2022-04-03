package com.example.staybooking.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stay_image")
public class StayImage implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private String url;

  @ManyToOne
  @JoinColumn(name = "stay_id")

  //must add JsonIgnore, otherwise recurse forever
  @JsonIgnore
  private Stay stay;

  public StayImage() {}

  public StayImage(String url, Stay stay) {
    this.url = url;
    this.stay = stay;
  }

  public String getUrl() {
    return url;
  }

  public StayImage setUrl(String url) {
    this.url = url;
    return this;
  }

  public Stay getStay() {
    return stay;
  }

  public StayImage setStay(Stay stay) {
    this.stay = stay;
    return this;
  }
}
