package org.ums.decorator;

import org.ums.domain.model.mutable.MutableProgram;
import org.ums.domain.model.immutable.Program;
import org.ums.manager.ProgramManager;

import java.util.List;

public class ProgramDaoDecorator extends ContentDaoDecorator<Program, MutableProgram, Integer, ProgramManager>
    implements ProgramManager {
  @Override
  public List<Program> getProgramByDepartmentId(String pDepartmentId) {
    return getManager().getProgramByDepartmentId(pDepartmentId);
  }
}
