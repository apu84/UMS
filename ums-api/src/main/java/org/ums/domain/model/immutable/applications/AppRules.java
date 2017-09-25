package org.ums.domain.model.immutable.applications;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.applications.MutableAppRules;
import org.ums.fee.FeeCategory;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 21-Sep-17.
 */
public interface AppRules extends Serializable, EditType<MutableAppRules>, LastModifier, Identifier<Long> {
  FeeCategory getFeeCategory();

  FeeCategory getDependentFeeCategory();
}
