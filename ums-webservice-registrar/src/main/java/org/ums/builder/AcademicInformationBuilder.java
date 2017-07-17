package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.enums.common.AcademicDegreeType;
import org.ums.usermanagement.user.UserManager;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AcademicInformationBuilder implements Builder<AcademicInformation, MutableAcademicInformation> {
  @Autowired
  UserManager userManager;

  AcademicDegreeType mAcademicDegreeType;

  @Override
  public void build(JsonObjectBuilder pBuilder, AcademicInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

    JsonBuilderFactory factory = Json.createBuilderFactory(null);
    JsonObject value =
        factory.createObjectBuilder().add("id", pReadOnly.getDegreeId())
            .add("name", mAcademicDegreeType.get(pReadOnly.getDegreeId()).getLabel()).build();
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("degree", value);
    pBuilder.add("institution", pReadOnly.getInstitute());
    pBuilder.add("passingYear", pReadOnly.getPassingYear());
  }

  @Override
  public void build(MutableAcademicInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    if(pJsonObject.containsKey("dbAction")) {
      if(pJsonObject.getString("dbAction").equals("Update")) {
        pMutable.setId(pJsonObject.getInt("id"));
        pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
      }
      else if(pJsonObject.getString("dbAction").equals("Create")) {
        pMutable.setEmployeeId(userManager.get(SecurityUtils.getSubject().getPrincipal().toString()).getEmployeeId());
      }
    }
    else {
      pMutable.setId(pJsonObject.getInt("id"));
      pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    }
    pMutable.setDegree(mAcademicDegreeType.get(pJsonObject.getJsonObject("degree").getInt("id")));
    pMutable.setInstitute(pJsonObject.getString("institution"));
    pMutable.setPassingYear(pJsonObject.getInt("passingYear"));

  }
}
