package org.ums.services.academic;

import org.ums.domain.model.immutable.TaskStatus;
import org.ums.response.type.GenericResponse;

public interface ProcessResult {
  void process(int pProgramId, final int pSemesterId) throws Exception;

  GenericResponse<TaskStatus> status(int pProgramId, final int pSemesterId) throws Exception;
}
