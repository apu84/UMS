package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableProgram;
import org.ums.domain.model.readOnly.Program;
import org.ums.manager.ProgramManager;

public class ProgramDaoDecorator extends ContentDaoDecorator<Program, MutableProgram, Integer, ProgramManager> implements ProgramManager {
}
