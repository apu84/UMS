package org.ums.academic.resource.fee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.fee.FeeCategory;
import org.ums.fee.FeeCategoryManager;
import org.ums.resource.Resource;

@Component
@Path("/fee-category")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class FeeCategoryResource extends Resource {
  @Autowired
  FeeCategoryManager mFeeCategoryManager;

  @GET
  @Path("/all")
  public List<FeeCategory> getFeeCategories() throws Exception {
    return mFeeCategoryManager.getAll();
  }

  @GET
  @Path("/type/{typeId}")
  public List<FeeCategory> getFeeCategories(@PathParam("typeId") Integer pTypeId) throws Exception {
    return mFeeCategoryManager.getAll().stream()
        .filter(pFeeCategory -> pFeeCategory.getFeeTypeId().intValue() == pTypeId).collect(Collectors.toList());
  }
}
