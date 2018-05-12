package org.ums.employee.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.manager.common.AcademicDegreeManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AcademicInformationBuilder implements Builder<AcademicInformation, MutableAcademicInformation> {

  @Autowired
  private AcademicDegreeManager mAcademicDegreeManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, AcademicInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    JsonObjectBuilder degreeBuilder = Json.createObjectBuilder();
    degreeBuilder.add("id", pReadOnly.getDegreeId()).add("name",
        mAcademicDegreeManager.get(pReadOnly.getDegreeId()).getDegreeName());
    pBuilder.add("degree", degreeBuilder);
    pBuilder.add("institution", pReadOnly.getInstitute());
    pBuilder.add("passingYear", pReadOnly.getPassingYear());
    pBuilder.add("result", pReadOnly.getResult() == null ? "" : pReadOnly.getResult());
  }

  @Override
  public void build(MutableAcademicInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setId(!pJsonObject.getString("id").equals("") ? Long.parseLong(pJsonObject.getString("id")) : null);
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setDegreeId(pJsonObject.getJsonObject("degree").getInt("id"));
    pMutable.setInstitute(pJsonObject.getString("institution"));
    pMutable.setPassingYear(pJsonObject.getInt("passingYear"));
    pMutable.setResult(pJsonObject.getString("result") == null ? "" : pJsonObject.getString("result"));
  }
}
