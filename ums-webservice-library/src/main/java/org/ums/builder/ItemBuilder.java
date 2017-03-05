package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.dto.library.ImprintDto;
import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.immutable.library.Record;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.domain.model.mutable.library.MutableRecord;
import org.ums.enums.common.Language;
import org.ums.enums.library.*;
import org.ums.manager.library.ItemManager;
import org.ums.manager.library.RecordManager;
import org.ums.persistent.model.library.PersistentPublisher;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Ifti on 04-Mar-17.
 */
@Component
public class ItemBuilder implements Builder<Item, MutableItem> {

  @Autowired
  ItemManager mItemManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Item pReadOnly, UriInfo pUriInfo,
      final LocalCache pLocalCache) {
     pBuilder.add("mfnNo", pReadOnly.getMfn());
     pBuilder.add("itemId", pReadOnly.getId());
     pBuilder.add("copyNumber", pReadOnly.getCopyNumber());
     pBuilder.add("accessionNumber", pReadOnly.getAccessionNumber());
     pBuilder.add("accessionDate", pReadOnly.getAccessionDate());
     pBuilder.add("barcode", pReadOnly.getBarcode());
     pBuilder.add("price", pReadOnly.getPrice());
     pBuilder.add("internalNote", pReadOnly.getInternalNote());

    pBuilder.add("status",pReadOnly.getStatus() .getId());
    pBuilder.add("statusName", pReadOnly.getStatus().getLabel());
    pBuilder.add("insertedBy", pReadOnly.getInsertedBy());
    pBuilder.add("insertedOn", pReadOnly.getInsertedOn());
    pBuilder.add("lastUpdatedBy", pReadOnly.getLastUpdatedBy());
    pBuilder.add("lastUpdatedOn", pReadOnly.getLastUpdatedOn());
  }

  @Override
  public void build(final MutableItem pMutable, final JsonObject pJsonObject,
      final LocalCache pLocalCache) {

    // pMutable.setId(pJsonObject.get("title"));
    pMutable.setMfn(Long.valueOf(pJsonObject.getString("mfnNo")));
    pMutable.setCopyNumber(pJsonObject.getInt("copyNumber"));
    pMutable.setAccessionNumber(pJsonObject.getString("accessionNumber"));
    pMutable.setAccessionDate(pJsonObject.getString("accessionDate"));
    if(pJsonObject.containsKey("barcode"))
      pMutable.setBarcode(pJsonObject.getString("barcode"));
    pMutable.setPrice(pJsonObject.getJsonNumber("price").doubleValue());
    pMutable.setInternalNote(pJsonObject.getString("internalNote"));
    // pMutable.setSupplier(pJsonObject.getInt("copyNumber"));
    // pMutable.setSupplierId(pJsonObject.getInt("supplier"));
    pMutable.setStatus(ItemStatus.get(pJsonObject.getInt("status")));
  }

}
