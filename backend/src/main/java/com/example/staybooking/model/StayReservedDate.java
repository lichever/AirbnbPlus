package com.example.staybooking.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "stay_reserved_date")
public class StayReservedDate implements Serializable {
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private StayReservedDateKey id;

  // column stay_id  is a forenign key created in this table referencing the table stay
  //here MapsId maps stay_id in the StayReservedDateKey, no need to create a new one, like JoinColumn
  @MapsId("stay_id")
  @ManyToOne
  private Stay stay;


  public StayReservedDate() {}

  public StayReservedDate(StayReservedDateKey id, Stay stay) {
    this.id = id;
    this.stay = stay;
  }

  public StayReservedDateKey getId() {
    return id;
  }

  public Stay getStay() {
    return stay;
  }

}
