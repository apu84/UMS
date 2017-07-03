package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.common.MaritalStatus;
import org.ums.domain.model.mutable.common.MutableMaritalStatus;

public class MutableMaritalStatusResource extends Resource {

  @Autowired
  ResourceHelper<MaritalStatus, MutableMaritalStatus, Integer> mHelper;
}
