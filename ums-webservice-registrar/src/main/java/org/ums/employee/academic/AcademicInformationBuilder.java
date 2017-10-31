package org.ums.employee.academic;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.employee.academic.AcademicInformation;
import org.ums.employee.academic.MutableAcademicInformation;
import org.ums.enums.common.AcademicDegreeType;

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
    pBuilder.add("result", pReadOnly.getResult() == null ? "" : pReadOnly.getResult());
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
    pMutable.setPassingYear(pJsonObject.getString("passingYear"));
    pMutable.setResult(pJsonObject.getString("result") == null ? "" : pJsonObject.getString("result"));
  }
}
