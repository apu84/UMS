package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.SeatPlanPublish;
import org.ums.domain.model.mutable.MutableSeatPlanPublish;
import org.ums.enums.ExamType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by My Pc on 8/4/2016.
 */

@Component
public class SeatPlanPublishBuilder implements Builder<SeatPlanPublish,MutableSeatPlanPublish> {
  @Override
  public void build(JsonObjectBuilder pBuilder, SeatPlanPublish pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) throws Exception {
    if(pReadOnly.getId()!=null)
        pBuilder.add("id",pReadOnly.getId());
    if(pReadOnly.getSemesterId()!=null)
        pBuilder.add("semesterId",pReadOnly.getSemesterId());
    if(pReadOnly.getExamType()!=null)
        pBuilder.add("examType",pReadOnly.getExamType().getValue());
    if(pReadOnly.getExamDate()!=null)
        pBuilder.add("examDate",pReadOnly.getExamDate());
    if(pReadOnly.getPublishStatus()!=null)
        pBuilder.add("published",pReadOnly.getPublishStatus());
  }

  @Override
  public void build(MutableSeatPlanPublish pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {
    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setExamType(ExamType.get(pJsonObject.getInt("examType")));
    pMutable.setExamDate(pJsonObject.getString("examDate"));
    pMutable.setPublishStatus(pJsonObject.getInt("status"));
  }
}
