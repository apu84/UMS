package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionMeritList;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableAdmissionMeritList;
import org.ums.enums.QuotaType;
import org.ums.manager.FacultyManager;
import org.ums.manager.SemesterManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 11-Dec-16.
 */
@Component
public class AdmissionMeritListBuilder implements
    Builder<AdmissionMeritList, MutableAdmissionMeritList> {

  @Autowired
  SemesterManager mSemesterManager;

  @Autowired
  FacultyManager mFacultyManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, AdmissionMeritList pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("semesterName", pReadOnly.getSemester().getName());
    pBuilder.add("meritSlNo", pReadOnly.getMeritListSerialNo());
    pBuilder.add("receiptId", pReadOnly.getReceiptId());
    pBuilder.add("admissionRoll", pReadOnly.getAdmissionRoll());
    pBuilder.add("candidateName", pReadOnly.getCandidateName());
    pBuilder.add("admissionGroup", pReadOnly.getAdmissionGroup().getId());
    pBuilder.add("facultyId", pReadOnly.getFaculty().getId());
    pBuilder.add("facultyShortName", pReadOnly.getFaculty().getShortName());
    pBuilder.add("facultyLongName", pReadOnly.getFaculty().getLongName());
    pBuilder.add("lastModified", pReadOnly.getLastModified());
  }

  @Override
  public void build(MutableAdmissionMeritList pMutable, JsonObject pJsonObject,
      LocalCache pLocalCache) {
    Semester semester = mSemesterManager.get(pJsonObject.getInt("semesterId"));
    pMutable.setSemester(semester);

    pMutable.setMeritListSerialNo(pJsonObject.getInt("meritSlNo"));
    pMutable.setReceiptId(pJsonObject.getInt("receiptId"));
    pMutable.setAdmissionRoll(pJsonObject.getInt("admissionRoll"));
    pMutable.setCandidateName(pJsonObject.getString("candidateName"));
    pMutable.setAdmissionGroup(QuotaType.get(pJsonObject.getInt("admissionGroup")));

    Faculty faculty = mFacultyManager.get(pJsonObject.getInt("facultyId"));
    pMutable.setFaculty(faculty);
  }
}
