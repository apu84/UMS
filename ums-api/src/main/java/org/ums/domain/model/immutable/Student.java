package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableStudent;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public interface Student extends Serializable, EditType<MutableStudent>, Identifier<String>, LastModifier {
  User getUser();

  String getFullName();

  String getDepartmentId();

  Department getDepartment() throws Exception;

  Integer getSemesterId();

  Semester getSemester() throws Exception;

  Integer getProgramId();

  Program getProgram() throws Exception;

  String getFatherName();

  String getMotherName();

  Date getDateOfBirth();

  String getGender();

  String getPresentAddress();

  String getPermanentAddress();

  String getMobileNo();

  String getPhoneNo();

  String getBloodGroup();

  String getEmail();

  String getGuardianName();

  String getGuardianMobileNo();

  String getGuardianPhoneNo();

  String getGuardianEmail();

  EnrollmentType getEnrollmentType();

  Integer getCurrentYear();

  Integer getCurrentAcademicSemester();

  Integer getCurrentEnrolledSemesterId();

  Semester getCurrentEnrolledSemester();

  String getTheorySection();

  String getSessionalSection();

  Integer getApplicationType();

  String getProgramShortName();

  enum EnrollmentType {
    ACTUAL(1),
    TEMPORARY(0);

    private static final Map<Integer, EnrollmentType> lookup
        = new HashMap<>();

    static {
      for (EnrollmentType c : EnumSet.allOf(EnrollmentType.class)) {
        lookup.put(c.getValue(), c);
      }
    }


    private int typeCode;

    private EnrollmentType(int pTypeCode) {
      this.typeCode = pTypeCode;
    }

    public static EnrollmentType get(final int pTypeCode) {
      return lookup.get(pTypeCode);
    }

    public int getValue() {
      return this.typeCode;
    }
  }
}
