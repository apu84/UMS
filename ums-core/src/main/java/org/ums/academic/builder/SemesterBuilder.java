package org.ums.academic.builder;


import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.Semester;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.SimpleDateFormat;

public class SemesterBuilder implements Builder<Semester, MutableSemester> {
  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  public void build(final JsonObjectBuilder pBuilder, final Semester pSemester, final UriInfo pUriInfo) throws Exception {
    pBuilder.add("id", pSemester.getId());
    pBuilder.add("name", pSemester.getName());
    pBuilder.add("startDate", dateFormat.format(pSemester.getStartDate()));
    pBuilder.add("status", pSemester.getStatus());
  }

  public void build(final MutableSemester pMutableSemester, JsonObject pJsonObject) throws Exception {
    String id = pJsonObject.getString("id");
    String name = pJsonObject.getString("name");
    String startDate = pJsonObject.getString("startDate");
    String status = pJsonObject.getString("status");
    pMutableSemester.setId(id);
    pMutableSemester.setName(name);
    pMutableSemester.setStartDate(dateFormat.parse(startDate));
    pMutableSemester.setStatus(Boolean.valueOf(status));
  }
}
