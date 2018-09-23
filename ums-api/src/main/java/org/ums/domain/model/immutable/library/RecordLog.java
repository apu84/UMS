package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.library.MutableRecordLog;

import java.io.Serializable;
import java.util.Date;

public interface RecordLog extends Serializable, LastModifier, EditType<MutableRecordLog>, Identifier<Long> {

  Long getMfn();

  String getModifiedBy();

  Date getModifiedOn();

  Integer getModificationType();

  String getPreviousJson();

  String getModifiedJson();
}
