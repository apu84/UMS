package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.mutable.MutableApplicationCCI;
import org.ums.enums.ApplicationType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by My Pc on 7/14/2016.
 */
@Component
public class ApplicationCCIBuilder implements Builder<ApplicationCCI,MutableApplicationCCI>{
  @Override
  public void build(JsonObjectBuilder pBuilder, ApplicationCCI pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    if(pReadOnly.getId()!=null)
      pBuilder.add("id",pReadOnly.getId());
    if(pReadOnly.getSemesterId()!=null)
      pBuilder.add("semesterId",pReadOnly.getSemesterId());
    if(pReadOnly.getStudentId()!=null)
      pBuilder.add("studentId",pReadOnly.getStudentId());
    if(pReadOnly.getCourseId()!=null)
      pBuilder.add("courseId",pReadOnly.getCourseId());
    if(pReadOnly.getApplicationType()!=null)
      pBuilder.add("applicationType",pReadOnly.getApplicationType().getValue());
    if(pReadOnly.getApplicationDate()!=null)
      pBuilder.add("applicationDate",pReadOnly.getApplicationDate());
    if(pReadOnly.getCourseNo()!=null)
      pBuilder.add("courseNo",pReadOnly.getCourseNo());
    if(pReadOnly.getCourseTitle()!=null)
      pBuilder.add("courseTitle",pReadOnly.getCourseTitle());
    if(pReadOnly.getExamDate()!=null){
      pBuilder.add("examDate",pReadOnly.getExamDate());
      pBuilder.add("examDateOriginal",pReadOnly.getExamDate());
    }
    if(pReadOnly.totalStudent()!=null) {
      pBuilder.add("totalStudent", pReadOnly.totalStudent());
      pBuilder.add("studentNumber", pReadOnly.totalStudent());

    }
    if(pReadOnly.getCourseYear()!=null){
      pBuilder.add("year",pReadOnly.getCourseYear());
    }

    if(pReadOnly.getCourseSemester()!=null){
      pBuilder.add("semester",pReadOnly.getCourseSemester());
    }
  }

  @Override
  public void build(MutableApplicationCCI pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setStudentId(pJsonObject.getString("studentId"));
    pMutable.setCourseId(pJsonObject.getString("courseId"));
    pMutable.setApplicationType(ApplicationType.get(pJsonObject.getInt("applicationType")));
    //pMutable.setApplicationDate(pJsonObject.getString("applicationDate"));
  }
}
