package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.builder.SpStudentBuilder;
import org.ums.cache.LocalCache;
import org.ums.academic.resource.SpStudentResource;
import org.ums.domain.model.immutable.SpStudent;
import org.ums.domain.model.mutable.MutableSpStudent;
import org.ums.manager.ProgramManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.SpStudentManager;
import org.ums.persistent.model.PersistentSpStudent;
import org.ums.resource.ResourceHelper;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Created by My Pc on 4/28/2016.
 */

@Component
public class SpStudentResourceHelper extends ResourceHelper<SpStudent, MutableSpStudent, String> {

  @Autowired
  private SpStudentManager mManager;

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private ProgramManager mProgramManager;

  @Autowired
  private SpStudentBuilder mBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableSpStudent spStudent = new PersistentSpStudent();
    LocalCache localCache = new LocalCache();
    getBuilder().build(spStudent, pJsonObject, localCache);
    spStudent.create();
    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(SpStudentResource.class).path(SpStudentResource.class, "get")
            .build(spStudent.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public JsonObject getStudentByProgramYearSemesterStatus(final int programId, final int academicYear,
      final int academicSemester, final int status, final Request pRequest, final UriInfo pUriInfo) {
    List<SpStudent> spStudents =
        getContentManager().getStudentByProgramYearSemesterStatus(programId, academicYear, academicSemester, status);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(SpStudent routine : spStudents) {
      children.add(toJson(routine, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  public SpStudentManager getContentManager() {
    return mManager;
  }

  @Override
  public Builder<SpStudent, MutableSpStudent> getBuilder() {
    return mBuilder;
  }

  @Override
  public String getETag(SpStudent pReadonly) {
    return pReadonly.getLastModified();
  }
}
