package org.ums.report.generator.certificates;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.manager.StudentManager;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Monjur-E-Morshed on 14-Sep-17.
 */
@Service
public class CharacterCertificateImpl implements CharacterCertificate {

  @Autowired
  StudentManager mStudentManager;

  public static String filePath = "I:/character_certificate.pdf";

  public void generateCharacterCertificate(String pStudentId) throws IOException, DocumentException {
    Document document = new Document();
    document.addTitle("Character Certificate");
    // ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
    document.open();
    document.setPageSize(PageSize.A4);
    document.newPage();

    Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 16.0f, Font.BOLD, BaseColor.BLACK);

    Paragraph paragraph = new Paragraph("Character Certificate", headerFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);

    document.add(paragraph);

    document.close();
    // baos.writeTo(pOutputStream);

  }
}
