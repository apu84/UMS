package org.ums.common.academic.resource.helper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentStudent;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.academic.resource.StudentResource;
import org.ums.domain.model.MutableStudent;
import org.ums.domain.model.Student;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Component
public class StudentResourceHelper extends ResourceHelper<Student, MutableStudent, String> {
  @Autowired
  @Qualifier("studentManager")
  private ContentManager<Student, MutableStudent, String> mManager;

  @Autowired
  private List<Builder<Student, MutableStudent>> mBuilders;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    MutableStudent mutableStudent = new PersistentStudent();
    LocalCache localCache = new LocalCache();
    for (Builder<Student, MutableStudent> builder : mBuilders) {
      builder.build(mutableStudent, pJsonObject, localCache);
    }
    mutableStudent.commit(false);

    URI contextURI = pUriInfo.getBaseUriBuilder().path(StudentResource.class).path(StudentResource.class, "get").build(mutableStudent.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);

    return builder.build();
  }

  @Override
  protected ContentManager<Student, MutableStudent, String> getContentManager() {
    return mManager;
  }

  @Override
  protected List<Builder<Student, MutableStudent>> getBuilders() {
    return mBuilders;
  }

  @Override
  protected String getEtag(Student pReadonly) {
    return pReadonly.getLastModified();
  }
}
