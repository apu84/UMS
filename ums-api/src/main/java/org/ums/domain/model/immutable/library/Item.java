package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.library.MutableItem;
import org.ums.enums.library.ItemStatus;

import java.io.Serializable;

/**
 * Created by Ifti on 04-Mar-17.
 */
public interface Item extends Serializable, EditType<MutableItem>, LastModifier, Identifier<Long> {

  Long getMfn();

  Integer getCopyNumber();

  String getAccessionNumber();

  String getAccessionDate();

  String getBarcode();

  Double getPrice();

  String getInternalNote();

  Supplier getSupplier();

  Integer getSupplierId();

  ItemStatus getStatus();

  String getInsertedBy();

  String getInsertedOn();

  String getLastUpdatedBy();

  String getLastUpdatedOn();

}
