package org.ums.resource.helper;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.ItemBuilder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.User;
import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.manager.UserManager;
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
public class ItemResourceHelper extends ResourceHelper<Item, MutableItem, Long>

{

  @Autowired
  private ItemManager mManager;

  @Autowired
  private ItemBuilder mBuilder;

  @Autowired
  private UserManager mUserManager;

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

  @Override
  public Response post(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    MutableItem mutableItem = new PersistentItem();
    LocalCache localCache = new LocalCache();

    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    User user = mUserManager.get(userId);

    mutableItem.setLastUpdatedBy(user.getId());
    getBuilder().build(mutableItem, pJsonObject, localCache);
    mutableItem.commit(false);

    URI contextURI =
        pUriInfo.getBaseUriBuilder().path(ItemResource.class).path(ItemResource.class, "get")
            .build(mutableItem.getId());
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response batchPost(final JsonObject pJsonObject, final UriInfo pUriInfo) {
    List<MutableItem> itemList = new ArrayList<>();

    JsonArray entries = pJsonObject.getJsonArray("items");

    for(int i = 0; i < entries.size(); i++) {
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      MutableItem item = new PersistentItem();
      getBuilder().build(item, jsonObject, localCache);
      itemList.add(item);
    }

    mManager.create(itemList);

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
