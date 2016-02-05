package org.ums.academic.builder;


import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.readOnly.ProgramType;
import org.ums.domain.model.readOnly.Semester;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;

public class SemesterBuilder implements Builder<Semester, MutableSemester> {
  DateFormat mDateFormat;
  ContentManager<ProgramType, MutableProgramType, Integer> mProgramTypeManager;

  public SemesterBuilder(final DateFormat pDateFormat,
                         final ContentManager<ProgramType, MutableProgramType, Integer> pProgramTypeManager) {
    mDateFormat = pDateFormat;
    mProgramTypeManager = pProgramTypeManager;
  }

  public void build(final JsonObjectBuilder pBuilder, final Semester pSemester, final UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pSemester.getId());
    pBuilder.add("name", pSemester.getName());
    pBuilder.add("startDate", mDateFormat.format(pSemester.getStartDate()));
    if (pSemester.getEndDate() != null) {
      pBuilder.add("endDate", mDateFormat.format(pSemester.getEndDate()));
    }
    ProgramType programType = (ProgramType) pLocalCache.cache(() -> pSemester.getProgramType(),
        pSemester.getProgramTypeId(), ProgramType.class);

    pBuilder.add("programType", pUriInfo.getBaseUriBuilder().path("academic").path("programtype")
        .path(String.valueOf(programType.getId())).build().toString());
    pBuilder.add("status", pSemester.getStatus());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("semester")
        .path(String.valueOf(pSemester.getId())).build().toString());
  }

  public void build(final MutableSemester pMutableSemester, JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception {
    int id = pJsonObject.getInt("semesterId");
    String name = pJsonObject.getString("semesterName");
    String startDate = pJsonObject.getString("startDate");
    int program = Integer.parseInt(pJsonObject.getString("programTypeId"));
    boolean status = pJsonObject.getInt("statusId")==1?Boolean.TRUE:Boolean.FALSE;
    pMutableSemester.setId(id);
    pMutableSemester.setName(name);
    pMutableSemester.setStartDate(mDateFormat.parse(startDate));
    if (pJsonObject.containsKey("endDate")) {
      String endDate = pJsonObject.getString("endDate");
      pMutableSemester.setEndDate(mDateFormat.parse(endDate));
    }
    pMutableSemester.setProgramType(mProgramTypeManager.get(program));
    pMutableSemester.setStatus(status);
  }
}
