package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableAppSetting;

import java.io.Serializable;

/**
 * Created by My Pc on 30-Aug-16.
 */
public interface AppSetting extends Serializable,LastModifier,EditType<MutableAppSetting>, Identifier<Integer>{
  String getParameterName();
  String getParameterValue();
  String getDescription();
  String getDataType();
}