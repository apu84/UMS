package org.ums.builder;

import java.text.SimpleDateFormat;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Fine;
import org.ums.domain.model.mutable.library.MutableCirculation;
import org.ums.domain.model.mutable.library.MutableFine;
import org.ums.enums.library.FineStatus;
import org.ums.formatter.DateFormat;
import org.ums.manager.library.CirculationManager;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

@Component
public class FineBuilder implements Builder<Fine, MutableFine> {
  @Autowired
  private DateFormat mDateFormat;

  @Autowired
  private CirculationManager mCirculationManager;

  @Autowired
  UserManager mUserManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, Fine pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("patronId", pReadOnly.getPatronId());
    pBuilder.add("circulationId", pReadOnly.getCirculationId().toString());
    pBuilder.add("amount", pReadOnly.getAmount());
    pBuilder.add("fineCategory", pReadOnly.getFineCategory());
    pBuilder.add("description", pReadOnly.getDescription() == null ? "" : pReadOnly.getDescription());
    pBuilder.add("fineAppliedDate", mDateFormat.format(pReadOnly.getFineAppliedDate()));
    pBuilder.add("fineAppliedBy", pReadOnly.getFineAppliedBy());
    pBuilder.add("fineForgivenBy", pReadOnly.getFineForgivenBy() == null ? "" : pReadOnly.getFineForgivenBy());
    pBuilder.add("finePaymentDate",
        pReadOnly.getFinePaymentDate() == null ? "" : mDateFormat.format(pReadOnly.getFinePaymentDate()));

    MutableCirculation mutableCirculation = (MutableCirculation) mCirculationManager.get(pReadOnly.getCirculationId());
    java.text.DateFormat outputReturnDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aaa");
    String outputReturnDateString = outputReturnDateFormat.format(mutableCirculation.getDueDate());
    pBuilder.add("dueDate", outputReturnDateString);
    pBuilder.add("mfn", mutableCirculation.getMfn().toString());
    pBuilder.add("status", mutableCirculation.getFineStatus());
    pBuilder.add("statusName", FineStatus.get(mutableCirculation.getFineStatus()).getLabel());
  }

  @Override
  public void build(MutableFine pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setId(pJsonObject.containsKey("id") ? Long.parseLong(pJsonObject.getString("id")) : null);
    pMutable.setPatronId(pJsonObject.getString("patronId"));
    pMutable.setCirculationId(Long.parseLong(pJsonObject.getString("circulationId")));
    pMutable.setAmount(pJsonObject.getInt("amount"));
    pMutable.setFineCategory(pJsonObject.getInt("fineCategory"));
    pMutable.setDescription(pJsonObject.getString("description"));
    pMutable.setFineAppliedBy(pJsonObject.getString("fineAppliedBy"));
    pMutable.setFineAppliedDate(mDateFormat.parse(pJsonObject.getString("fineAppliedDate")));
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    pMutable.setFineForgivenBy(user.getEmployeeId());
    pMutable.setFinePaymentDate(null);
  }
}
