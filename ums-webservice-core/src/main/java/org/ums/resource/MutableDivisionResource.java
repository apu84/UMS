package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.common.Division;
import org.ums.domain.model.mutable.common.MutableDivision;

public class MutableDivisionResource extends Resource {

  @Autowired
  ResourceHelper<Division, MutableDivision, Integer> mHelper;

}
