package com.faceshoots.aigateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.*;

/**
 * Minimal OpenAI image client.
 * Returns ONLY a list of data-URLs (base64) for gpt-image-1.
 */
@Service
public class OpenAiImageClient {

  private final RestClient rest;

  @Value("${faceshoots.openai.apiKey:}")
  private String apiKey;

  @Value("${faceshoots.openai.baseUrl:https://api.openai.com}")
  private String baseUrl;

  @Value("${faceshoots.openai.model:gpt-image-1}")
  private String model;

  @Value("${faceshoots.openai.n:4}")
  private int n;

  @Value("${faceshoots.openai.size:1024x1024}")
  private String size;

  public OpenAiImageClient(RestClient.Builder builder) {
    this.rest = builder.build();
  }

  public List<String> editImage(byte[] imageBytes, String filename, String contentType, String prompt) {
    if (apiKey == null || apiKey.isBlank()) {
      throw new IllegalStateException("Missing OPENAI_API_KEY env var.");
    }

    String url = baseUrl + "/v1/images/edits";

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(apiKey);
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("model", model);
    body.add("prompt", prompt);
    body.add("n", String.valueOf(n));
    body.add("size", size);

    MultipartByteArrayResource fileResource = new MultipartByteArrayResource(
        imageBytes,
        (filename == null || filename.isBlank()) ? "input.png" : filename
    );
    HttpHeaders fileHeaders = new HttpHeaders();
    fileHeaders.setContentType(MediaType.parseMediaType(contentType));
    HttpEntity<MultipartByteArrayResource> filePart = new HttpEntity<>(fileResource, fileHeaders);
    body.add("image", filePart);

    @SuppressWarnings("unchecked")
    Map<String, Object> resp = rest.post()
        .uri(url)
        .headers(h -> h.addAll(headers))
        .body(body)
        .retrieve()
        .body(Map.class);

    if (resp == null) throw new IllegalStateException("Empty response from provider");

    Object dataObj = resp.get("data");
    if (!(dataObj instanceof List<?> dataList)) {
      return List.of();
    }

    List<String> images = new ArrayList<>();
    for (Object itemObj : dataList) {
      if (!(itemObj instanceof Map<?, ?> item)) continue;
      Object b64 = item.get("b64_json");
      if (b64 != null) {
        images.add("data:image/png;base64," + b64.toString());
      }
    }
    return images;
  }
}
