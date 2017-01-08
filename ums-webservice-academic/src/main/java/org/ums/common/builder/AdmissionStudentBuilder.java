package org.ums.common.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.helper.AdmissionStudentResourceHelper;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.domain.model.immutable.AdmissionStudentCertificate;
import org.ums.domain.model.mutable.MutableAdmissionStudent;
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
    }

    if(type.equals("departmentSelection")) {
      pBuilder.add("studentId", pReadOnly.getStudentId());
      pBuilder.add("allocatedProgramId", pReadOnly.getAllocatedProgram().getId());
      pBuilder.add("programShortName", pReadOnly.getAllocatedProgram().getShortName());
      pBuilder.add("programLongName", pReadOnly.getAllocatedProgram().getLongName());
      pBuilder.add("migrationStatus", pReadOnly.getMigrationStatus().getId());
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

  public void admissionStudentCertificateBuilder(JsonObjectBuilder pBuilder,
      AdmissionStudentCertificate pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("certificateId", pReadOnly.getCertificateId());
    pBuilder.add("certificateTitle", pReadOnly.getCertificateTitle());
    pBuilder.add("certificateType", pReadOnly.getCertificateType());
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
  }
}
