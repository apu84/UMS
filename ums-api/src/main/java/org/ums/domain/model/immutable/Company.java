package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableCompany;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 28-Jan-18.
 */
public interface Company extends Serializable, EditType<MutableCompany>, LastModifier, Identifier<String> {

  String getName();

  String getShortName();
}
