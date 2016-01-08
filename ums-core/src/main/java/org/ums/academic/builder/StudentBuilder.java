package org.ums.academic.builder;

import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.regular.Department;
import org.ums.domain.model.regular.Program;
import org.ums.domain.model.regular.Semester;
import org.ums.domain.model.regular.Student;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;

public class StudentBuilder implements Builder<Student, MutableStudent> {
  DateFormat mDateFormat;

  public StudentBuilder(final DateFormat pDateFormat) {
    mDateFormat = pDateFormat;
  }

  public void build(final JsonObjectBuilder pBuilder,
                    final Student pStudent,
                    final UriInfo pUriInfo,
                    final LocalCache pLocalCache) throws Exception {

    pBuilder.add("id", pStudent.getId());
    pBuilder.add("fullName", pStudent.getFullName());

    Department department = (Department) pLocalCache.cache(() -> pStudent.getDepartment(), pStudent.getDepartmentId(), Department.class);
    pBuilder.add("departmentId", department.getId());
    pBuilder.add("department", pUriInfo.getBaseUriBuilder().path("academic").path("department")
        .path(String.valueOf(department.getId())).build().toString());

    Semester semester = (Semester) pLocalCache.cache(() -> pStudent.getSemester(), pStudent.getSemesterId(), Semester.class);
    pBuilder.add("semesterId", semester.getId());
    pBuilder.add("semester", pUriInfo.getBaseUriBuilder().path("academic").path("semester")
        .path(String.valueOf(semester.getId())).build().toString());

    Program program = (Program) pLocalCache.cache(() -> pStudent.getProgram(), pStudent.getProgramId(), Program.class);
    pBuilder.add("programId", program.getId());
    pBuilder.add("program", pUriInfo.getBaseUriBuilder().path("academic").path("program")
        .path(String.valueOf(program.getId())).build().toString());

    pBuilder.add("fatherName", pStudent.getFatherName());
    pBuilder.add("motherName", pStudent.getMotherName());
    pBuilder.add("dateOfBirth", mDateFormat.format(pStudent.getDateOfBirth()));
    pBuilder.add("gender", pStudent.getGender());
    pBuilder.add("presentAddress", pStudent.getPresentAddress());
    pBuilder.add("permanentAddress", pStudent.getPermanentAddress());
    pBuilder.add("mobileNo", pStudent.getMobileNo());
    pBuilder.add("phoneNo", pStudent.getPhoneNo());
    pBuilder.add("bloodGroup", pStudent.getBloodGroup());
    pBuilder.add("email", pStudent.getEmail());
    pBuilder.add("guardianName", pStudent.getGuardianName());
    pBuilder.add("guardianMobileNo", pStudent.getGuardianMobileNo());
    pBuilder.add("guardianPhoneNo", pStudent.getGuardianPhoneNo());
    pBuilder.add("guardianEmail", pStudent.getGuardianEmail());
  }

  public void build(final MutableStudent pMutableStudent,
                    final JsonObject pJsonObject,
                    final LocalCache pLocalCache) throws Exception {
    pMutableStudent.setId(pJsonObject.getString("id"));
    pMutableStudent.setFullName(pJsonObject.getString("fullName"));
    pMutableStudent.setDepartmentId(Integer.parseInt(pJsonObject.getString("departmentId")));
    pMutableStudent.setSemesterId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutableStudent.setProgramId(Integer.parseInt(pJsonObject.getString("programId")));
    pMutableStudent.setFatherName(pJsonObject.getString("fatherName"));
    pMutableStudent.setMotherName(pJsonObject.getString("motherName"));
    pMutableStudent.setDateOfBirth(mDateFormat.parse(pJsonObject.getString("dateOfBirth")));
    pMutableStudent.setGender(pJsonObject.getString("gender"));
    pMutableStudent.setPresentAddress(pJsonObject.getString("presentAddress"));
    pMutableStudent.setPermanentAddress(pJsonObject.getString("permanentAddress"));
    pMutableStudent.setMobileNo(pJsonObject.getString("mobileNo"));
    pMutableStudent.setPhoneNo(pJsonObject.getString("phoneNo"));
    pMutableStudent.setBloodGroup(pJsonObject.getString("bloodGroup"));
    pMutableStudent.setEmail(pJsonObject.getString("email"));
    pMutableStudent.setGuardianName(pJsonObject.getString("guardianName"));
    pMutableStudent.setGuardianMobileNo(pJsonObject.getString("guardianMobileNo"));
    pMutableStudent.setGuardianPhoneNo(pJsonObject.getString("guardianPhoneNo"));
    pMutableStudent.setGuardianEmail(pJsonObject.getString("guardianEmail"));
  }

}
