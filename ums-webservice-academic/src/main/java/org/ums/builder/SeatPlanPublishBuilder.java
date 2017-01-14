package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
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
public class SeatPlanPublishBuilder implements Builder<SeatPlanPublish, MutableSeatPlanPublish> {
  @Override
  public void build(JsonObjectBuilder pBuilder, SeatPlanPublish pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    if(pReadOnly.getId() != null)
      pBuilder.add("id", pReadOnly.getId());
    if(pReadOnly.getSemesterId() != null)
      pBuilder.add("semesterId", pReadOnly.getSemesterId());
    if(pReadOnly.getExamType() != null)
      pBuilder.add("examType", pReadOnly.getExamType().getId());
    if(pReadOnly.getExamDate() != null)
      pBuilder.add("examDate", pReadOnly.getExamDate());
    if(pReadOnly.getPublishStatus() != null) {
      if(pReadOnly.getPublishStatus() == 0) {
        pBuilder.add("published", "false");

      }
      else {
        pBuilder.add("published", "true");

      }

    }
  }

  @Override
  public void build(MutableSeatPlanPublish pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    /**
     * In database, id is auto generated type. So, firstly, when there is no data of the element or
     * item, then, there is no need to have any id, as that will be auto generated. But, for update
     * purpose, we need an id, so, at the client side, we will set id==0, when, there is no id
     * information, i.e. when we are inserting neew data. While updating, as we need the id, so, we
     * will set the id value from the client side, and will use here.
     * 
     * so, id==0, when, data is newly inserted. and id==(any numeric value), when we are updating.
     */
    if(pJsonObject.getInt("id") != 0) {
      pMutable.setId(pJsonObject.getInt("id"));
    }
    pMutable.setSemesterId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setExamType(ExamType.get(pJsonObject.getInt("examType")));
    pMutable.setExamDate(pJsonObject.getString("examDate"));
    pMutable.setPublishStatus(pJsonObject.getInt("published"));
  }
}
