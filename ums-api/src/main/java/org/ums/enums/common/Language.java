package org.ums.enums.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 16-Feb-17.
 */
public enum Language {

  SELECT_A_LANGUAGE(101101, "Select a Language"),
  ENGLISH(1, "English"),
  BENGALI(2, "Bengali"),
  FRENCH(3, "French"),
  CHINESE(4, "Chinese"),
  RUSSIAN(5, "Russian"),
  SPANISH(6, "Spanish"),
  GERMAN(7, "German"),
  JAPANESE(8, "Japanese"),
  HINDI(9, "Hindi"),
  URDU(10, "Urdu"),
  ARABIC(11, "Arabic"),
  SANSKRIT(12, "Sanskrit");

  private String label;
  private int id;

  private Language(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, Language> lookup = new HashMap<>();

  static {
    for(Language c : EnumSet.allOf(Language.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static Language get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }
}
