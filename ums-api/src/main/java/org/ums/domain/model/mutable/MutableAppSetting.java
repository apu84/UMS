package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.AppSetting;

/**
 * Created by My Pc on 30-Aug-16.
 */
public interface MutableAppSetting extends AppSetting, Mutable, MutableLastModifier,
    MutableIdentifier<Integer> {
  void setParameterName(final String pParameterName);

  void setParameterValue(final String pParameterValue);

  void setParameterDescription(final String pParameterDescription);

  void setDataType(final String pDataType);
}
