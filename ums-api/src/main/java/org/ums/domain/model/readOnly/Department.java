package org.ums.domain.model.readOnly;


import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableDepartment;

import java.io.Serializable;

public interface Department extends Serializable, EditType<MutableDepartment>, LastModifier, Identifier<String> {
  String getShortName() throws Exception;

  String getLongName() throws Exception;

  int getType() throws Exception;
}
