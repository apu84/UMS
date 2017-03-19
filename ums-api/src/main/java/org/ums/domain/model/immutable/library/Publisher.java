package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.mutable.library.MutablePublisher;
import org.ums.domain.model.mutable.library.MutableSupplier;

import java.io.Serializable;

/**
 * Created by Ifti on 04-Feb-17.
 */
public interface Publisher extends Serializable, LastModifier, EditType<MutablePublisher>, Identifier<Integer> {

  String getName();

  int getCountryId();

  Country getCountry();

}
