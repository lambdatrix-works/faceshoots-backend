package com.faceshoots.aigateway.service;

import com.faceshoots.aigateway.domain.StyleKey;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

  /**
   * Frontend sends only: image + styleKey.
   * We build the prompt here to keep the client minimal.
   */
  public String build(StyleKey styleKey) {
    String common = "Create a photorealistic, studio-quality headshot from the input image. "
        + "Keep identity consistent with the input person. "
        + "Professional lighting, sharp focus, clean background, natural skin texture. "
        + "No text, no watermark, no logo. "
        + "Head and shoulders framing, centered composition.";

    return switch (styleKey) {
      case doctor -> common + " Doctor style: white coat or medical attire, bright clean background, friendly confident look.";
      case lawyer -> common + " Lawyer style: formal suit, premium portrait lighting, subtle dark/neutral background, confident expression.";
      case corporate -> common + " Corporate style: business attire, neutral background, modern premium LinkedIn-style headshot.";
    };
  }
}
