package com.example.staybooking.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

/**
 * ES7以后，一个db 一张table
 * 所有field都会建立index
 *
 */
@Document(indexName = "loc")
public class Location implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id//这里的id类似关系数据库的PK
  @Field(type = FieldType.Long)
  private Long id;//stay id in mySQL

  @GeoPointField
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
