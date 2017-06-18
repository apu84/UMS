package org.ums.builder;

import java.text.ParseException;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.formatter.DateFormat;
import org.ums.manager.BinaryContentManager;
import org.ums.persistent.model.PersistentTeacher;

@Component
@Qualifier("StudentBuilder")
public class StudentBuilder implements Builder<Student, MutableStudent> {
  @Autowired
  private DateFormat mDateFormat;
  @Autowired
  @Qualifier("fileContentManager")
  private BinaryContentManager<byte[]> mBinaryContentManager;

  public void build(final JsonObjectBuilder pBuilder, final Student pStudent,
      final UriInfo pUriInfo, final LocalCache pLocalCache) {

    pBuilder.add("id", pStudent.getId());
    pBuilder.add("fullName", pStudent.getFullName());

    Department department = (Department) pLocalCache.cache(() -> pStudent.getDepartment(),
        pStudent.getDepartmentId(), Department.class);
    pBuilder.add("departmentId", department.getId());
    pBuilder.add("department", pUriInfo.getBaseUriBuilder().path("academic").path("department")
        .path(String.valueOf(department.getId())).build().toString());
    pBuilder.add("departmentName", department.getLongName());
    pBuilder.add("departmentShortName", department.getShortName());

    Semester semester = (Semester) pLocalCache.cache(() -> pStudent.getSemester(),
        pStudent.getSemesterId(), Semester.class);
    pBuilder.add("semesterId", semester.getId());
    pBuilder.add("semesterName", semester.getName());
    pBuilder.add("semester", pUriInfo.getBaseUriBuilder().path("academic").path("semester")
        .path(String.valueOf(semester.getId())).build().toString());

    Semester currentSemester = (Semester) pLocalCache.cache(() -> pStudent.getCurrentEnrolledSemester(),
        pStudent.getCurrentEnrolledSemesterId(), Semester.class);
    pBuilder.add("currentEnrolledSemesterId", currentSemester.getId());
    pBuilder.add("currentEnrolledSemesterName", currentSemester.getName());

    Program program = (Program) pLocalCache.cache(() -> pStudent.getProgram(),
        pStudent.getProgramId(), Program.class);
    pBuilder.add("programId", program.getId());
    pBuilder.add("program", pUriInfo.getBaseUriBuilder().path("academic").path("program")
        .path(String.valueOf(program.getId())).build().toString());
    pBuilder.add("programName", program.getLongName());
    pBuilder.add("programShortName", program.getShortName());
    pBuilder.add("programTypeId", program.getProgramTypeId());
    pBuilder.add("year", pStudent.getCurrentYear());
    pBuilder.add("academicSemester", pStudent.getCurrentAcademicSemester());

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
    pBuilder.add("year", pStudent.getCurrentYear());
    pBuilder.add("academicSemester", pStudent.getCurrentAcademicSemester());
    pBuilder.add("guardianName", pStudent.getGuardianName());
    pBuilder.add("guardianMobileNo", pStudent.getGuardianMobileNo());
    pBuilder.add("guardianPhoneNo", pStudent.getGuardianPhoneNo());
    pBuilder.add("guardianEmail", pStudent.getGuardianEmail());

    if(pStudent.getAdviser().getId() != null) {
      pBuilder.add("adviser", pStudent.getAdviser().getId());
    }
    if(pStudent.getTheorySection() != null) {
      pBuilder.add("section", pStudent.getTheorySection());
    }

    pBuilder.add("status", pStudent.getStatus().getId());

  }

  public void build(final MutableStudent pMutableStudent, final JsonObject pJsonObject, final LocalCache pLocalCache) {

    /*
     * Validator validator = new StudentValidator(); validator.validate(pJsonObject);
     */

    pMutableStudent.setId(pJsonObject.getString("id"));
    pMutableStudent.setFullName(pJsonObject.getString("fullName"));
    if(pJsonObject.getJsonObject("programSelector") != null) {
      pMutableStudent.setDepartmentId(pJsonObject.getJsonObject("programSelector").getString("departmentId"));
      pMutableStudent.setProgramId(Integer
          .parseInt(pJsonObject.getJsonObject("programSelector").getString("programId")));
    }
    else {

      pMutableStudent.setDepartmentId(pJsonObject.getString("departmentId"));
      pMutableStudent.setProgramId(pJsonObject.getInt("programId"));
    }
    pMutableStudent.setSemesterId(Integer.parseInt(pJsonObject.getString("semesterId")));
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
    if(pJsonObject.getString("adviser") != null) {
      PersistentTeacher teacher = new PersistentTeacher();
      teacher.setId(pJsonObject.getString("adviser"));
      pMutableStudent.setAdviser(teacher);
    }
  }

  public void buildAdvisor(final MutableStudent pMutableStudent, final JsonObject pJsonObject,
      final LocalCache pLocalCache) {
    pMutableStudent.setId(pJsonObject.getString("id"));
    if(pJsonObject.getString("adviser") != null) {
      PersistentTeacher teacher = new PersistentTeacher();
      teacher.setId(pJsonObject.getString("adviser"));
      pMutableStudent.setAdviser(teacher);
    }
  }

}
