package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.common.DegreeTitle;
import org.ums.domain.model.mutable.common.MutableDegreeTitle;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("degreeTitles")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class DegreeTitleResource extends MutableDegreeTitleResource {

  @Autowired
  private ResourceHelper<DegreeTitle, MutableDegreeTitle, Integer> mHelper;

  @GET
  public JsonObject getAll() throws Exception {
    return mHelper.getAll(mUriInfo);
  }
}
