package org.ums.indexer.model;

import java.io.Serializable;
import java.util.Date;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.LastModifier;

public interface Index extends Serializable, EditType<MutableIndex>, LastModifier, Identifier<Long> {

  String getEntityId();

  String getEntityType();

  Boolean getIsDeleted();

  Date getModified();
}
