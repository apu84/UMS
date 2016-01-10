package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.ums.academic.model.PersistentSemester;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.SemesterSyllabusMapResourceHelper;
import org.ums.common.academic.resource.helper.StudentResourceHelper;
import org.ums.common.academic.resource.helper.SyllabusResourceHelper;
import org.ums.manager.SemesterSyllabusMapManager;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 08-Jan-16.
 */
public class MutableSemesterSyllabusMapResource extends Resource {

  @Autowired
  SemesterSyllabusMapResourceHelper  mResourceHelper;

  @Autowired
  @Qualifier("semesterSyllabusMapManager")
  SemesterSyllabusMapManager mManager;

  @PUT
  @Path(PATH_PARAM_OBJECT_ID)
  public Response updateSemester(final @PathParam("object-id") int pObjectId,
                                 final @Context Request pRequest,
                                 final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
                                 final JsonObject pJsonObject) throws Exception {

    return mResourceHelper.put(pJsonObject);
  }

}
