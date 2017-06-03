package org.ums.academic.resource.fee;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.fee.FeeType;
import org.ums.fee.FeeTypeManager;
import org.ums.resource.Resource;

@Component
@Path("/fee-types")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class FeeTypeResource extends Resource {
  @Autowired
  FeeTypeManager mFeeTypeManager;

  @GET
  @Path("/all")
  public List<FeeType> getFeeTypes() throws Exception {
    return mFeeTypeManager.getAll();
  }
}
