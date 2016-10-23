package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.academic.resource.helper.ExamRoutineResourceHelper;
import org.ums.resource.Resource;

import javax.json.JsonObject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 26-Feb-16.
 */
public class MutableExamRoutineResource extends Resource {

  @Autowired
  ExamRoutineResourceHelper mResourceHelper;

  @PUT
  @Path("/semester/{semester-id}/examtype/{exam-type}")
  public Response saveExamRoutine(final @PathParam("semester-id") Integer pSemesterId,
      final @PathParam("exam-type") Integer pExamType, final JsonObject pJsonObject)
      throws Exception {
    return mResourceHelper.save(pJsonObject, pSemesterId, pExamType);
  }

}
