package org.ums.services.academic.routine;

import com.itextpdf.text.DocumentException;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.stereotype.Service;
import org.ums.enums.ProgramType;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 20-Sep-18.
 */
public interface RoutineReportService {
  void createTeachersRoutine(String pTeachersId, Integer pSemesterId, OutputStream pOutputStream)
      throws InvalidObjectException, DocumentException, IOException;

  void createSemesterWiseRoutine(Integer pSemesterId, Integer pProgramId, Integer pYear, Integer pSemester,
      String pSection, OutputStream pOutputStream) throws InvalidObjectException, DocumentException, IOException;

  void createRoomBasedRoutine(Long pRoomId, Integer pSemesterId, OutputStream pOutputStream)
      throws InvalidObjectException, DocumentException, IOException;

  void createRoutineTemplate(Integer pSemesterId, ProgramType pProgramType, OutputStream pOutputStream)
      throws Exception;
}
