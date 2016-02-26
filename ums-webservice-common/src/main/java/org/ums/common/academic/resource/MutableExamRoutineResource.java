package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.domain.model.readOnly.ClassRoom;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 26-Feb-16.
 */
public class MutableExamRoutineResource  extends Resource {

  @Autowired
  ResourceHelper<ClassRoom, MutableClassRoom, Integer> mResourceHelper;

  @PUT
  @Path( "/semester/{semester-id}/examtype/{exam-type}")
  public Response updateExamRoutine(final @PathParam("semester-id") String pSemesterId,
                                    final @PathParam("semester-id") String pExamType,
                                    final JsonObject pJsonObject) throws Exception {
    //return mResourceHelper.put(Integer.parseInt(pObjectId), pRequest, pIfMatchHeader, pJsonObject);
    return null;
  }

}
