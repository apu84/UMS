package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableDistrict;

import java.io.Serializable;

public interface District extends Serializable, LastModifier, EditType<MutableDistrict>, Identifier<String> {

  String getDistrictId();

  String getDivisionId();

  String getDistrictName();
}
