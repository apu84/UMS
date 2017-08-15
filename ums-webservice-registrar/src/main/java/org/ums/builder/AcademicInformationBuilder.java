package org.ums.builder;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;
import org.ums.enums.common.AcademicDegreeType;
import org.ums.usermanagement.user.UserManager;

import javax.json.*;
import javax.ws.rs.core.UriInfo;

@Component
public class AcademicInformationBuilder implements Builder<AcademicInformation, MutableAcademicInformation> {

  @Override
  public void build(JsonObjectBuilder pBuilder, AcademicInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    JsonObjectBuilder degreeBuilder = Json.createObjectBuilder();
    degreeBuilder.add("id", pReadOnly.getDegreeId()).add("name",
        AcademicDegreeType.get(pReadOnly.getDegreeId()).getLabel());
    pBuilder.add("degree", degreeBuilder);
    pBuilder.add("institution", pReadOnly.getInstitute());
    pBuilder.add("passingYear", pReadOnly.getPassingYear());
    pBuilder.add("dbAction", "");
  }

  @Override
  public void build(MutableAcademicInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable
        .setId(pJsonObject.containsKey("dbAction") ? (pJsonObject.getString("dbAction").equals("Update") || pJsonObject
            .getString("dbAction").equals("Delete")) ? Long.parseLong(pJsonObject.getString("id")) : 0 : 0);
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setDegreeId(pJsonObject.getJsonObject("degree").getInt("id"));
    pMutable.setInstitute(pJsonObject.getString("institution"));
    pMutable.setPassingYear(pJsonObject.getInt("passingYear"));
  }
}
