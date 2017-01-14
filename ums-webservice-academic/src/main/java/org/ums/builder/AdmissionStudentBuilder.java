package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
import org.ums.enums.DepartmentSelectionType;
import org.ums.enums.MigrationStatus;
import org.ums.enums.ProgramType;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import sun.dc.pr.PRError;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 14-Dec-16.
 */
@Component
public class AdmissionStudentBuilder implements Builder<AdmissionStudent, MutableAdmissionStudent> {

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  ProgramManager mProgramManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, AdmissionStudent pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {

  }

  public void admissionStudentBuilder(JsonObjectBuilder pBuilder, AdmissionStudent pReadOnly,
      UriInfo pUriInfo, LocalCache pLocalCache, String type) {

    pBuilder.add("id", pReadOnly.getReceiptId());
    pBuilder.add("text", pReadOnly.getReceiptId());
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("semesterName", pReadOnly.getSemester().getName());
    pBuilder.add("receiptId", pReadOnly.getId());
    pBuilder.add("studentName", pReadOnly.getStudentName());
    pBuilder.add("quota", pReadOnly.getQuota());

    pBuilder.add("pin", pReadOnly.getPin());
    pBuilder.add("hscBoard", pReadOnly.getHSCBoard());
    pBuilder.add("hscRoll", pReadOnly.getHSCRoll());
    pBuilder.add("hscRegNo", pReadOnly.getHSCRegNo());
    pBuilder.add("hscYear", pReadOnly.getHSCYear());
    pBuilder.add("hscGroup", pReadOnly.getHSCGroup());
    pBuilder.add("sscBoard", pReadOnly.getSSCBoard());
    pBuilder.add("sscRoll", pReadOnly.getSSCRoll());
    pBuilder.add("sscYear", pReadOnly.getSSCYear());
    pBuilder.add("sscGroup", pReadOnly.getSSCGroup());
    pBuilder.add("gender", pReadOnly.getGender());
    pBuilder.add("dateOfBirth", pReadOnly.getBirthDate());
    pBuilder.add("fatherName", pReadOnly.getFatherName());
    pBuilder.add("motherName", pReadOnly.getMotherName());
    pBuilder.add("sscGpa", pReadOnly.getSSCGpa());
    pBuilder.add("hscGpa", pReadOnly.getHSCGpa());
    pBuilder.add("unit", pReadOnly.getUnit());

    if(type.equals("meritList")) {
      pBuilder.add("admissionRoll", pReadOnly.getAdmissionRoll());
      pBuilder.add("meritSlNo", pReadOnly.getMeritSerialNo());
      if(pReadOnly.getProgramIdByMerit() != 0) {
        pBuilder.add("programIdByMerit", pReadOnly.getProgramByMerit().getId());
        pBuilder.add("programNameByMerit", pReadOnly.getProgramByMerit().getShortName());
      }
      if(pReadOnly.getProgramIdByTransfer() != 0) {
        pBuilder.add("programIdByTransfer", pReadOnly.getProgramByTransfer().getId());
        pBuilder.add("programNameByTransfer", pReadOnly.getProgramByTransfer().getShortName());
      }
    }

    if(type.equals("departmentSelection")) {
      pBuilder.add("studentId", pReadOnly.getStudentId());
      pBuilder.add("allocatedProgramId", pReadOnly.getAllocatedProgram().getId());
      pBuilder.add("programShortName", pReadOnly.getAllocatedProgram().getShortName());
      pBuilder.add("programLongName", pReadOnly.getAllocatedProgram().getLongName());
      pBuilder.add("migrationStatus", pReadOnly.getMigrationStatus().getId());
    }

    if(pReadOnly.getDeadline() != null) {
      pBuilder.add("deadline", pReadOnly.getDeadline());
    }

  }

  public void getAdmissionStudentByReceiptIdBuilder(JsonObjectBuilder pBuilder,
      AdmissionStudent pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("semesterName", pReadOnly.getSemester().getName());
    pBuilder.add("receiptId", pReadOnly.getId());
    pBuilder.add("studentName", pReadOnly.getStudentName());
    pBuilder.add("quota", pReadOnly.getQuota());

    pBuilder.add("pin", pReadOnly.getPin());
    pBuilder.add("hscBoard", pReadOnly.getHSCBoard());
    pBuilder.add("hscRoll", pReadOnly.getHSCRoll());
    pBuilder.add("hscRegNo", pReadOnly.getHSCRegNo());
    pBuilder.add("hscYear", pReadOnly.getHSCYear());
    pBuilder.add("hscGroup", pReadOnly.getHSCGroup());
    pBuilder.add("sscBoard", pReadOnly.getSSCBoard());
    pBuilder.add("sscRoll", pReadOnly.getSSCRoll());
    pBuilder.add("sscYear", pReadOnly.getSSCYear());
    pBuilder.add("sscGroup", pReadOnly.getSSCGroup());
    pBuilder.add("gender", pReadOnly.getGender());
    pBuilder.add("dateOfBirth", pReadOnly.getBirthDate());
    pBuilder.add("fatherName", pReadOnly.getFatherName());
    pBuilder.add("motherName", pReadOnly.getMotherName());
    pBuilder.add("sscGpa", pReadOnly.getSSCGpa());
    pBuilder.add("hscGpa", pReadOnly.getHSCGpa());
    pBuilder.add("unit", pReadOnly.getUnit());

    if(pReadOnly.getUnit().equals("ENGINEERING") || pReadOnly.getAdmissionRoll().equals(null)) {
      pBuilder.add("admissionRoll", pReadOnly.getAdmissionRoll());
      pBuilder.add("meritSlNo", pReadOnly.getMeritSerialNo());
    }
    else {
      pBuilder.add("admissionRoll", "Not Available");
      pBuilder.add("meritSlNo", "Not Available");
    }
  }

