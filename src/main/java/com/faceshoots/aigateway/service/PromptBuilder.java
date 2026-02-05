package com.faceshoots.aigateway.service;

import com.faceshoots.aigateway.domain.StyleKey;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

	/**
	 * Frontend sends only: image + styleKey. We build the prompt here to keep the
	 * client minimal.
	 */
	public String build(StyleKey styleKey) {
		String common = "Create a photorealistic, studio-quality headshot from the input image. "
				+ "Preserve the person’s exact likeness and identity (same face). "
				+ "Do not change facial structure, skin tone, age, hairstyle, or gender expression. "
				+ "One person only. No extra faces, hands, or artifacts. "
				+ "Professional studio lighting, sharp focus, natural skin texture. "
				+ "Clean smooth background (solid or soft gradient). "
				+ "Head-and-shoulders framing (chest-up), centered composition, slight headroom. "
				+ "No text, watermark, logo, or graphics.";

		return switch (styleKey) {
		case doctor -> common
				+ " Doctor style: white coat or medical professional attire, bright clean background, friendly confident expression. "
				+ "No cartoon props, no exaggerated stethoscope/medical icons.";
		case lawyer -> common
				+ " Lawyer style: authoritative legal professional portrait. Formal black suit with traditional white advocate neck band. Dramatic, contrast-rich studio lighting. Dark neutral background. Serious, composed expression conveying trust and authority.";
		case corporate -> common
				+ " Corporate style: business professional attire, neutral modern background, premium LinkedIn-style headshot, approachable expression. "
				+ "No flashy fashion, keep it simple and executive.";
		};
	}

}
