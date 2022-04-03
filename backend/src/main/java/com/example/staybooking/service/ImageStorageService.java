package com.example.staybooking.service;

import com.example.staybooking.exception.GCSUploadException;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageStorageService {

  @Value("${gcs.bucket}")
  private String bucketName;//data source

  private Storage storage;

  @Autowired
  public ImageStorageService(Storage storage) {
    this.storage = storage;//session-factory
  }

  //return url
  public String save(MultipartFile file) throws GCSUploadException {
    String filename = UUID.randomUUID().toString();
    BlobId blobId = BlobId.of(bucketName, filename);

    //setAcl(new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))) set the url of file public READER role after uploading
    //这个比较粗糙，更好的是 在前端程序也写死生成一个key，后端来验证 访问来源 是来自自己的程序 就开放public。只对我自己的前端程序开放
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").setAcl(
        new ArrayList<>(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER)))).build();

    try {
      blobInfo = storage.createFrom( blobInfo, file.getInputStream());
    } catch (IOException exception) {
      throw new GCSUploadException("Failed to upload file to GCS");
    }

    return blobInfo.getMediaLink();
  }
}

