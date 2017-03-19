package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Program;
import org.ums.domain.model.immutable.ResultPublish;
import org.ums.domain.model.immutable.Semester;

import java.util.Date;

public interface MutableResultPublish extends ResultPublish, Mutable, MutableLastModifier, MutableIdentifier<Long> {

  void setSemesterId(Integer pSemesterId);

  void setSemester(Semester pSemester);

  void setProgramId(Integer pProgramId);

  void setProgram(Program pProgram);

  void setPublishDate(Date pPublishDate);
}
