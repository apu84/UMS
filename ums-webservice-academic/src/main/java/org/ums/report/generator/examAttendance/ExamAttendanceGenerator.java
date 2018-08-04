package org.ums.report.generator.examAttendance;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 6/26/2018.
 */
public interface ExamAttendanceGenerator {
  void createTestimonial(Integer pSemesterId, Integer pExamType, String pExamDate, OutputStream pOutputStream)
      throws IOException, DocumentException;

}
