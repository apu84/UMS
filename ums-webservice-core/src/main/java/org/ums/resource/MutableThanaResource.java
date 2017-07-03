package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.common.Thana;
import org.ums.domain.model.mutable.common.MutableThana;

public class MutableThanaResource extends Resource {

  @Autowired
  ResourceHelper<Thana, MutableThana, Integer> mHelper;

}
