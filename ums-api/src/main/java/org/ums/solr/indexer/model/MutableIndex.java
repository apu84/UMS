package org.ums.solr.indexer.model;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import java.util.Date;

public interface MutableIndex extends Index, Mutable, MutableIdentifier<Long>, MutableLastModifier {

  void setEntityId(String pEntityId);

  void setEntityType(String pEntityType);

  void setIsDeleted(Boolean pIsDeleted);

  void setModified(Date pModified);
}
