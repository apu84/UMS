package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.mutable.MutableSubGroup;
import org.ums.persistent.model.PersistentSeatPlanGroup;
import org.ums.persistent.model.PersistentSemester;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by My Pc on 5/5/2016.
 */

@Component
public class SubGroupBuilder implements Builder<SubGroup,MutableSubGroup> {
  @Override
  public void build(JsonObjectBuilder pBuilder, SubGroup pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    pBuilder.add("id",pReadOnly.getId());
    pBuilder.add("semesterId",pReadOnly.getSemester().getId());
    pBuilder.add("groupNo",pReadOnly.getGroup().getGroupNo());
    pBuilder.add("subGroupNumber",pReadOnly.subGroupNo());
    pBuilder.add("groupId",pReadOnly.getGroup().getId());
    pBuilder.add("position",pReadOnly.getPosition());
    pBuilder.add("studentNumber",pReadOnly.getStudentNumber());
    pBuilder.add("examType",pReadOnly.getExamType());
    if(pReadOnly.getProgramShortName()!=null){
      pBuilder.add("programName",pReadOnly.getProgramShortName().replaceAll("BSc in ",""));
    }
    if(pReadOnly.getStudentYear()!=null){
      pBuilder.add("year",pReadOnly.getStudentYear());
    }
    if(pReadOnly.getStudentSemester()!=null){
      pBuilder.add("semester",pReadOnly.getStudentSemester());
    }
  }

  @Override
  public void build(MutableSubGroup pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {


    PersistentSemester semester = new PersistentSemester();
    semester.setId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setSemester(semester);
    pMutable.setSubGroupNo(pJsonObject.getInt("subGroupNo"));
    PersistentSeatPlanGroup group = new PersistentSeatPlanGroup();
    group.setGroupNo(Integer.parseInt(pJsonObject.getString("groupNo")));
    group.setId(pJsonObject.getInt("groupId"));
    pMutable.setGroup(group);
    pMutable.setPosition(pJsonObject.getInt("position"));
    pMutable.setStudentNumber(pJsonObject.getInt("studentNumber"));
    pMutable.setExamType(Integer.parseInt(pJsonObject.getString("examType")));
  }
}
