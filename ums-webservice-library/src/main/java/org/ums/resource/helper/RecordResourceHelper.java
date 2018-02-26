package org.ums.resource.helper;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.ums.builder.RecordBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.library.FilterDto;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.manager.library.RecordManager;
import org.ums.persistent.model.library.PersistentRecord;
import org.ums.resource.RecordResource;
import org.ums.resource.ResourceHelper;
import org.ums.solr.repository.document.lms.RecordDocument;
import org.ums.solr.repository.lms.RecordRepository;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;
import org.ums.util.UmsUtils;

import com.google.gson.Gson;

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

  public JsonObject searchRecord(int pPage, int pItemPerPage, final String pFilter, final UriInfo pUriInfo) {
    List<RecordDocument> recordDocuments =
        mRecordRepository.findByCustomQuery(queryBuilder(pFilter), new PageRequest(pPage, pItemPerPage));
    List<Record> records = new ArrayList<>();
    for(RecordDocument document : recordDocuments) {
      records.add(mManager.get(Long.valueOf(document.getId())));
    }
    // queryBuilder(pFilter);
    return convertToJson(records, mRecordRepository.getTotalCount(queryBuilder(pFilter)), pUriInfo);
  }

  private String queryBuilder(final String pFilter) {
    Gson g = new Gson();
    FilterDto filterDto = g.fromJson(pFilter, FilterDto.class);
    String queryString = "";

    if(filterDto.getSearchType().equalsIgnoreCase("basic")) {
      String queryTerm;
      if(filterDto.getBasicQueryField().equals("any")) {
        /* queryString = String.format("%s AND type_s:Record", queryTerm); */
        queryTerm = StringUtils.isEmpty(filterDto.getBasicQueryTerm()) ? "*:*" : filterDto.getBasicQueryTerm();
        queryString = String.format("%s AND type_s:Record", queryTerm);
      }
      else {
        queryTerm = StringUtils.isEmpty(filterDto.getBasicQueryTerm()) ? "*" : filterDto.getBasicQueryTerm();
        queryString = String.format(filterDto.getBasicQueryField() + " : %s AND type_s:Record", queryTerm);
      }

      // queryString =
      // String.format("{!parent which=\"type_s:Record  AND _text_:*%s*\"}roleName_txt:*%s* OR contributorName_txt:*%s*",
      // filterDto.getBasicQueryTerm(), filterDto.getBasicQueryTerm(),
      // filterDto.getBasicQueryTerm());

    }
    else if(filterDto.getSearchType().equalsIgnoreCase("advanced")) {
      queryString = "title_txt:Programming AND type_s: Record";
    }

    return queryString;
  }

  private JsonObject convertToJson(List<Record> records, long totalCount, UriInfo pUriInfo) {
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();

    for(Record record : records) {
      children.add(toJson(record, pUriInfo, localCache));
    }
    object.add("entries", children);
    object.add("total", totalCount);
    localCache.invalidate();
    return object.build();
  }

  @Override
  protected String getETag(Record pReadonly) {
    return UmsUtils.nullConversion(pReadonly.getLastModified());
  }

}
