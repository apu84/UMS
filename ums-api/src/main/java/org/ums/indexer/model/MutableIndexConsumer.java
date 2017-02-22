package org.ums.indexer.model;

import java.util.Date;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableIndexConsumer extends IndexConsumer, Editable<Long>,
    MutableIdentifier<Long>, MutableLastModifier {

  void setHost(String pHost);

  void setInstance(String pInstance);

  void setHead(Long pHead);

  void setLastChecked(Date pLastChecked);
}
