package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.enums.library.AcquisitionType;
import org.ums.enums.library.ItemStatus;
import org.ums.manager.accounts.CurrencyManager;
import org.ums.manager.library.ItemManager;
import org.ums.util.UmsUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Ifti on 04-Mar-17.
 */
@Component
public class ItemBuilder implements Builder<Item, MutableItem> {

  @Autowired
  ItemManager mItemManager;

  @Autowired
  CurrencyManager mCurrencyManager;

  @Override
  public void build(final JsonObjectBuilder pBuilder, final Item pReadOnly, UriInfo pUriInfo,
      final LocalCache pLocalCache) {
    pBuilder.add("mfnNo", pReadOnly.getMfn().toString());
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("copyNumber", pReadOnly.getCopyNumber() == 0 ? "0" : pReadOnly.getCopyNumber().toString());
    pBuilder.add("accessionNumber", pReadOnly.getAccessionNumber());
    pBuilder.add("accessionDate", UmsUtils.nullConversion(pReadOnly.getAccessionDate()));
    pBuilder.add("barcode", UmsUtils.nullConversion(pReadOnly.getBarcode()));
    pBuilder.add("price", pReadOnly.getPrice() == 0 ? "0" : pReadOnly.getPrice().toString());
    pBuilder.add("internalNote", UmsUtils.nullConversion(pReadOnly.getInternalNote()));
    // pBuilder.add("supplierName", pReadOnly.getSupplier().getName());
    // pBuilder.add("supplier", pReadOnly.getSupplier().getId());
    pBuilder.add("status", pReadOnly.getStatus() == null ? 0 : pReadOnly.getStatus().getId());
    pBuilder.add("statusName", pReadOnly.getStatus() == null ? "" : pReadOnly.getStatus().getLabel());
    pBuilder.add("insertedBy", UmsUtils.nullConversion(pReadOnly.getInsertedBy()));
    pBuilder.add("insertedOn", UmsUtils.nullConversion(pReadOnly.getInsertedOn()));
    pBuilder.add("lastUpdatedBy", UmsUtils.nullConversion(pReadOnly.getLastUpdatedBy()));
    pBuilder.add("lastUpdatedOn", UmsUtils.nullConversion(pReadOnly.getLastUpdatedOn()));

    JsonObjectBuilder object = Json.createObjectBuilder();
    object.add("id", pReadOnly.getSupplier() == null ? "" : pReadOnly.getSupplier().getId().toString());
    object.add("name", pReadOnly.getSupplier() == null ? "" : pReadOnly.getSupplier().getName());
    pBuilder.add("supplier", object);

    pBuilder.add("circulationStatus", pReadOnly.getCirculationStatus());

    pBuilder.add("currency", pReadOnly.getCurrencyId().toString());

    pBuilder.add("acqType", pReadOnly.getAcquisitionType() == null ? 101101 : pReadOnly.getAcquisitionType().getId());

    pBuilder
        .add("acqTypeName", pReadOnly.getAcquisitionType() == null ? "" : pReadOnly.getAcquisitionType().getLabel());

    pBuilder.add("currencyName", pReadOnly.getCurrencyId() == 0 ? "" : mCurrencyManager.get(pReadOnly.getCurrencyId())
        .getNotation());

  }

  @Override
  public void build(final MutableItem pMutable, final JsonObject pJsonObject, final LocalCache pLocalCache) {

    // pMutable.setId(pJsonObject.get("title"));

    pMutable.setMfn(Long.valueOf(pJsonObject.getString("mfnNo")));
    pMutable
        .setCopyNumber(pJsonObject.containsKey("copyNumber") ? pJsonObject.get("copyNumber").getValueType() == JsonValue.ValueType.NUMBER ? pJsonObject
            .getInt("copyNumber") : Integer.valueOf(pJsonObject.getString("copyNumber"))
            : null);
    pMutable.setAccessionNumber(pJsonObject.getString("accessionNumber"));
    pMutable.setAccessionDate(pJsonObject.containsKey("accessionDate") ? pJsonObject.getString("accessionDate") : null);

    if(pJsonObject.containsKey("supplier")) {
      JsonObject supplierObject = (JsonObject) (pJsonObject.get("supplier"));
      if(!supplierObject.getString("id").equals("")) {
        pMutable.setSupplierId(Long.valueOf(supplierObject.getString("id")));
      }
      else {
        pMutable.setSupplierId(null);
      }
    }

    if(pJsonObject.containsKey("barcode"))
      pMutable.setBarcode(pJsonObject.getString("barcode"));
    pMutable
        .setPrice(pJsonObject.containsKey("price") ? pJsonObject.get("price").getValueType() == JsonValue.ValueType.NUMBER ? pJsonObject
            .getInt("price") : Double.valueOf(pJsonObject.getString("price"))
            : null);
    pMutable.setInternalNote(pJsonObject.containsKey("internalNote") ? pJsonObject.getString("internalNote") : null);
    // pMutable.setSupplier(pJsonObject.getInt("copyNumber"));
    // pMutable.setSupplierId(pJsonObject.getInt("supplier"));
    pMutable.setStatus(pJsonObject.containsKey("status") ? ItemStatus.get(pJsonObject.getInt("status"))
        : ItemStatus.AVAILABLE);

    pMutable.setCirculationStatus(pJsonObject.containsKey("circulationStatus") ? pJsonObject
        .getInt("circulationStatus") : 0);

    pMutable.setCurrencyId(pJsonObject.get("currency").getValueType() == JsonValue.ValueType.NUMBER ? pJsonObject
        .getInt("currency") : Long.parseLong(pJsonObject.getString("currency")));

    pMutable.setAcquisitionType(pJsonObject.getInt("acqType") == 101101 ? null : AcquisitionType.get(pJsonObject
        .getInt("acqType")));
  }

}
