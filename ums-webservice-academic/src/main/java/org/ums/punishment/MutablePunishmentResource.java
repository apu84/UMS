package org.ums.punishment;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;
import org.ums.resource.ResourceHelper;

public class MutablePunishmentResource extends Resource {

  @Autowired
  ResourceHelper<Punishment, MutablePunishment, Long> mHelper;
}
