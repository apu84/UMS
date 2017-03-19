package org.ums.solr.indexer.model;

import java.io.Serializable;
import java.util.Date;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;

public interface IndexConsumer extends Serializable, EditType<MutableIndexConsumer>, LastModifier, Identifier<Long> {

  String getHost();

  String getInstance();

  Date getHead();

  Date getLastChecked();
}
