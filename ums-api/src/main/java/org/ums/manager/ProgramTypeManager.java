package org.ums.manager;

import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.domain.model.immutable.ProgramType;

public interface ProgramTypeManager extends
    ContentManager<ProgramType, MutableProgramType, Integer> {
}
