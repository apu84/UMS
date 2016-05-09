package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.SubGroupResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by My Pc on 5/5/2016.
 */
public class MutableSubGroupResource extends Resource{

  @Autowired
  SubGroupResourceHelper mSubGroupResourceHelper;

  @POST
  public Response createSubGroup(final JsonObject pJsonObject) throws Exception{
    return mSubGroupResourceHelper.post(pJsonObject,mUriInfo);
  }

  @PUT
  public Response updateSubGroup(
      final @Context Request pRequest,
      final @HeaderParam(HEADER_IF_MATCH) String pIfMatchHeader,
      final JsonObject pJsonObject
      )throws Exception{
    return mSubGroupResourceHelper.put(pJsonObject.getInt("id"),pRequest,pIfMatchHeader,pJsonObject);
  }

  @DELETE
  @Path(PATH_PARAM_OBJECT_ID)
  public Response deleteSubGroup(final @PathParam("object-id") int objectId) throws Exception{
    return mSubGroupResourceHelper.delete(objectId);
  }


  @DELETE
  @Path("/semesterId/{semesterId}/groupNo/{groupNo}")
  public Response deleteBySemesterAndGroup(final @PathParam("semesterId") int semesterId,final @PathParam("groupNo") int groupNo)throws Exception{
    return mSubGroupResourceHelper.deleteBySemesterAndGroup(semesterId,groupNo);
  }
}
