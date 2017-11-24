package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.AcademicDegree;
import org.ums.domain.model.mutable.common.MutableAcademicDegree;
import org.ums.manager.common.AcademicDegreeManager;

public class AcademicDegreeDaoDecorator extends
    ContentDaoDecorator<AcademicDegree, MutableAcademicDegree, Integer, AcademicDegreeManager> implements
    AcademicDegreeManager {
}
