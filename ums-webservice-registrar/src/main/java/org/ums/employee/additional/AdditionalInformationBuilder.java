package org.ums.employee.additional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.registrar.AreaOfInterestInformationManager;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdditionalInformationBuilder implements Builder<AdditionalInformation, MutableAdditionalInformation> {
  @Autowired
  private UserManager userManager;

  @Autowired
  private AreaOfInterestInformationManager mAreaOfInterestInformationManager;

  @Autowired
  private AreaOfInterestInformationBuilder mAreaOfInterestInformationBuilder;

  @Override
  public void build(JsonObjectBuilder pBuilder, AdditionalInformation pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    pBuilder.add("employee_id", pReadOnly.getId() == null ? "" : pReadOnly.getId());
    pBuilder.add("roomNo", pReadOnly.getRoomNo() == null ? "" : pReadOnly.getRoomNo());
    pBuilder.add("extNo", pReadOnly.getExtNo() == null ? "" : pReadOnly.getExtNo());
    pBuilder.add("academicInitial", pReadOnly.getAcademicInitial() == null ? "" : pReadOnly.getAcademicInitial());
    List<AreaOfInterestInformation> areaOfInterestInformations = new ArrayList<>();
    JsonArrayBuilder children = Json.createArrayBuilder();
    areaOfInterestInformations = mAreaOfInterestInformationManager.getAreaOfInterestInformation(pReadOnly.getId());
    for(AreaOfInterestInformation areaOfInterestInformation : areaOfInterestInformations) {
      JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
      mAreaOfInterestInformationBuilder.build(jsonObjectBuilder, areaOfInterestInformation, pUriInfo, pLocalCache);
      children.add(jsonObjectBuilder);
    }
    pBuilder.add("areaOfInterestInformation", children);
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
