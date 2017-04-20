package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableThana;

import java.io.Serializable;

public interface Thana extends Serializable, LastModifier, EditType<MutableThana>, Identifier<String> {

  String getThanaId();

  String getDistrictId();

  String getThanaName();
}
