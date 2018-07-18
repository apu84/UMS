package org.ums.resource.leavemanagement;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.common.LmsAppStatus;
import org.ums.domain.model.mutable.common.MutableLmsAppStatus;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 18-May-17.
 */
@Component
public class LmsAppStatusBuilder implements Builder<LmsAppStatus, MutableLmsAppStatus> {

  @Autowired
  private UserManager mUserManager;
  @Autowired
  private LmsApplicationBuilder mLmsApplicationBuilder;

  private static final Logger mLogger = LoggerFactory.getLogger(LmsAppStatusBuilder.class);

  @Override
  public void build(JsonObjectBuilder pBuilder, LmsAppStatus pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("appId", pReadOnly.getLmsApplication().getId().toString());
    JsonObjectBuilder leaveApplication = Json.createObjectBuilder();
    mLmsApplicationBuilder.build(leaveApplication, pReadOnly.getLmsApplication(), pUriInfo, pLocalCache);
    pBuilder.add("leaveApplication", leaveApplication);
    pBuilder.add("applicantsId", pReadOnly.getLmsApplication().getEmployee().getId());
    pBuilder.add("deptName", pReadOnly.getLmsApplication().getEmployee().getDepartment().getShortName());
    pBuilder.add("applicantsName", pReadOnly.getLmsApplication().getEmployee().getPersonalInformation().getName());
    pBuilder.add("reason", pReadOnly.getLmsApplication().getReason());
    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
    pBuilder.add("appliedOn", formatter.format(pReadOnly.getLmsApplication().getAppliedOn()));
    formatter = new SimpleDateFormat("dd/MM/yyyy");
    pBuilder.add("fromDate", formatter.format(pReadOnly.getLmsApplication().getFromDate()));
    formatter = new SimpleDateFormat("dd/MM/yyyy");
    pBuilder.add("toDate", formatter.format(pReadOnly.getLmsApplication().getToDate()));
    long diff =
        pReadOnly.getLmsApplication().getToDate().getTime() - pReadOnly.getLmsApplication().getFromDate().getTime();
    int duration = (int) (diff / (24 * 60 * 60 * 1000) + 1);
    pBuilder.add("duration", duration);
    pBuilder.add("leaveTypeName", pReadOnly.getLmsApplication().getLmsType().getName());
    /*
     * pBuilder.add("applicationStatus",
     * pReadOnly.getLmsApplication().getApplicationStatus().getId());
     * pBuilder.add("applicationStatusLabel",
     * pReadOnly.getLmsApplication().getApplicationStatus().getLabel());
     */
    if(pReadOnly.getRowNumber() != 0)
      pBuilder.add("rowNumber", pReadOnly.getRowNumber());
    pBuilder.add("actionTakenOn", formatter.format(pReadOnly.getActionTakenOn()));
    pBuilder.add("actionTakenBy", pReadOnly.getActionTakenBy().getId());
    pBuilder.add("actionTakenByName", pReadOnly.getActionTakenBy().getPersonalInformation().getName());
    pBuilder.add("comments", pReadOnly.getComments());
    pBuilder.add("actionStatus", pReadOnly.getActionStatus().getId());
    pBuilder.add("actionStatusLabel", pReadOnly.getActionStatus().getLabel());
  }

  @Override
  public void build(MutableLmsAppStatus pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    if(pJsonObject.containsKey("id"))
      pMutable.setId(Long.valueOf(pJsonObject.getInt("id")));
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
    else {
      pMutable.setActionTakenOn(new Date());
    }
    if(pJsonObject.containsKey("actionTakenBy"))
      pMutable.setActionTakenById(pJsonObject.getString("actionTakenBy"));
    else {
      pMutable.setActionTakenById(mUserManager.get(SecurityUtils.getSubject().getPrincipal().toString())
          .getEmployeeId());
    }

    if(pJsonObject.containsKey("comments"))
      pMutable.setComments(pJsonObject.getString("comments"));
    if(pJsonObject.containsKey("actionStatus"))
      pMutable.setActionStatus(LeaveApplicationApprovalStatus.get(pJsonObject.getInt("actionStatus")));
  }
}
