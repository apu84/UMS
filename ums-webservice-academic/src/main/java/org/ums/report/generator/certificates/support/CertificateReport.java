package org.ums.report.generator.certificates.support;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import org.ums.fee.FeeCategory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 07-Nov-17.
 */
@Component
public class CertificateReport {
  public void createGradeSheetPdf(FeeCategory pFeeCategory, String pStudentId, Integer pSemesterId,
      OutputStream pOutputStream) throws IOException, DocumentException {
    Document document = new Document();
    document.addTitle("Semester Final GradeSheet");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    document.open();
    document.setPageSize(PageSize.A4);

    Paragraph paragraph = new Paragraph("This is dummy Certificate/Provisional Certificate/ Duplicate Certificate");
    paragraph.setAlignment(Element.ALIGN_CENTER);

    document.add(paragraph);

    document.close();

    baos.writeTo(pOutputStream);
  }
}
