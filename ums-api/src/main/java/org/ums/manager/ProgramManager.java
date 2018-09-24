package org.ums.manager;

import org.ums.domain.model.mutable.MutableProgram;
import org.ums.domain.model.immutable.Program;

import java.util.List;

public interface ProgramManager extends ContentManager<Program, MutableProgram, Integer> {
  List<Program> getProgramByDepartmentId(final String pDepartmentId);
}
