package org.ums.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.enums.common.LeaveApprovalStatus;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * Created by Monjur-E-Morshed on 18-May-17.
 */
@Component
public class LmsAppStatusBuilder implements Builder<LmsAppStatus, MutableLmsAppStatus> {

  private static final Logger mLogger = LoggerFactory.getLogger(LmsAppStatusBuilder.class);

  @Override
  public void build(JsonObjectBuilder pBuilder, LmsAppStatus pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("appId", pReadOnly.getLmsApplication().getId());
    Format formatter = new SimpleDateFormat("dd/MM/YYYY");
    pBuilder.add("actionTakenOn", formatter.format(pReadOnly.getActionTakenOn()));
    pBuilder.add("actionTakenBy", pReadOnly.getActionTakenBy().getId());
    pBuilder.add("actionTakenByName", pReadOnly.getActionTakenBy().getEmployeeName());
    pBuilder.add("comments", pReadOnly.getComments());
    pBuilder.add("actionStatus", pReadOnly.getActionStatus().getId());
    pBuilder.add("actionStatusLabel", pReadOnly.getActionStatus().getLabel());
  }

  @Override
  public void build(MutableLmsAppStatus pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(pJsonObject.getInt("id"));
    if(pJsonObject.containsKey("appId"))
      pMutable.setLmsApplicationId(Long.valueOf(pJsonObject.getInt("appId")));
    if(pJsonObject.containsKey("actionTakenOn")) {
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      try {
        pMutable.setActionTakenOn(formatter.parse(pJsonObject.getString("actionTakenOn")));
      } catch(Exception e) {
        mLogger.error(e.getMessage());
      }
    }
    if(pJsonObject.containsKey("actionTakenBy"))
      pMutable.setActionTakenById(pJsonObject.getString("actionTakenBy"));
    if(pJsonObject.containsKey("comments"))
      pMutable.setComments(pJsonObject.getString("comments"));
    if(pJsonObject.containsKey("actionStatus"))
      pMutable.setActionStatus(LeaveApprovalStatus.get(pJsonObject.getInt("actionStatus")));
  }
}
