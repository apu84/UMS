package org.ums.builder;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.formatter.DateFormat;
import org.ums.manager.ProgramTypeManager;

@Component
public class SemesterBuilder implements Builder<Semester, MutableSemester> {
  @Autowired
  @Qualifier("genericDateFormat")
  DateFormat mDateFormat;
  @Autowired
  ProgramTypeManager mProgramTypeManager;

  public void build(final JsonObjectBuilder pBuilder, final Semester pSemester,
      final UriInfo pUriInfo, final LocalCache pLocalCache) {
    pBuilder.add("id", pSemester.getId());
    pBuilder.add("name", pSemester.getName());
    pBuilder.add("startDate", mDateFormat.format(pSemester.getStartDate()));
    if(pSemester.getEndDate() != null) {
      pBuilder.add("endDate", mDateFormat.format(pSemester.getEndDate()));
    }
    ProgramType programType = (ProgramType) pLocalCache.cache(() -> pSemester.getProgramType(),
        pSemester.getProgramTypeId(), ProgramType.class);

    pBuilder.add("programType", pUriInfo.getBaseUriBuilder().path("academic").path("programtype")
        .path(String.valueOf(programType.getId())).build().toString());
    pBuilder.add("programTypeId", String.valueOf(programType.getId()));
    pBuilder.add("status", pSemester.getStatus().getValue());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("semester")
        .path(String.valueOf(pSemester.getId())).build().toString());
  }

  public void build(final MutableSemester pMutableSemester, JsonObject pJsonObject, final LocalCache pLocalCache) {
    int id = Integer.parseInt(pJsonObject.getString("id"));
    String name = pJsonObject.getString("name");
    String startDate = pJsonObject.getString("startDate");
    // int program = Integer.parseInt(pJsonObject.getString("programTypeId"));
    int program = Integer.parseInt(pJsonObject.getString("programTypeId"));
    Semester.Status status = Semester.Status.get(Integer.parseInt(pJsonObject.getString("status")));
    pMutableSemester.setId(id);
    pMutableSemester.setName(name);

    pMutableSemester.setStartDate(mDateFormat.parse(startDate));
    if(pJsonObject.containsKey("endDate")) {
      String endDate = pJsonObject.getString("endDate");
      pMutableSemester.setEndDate(mDateFormat.parse(endDate));
    }
    pMutableSemester.setProgramType(mProgramTypeManager.get(program));
    pMutableSemester.setStatus(status);
  }
}
