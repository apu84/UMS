package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.common.AcademicDegree;
import org.ums.domain.model.mutable.common.MutableAcademicDegree;

public class MutableAcademicDegreeResource extends Resource {

  @Autowired
  ResourceHelper<AcademicDegree, MutableAcademicDegree, Integer> mHelper;
}
