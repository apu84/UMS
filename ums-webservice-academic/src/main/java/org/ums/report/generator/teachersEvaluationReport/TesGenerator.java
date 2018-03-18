package org.ums.report.generator.teachersEvaluationReport;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 3/15/2018.
 */
public interface TesGenerator {
  void createTesReport(String pCourseId, String pTeacherId, Integer pSemesterId, OutputStream pOutputStream)
      throws IOException, DocumentException;
}
