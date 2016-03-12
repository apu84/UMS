package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.domain.model.readOnly.ProgramType;
import org.ums.manager.ProgramTypeManager;

public class ProgramTypeDaoDecorator extends ContentDaoDecorator<ProgramType, MutableProgramType, Integer, ProgramTypeManager> implements ProgramTypeManager {
}
