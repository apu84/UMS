package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.RelationType;
import org.ums.domain.model.mutable.common.MutableRelationType;
import org.ums.manager.common.RelationTypeManager;

public class RelationTypeDaoDecorator extends
    ContentDaoDecorator<RelationType, MutableRelationType, Integer, RelationTypeManager> implements RelationTypeManager {
}
