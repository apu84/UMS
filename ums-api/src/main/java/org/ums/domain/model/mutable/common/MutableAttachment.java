package org.ums.domain.model.mutable.common;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.Attachment;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.ApplicationType;

/**
 * Created by Monjur-E-Morshed on 11-Jul-17.
 */
public interface MutableAttachment extends Attachment, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setApplicationType(ApplicationType pApplicationType);

  void setApplicationId(String pApplicationId);

  void setFileName(String pFileName);
}
