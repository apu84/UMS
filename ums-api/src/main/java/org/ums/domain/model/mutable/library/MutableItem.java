package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.accounts.Currency;
import org.ums.domain.model.immutable.library.Item;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.library.AcquisitionType;
import org.ums.enums.library.BookBindingType;
import org.ums.enums.library.ItemStatus;

/**
 * Created by Ifti on 04-Mar-17.
 */
public interface MutableItem extends Item, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setMfn(Long pMfn);

  void setCopyNumber(Integer pCopyNumber);

  void setAccessionNumber(String pAccessionNumber);

  void setAccessionDate(String pAccessionDate);

  void setBarcode(String pBarcode);

  void setPrice(Double pPrice);

  void setInternalNote(String pInternalNote);

  void setSupplier(Supplier pSupplier);

  void setSupplierId(Long pSupplierId);

  void setStatus(ItemStatus pStatus);

  void setInsertedBy(String pInsertedBy);

  void setInsertedOn(String pInsertedOn);

  void setLastUpdatedBy(String pUpdatedBy);

  void setLastUpdatedOn(String pLastUpdatedOn);

  void setCirculationStatus(int pCirculationStatus);

  void setCurrency(Currency pCurrency);

  void setAcquisitionType(AcquisitionType pAcquisitionType);

  void setCurrencyId(Long pCurrencyId);

  void setBookBindingType(BookBindingType pBookBindingType);
}
