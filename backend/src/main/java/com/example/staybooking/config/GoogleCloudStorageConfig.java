package com.example.staybooking.config;

import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleCloudStorageConfig {


  @Bean
  public Storage storage() throws IOException {//similar to mysql's datasource+session-factory

    //from any class to load the file in resource folder:getClass().getClassLoader().getResourceAsStream
    Credentials credentials = ServiceAccountCredentials.fromStream(
        getClass().getClassLoader().getResourceAsStream("staybooking-credential.json"));

    return StorageOptions.newBuilder().setCredentials(credentials).build().getService();

  }
}