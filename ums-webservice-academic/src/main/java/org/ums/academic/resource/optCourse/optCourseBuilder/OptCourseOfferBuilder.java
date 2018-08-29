package org.ums.academic.resource.optCourse.optCourseBuilder;

import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.optCourse.OptCourseOffer;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseOffer;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class OptCourseOfferBuilder implements Builder<OptCourseOffer, MutableOptCourseOffer> {
    @Override
    public void build(JsonObjectBuilder pBuilder, OptCourseOffer pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
        if(pReadOnly.getId() != null) {
            pBuilder.add("id", pReadOnly.getId().toString());
        }
        if(pReadOnly.getSemesterId() != null) {
            pBuilder.add("semesterId", pReadOnly.getSemesterId());
        }
        if(pReadOnly.getDepartmentId() != null) {
            pBuilder.add("deptId", pReadOnly.getDepartmentId());
        }
        if(pReadOnly.getDepartmentId() != null) {
            Department department = pReadOnly.getDepartment();
            pBuilder.add("departmentId", department.getId());
            pBuilder.add("department",
                    pUriInfo.getBaseUriBuilder().path("academic").path("department").path(String.valueOf(department.getId()))
                            .build().toString());
            pBuilder.add("deptName", department.getLongName());
            pBuilder.add("deptShortName", department.getShortName());
        }
        if(pReadOnly.getCourseId() !=null){
            pBuilder.add("courseId",pReadOnly.getCourseId());
        }
        if(pReadOnly.getCourseId() !=null){
            Course course=pReadOnly.getCourses();
            pBuilder.add("courseNo",course.getNo());
            pBuilder.add("courseTitle",course.getTitle());
        }
        if(pReadOnly.getProgramId() !=null){
            pBuilder.add("programId",pReadOnly.getProgramId());
        }
        if(pReadOnly.getProgramName() !=null){
            pBuilder.add("programName",pReadOnly.getProgramName());
        }
        if(pReadOnly.getYear() !=null){
            pBuilder.add("year",pReadOnly.getYear());
        }
        if(pReadOnly.getSemester() !=null){
            pBuilder.add("semester",pReadOnly.getSemester());
        }
        if(pReadOnly.getCallForApplication() !=null){
            pBuilder.add("callForApplication",pReadOnly.getCallForApplication());
        }
        if(pReadOnly.getApproved() !=null){
            pBuilder.add("approved",pReadOnly.getApproved());
        }
        if(pReadOnly.getTotal() !=null){
            pBuilder.add("totalApplied",pReadOnly.getTotal());
        }

    }

    @Override
    public void build(MutableOptCourseOffer pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    }
}
