package org.ums.domain.model.mutable.applications;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.applications.AppRules;
import org.ums.domain.model.mutable.MutableLastModifier;

/**
 * Created by Monjur-E-Morshed on 21-Sep-17.
 */
public interface MutableAppRules extends AppRules, Editable<Long>, MutableLastModifier, MutableIdentifier<Long> {
  void setFeeCategoryId(String pFeeCategoryId);

  void setDependentFeeCategoryId(String pDependentFeeCategoryId);
}
