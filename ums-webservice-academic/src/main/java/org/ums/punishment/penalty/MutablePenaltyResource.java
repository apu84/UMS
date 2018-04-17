package org.ums.punishment.penalty;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.Resource;
import org.ums.resource.ResourceHelper;

public class MutablePenaltyResource extends Resource {

  @Autowired
  ResourceHelper<Penalty, MutablePenalty, Long> mHelper;
}
