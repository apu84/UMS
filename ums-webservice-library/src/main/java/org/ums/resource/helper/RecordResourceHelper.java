package org.ums.resource.helper;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.RecordBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.library.FilterDto;
import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.domain.model.mutable.library.MutableRecordLog;
import org.ums.formatter.DateFormat;
import org.ums.manager.library.ItemManager;
import org.ums.manager.library.RecordLogManager;
import org.ums.manager.library.RecordManager;
import org.ums.persistent.model.library.PersistentRecord;
import org.ums.persistent.model.library.PersistentRecordLog;
import org.ums.resource.RecordResource;
import org.ums.resource.ResourceHelper;
import org.ums.solr.repository.converter.Converter;
import org.ums.solr.repository.converter.SimpleConverter;
import org.ums.solr.repository.document.lms.RecordDocument;
import org.ums.solr.repository.lms.RecordRepository;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;
import org.ums.util.Constants;
import org.ums.util.UmsUtils;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
  RecordRepository mRecordRepository;

  @Autowired
  ItemManager mItemManger;

  @Autowired
  RecordLogManager mRecordLogManager;

  @Override
  public RecordManager getContentManager() {
    return mManager;
  }

  @Override
  public RecordBuilder getBuilder() {
    return mBuilder;
  }

  @Autowired
  @Qualifier("genericDateFormat")
  private DateFormat mDateFormat;

  @Override
  @Transactional
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    MutableRecord mutableRecord = new PersistentRecord();
    LocalCache localCache = new LocalCache();

    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);
    mutableRecord.setDocumentalist(user.getId());
    mutableRecord.setLastUpdatedBy(user.getId());
    getBuilder().build(mutableRecord, pJsonObject, localCache);
    mutableRecord.create();

    createRecordLog(mutableRecord.getId(), 1, "", pJsonObject.toString(), user.getId());

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(RecordResource.class).path(RecordResource.class, "get")
            .build(mutableRecord.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public void createRecordLog(Long pMfn, Integer pModificationType, String pPreviousJson, String pModifiedJson,
      String pModifiedBy) {
    MutableRecordLog mutableRecordLog = new PersistentRecordLog();
    mutableRecordLog.setMfn(pMfn);
    mutableRecordLog.setModificationType(pModificationType);
    mutableRecordLog.setPreviousJson(pPreviousJson);
    mutableRecordLog.setModifiedJson(pModifiedJson);
    mutableRecordLog.setModifiedBy(pModifiedBy.isEmpty() ? getModifiedBy(pModifiedBy).getId() : pModifiedBy);
    mutableRecordLog.setModifiedOn(mDateFormat.parse(new SimpleDateFormat(Constants.DATE_FORMAT).format(new Date())));
    mutableRecordLog.create();
  }

  public void insertRecordLog(Long pObjectId, Integer pModificationType, String pJsonObject) {
    createRecordLog(pObjectId, pModificationType, "", pJsonObject, "");
  }

  @Nullable
  private User getModifiedBy(String pModifiedBy) {
    User user = null;
    if(pModifiedBy.isEmpty()) {
      String userId = SecurityUtils.getSubject().getPrincipal().toString();
      user = mUserManager.get(userId);
    }
    return user;
  }

  public JsonObject searchRecord(int pPage, int pItemPerPage, final String pFilter, final UriInfo pUriInfo) {
    String query = queryBuilder(pFilter);
    List<RecordDocument> recordDocuments =
        mRecordRepository.findByCustomQuery(query, new PageRequest(pPage, pItemPerPage), query.contains("*:*"));
    List<Record> records = new ArrayList<>();
    for(RecordDocument document : recordDocuments) {
      records.add(mManager.get(Long.valueOf(document.getId())));
    }
    // queryBuilder(pFilter);
    return convertToJson(records, mRecordRepository.totalDocuments(), pUriInfo);
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
        queryString = String.format(filterDto.getBasicQueryField() + ":%s AND type_s:Record", queryTerm);
      }

      // queryString =
      // String.format("{!parent which=\"type_s:Record AND _text_:*%s*\"}roleName_txt:*%s* OR
      // contributorName_txt:*%s*",
      // filterDto.getBasicQueryTerm(), filterDto.getBasicQueryTerm(),
      // filterDto.getBasicQueryTerm());

    }
    else if(filterDto.getSearchType().equalsIgnoreCase("advanced_search")) {
      StringBuilder sb = new StringBuilder("");
      int sizeOfMap = filterDto.getAdvancedSearchFilter().size();
      for(int i = 0; i < sizeOfMap; i++) {
        sb.append(filterDto.getAdvancedSearchFilter().get(i).get("key"))
            .append(":")
            .append(
                filterDto.getAdvancedSearchFilter().get(i).get("value").isEmpty() ? "*" : filterDto
                    .getAdvancedSearchFilter().get(i).get("value")).append(" ")
            .append(sizeOfMap - 1 == i ? "" : "AND ");
      }
      queryString = sb.append(" AND type_s: Record").toString();
    }

    return queryString;
  }

  @Transactional
  public Response deleteRecord(String pMfnNo) {
    insertRecordLog(Long.parseLong(pMfnNo), 3, "");
    LocalCache localCache = new LocalCache();
    PersistentRecord record = new PersistentRecord();
    record = (PersistentRecord) mManager.get(Long.parseLong(pMfnNo));
    List<Item> itemList = new ArrayList<>();
    if(record.getTotalItems().equals(record.getTotalAvailable())) {
      Converter<Record, RecordDocument> converter = new SimpleConverter<>(Record.class, RecordDocument.class);
      itemList = mItemManger.getByMfn(record.getId());
      mManager.delete(record);
      for(Item item : itemList) {
        mItemManger.delete((MutableItem) item);
        mRecordRepository.delete(Long.valueOf(converter.convert(record).getId()));
      }
      localCache.invalidate();
      return Response.noContent().build();
    }

    return Response.notModified().build();
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
