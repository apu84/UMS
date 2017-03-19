package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableSupplier;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class MutableSupplierResource extends Resource {
  @Autowired
  ResourceHelper<Supplier, MutableSupplier, Long> mResourceHelper;

  @POST
  public Response createSupplier(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

}
