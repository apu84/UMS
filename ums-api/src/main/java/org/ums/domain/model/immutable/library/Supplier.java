package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.library.MutableSupplier;

import java.io.Serializable;

/**
 * Created by Ifti on 04-Feb-17.
 */
public interface Supplier extends Serializable, LastModifier, EditType<MutableSupplier>,
    Identifier<Long> {

  String getName();

  String getEmail();

  String getContactPerson();

  String getContactNumber();

  String getAddress();

  String getNote();

}
