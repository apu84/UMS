package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.AreaOfInterest;
import org.ums.domain.model.mutable.MutableAreaOfInterest;

public class MutableAreaOfInterestResource extends Resource {

  @Autowired
  ResourceHelper<AreaOfInterest, MutableAreaOfInterest, Integer> mHelper;
}
