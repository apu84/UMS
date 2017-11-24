package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableAcademicDegree;

import java.io.Serializable;

public interface AcademicDegree extends Serializable, LastModifier, EditType<MutableAcademicDegree>,
    Identifier<Integer> {

  int getDegreeType();

  String getDegreeName();
}
