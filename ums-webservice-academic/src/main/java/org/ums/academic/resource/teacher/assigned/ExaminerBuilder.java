package org.ums.academic.resource.teacher.assigned;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableExaminer;
import org.ums.manager.CourseManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.TeacherManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ExaminerBuilder implements Builder<Examiner, MutableExaminer> {
  @Autowired
  private CourseManager mCourseManager;
  @Autowired
  private TeacherManager mTeacherManager;
  @Autowired
  private SemesterManager mSemesterManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, Examiner pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    if (pReadOnly.getId() != null && pReadOnly.getId() != 0) {
      pBuilder.add("id", pReadOnly.getId().toString());
    }

    Course course = (Course) pLocalCache.cache(() -> pReadOnly.getCourse(),
        pReadOnly.getCourseId(), Course.class);

    pBuilder.add("courseId", course.getId());
    pBuilder.add("courseNo", course.getNo());
    pBuilder.add("courseTitle", course.getTitle());
    pBuilder.add("courseCrHr", course.getCrHr());
    pBuilder.add("year", course.getYear());
    pBuilder.add("semester", course.getSemester());
//    pBuilder.add("syllabusId", course.getSyllabusId());

    if (!StringUtils.isEmpty(pReadOnly.getPreparerId())) {
      Teacher teacher = (Teacher) pLocalCache.cache(() -> pReadOnly.getPreparer(),
          pReadOnly.getPreparerId(), Teacher.class);

      pBuilder.add("preparerId", teacher.getId());
      pBuilder.add("preparerName", teacher.getName());
    }

    if (!StringUtils.isEmpty(pReadOnly.getScrutinizerId())) {
      Teacher teacher = (Teacher) pLocalCache.cache(() -> pReadOnly.getScrutinizer(),
          pReadOnly.getScrutinizerId(), Teacher.class);

      pBuilder.add("scrutinizerId", teacher.getId());
      pBuilder.add("scrutinizerName", teacher.getName());
    }

    Department department = (Department) pLocalCache.cache(() -> course.getOfferedBy(),
        course.getOfferedDepartmentId(), Department.class);

    pBuilder.add("courseOfferedByDepartmentId", department.getId());
    pBuilder.add("courseOfferedByDepartmentName", department.getShortName());

    Program program = (Program) pLocalCache.cache(() -> course.getOfferedToProgram(),
        course.getOfferedDepartmentId(), Program.class);

    Department offeredToDepartment = (Department) pLocalCache.cache(() -> program.getDepartment(),
        program.getDepartmentId(), Department.class);

    pBuilder.add("courseOfferedToDepartmentId", offeredToDepartment.getId());
    pBuilder.add("courseOfferedToDepartmentName", offeredToDepartment.getShortName());
  }

  @Override
  public void build(MutableExaminer pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id")) {
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    }
    pMutable.setCourse(mCourseManager.get(pJsonObject.getString("courseId")));
    pMutable.setCourseId(pJsonObject.getString("courseId"));
    if(!pJsonObject.getString("preparerId").equalsIgnoreCase("-1")) {
      pMutable.setPreparer(mTeacherManager.get(pJsonObject.getString("preparerId")));
    }
    if(!pJsonObject.getString("scrutinizerId").equalsIgnoreCase("-1")) {
      pMutable.setScrutinizer(mTeacherManager.get(pJsonObject.getString("scrutinizerId")));
    }
    pMutable.setSemester(mSemesterManager.get(Integer.parseInt(pJsonObject.getString("semesterId"))));
    pMutable.setSemesterId(Integer.parseInt(pJsonObject.getString("semesterId")));
  }
}
