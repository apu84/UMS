package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.EmploymentType;
import org.ums.domain.model.mutable.MutableEmploymentType;
import org.ums.resource.helper.EmploymentTypeResourceHelper;

public class MutableEmploymentTypeResource extends Resource {

  @Autowired
  ResourceHelper<EmploymentType, MutableEmploymentType, Integer> mHelper;
}
