package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.mutable.MutableFaculty;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 06-Dec-16.
 */
public interface Faculty extends Serializable, EditType<MutableFaculty>, LastModifier,
    Identifier<Integer> {
  String getLongName();

  String getShortName();
}
