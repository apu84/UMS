package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.common.Religion;
import org.ums.domain.model.mutable.common.MutableReligion;

public class MutableReligionResource extends Resource {

  @Autowired
  ResourceHelper<Religion, MutableReligion, Integer> mHelper;
}
