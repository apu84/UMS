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

  AcademicDegreeType mAcademicDegreeType;

  @Override
  public void build(JsonObjectBuilder pBuilder, AcademicInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("degreeId", pReadOnly.getDegreeId());
    pBuilder.add("institution", pReadOnly.getInstitute());
    pBuilder.add("passingYear", pReadOnly.getPassingYear());
  }

  @Override
  public void build(MutableAcademicInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    if(pJsonObject.containsKey("dbAction")) {
      if(pJsonObject.getString("dbAction").equals("Update")) {
        pMutable.setId(pJsonObject.getInt("id"));
      }
      else if(pJsonObject.getString("dbAction").equals("Create")) {
      }
      else if(pJsonObject.getString("dbAction").equals("Delete")) {
        pMutable.setId(pJsonObject.getInt("id"));
      }
      pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
      pMutable.setDegree(mAcademicDegreeType.get(pJsonObject.getJsonObject("degree").getInt("id")));
      pMutable.setInstitute(pJsonObject.getString("institution"));
      pMutable.setPassingYear(pJsonObject.getInt("passingYear"));

    }
  }
}
