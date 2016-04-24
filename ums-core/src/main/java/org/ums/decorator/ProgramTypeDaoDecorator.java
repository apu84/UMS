package org.ums.decorator;

import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.domain.model.immutable.ProgramType;
import org.ums.manager.ProgramTypeManager;

public class ProgramTypeDaoDecorator extends ContentDaoDecorator<ProgramType, MutableProgramType, Integer, ProgramTypeManager> implements ProgramTypeManager {
}
