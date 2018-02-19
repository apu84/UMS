package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ums.builder.ItemBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.enums.library.ItemStatus;
import org.ums.manager.library.RecordManager;
import org.ums.usermanagement.user.User;
import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.usermanagement.user.UserManager;
import org.ums.manager.library.ItemManager;
import org.ums.persistent.model.library.PersistentItem;
import org.ums.resource.ItemResource;
import org.ums.resource.ResourceHelper;
import org.ums.util.UmsUtils;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ifti on 04-Mar-17.
 */
@Component
public class ItemResourceHelper extends ResourceHelper<Item, MutableItem, Long> {

  @Autowired
  private ItemManager mManager;

  @Autowired
  private ItemBuilder mBuilder;

  @Autowired
  private UserManager mUserManager;

  @Autowired
  private RecordManager mRecordManager;

  @Override
  public ItemManager getContentManager() {
    return mManager;
  }

  @Override
  public ItemBuilder getBuilder() {
    return mBuilder;
  }

  public JsonObject getByMfn(final Long pMfn, final UriInfo pUriInfo) {
    List<Item> itemList = mManager.getByMfn(pMfn);

    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(Item readOnly : itemList) {
      children.add(toJson(readOnly, pUriInfo, localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();

  }

  public JsonObject getByAccessionNUmber(final String pAccessionNumber, final UriInfo pUriInfo) {
    Item item = mManager.getByAccessionNumber(pAccessionNumber);

    JsonObjectBuilder object = Json.createObjectBuilder();
    LocalCache localCache = new LocalCache();
    object.add("entries", toJson(item, pUriInfo, localCache));
    localCache.invalidate();
    return object.build();

  }

  @Override
  @Transactional
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    MutableItem mutableItem = new PersistentItem();
    LocalCache localCache = new LocalCache();

    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);

    mutableItem.setLastUpdatedBy(user.getId());
    getBuilder().build(mutableItem, pJsonObject, localCache);
    mutableItem.create();

    MutableRecord mutableRecord = (MutableRecord) mRecordManager.get(mutableItem.getMfn());
    if(mutableItem.getStatus() == ItemStatus.AVAILABLE) {
      mutableRecord.setTotalItems(mutableRecord.getTotalItems() + 1);
      mutableRecord.setTotalAvailable(mutableRecord.getTotalAvailable() + 1);
    }
    else if(mutableItem.getStatus() == ItemStatus.ON_HOLD) {
      mutableRecord.setTotalItems(mutableRecord.getTotalItems() + 1);
      mutableRecord.setTotalOnHold(mutableRecord.getTotalOnHold() + 1);
    }
    mRecordManager.update(mutableRecord);

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(ItemResource.class).path(ItemResource.class, "get")
            .build(mutableItem.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Transactional
  public Response updateItem(JsonObject pJsonObject, UriInfo pUriInfo) {
    MutableItem mutableItem = new PersistentItem();
    MutableItem mutableItem1 = new PersistentItem();
    mutableItem1 = (MutableItem) mManager.get(Long.parseLong(pJsonObject.getString("id")));
    LocalCache localCache = new LocalCache();

    getBuilder().build(mutableItem, pJsonObject, localCache);
    mutableItem.setId(mutableItem1.getId());
    mutableItem.update();

    MutableRecord mutableRecord = (MutableRecord) mRecordManager.get(mutableItem.getMfn());
    if(mutableItem1.getStatus() == ItemStatus.AVAILABLE && mutableItem.getStatus() == ItemStatus.ON_HOLD) {
      mutableRecord.setTotalAvailable(mutableRecord.getTotalAvailable() - 1);
      mutableRecord.setTotalOnHold(mutableRecord.getTotalOnHold() + 1);
    }
    else if(mutableItem1.getStatus() == ItemStatus.ON_HOLD && mutableItem.getStatus() == ItemStatus.AVAILABLE) {
      mutableRecord.setTotalAvailable(mutableRecord.getTotalAvailable() + 1);
      mutableRecord.setTotalOnHold(mutableRecord.getTotalOnHold() - 1);
    }
    mRecordManager.update(mutableRecord);
    localCache.invalidate();

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  @Transactional
  public Response batchPost(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    int mTotalAvailable = 0;
    int mTotalItems = 0;
    int mTotalOnHold = 0;

    List<MutableItem> itemList = new ArrayList<>();

    JsonArray entries = pJsonObject.getJsonArray("items");

    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      MutableItem item = new PersistentItem();
      getBuilder().build(item, jsonObject, localCache);
      mTotalItems = mTotalItems + 1;
      if(item.getStatus() == ItemStatus.AVAILABLE) {
        mTotalAvailable = mTotalAvailable + 1;
      }
      else if(item.getStatus() == ItemStatus.ON_HOLD) {
        mTotalOnHold = mTotalOnHold + 1;
      }
      itemList.add(item);
    }
    mManager.create(itemList);

    MutableRecord mutableRecord =
        (MutableRecord) mRecordManager.get(Long.parseLong(entries.getJsonObject(0).getString("mfnNo")));
    mutableRecord.setTotalItems(mutableRecord.getTotalItems() + mTotalItems);
    mutableRecord.setTotalAvailable(mutableRecord.getTotalAvailable() + mTotalAvailable);
    mutableRecord.setTotalOnHold(mutableRecord.getTotalOnHold() + mTotalOnHold);

    mRecordManager.update(mutableRecord);

    return null;
    // mManager.create(itemList);
    //
    // URI contextURI =
    // pUriInfo.getBaseUriBuilder().path(ItemResource.class).path(ItemResource.class, "get")
    // .build("1");
    // Response.ResponseBuilder builder = Response.created(contextURI);
    // builder.status(Response.Status.CREATED);
    // return builder.build();
  }

  @Override
  protected String getETag(Item pReadonly) {
    return UmsUtils.nullConversion(pReadonly.getLastModified());
  }
}
