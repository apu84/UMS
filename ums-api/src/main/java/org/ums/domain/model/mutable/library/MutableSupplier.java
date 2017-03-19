package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Ifti on 04-Feb-17.
 */

public interface MutableSupplier extends Supplier, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setName(final String pName);

  void setEmail(final String pEmail);

  void setContactPerson(final String pContactPerson);

  void setContactNumber(final String pContactNumber);

  void setAddress(final String pAddress);

  void setNote(final String pNote);
}
