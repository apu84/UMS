package org.ums.domain.model;


import java.io.Serializable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface Course extends Serializable, LastModifier, EditType<MutableCourse>, Identifier<String> {

  String getNo();

  String getTitle();

  float getCrHr();

  int getOfferedDepartmentId();

  Department getOfferedBy() throws Exception;

  Department getOfferedTo() throws Exception;

  int getYear();

  int getSemester();

  int getViewOrder();

  int getCourseGroupId();

  CourseGroup getCourseGroup(final String pSyllabusId) throws Exception;

  String getSyllabusId();

  Syllabus getSyllabus() throws Exception;

  CourseType getCourseType();

  CourseCategory getCourseCategory();

  enum CourseType {
    THEORY(1),
    SESSIONAL(2),
    THESIS_PROJECT(3),
    NONE(0);

    private static final Map<Integer, CourseType> lookup
        = new HashMap<>();

    static {
      for (CourseType c : EnumSet.allOf(CourseType.class)) {
        lookup.put(c.getValue(), c);
      }
    }


    private int typeCode;

    private CourseType(int pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static CourseType get(final int pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public int getValue() {
      return this.typeCode;
    }
  }

  enum CourseCategory {
    MANDATORY(1),
    OPTIONAL(2),
    NONE(0);

    private static final Map<Integer, CourseCategory> lookup
        = new HashMap<>();

    static {
      for (CourseCategory c : EnumSet.allOf(CourseCategory.class))
        lookup.put(c.getValue(), c);
    }


    private int typeCode;

    private CourseCategory(int pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static CourseCategory get(final int pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public int getValue() {
      return this.typeCode;
    }
  }
}
