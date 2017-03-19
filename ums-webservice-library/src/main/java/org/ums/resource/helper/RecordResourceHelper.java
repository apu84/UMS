package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.RecordBuilder;
import org.ums.builder.SupplierBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.domain.model.mutable.library.MutableSupplier;
import org.ums.manager.UserManager;
import org.ums.manager.library.RecordManager;
import org.ums.manager.library.SupplierManager;
import org.ums.persistent.model.library.PersistentRecord;
import org.ums.persistent.model.library.PersistentSupplier;
import org.ums.resource.RecordResource;
import org.ums.resource.ResourceHelper;
import org.ums.resource.SemesterResource;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by Ifti on 19-Feb-17.
 */
@Component
public class RecordResourceHelper extends ResourceHelper<Record, MutableRecord, Long>

{

  @Autowired
  private RecordManager mManager;

  @Autowired
  private RecordBuilder mBuilder;

  @Autowired
  private UserManager mUserManager;

  @Override
  public RecordManager getContentManager() {
    return mManager;
  }

  @Override
  public RecordBuilder getBuilder() {
    return mBuilder;
  }

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    MutableRecord mutableRecord = new PersistentRecord();
    LocalCache localCache = new LocalCache();

    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    mutableRecord.setDocumentalist(user.getId());
    mutableRecord.setLastUpdatedBy(user.getId());
    getBuilder().build(mutableRecord, pJsonObject, localCache);
    mutableRecord.create();

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(RecordResource.class).path(RecordResource.class, "get")
            .build(mutableRecord.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Override
  protected String getETag(Record pReadonly) {
    return UmsUtils.nullConversion(pReadonly.getLastModified());
  }

}
