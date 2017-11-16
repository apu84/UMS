package org.ums.report.generator.certificates.support;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.fee.FeeCategory;
import org.ums.fee.certificate.CertificateStatusManager;
import org.ums.manager.StudentManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Monjur-E-Morshed on 11-Nov-17.
 */
@Component
public class CharacterCertificateReport {

  @Autowired
  CertificateStatusManager mCertificateStatusManager;
  @Autowired
  StudentManager mStudentManager;

  public void createCharacterCertificateReport(FeeCategory pFeeCategory, String pStudentId, Integer pSemesterId,
      OutputStream pOutputStream) throws IOException, DocumentException {

    float indentionValue = 30.0f;
    Document document = new Document();
    document.addTitle("Character Certificate Report");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    document.open();
    document.setPageSize(PageSize.A4);

    Paragraph paragraph = new Paragraph("");
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    document.close();

    baos.writeTo(pOutputStream);

  }

}
