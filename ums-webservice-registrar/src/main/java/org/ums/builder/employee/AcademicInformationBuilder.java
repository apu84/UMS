package org.ums.builder.employee;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.registrar.Employee.AcademicInformation;
import org.ums.domain.model.mutable.registrar.Employee.MutableAcademicInformation;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AcademicInformationBuilder implements Builder<AcademicInformation, MutableAcademicInformation> {
  @Override
  public void build(JsonObjectBuilder pBuilder, AcademicInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    pBuilder.add("academicDegreeName", pReadOnly.getDegreeName());
    pBuilder.add("academicInstitution", pReadOnly.getDegreeInstitute());
    pBuilder.add("academicPassingYear", pReadOnly.getDegreePassingYear());
  }

  @Override
  public void build(MutableAcademicInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setEmployeeId(pJsonObject.getInt("employeId"));
    pMutable.setDegreeName(pJsonObject.getString("academicDegreeName"));
    pMutable.setDegreeInstitute(pJsonObject.getString("academicInstitution"));
    pMutable.setDegreePassingYear(pJsonObject.getString("academicPassingYear"));
  }
}
