package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AdditionalInformation;
import org.ums.domain.model.mutable.registrar.MutableAdditionalInformation;
import org.ums.resource.MutableAdditionalInformationResource;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AdditionalInformationBuilder implements Builder<AdditionalInformation, MutableAdditionalInformation> {
  @Autowired
  private UserManager userManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, AdditionalInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("employee_id", pReadOnly.getId() == null ? "" : pReadOnly.getId());
    pBuilder.add("roomNo", pReadOnly.getRoomNo() == null ? "" : pReadOnly.getRoomNo());
    pBuilder.add("extNo", pReadOnly.getExtNo());
    pBuilder.add("academicInitial", pReadOnly.getAcademicInitial() == null ? "" : pReadOnly.getAcademicInitial());
  }

  @Override
  public void build(MutableAdditionalInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("employeeId"));
    pMutable.setRoomNo(pJsonObject.containsKey("roomNo") ? pJsonObject.getString("roomNo") : "");
    pMutable.setExtNo(pJsonObject.getInt("extNo"));
    pMutable.setAcademicInitial(pJsonObject.containsKey("academicInitial") ? pJsonObject.getString("academicInitial")
        : "");
  }
}
