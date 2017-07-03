package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.common.BloodGroup;
import org.ums.domain.model.mutable.common.MutableBloodGroup;

public class MutableBloodGroupResource extends Resource {

  @Autowired
  ResourceHelper<BloodGroup, MutableBloodGroup, Integer> mHelper;
}
