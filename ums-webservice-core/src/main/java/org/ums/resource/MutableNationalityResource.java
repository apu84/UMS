package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.common.Nationality;
import org.ums.domain.model.mutable.common.MutableNationality;

public class MutableNationalityResource extends Resource {

  @Autowired
  ResourceHelper<Nationality, MutableNationality, Integer> mHelper;
}
