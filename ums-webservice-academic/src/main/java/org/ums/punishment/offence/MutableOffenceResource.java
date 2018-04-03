package org.ums.punishment.offence;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;
import org.ums.resource.ResourceHelper;

public class MutableOffenceResource extends Resource {

  @Autowired
  ResourceHelper<Offence, MutableOffence, Long> mHelper;
}
