package com.faceshoots.aigateway.api;

import com.faceshoots.aigateway.domain.StyleKey;
import com.faceshoots.aigateway.service.OpenAiImageClient;
import com.faceshoots.aigateway.service.PromptBuilder;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GenerateController {

  private final PromptBuilder promptBuilder;
  private final OpenAiImageClient openAiImageClient;

  public GenerateController(PromptBuilder promptBuilder, OpenAiImageClient openAiImageClient) {
    this.promptBuilder = promptBuilder;
    this.openAiImageClient = openAiImageClient;
  }

  /**
   * Frontend sends ONLY:
   *  - image (Multipart)
   *  - styleKey (doctor | corporate | lawyer)
   *
   * Backend returns ONLY:
   *  - images: ["data:image/png;base64,...", ...]
   */
  @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> generate(
      @RequestPart("image") MultipartFile image,
      @RequestPart("styleKey") @NotBlank String styleKey
  ) throws Exception {

    if (image == null || image.isEmpty()) {
      return ResponseEntity.badRequest().body(Map.of("error", "image is required"));
    }

    String ct = image.getContentType() == null ? "" : image.getContentType().toLowerCase();
    if (!(ct.contains("jpeg") || ct.contains("jpg") || ct.contains("png") || ct.contains("webp"))) {
      return ResponseEntity.badRequest().body(Map.of("error", "Only JPG/PNG/WEBP allowed"));
    }

    StyleKey key = StyleKey.from(styleKey);
    String prompt = promptBuilder.build(key);

    List<String> images = openAiImageClient.editImage(
        image.getBytes(),
        image.getOriginalFilename(),
        image.getContentType(), 
        prompt
    );

    return ResponseEntity.ok(Map.of("images", images));
  }
}
