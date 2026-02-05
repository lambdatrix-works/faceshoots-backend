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
		String common = "Edit the provided photo to create a photorealistic, studio-quality headshot. "
				+ "This is an image EDIT of the input photo, not a newly generated person. "
				+ "Do NOT generate a new face. Preserve the exact facial features, proportions, and identity. "
				+ "Do not change facial structure, skin tone, age, hairstyle, or gender expression. "
				+ "Only change clothing, lighting, and background. "
				+ "One person only. No extra faces, hands, or artifacts. "
				+ "Natural skin texture (no beauty filters or plastic skin). "
				+ "Sharp focus, professional studio lighting. " + "Clean smooth background (solid or soft gradient). "
				+ "Head-and-shoulders framing (chest-up), centered composition, slight headroom. "
				+ "No text, watermark, logo, or graphics. ";

		return switch (styleKey) {
		case doctor -> common
				+ "Doctor style: white coat or medical professional attire, bright clean background, friendly confident expression. "
				+ "No cartoon props, no exaggerated stethoscope/medical icons.";
		case lawyer -> common + "Lawyer style: authoritative legal professional portrait. "
				+ "Formal black suit with a subtle traditional white advocate neck band, naturally fitted to the same person. "
				+ "Dramatic but realistic contrast-rich studio lighting. "
				+ "Dark neutral background (charcoal/deep grey). "
				+ "Serious, composed expression conveying trust and authority. " + "Do not change the person’s face.";
		case corporate -> common + "Corporate style: modern premium LinkedIn headshot. "
				+ "Business professional attire. Bright, evenly balanced lighting. "
				+ "Light neutral background (grey/beige/soft gradient). " + "Approachable, friendly expression. "
				+ "Keep it clean and contemporary.";
		};
	}

}
