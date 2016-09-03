package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.SubGroupCCI;
import org.ums.domain.model.mutable.MutableSubGroupCCI;
import org.ums.persistent.model.PersistentSemester;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by My Pc on 7/23/2016.
 */
@Component
public class SubGroupCCIBuilder implements Builder<SubGroupCCI,MutableSubGroupCCI> {
  @Override
  public void build(JsonObjectBuilder pBuilder, SubGroupCCI pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    if(pReadOnly.getId()!=null)
      pBuilder.add("id",pReadOnly.getId());
    if(pReadOnly.getSemesterId()!=null)
      pBuilder.add("semesterId",pReadOnly.getSemesterId());
    if(pReadOnly.getSubGroupNo()!=null)
    {
      pBuilder.add("subGroupNo",pReadOnly.getSubGroupNo());
      pBuilder.add("subGroupNumber",pReadOnly.getSubGroupNo());
    }
    if(pReadOnly.getTotalStudent()!=null)
    {
      pBuilder.add("totalStudent",pReadOnly.getTotalStudent());
      pBuilder.add("studentNumber",pReadOnly.getTotalStudent());
    }
    if(pReadOnly.getCourseId()!=null)
      pBuilder.add("courseId",pReadOnly.getCourseId());
    if(pReadOnly.getExamDate()!=null)
      pBuilder.add("examDate",pReadOnly.getExamDate());
    if(pReadOnly.getCourseNo()!=null){
      pBuilder.add("courseNo",pReadOnly.getCourseNo());
    }
    if(pReadOnly.getCourseYear()!=null)
      pBuilder.add("year",pReadOnly.getCourseYear());
    if(pReadOnly.getCourseSemester()!=null)
      pBuilder.add("semester",pReadOnly.getCourseSemester());
  }

  @Override
  public void build(MutableSubGroupCCI pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    PersistentSemester semester = new PersistentSemester();
    semester.setId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setSemester(semester);
    pMutable.setSemesterId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setSubGroupNo(pJsonObject.getInt("subGroupNo"));
    pMutable.setTotalStudent(pJsonObject.getInt("totalStudent"));
    pMutable.setCourseId(pJsonObject.getString("courseId"));
    pMutable.setExamDate(pJsonObject.getString("examDate"));
  }
}
