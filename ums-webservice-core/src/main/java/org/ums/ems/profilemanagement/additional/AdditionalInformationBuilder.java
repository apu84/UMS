package org.ums.ems.profilemanagement.additional;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AdditionalInformationBuilder implements Builder<AdditionalInformation, MutableAdditionalInformation> {

  @Override
  public void build(JsonObjectBuilder pBuilder, AdditionalInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getId() == null ? "" : pReadOnly.getId());
    pBuilder.add("roomNo", pReadOnly.getRoomNo() == null ? "" : pReadOnly.getRoomNo());
    pBuilder.add("extNo", pReadOnly.getExtNo() == null ? "" : pReadOnly.getExtNo());
    pBuilder.add("academicInitial", pReadOnly.getAcademicInitial() == null ? "" : pReadOnly.getAcademicInitial());
  }

  @Override
  public void build(MutableAdditionalInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setId(pJsonObject.getString("employeeId"));
    pMutable.setRoomNo(pJsonObject.containsKey("roomNo") ? pJsonObject.getString("roomNo").equals("") ? null
        : pJsonObject.getString("roomNo") : "");
    pMutable.setExtNo(pJsonObject.containsKey("extNo") ? pJsonObject.getString("extNo").equals("") ? null : pJsonObject
        .getString("extNo") : "");
    pMutable.setAcademicInitial(pJsonObject.containsKey("academicInitial") ? pJsonObject.getString("academicInitial")
        .equals("") ? null : pJsonObject.getString("academicInitial") : "");
  }
}
