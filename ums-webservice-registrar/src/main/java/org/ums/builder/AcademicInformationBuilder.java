package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.registrar.MutableAcademicInformation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AcademicInformationBuilder implements Builder<AcademicInformation, MutableAcademicInformation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AcademicInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("academicDegreeName", pReadOnly.getDegreeName());
    pBuilder.add("academicInstitution", pReadOnly.getDegreeInstitute());
    pBuilder.add("academicPassingYear", pReadOnly.getDegreePassingYear());
  }

  @Override
  public void build(MutableAcademicInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setDegreeName(pJsonObject.getJsonObject("academicDegreeName").getString("name"));
    pMutable.setDegreeInstitute(pJsonObject.getString("academicInstitution"));
    pMutable.setDegreePassingYear(pJsonObject.getString("academicPassingYear"));
  }
}
