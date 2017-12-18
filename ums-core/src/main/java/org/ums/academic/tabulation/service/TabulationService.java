package org.ums.academic.tabulation.service;

import org.ums.academic.tabulation.model.TabulationReportModel;

public interface TabulationService {
  TabulationReportModel getTabulation(int pProgramId, int pSemesterId, int pYear, int pAcademicSemester);
}
