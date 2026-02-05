package com.faceshoots.aigateway.service;

import org.springframework.core.io.ByteArrayResource;

/**
 * ByteArrayResource normally returns null filename; multipart receivers often require a filename.
 */
public class MultipartByteArrayResource extends ByteArrayResource {
  private final String filename;

  public MultipartByteArrayResource(byte[] byteArray, String filename) {
    super(byteArray);
    this.filename = filename;
  }

  @Override
  public String getFilename() {
    return this.filename;
  }
}
