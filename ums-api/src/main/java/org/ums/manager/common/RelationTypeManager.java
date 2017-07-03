package org.ums.manager.common;

import org.ums.domain.model.immutable.common.RelationType;
import org.ums.domain.model.mutable.common.MutableRelationType;
import org.ums.manager.ContentManager;

public interface RelationTypeManager extends ContentManager<RelationType, MutableRelationType, Integer> {
}
