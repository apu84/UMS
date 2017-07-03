package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.common.RelationType;
import org.ums.domain.model.mutable.common.MutableRelationType;

public class MutableRelationTypeResource extends Resource {

  @Autowired
  ResourceHelper<RelationType, MutableRelationType, Integer> mHelper;
}
