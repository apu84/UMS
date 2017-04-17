package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.ums.builder.RecordBuilder;
import org.ums.builder.SupplierBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Employee;
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
import org.ums.solr.repository.EmployeeRepository;
import org.ums.solr.repository.document.EmployeeDocument;
import org.ums.solr.repository.document.lms.RecordDocument;
import org.ums.solr.repository.lms.RecordRepository;
import org.ums.util.UmsUtils;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

  @Autowired
  @Qualifier("recordRepositoryImpl")
  RecordRepository mRecordRepository;

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

  public JsonObject searchRecord(String pQuery, int page, final UriInfo pUriInfo) {
    List<RecordDocument> recordDocuments = mRecordRepository.findByCustomQuery(pQuery, new PageRequest(0, 10));
    List<Record> records = new ArrayList<>();
    for(RecordDocument document : recordDocuments) {
      records.add(mManager.get(Long.valueOf(document.getId())));
    }
    return convertToJson(records, pUriInfo);
  }

  private JsonObject convertToJson(List<Record> records, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(Record record : records) {
      children.add(toJson(record, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected String getETag(Record pReadonly) {
    return UmsUtils.nullConversion(pReadonly.getLastModified());
  }

}
