package org.ums.enums.library;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ifti on 16-Feb-17.
 */
public enum BookBindingType {

  SADDLE(1, "Saddle Stitched"),
  LOOP(2, "'Loop Stitched'"),
  STAB(3, "'Stab Stitched or Side Stitched'"),
  SEWN(4, "'Sewn Bound'"),
  PERFECT(5, "'Perfect Bound'"),
  TAPE(6, "'Tape Bound'"),
  SCREW(7, "'Screw Bound'"),
  HARDCOVER(8, "'Hardcover or Case Bound'"),
  PLASTIC(9, "'Plastic Grip'"),
  COMB(10, "'Comb Bound or Plastic Bound'"),
  SPIRAL(11, "'Spiral Bound or Coil Bound'"),
  WIRE_O(12, "'Wire-O Bound or Wire Bound'");
  ;

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
