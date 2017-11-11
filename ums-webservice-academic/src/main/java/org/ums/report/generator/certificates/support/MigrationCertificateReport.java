package org.ums.report.generator.certificates.support;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Student;
import org.ums.fee.FeeCategory;
import org.ums.fee.certificate.CertificateStatus;
import org.ums.fee.certificate.CertificateStatusManager;
import org.ums.manager.StudentManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 09-Nov-17.
 */
@Component
public class MigrationCertificateReport {
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  CertificateStatusManager mCertificateStatusManager;

  public void createMigrationCertificatePdf(FeeCategory pFeeCategory, String pStudentId, Integer pSemesterId,
      OutputStream pOutputStream) throws IOException, DocumentException {
    Document document = new Document();
    document.addTitle("Migration Certificate");

    float indentionValue = 30.0f;

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    document.open();
    document.setPageSize(PageSize.A4);

    document.add(new Paragraph(" "));
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    int certificateNo =
        mCertificateStatusManager.getByStatusAndFeeCategory(CertificateStatus.Status.DELIVERED, pFeeCategory).size();
    certificateNo =
        certificateNo
            + mCertificateStatusManager.getByStatusAndFeeCategory(CertificateStatus.Status.PROCESSED, pFeeCategory)
                .size() + 1;
    Paragraph paragraph = new Paragraph("No. " + certificateNo, FontFactory.getFont(FontFactory.TIMES_ITALIC, 13));
    paragraph.setAlignment(Element.ALIGN_LEFT);
    paragraph.setIndentationLeft(indentionValue);
    document.add(paragraph);

    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    paragraph =
        new Paragraph("AHSANULLAH UNIVERSITY OF SCIENCE AND TECHNOLOGY",
            FontFactory.getFont(FontFactory.TIMES_BOLD, 15));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    paragraph = new Paragraph("MIGRATION CERTIFICATE", FontFactory.getFont(FontFactory.TIMES_BOLD, 17));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    Student student = mStudentManager.get(pStudentId);
    String heOrShe = student.getGender().equals("M") ? "he" : "she";
    String hisOrHer = student.getGender().equals("M") ? "his" : "her";

    Phrase phrase = new Phrase();
    phrase
        .add(new Paragraph("With reference to the application of ", FontFactory.getFont(FontFactory.TIMES_ITALIC, 13)));
    phrase.add(new Paragraph("" + student.getFullName(), FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 13)));
    phrase.add(new Paragraph(" ,", FontFactory.getFont(FontFactory.TIMES_ITALIC, 13)));
    phrase.add(new Paragraph(" Student No: " + student.getId(), FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 13)));
    phrase.add(new Paragraph(" of ", FontFactory.getFont(FontFactory.TIMES_ITALIC, 13)));
    phrase.add(new Paragraph("" + student.getProgram().getLongName(), FontFactory.getFont(FontFactory.TIMES_BOLDITALIC,
        13)));
    phrase.add(new Paragraph(" for migration certificate, " + heOrShe
        + " is here by informed that Ahsanullah University of Science and Technology has no objection to " + hisOrHer
        + " leaving this University for pursuing further study in other instution.", FontFactory.getFont(
        FontFactory.TIMES_ITALIC, 13)));

    paragraph = new Paragraph(phrase);
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    paragraph.setIndentationLeft(indentionValue);
    paragraph.setIndentationRight(indentionValue);
    document.add(paragraph);

    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    paragraph = new Paragraph("PERMANENT CAMPUS", FontFactory.getFont(FontFactory.TIMES_ITALIC, 10));
    paragraph.setIndentationLeft(indentionValue);
    document.add(paragraph);
    paragraph =
        new Paragraph("AHSANULLAH UNIVERSITY OF SCIENCE AND TECHNOLOGY", FontFactory.getFont(FontFactory.TIMES_ITALIC,
            10));
    paragraph.setIndentationLeft(indentionValue);
    document.add(paragraph);
    paragraph =
        new Paragraph("141-142 LOVE ROAD, TEJGAON INDUSTRIAL AREA, DHAKA-1208.", FontFactory.getFont(
            FontFactory.TIMES_ITALIC, 10));
    paragraph.setIndentationLeft(indentionValue);
    document.add(paragraph);

    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    PdfPTable signTable = new PdfPTable(2);
    PdfPCell cell = new PdfPCell();
    paragraph = new Paragraph("______________________");
    cell.addElement(paragraph);
    paragraph = new Paragraph("Prepared by:", FontFactory.getFont(FontFactory.TIMES_ITALIC, 13));
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    signTable.addCell(cell);

    cell = new PdfPCell();
    paragraph = new Paragraph("_____________________________");
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    paragraph = new Paragraph("Registrar", FontFactory.getFont(FontFactory.TIMES_ITALIC, 13));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    Date currentDate = new Date();
    Format formatter = new SimpleDateFormat("dd MMMM,YYYY");
    paragraph =
        new Paragraph("Date of Issue: " + formatter.format(currentDate), FontFactory.getFont(FontFactory.TIMES_ITALIC,
            13));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    cell.setBorder(Rectangle.NO_BORDER);
    signTable.addCell(cell);

    signTable.setWidthPercentage(90);
    document.add(signTable);

    document.close();

    baos.writeTo(pOutputStream);
  }
}
