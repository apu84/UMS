package org.ums.report.generator.certificates;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 07-Nov-17.
 */
public interface CertificateReportGenerator {
  void createReport(String pFeeCategoryId, String pStudentId, Integer pSemesterId, OutputStream pOutputStream)
      throws IOException, DocumentException;
}
