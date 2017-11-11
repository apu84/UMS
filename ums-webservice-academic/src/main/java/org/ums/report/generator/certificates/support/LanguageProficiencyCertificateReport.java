package org.ums.report.generator.certificates.support;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.Student;
import org.ums.fee.FeeCategory;
import org.ums.fee.certificate.CertificateStatus;
import org.ums.fee.certificate.CertificateStatusManager;
import org.ums.manager.SemesterManager;
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
public class LanguageProficiencyCertificateReport {

  @Autowired
  CertificateStatusManager mCertificateStatusManager;
  @Autowired
  StudentManager mStudentManager;
  @Autowired
  SemesterManager mSemesterManager;

  public void createLanguageProficiencyCertificateReport(FeeCategory pFeeCategory, String pStudentId,
      Integer pSemesterId, OutputStream pOutputStream) throws IOException, DocumentException {
    Document document = new Document();
    document.addTitle("Language Proficiency Certificate");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    document.open();
    document.setPageSize(PageSize.A4);

    Paragraph paragraph = new Paragraph(" ");
    document.add(paragraph);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    int certificateNumber =
        mCertificateStatusManager.getByStatusAndFeeCategory(CertificateStatus.Status.DELIVERED, pFeeCategory).size()
            + mCertificateStatusManager.getByStatusAndFeeCategory(CertificateStatus.Status.PROCESSED, pFeeCategory)
                .size() + 1;

    PdfPTable table = new PdfPTable(2);
    PdfPCell cellOne = new PdfPCell();
    paragraph =
        new Paragraph("No. AUST/Reg. Offi/MI/R-" + certificateNumber, FontFactory.getFont(FontFactory.TIMES, 12));
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cellOne.addElement(paragraph);
    cellOne.setBorder(Rectangle.NO_BORDER);

    Date currentDate = new Date();
    PdfPCell cellTwo = new PdfPCell();
    Format formatter = new SimpleDateFormat("dd MMMM,YYYY");
    paragraph = new Paragraph("" + formatter.format(currentDate), FontFactory.getFont(FontFactory.TIMES, 12));
    paragraph.setAlignment(Element.ALIGN_RIGHT);
    cellTwo.addElement(paragraph);
    cellTwo.setBorder(Rectangle.NO_BORDER);

    table.addCell(cellOne);
    table.addCell(cellTwo);
    table.setWidthPercentage(100);
    document.add(table);

    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    paragraph =
        new Paragraph("TO WHOM IT MAY CONCERN", FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.UNDERLINE));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    document.add(paragraph);

    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    Student student = mStudentManager.get(pStudentId);
    String paraString = "As per office record, ";

    String durationYear = student.getProgram().getId() == 110100 ? "five" : "four";

    Phrase phrase = new Phrase();
    phrase.add(new Paragraph("As per office record, ", FontFactory.getFont(FontFactory.TIMES, 15)));
    phrase.add(new Paragraph("" + student.getFullName(), FontFactory.getFont(FontFactory.TIMES_BOLD, 15)));
    phrase.add(new Paragraph(", bearing Student No. ", FontFactory.getFont(FontFactory.TIMES, 15)));
    phrase.add(new Paragraph("" + student.getId(), FontFactory.getFont(FontFactory.TIMES_BOLD, 15)));
    phrase.add(new Paragraph(" studied in this University from " + student.getSemester().getName() + " to "
        + student.getCurrentEnrolledSemester().getName() + " and the duration of his degree program is of "
        + durationYear + " years. ", FontFactory.getFont(FontFactory.TIMES, 15)));
    phrase.add(new Paragraph("The medium of his study was English.", FontFactory.getFont(FontFactory.TIMES_BOLD, 15)));

    paragraph = new Paragraph();
    paragraph.add(phrase);
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    document.add(paragraph);

    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);
    document.add(Chunk.NEWLINE);

    PdfPTable signTable = new PdfPTable(2);
    cellOne = new PdfPCell();
    cellOne.addElement(new Paragraph(" "));
    cellOne.setBorder(Rectangle.NO_BORDER);
    signTable.addCell(cellOne);

    cellTwo = new PdfPCell();
    paragraph = new Paragraph("(Md. Muniruzzaman)", FontFactory.getFont(FontFactory.TIMES_BOLD, 15));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cellTwo.addElement(paragraph);
    paragraph = new Paragraph("Deputy Registrar", FontFactory.getFont(FontFactory.TIMES, 15));
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cellTwo.addElement(paragraph);
    cellTwo.setBorder(Rectangle.NO_BORDER);

    signTable.addCell(cellTwo);
    document.add(signTable);
    document.close();

    baos.writeTo(pOutputStream);
  }
}
