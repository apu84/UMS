package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.domain.model.mutable.library.MutableRecord;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

/**
 * Created by Ifti on 19-Feb-17.
 */
public class MutableRecordResource extends Resource {
  @Autowired
  ResourceHelper<Record, MutableRecord, Long> mResourceHelper;

  @POST
  public Response createRecord(final JsonObject pJsonObject) throws Exception {
    return mResourceHelper.post(pJsonObject, mUriInfo);
  }

}
