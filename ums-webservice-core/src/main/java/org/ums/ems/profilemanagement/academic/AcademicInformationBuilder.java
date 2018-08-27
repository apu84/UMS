package org.ums.ems.profilemanagement.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.enums.common.DegreeLevel;
import org.ums.manager.common.DegreeTitleManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class AcademicInformationBuilder implements Builder<AcademicInformation, MutableAcademicInformation> {

  @Autowired
  private DegreeTitleManager mDegreeTitleManager;

  private DegreeLevel mDegreeLevel;

  @Override
  public void build(JsonObjectBuilder pBuilder, AcademicInformation pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {

    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("employeeId", pReadOnly.getEmployeeId());
    JsonObjectBuilder degreeLevelBuilder = Json.createObjectBuilder();
    degreeLevelBuilder.add("id", pReadOnly.getDegreeLevelId()).add("name",
        mDegreeLevel.get(pReadOnly.getDegreeLevelId()).getLabel());
    pBuilder.add("degreeLevel", degreeLevelBuilder);
    JsonObjectBuilder degreeTitleBuilder = Json.createObjectBuilder();
    if(pReadOnly.getDegreeTitleId() == 0) {
      degreeTitleBuilder.add("id", 0).add("title", "").add("degreeLevelId", 0);
    }
    else {
      degreeTitleBuilder.add("id", pReadOnly.getDegreeTitleId())
          .add("title", mDegreeTitleManager.get(pReadOnly.getDegreeTitleId()).getTitle())
          .add("degreeLevelId", mDegreeTitleManager.get(pReadOnly.getDegreeTitleId()).getDegreeLevelId());
    }
    pBuilder.add("degreeTitle", degreeTitleBuilder);
    pBuilder.add("board", pReadOnly.getBoard() == null ? "" : pReadOnly.getBoard());
    pBuilder.add("institution", pReadOnly.getInstitute());
    pBuilder.add("passingYear", pReadOnly.getPassingYear());
    pBuilder.add("result", pReadOnly.getResult() == null ? "" : pReadOnly.getResult());
    pBuilder.add("major", pReadOnly.getMajor() == null ? "" : pReadOnly.getMajor());
  }

  @Override
  public void build(MutableAcademicInformation pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {

    pMutable.setId(!pJsonObject.getString("id").equals("") ? Long.parseLong(pJsonObject.getString("id")) : null);
    pMutable.setEmployeeId(pJsonObject.getString("employeeId"));
    pMutable.setDegreeLevelId(pJsonObject.getJsonObject("degreeLevel").getInt("id"));
    pMutable.setDegreeTitleId(pJsonObject.getJsonObject("degreeTitle").getInt("id"));
    pMutable.setInstitute(pJsonObject.getString("institution"));
    pMutable.setPassingYear(pJsonObject.getInt("passingYear"));
    pMutable.setResult(pJsonObject.getString("result") == null ? "" : pJsonObject.getString("result"));
    pMutable.setMajor(pJsonObject.getString("major") == null ? "" : pJsonObject.getString("major"));
    pMutable.setBoard(pJsonObject.getString("board") == null ? "" : pJsonObject.getString("board"));
  }
}
