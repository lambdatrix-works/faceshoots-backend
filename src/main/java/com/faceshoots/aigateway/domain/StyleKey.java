package com.faceshoots.aigateway.domain;

public enum StyleKey {
  doctor,
  corporate,
  lawyer;

  public static StyleKey from(String raw) {
    if (raw == null || raw.isBlank()) return corporate;
    try {
      return StyleKey.valueOf(raw.trim().toLowerCase());
    } catch (Exception e) {
      return corporate;
    }
  }
}
