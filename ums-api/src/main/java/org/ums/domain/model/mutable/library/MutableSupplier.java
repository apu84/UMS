package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.immutable.library.Supplier;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Ifti on 04-Feb-17.
 */

public interface MutableSupplier extends Supplier, Mutable, MutableLastModifier,
    MutableIdentifier<Integer> {

  void setName(final String pName);

  void setAddress(final String pAddress);

  void setContactPerson(final String pContactPerson);

  void setContactNumber(final String pContactNumber);

}
