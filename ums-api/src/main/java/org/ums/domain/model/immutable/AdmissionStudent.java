package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.enums.*;
import org.ums.enums.ProgramType;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 12-Dec-16.
 */
public interface AdmissionStudent extends Serializable, EditType<MutableAdmissionStudent>, Identifier<String>,
    LastModifier {
  Semester getSemester();

  Integer getSemesterId();

  ProgramType getProgramType();

  String getReceiptId();

  String getPin();

  String getHSCBoard();

  String getHSCRoll();

  String getHSCRegNo();

  Integer getHSCYear();

  String getHSCGroup();

  Double getHSCGpa();

  String getSSCBoard();

  String getSSCRoll();

  Integer getSSCYear();

  String getSSCGroup();

  Double getSSCGpa();

  String getGender();

  String getBirthDate();

  String getStudentName();

  String getFatherName();

  String getMotherName();

  String getQuota();

  String getUnit();

  String getAdmissionRoll();

  Integer getMeritSerialNo();

  String getStudentId();

  Program getAllocatedProgram();

  Integer getAllocatedProgramId();

  String getNID();

  String getBirthRegNo();

  String getPassportNo();

  MigrationStatus getMigrationStatus();

  PresentStatus getPresentStatus();

  Integer getVerificationStatus();

  String getUndertakenDeadline();

  Program getProgramByMerit();

  Integer getProgramIdByMerit();

  Program getProgramByTransfer();

  Integer getProgramIdByTransfer();

  String getDeadline();

  String getMigratedFrom();

}
