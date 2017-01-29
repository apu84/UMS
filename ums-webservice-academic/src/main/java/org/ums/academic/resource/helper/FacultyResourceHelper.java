package org.ums.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.FacultyBuilder;
import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.mutable.MutableFaculty;
import org.ums.manager.FacultyManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 07-Dec-16.
 */
@Component
public class FacultyResourceHelper extends ResourceHelper<Faculty, MutableFaculty, Integer> {

  @Autowired
  private FacultyManager mFacultyManager;

  @Autowired
  private FacultyBuilder mFacultyBuilder;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    return null;
  }

  @Override
  protected FacultyManager getContentManager() {
    return mFacultyManager;
  }

  @Override
  protected FacultyBuilder getBuilder() {
    return mFacultyBuilder;
  }

  @Override
  protected String getETag(Faculty pReadonly) {
    return pReadonly.getLastModified();
  }
}
