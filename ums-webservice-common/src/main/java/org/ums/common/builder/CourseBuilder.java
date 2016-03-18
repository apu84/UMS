package org.ums.common.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.persistent.model.PersistentDepartment;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.CourseGroup;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.enums.CourseCategory;
import org.ums.enums.CourseType;
import org.ums.manager.CourseGroupManager;
import org.ums.manager.DepartmentManager;
import org.ums.manager.SyllabusManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.Types;

@Component
public class CourseBuilder implements Builder<Course, MutableCourse> {
  @Autowired
  DepartmentManager mDepartmentManager;

  @Autowired
  CourseGroupManager mCourseGroupManager;

  @Autowired
  SyllabusManager mSyllabusManager;

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
    pBuilder.add("pairCourseId",pReadOnly.getPairCourseId()==null?"":pReadOnly.getPairCourseId());

    if (!StringUtils.isEmpty(pReadOnly.getOfferedDepartmentId())) {
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
        pBuilder.add("groupName", courseGroup.getName());
      }
    }

    pBuilder.add("viewOrder", pReadOnly.getViewOrder());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("course")
        .path(String.valueOf(pReadOnly.getId())).build().toString());

    pBuilder.add("totalApplied", pReadOnly.getTotalApplied());

  }

  @Override
  public void build(final MutableCourse pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception {

    //pMutable.setId(pJsonObject.getString("courseId"));
    String couseIdMiddle=(pJsonObject.getString("syllabusId").substring(4,8)).equalsIgnoreCase("01")?"S":"F"+pJsonObject.getString("syllabusId").substring(4,8);
    pMutable.setId(pJsonObject.getString("courseNumber").trim()+"_"+couseIdMiddle+"_"+pJsonObject.getString("programId"));
    pMutable.setNo(pJsonObject.getString("courseNumber"));
    pMutable.setTitle(pJsonObject.getString("courseTitle"));
    pMutable.setCrHr(Float.parseFloat(pJsonObject.getString("creditHour")));
    pMutable.setCourseType(CourseType.values()[Integer.parseInt(pJsonObject.getString("courseTypeId"))]);
    pMutable.setCourseCategory(CourseCategory.values()[Integer.parseInt(pJsonObject.getString("courseCategoryId"))]);
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
    Integer groupId = StringUtils.isEmpty(pJsonObject.getString("optionalGroupId")) ? Types.NULL: Integer.parseInt(pJsonObject.getString("optionalGroupId"));
    pMutable.setCourseGroupId(groupId);




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