  @Override
  public void build(MutableAdmissionStudent pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("receiptId"));
    pMutable.setSemester(mSemesterManager.get(pJsonObject.getInt("semesterId")));
    pMutable.setPin(pJsonObject.getString("pin"));
    pMutable.setHSCBoard(pJsonObject.getString("hscBoard"));
    pMutable.setSSCBoard(pJsonObject.getString("sscBoard"));
    pMutable.setHSCRoll(pJsonObject.getString("hscRoll"));
    pMutable.setSSCRoll(pJsonObject.getString("sscRoll"));
    pMutable.setHSCRegNo(pJsonObject.getString("hscRegNo"));
    pMutable.setUnit(pJsonObject.getString("unit"));
    pMutable.setHSCYear(pJsonObject.getInt("hscYear"));
    pMutable.setSSCYear(pJsonObject.getInt("sscYear"));
    pMutable.setHSCGroup(pJsonObject.getString("hscGroup"));
    pMutable.setSSCGroup(pJsonObject.getString("sscGroup"));
    pMutable.setGender(pJsonObject.getString("gender"));
    pMutable.setDateOfBirth(pJsonObject.getString("dateOfBirth"));
    pMutable.setStudentName(pJsonObject.getString("studentName"));
    pMutable.setFatherName(pJsonObject.getString("fatherName"));
    pMutable.setMotherName(pJsonObject.getString("motherName"));
    pMutable.setSSCGpa(Double.parseDouble(pJsonObject.getString("sscGpa")));
    pMutable.setHSCGpa(Double.parseDouble(pJsonObject.getString("hscGpa")));
    pMutable.setQuota(pJsonObject.getString("quota"));
    pMutable.setUnit(pJsonObject.getString("unit"));
    if(!pJsonObject.getString("admissionRoll").equals("null"))
      pMutable.setAdmissionRoll(pJsonObject.getString("admissionRoll"));
    if(!(pJsonObject.getString("meritSlNo")).contains("null")) {
      pMutable.setMeritSerialNo(pJsonObject.getInt("meritSlNo"));
    }
    if(!pJsonObject.getString("programId").contains("null")) {
      pMutable.setAllocatedProgram(mProgramManager.get(pJsonObject.getInt("programId")));
    }
    if(!(pJsonObject.getString("migrationStatus")).contains("null")) {
      pMutable.setMigrationStatus(MigrationStatus.get(pJsonObject.getInt("migrationStatus")));
    }
    pMutable.setProgramType(ProgramType.get(pJsonObject.getInt("programType")));
  }

  public void build(MutableAdmissionStudent pMutable, JsonObject pJsonObject, String pType,
      LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("receiptId"));
    pMutable.setSemester(mSemesterManager.get(pJsonObject.getInt("semesterId")));

    if(pType.equals("meritList")) {
      pMutable.setMeritSerialNo(pJsonObject.getInt("meritSlNo"));
      pMutable.setAdmissionRoll(pJsonObject.getString("admissionRoll"));
    }
    if(pType.equals("departmentSelection")) {
      pMutable.setProgramIdByMerit(pJsonObject.getInt("programIdByMerit"));
      pMutable.setProgramIdByTransfer(pJsonObject.getInt("programIdByTransfer"));
    }

  }

  public void build(MutableAdmissionStudent pMutable, JsonObject pJsonObject,
      DepartmentSelectionType pType, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("receiptId"));
    pMutable.setSemester(mSemesterManager.get(pJsonObject.getInt("semesterId")));
    pMutable.setProgramType(ProgramType.get(pJsonObject.getInt("programType")));
    pMutable.setUnit(pJsonObject.getString("unit"));
    pMutable.setQuota(pJsonObject.getString("quota"));

    if(DepartmentSelectionType.MERIT_WAITING_PROGRAMS_SELECTED == pType) {
      pMutable.setProgramIdByMerit(pJsonObject.getInt("programIdByMerit"));
      pMutable.setProgramIdByTransfer(pJsonObject.getInt("programIdByTransfer"));
      pMutable.setDeadline(pJsonObject.getString("deadline"));
    }
    else if(DepartmentSelectionType.MERIT_PROGRAM_SELECTED == pType) {
      pMutable.setProgramIdByMerit(pJsonObject.getInt("programIdByMerit"));
      pMutable.setDeadline(pJsonObject.getString("deadline"));
    }
    else if(DepartmentSelectionType.WAITING_PROGRAM_SELECTED == pType) {
      pMutable.setProgramIdByTransfer(pJsonObject.getInt("programIdByTransfer"));
    }
    else {
      // do nothing
    }

  }

  public void setVerificationStatus(MutableAdmissionStudent pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("receiptId"));
    pMutable.setSemester(mSemesterManager.get(pJsonObject.getInt("semesterId")));
    pMutable.setProgramType(ProgramType.get(pJsonObject.getInt("programType")));
    pMutable.setVerificationStatus(pJsonObject.getInt("status"));
  }
}