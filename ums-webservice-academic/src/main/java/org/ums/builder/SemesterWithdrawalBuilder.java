package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.SemesterWithdrawal;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.mutable.MutableSemesterWithdrawal;
import org.ums.manager.StudentManager;
import org.ums.persistent.model.PersistentProgram;
import org.ums.persistent.model.PersistentSemester;
import org.ums.persistent.model.PersistentStudent;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class SemesterWithdrawalBuilder implements Builder<SemesterWithdrawal, MutableSemesterWithdrawal> {

  @Autowired
  StudentManager mStudentManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, SemesterWithdrawal pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("semesterId", pReadOnly.getSemester().getId());
    pBuilder.add("programId", pReadOnly.getProgram().getId());
    pBuilder.add("studentId", pReadOnly.getStudent().getId());
    Student student = mStudentManager.get(pReadOnly.getStudent().getId());
    pBuilder.add("studentName", student.getFullName());
    pBuilder.add("year", pReadOnly.getStudent().getCurrentYear());
    pBuilder.add("semester", pReadOnly.getStudent().getCurrentAcademicSemester());
    pBuilder.add("cause", pReadOnly.getCause());
    pBuilder.add("appDate", pReadOnly.getAppDate());
    pBuilder.add("status", pReadOnly.getStatus());
    pBuilder.add("comments", pReadOnly.getComment());
    pBuilder.add("self",
        pUriInfo.getBaseUriBuilder().path("academic").path("semesterWithdraw").path(pReadOnly.getId().toString())
            .build().toString());

  }

  @Override
  public void build(MutableSemesterWithdrawal pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id")) {
      Long id = Long.parseLong(pJsonObject.getString("id"));
      if(id != 0) {
        pMutable.setId(id);
      }
    }
    int semId = pJsonObject.getInt("semesterId");
    PersistentSemester semester = new PersistentSemester();
    semester.setId(semId);
    pMutable.setSemester(semester);
    PersistentProgram program = new PersistentProgram();
    program.setId(pJsonObject.getInt("programId"));
    pMutable.setProgram(program);
    PersistentStudent student = new PersistentStudent();
    // int studentId = (pJsonObject.getInt("studentId"));
    student.setId(pJsonObject.getString("studentId"));
    student.setCurrentYear(pJsonObject.getInt("year"));
    student.setCurrentAcademicSemester(pJsonObject.getInt("semester"));
    pMutable.setStudent(student);
    pMutable.setCause(pJsonObject.getString("cause"));
    pMutable.setStatus(pJsonObject.getInt("status"));
    pMutable.setComments(pJsonObject.getString("comments"));
  }
}
