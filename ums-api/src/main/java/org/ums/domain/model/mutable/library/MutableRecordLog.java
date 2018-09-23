package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.library.RecordLog;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutableRecordLog extends RecordLog, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {

  void setMfn(final Long pMfn);

  void setModifiedBy(final String pModifiedBy);

  void setModifiedOn(final Date pModifiedOn);

  void setModificationType(final Integer pModificationType);

  void setPreviousJson(final String pPreviousJson);

  void setModifiedJson(final String pModifiedJson);
}
