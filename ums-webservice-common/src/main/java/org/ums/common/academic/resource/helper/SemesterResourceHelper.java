package org.ums.common.academic.resource.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentSemester;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.MutableSemester;
import org.ums.domain.model.Semester;
import org.ums.manager.SemesterManager;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
public class SemesterResourceHelper extends ResourceHelper<Semester> {
  @Autowired
  private SemesterManager mManager;

  @Autowired
  private List<Builder<Semester, MutableSemester>> mBuilders;

  public Semester load(final String pSemesterId) throws Exception {
    return mManager.get(pSemesterId);
  }

  public JsonObject toJson(final Semester pSemester, final UriInfo pUriInfo) throws Exception {
    JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    for (Builder<Semester, MutableSemester> builder : mBuilders) {
      builder.build(jsonObjectBuilder, pSemester, pUriInfo);
    }
    return jsonObjectBuilder.build();
  }

  public void delete(final Semester pSemester) throws Exception {
    pSemester.edit().delete();
  }

  public void put(final Semester pSemester, final JsonObject pJsonObject) throws Exception {
    MutableSemester mutableSemester = pSemester.edit();
    for (Builder<Semester, MutableSemester> builder : mBuilders) {
      builder.build(mutableSemester, pJsonObject);
    }
    mutableSemester.commit(true);
  }

  public void post(final JsonObject pJsonObject) throws Exception {
    MutableSemester mutableSemester = new PersistentSemester();
    for (Builder<Semester, MutableSemester> builder : mBuilders) {
      builder.build(mutableSemester, pJsonObject);
    }
    mutableSemester.commit(false);
  }


}
