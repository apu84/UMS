package org.ums.academic.resource.markssubmission;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.formatter.DateFormat;

@Component
public class MarksSubmissionStatusBuilder implements Builder<MarksSubmissionStatus, MutableMarksSubmissionStatus> {
  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, MarksSubmissionStatus pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    Course course = (Course) pLocalCache.cache(() -> pReadOnly.getCourse(), pReadOnly.getCourseId(), Course.class);
    // Syllabus syllabus = (Syllabus) pLocalCache.cache(() -> course.getSyllabus(),
    // course.getSyllabusId(),
    // Syllabus.class);
    Program program =
        (Program) pLocalCache.cache(() -> course.getOfferedToProgram(), course.getOfferedToProgramId(), Program.class);
    Department department =
        (Department) pLocalCache.cache(() -> program.getDepartment(), program.getDepartmentId(), Department.class);
    pBuilder.add("departmentId", department.getId());
    pBuilder.add("departmentName", department.getShortName());
    pBuilder.add("programName", program.getShortName());
    pBuilder.add("programId", program.getId());
    pBuilder.add("courseId", course.getId());
    pBuilder.add("courseNo", course.getNo());
    pBuilder.add("courseTitle", course.getTitle());
    pBuilder.add("year", pReadOnly.getYear());
    pBuilder.add("academicSemester", pReadOnly.getAcademicSemester());
    if(pReadOnly.getLastSubmissionDatePrep() != null) {
      pBuilder.add("lastSubmissionDate", mDateFormat.format(pReadOnly.getLastSubmissionDatePrep()));
    }
    pBuilder.add("statusId", pReadOnly.getStatus().getId());
    pBuilder.add("status", pReadOnly.getStatus().getLabel());
  }

  @Override
  public void build(MutableMarksSubmissionStatus pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

  }
}
