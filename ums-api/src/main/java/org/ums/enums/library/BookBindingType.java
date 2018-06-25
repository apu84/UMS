package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 16-Feb-17.
 */
public enum BookBindingType {

  EMPTY(101101, ""),
  Hard_Bound(1, "Hard Bound"),
  Soft_Cover(2, "Soft Cover"),
  Paperback(3, "Paperback"),
  Clip_Binding(4, "Clip Binding"),
  Gum_Paste_Binding(5, "Gum Paste Binding"),
  Cloth_Binding(6, "Cloth Binding");

  private String label;
  private int id;

  private BookBindingType(int id, String label) {
    this.id = id;
    this.label = label;
  }

  private static final Map<Integer, BookBindingType> lookup = new HashMap<>();

  static {
    for(BookBindingType c : EnumSet.allOf(BookBindingType.class)) {
      lookup.put(c.getId(), c);
    }
  }

  public static BookBindingType get(final int pId) {
    return lookup.get(pId);
  }

  public String getLabel() {
    return this.label;
  }

  public int getId() {
    return this.id;
  }

}
