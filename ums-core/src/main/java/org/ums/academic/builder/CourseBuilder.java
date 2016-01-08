package org.ums.academic.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.ums.academic.model.PersistentDepartment;
import org.ums.academic.model.PersistentProgram;
import org.ums.cache.LocalCache;
import org.ums.domain.model.*;
import org.ums.manager.ContentManager;
import org.ums.manager.CourseGroupManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Configurable
public class CourseBuilder implements Builder<Course, MutableCourse> {

  @Autowired
  @Qualifier("departmentManager")
  ContentManager<Department, MutableDepartment, Integer> mDepartmentManager;

  @Autowired
  @Qualifier("courseGroupManager")
  CourseGroupManager mCourseGroupManager;

  @Autowired
  @Qualifier("syllabusManager")
  ContentManager<Syllabus, MutableSyllabus, String> mSyllabusManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Course pReadOnly, final UriInfo pUriInfo,
                    final LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("no", pReadOnly.getNo());
    pBuilder.add("title", pReadOnly.getTitle());
    pBuilder.add("crhr", pReadOnly.getCrHr());
    pBuilder.add("type", pReadOnly.getCourseType().toString());
    pBuilder.add("type_value", pReadOnly.getCourseType().getValue());
    pBuilder.add("category", pReadOnly.getCourseCategory().toString());
    pBuilder.add("category_value", pReadOnly.getCourseCategory().getValue());
    pBuilder.add("year", pReadOnly.getYear());
    pBuilder.add("semester", pReadOnly.getSemester());

    if (pReadOnly.getOfferedDepartmentId() > 0) {
      Department offeredBy = (Department) pLocalCache.cache(() -> pReadOnly.getOfferedBy(),
          pReadOnly.getOfferedDepartmentId(), Department.class);
      if (offeredBy != null) {
        pBuilder.add("offeredBy", pUriInfo.getBaseUriBuilder().path("academic").path("department")
            .path(String.valueOf(offeredBy.getId())).build().toString());
      }
    }

    Syllabus syllabus = (Syllabus) pLocalCache.cache(() -> pReadOnly.getSyllabus(),
        pReadOnly.getSyllabusId(), Syllabus.class);
    pBuilder.add("syllabus", pUriInfo.getBaseUriBuilder().path("academic").path("syllabus")
        .path(String.valueOf(syllabus.getId())).build().toString());

    if (pReadOnly.getCourseGroupId() > 0) {
      CourseGroup courseGroup = (CourseGroup) pLocalCache.cache(() -> pReadOnly.getCourseGroup(syllabus.getId()),
          (syllabus.getId() + pReadOnly.getCourseGroupId()), CourseGroup.class);
      if (courseGroup != null) {
        pBuilder.add("group", pUriInfo.getBaseUriBuilder().path("academic")
            .path("courseGroup").path(String.valueOf(courseGroup.getId())).build().toString());
      }
    }

    pBuilder.add("viewOrder", pReadOnly.getViewOrder());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("course")
        .path(String.valueOf(pReadOnly.getId())).build().toString());
  }

  @Override
  public void build(final MutableCourse pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception {

    //pMutable.setId(pJsonObject.getString("courseId"));
    String couseIdMiddle=(pJsonObject.getString("syllabusId").substring(4,8)).equalsIgnoreCase("01")?"S":"F"+pJsonObject.getString("syllabusId").substring(4,8);
    pMutable.setId(pJsonObject.getString("courseNumber").trim()+"_"+couseIdMiddle+"_"+pJsonObject.getString("programId"));
    pMutable.setNo(pJsonObject.getString("courseNumber"));
    pMutable.setTitle(pJsonObject.getString("courseTitle"));
    pMutable.setCrHr(Float.parseFloat(pJsonObject.getString("creditHour")));
    pMutable.setCourseType(Course.CourseType.values()[Integer.parseInt(pJsonObject.getString("courseTypeId"))]);
    pMutable.setCourseCategory(Course.CourseCategory.values()[Integer.parseInt(pJsonObject.getString("courseCategoryId"))]);
    pMutable.setYear(Integer.parseInt(pJsonObject.getString("academicYearId")));
    pMutable.setSemester(Integer.parseInt(pJsonObject.getString("academicSemesterId")));

    PersistentDepartment persistentDepartment=new PersistentDepartment();
    persistentDepartment.setId(pJsonObject.getString("offerByDeptId"));
    pMutable.setOfferedBy(persistentDepartment);

    persistentDepartment=new PersistentDepartment();
    persistentDepartment.setId(pJsonObject.getString("offerToDeptId"));
    pMutable.setOfferedTo(persistentDepartment);

    pMutable.setSyllabusId(pJsonObject.getString("syllabusId"));
    pMutable.setViewOrder(Integer.parseInt(pJsonObject.getString("viewOrder")));

    pMutable.setCourseGroupId(Integer.parseInt(pJsonObject.getString("optionalGroupId")));

    //Unnecessary. No use of it in any Use Case. If any Use case need this then we will open it again
    /*
    if (pJsonObject.containsKey("offerdBy")) {
      pMutable.setOfferedBy(mDepartmentManager.get(pJsonObject.getInt("offeredBy")));
    }
    if (pJsonObject.containsKey("group")) {
      pMutable.setCourseGroup(mCourseGroupManager.get(pJsonObject.getInt("group")));
    }
        pMutable.setSyllabus(mSyllabusManager.get(pJsonObject.getString("syllabus")));
    */

  }
}
