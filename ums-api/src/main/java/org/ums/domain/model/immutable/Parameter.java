package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableParameter;

import java.io.Serializable;

/**
 * Created by My Pc on 3/13/2016.
 */
public interface Parameter extends Serializable,LastModifier,EditType<MutableParameter>,Identifier<String> {
  String getParameter();
  String getShortDescription();
  String getLongDescription();
  int getType();
}
