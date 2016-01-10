package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentSemesterSyllabusMap;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.domain.model.regular.Semester;
import org.ums.domain.model.regular.SemesterSyllabusMap;
import org.ums.domain.model.regular.Syllabus;
import org.ums.manager.ContentManager;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Ifti on 08-Jan-16.
 */

@Component
public class SemesterSyllabusMapResourceHelper extends ResourceHelper<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> {
  @Autowired
  @Qualifier("semesterSyllabusMapManager")
  private ContentManager<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> mManager;

  @Autowired
  private List<Builder<SemesterSyllabusMap, MutableSemesterSyllabusMap>> mBuilders;

  @Override
  protected Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected ContentManager<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> getContentManager() {
    return mManager;
  }

  public Response put(final JsonObject pJsonObject) throws Exception {
    MutableSemesterSyllabusMap mutable =new PersistentSemesterSyllabusMap();

    for (Builder<SemesterSyllabusMap, MutableSemesterSyllabusMap> builder : getBuilders()) {
      builder.build(mutable, pJsonObject, null);
    }
    SemesterSyllabusMap readOnly = load(mutable.getId());
    System.out.println(readOnly.getAcademicSemester().getStatus());

    mutable.commit(true);
    return Response.noContent().build();
  }
  @Override
  protected List<Builder<SemesterSyllabusMap, MutableSemesterSyllabusMap>> getBuilders() {
    return mBuilders;
  }

  public JsonObject buildMaps(final List<SemesterSyllabusMap> pSyllabuses, final UriInfo pUriInfo) throws Exception {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for (SemesterSyllabusMap readOnly : pSyllabuses) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected String getEtag(SemesterSyllabusMap pReadonly) {
    return "";
  }
}
