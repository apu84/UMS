package org.ums.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.AdmissionAllTypesOfCertificate;
import org.ums.domain.model.immutable.AdmissionCertificatesOfStudent;
import org.ums.domain.model.immutable.AdmissionStudent;
import org.ums.enums.ProgramType;
import org.ums.manager.AdmissionAllTypesOfCertificateManager;
import org.ums.manager.AdmissionCertificatesOfStudentManager;
import org.ums.manager.AdmissionStudentManager;
import org.ums.manager.ProgramManager;

import java.io.*;
import java.util.*;
import java.util.List;

@Component
public class UndertakenFormGeneratorImpl implements UndertakenFormGenerator {

  @Autowired
  AdmissionStudentManager mAdmissionStudentManager;

  @Autowired
  AdmissionAllTypesOfCertificateManager mAdmissionAllTypesOfCertificateManager;

  @Autowired
  AdmissionCertificatesOfStudentManager mAdmissionCertificatesOfStudentManager;

  @Autowired
  ProgramManager mProgramManager;

  public static final String DEST = "UndertakenForm.pdf";

  @Override
  public void createUndertakenForm(ProgramType pProgramType, int pSemesterId, String pReceiptId,
      OutputStream pOutputStream) throws IOException, DocumentException {

    AdmissionStudent admissionStudent =
        mAdmissionStudentManager.getAdmissionStudent(pSemesterId, pProgramType, pReceiptId);

    String getQuota = admissionStudent.getQuota();
    String quota;
    int getDepartment = admissionStudent.getProgramIdByMerit();
    String department;
    String getUndertakenDeadline = admissionStudent.getUndertakenDeadline();
    String undertakenDeadline;

    if(getDepartment == 0) {
      department = "Unavailable";
    }
    else {
      department = mProgramManager.get(admissionStudent.getProgramIdByMerit()).getShortName();
    }

    if(getQuota.equals("RA") || getQuota.equals("FF")) {
      quota = "GL";
    }
    else {
      quota = getQuota;
    }

    if(getUndertakenDeadline == null || getUndertakenDeadline.equals("")) {
      undertakenDeadline = "Unavailable";
    }
    else {
      undertakenDeadline = getUndertakenDeadline;
    }

    List<AdmissionAllTypesOfCertificate> certificates =
        mAdmissionAllTypesOfCertificateManager.getCertificates(quota);

    List<AdmissionCertificatesOfStudent> savedCertificates =
        mAdmissionCertificatesOfStudentManager.getStudentsSavedCertificateLists(pSemesterId,
            pReceiptId);

    Document document = new Document();
    document.addTitle("UnderTaking Form");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);

    Font universityNameFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);
    Font universityAddressFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);
    Font report_titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
    Font common = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);

    document.open();
    document.setPageSize(PageSize.A4);

    Chunk universityName = new Chunk();
    universityName.append("AHSANULLAH UNIVERSITY OF SCIENCE & TECHNOLOGY");
    Chunk universityAddress = new Chunk();
    universityAddress.append("\n141-142, Love Road, Tejgaon I/A, Dhaka-1208");

    Paragraph header = new Paragraph();
    header.setFont(universityNameFont);
    header.add(universityName);
    header.setFont(universityAddressFont);
    header.add(universityAddress);
    header.setAlignment(Element.ALIGN_CENTER);
    emptyLine(header, 2);
    document.add(header);

    Chunk reportChunk = new Chunk();
    reportChunk.append("UNDERTAKING");
    reportChunk.setUnderline(1.0f, -2.3f);
    Paragraph report_title = new Paragraph();
    report_title.setFont(report_titleFont);
    report_title.add(reportChunk);
    report_title.setAlignment(Element.ALIGN_CENTER);
    emptyLine(report_title, 2);
    document.add(report_title);

    Chunk bodyChunk1 = new Chunk();
    bodyChunk1.append("I ");
    Chunk bodyChunk2 = new Chunk();
    bodyChunk2.append("   " + admissionStudent.getStudentName() + "   ");
    bodyChunk2.setUnderline(0.5f, -2.3f);
    Chunk bodyChunk3 = new Chunk();
    bodyChunk3.append(" merit position ");
    Chunk bodyChunk4 = new Chunk();
    bodyChunk4.append("   " + admissionStudent.getMeritSerialNo().toString() + "   ");
    bodyChunk4.setUnderline(0.5f, -2.3f);
    Chunk bodyChunk5 = new Chunk();
    bodyChunk5.append(" receipt id ");
    Chunk bodyChunk6 = new Chunk();
    bodyChunk6.append("   " + admissionStudent.getReceiptId() + "   ");
    bodyChunk6.setUnderline(0.5f, -2.3f);
    Chunk bodyChunk7 = new Chunk();
    bodyChunk7.append(" selected Department ");
    Chunk bodyChunk8 = new Chunk();
    bodyChunk8.append("   " + department + "   ");
    bodyChunk8.setUnderline(0.5f, -2.3f);
    Chunk bodyChunk9 = new Chunk();
    bodyChunk9
        .append(" do hereby declare that I will produce my following original documents to AUST authority on or before ");
    Chunk bodyChunk10 = new Chunk();
    bodyChunk10.append("   " + undertakenDeadline + "   ");
    bodyChunk10.setUnderline(0.5f, -2.3f);
    Chunk bodyChunk11 = new Chunk();
    bodyChunk11.append(" . Otherwise my admission will be cancelled. ");

    Paragraph bodyText1 = new Paragraph();
    bodyText1.setFont(common);
    bodyText1.add(bodyChunk1);
    bodyText1.add(bodyChunk2);
    bodyText1.add(bodyChunk3);
    bodyText1.add(bodyChunk4);
    bodyText1.add(bodyChunk5);
    bodyText1.add(bodyChunk6);
    bodyText1.add(bodyChunk7);
    bodyText1.add(bodyChunk8);
    bodyText1.add(bodyChunk9);
    bodyText1.add(bodyChunk10);
    bodyText1.add(bodyChunk11);
    bodyText1.setAlignment(Element.ALIGN_LEFT);
    emptyLine(bodyText1, 2);
    document.add(bodyText1);

    Chunk bodyChunk12 = new Chunk();
    bodyChunk12.append("HSC/A-Level:");
    bodyChunk12.setUnderline(0.5f, -2.3f);
    Chunk bodyChunk13 = new Chunk();
    bodyChunk13.append("SSC/O-Level:");
    bodyChunk13.setUnderline(0.5f, -2.3f);

    Paragraph bodyText2 = new Paragraph();
    bodyText2.setFont(common);
    bodyText2.add(bodyChunk12);
    if(savedCertificates.size() == 0) {
      for(int i = 0; i < certificates.size(); i++) {
        if(certificates.get(i).getCertificateTitle().startsWith("HSC")
            || certificates.get(i).getCertificateTitle().startsWith("A-Level")) {
          createCheckbox(bodyText2, 1, certificates.get(i).getCertificateTitle());
          emptyLine(bodyText2, 1);
        }
      }
    }
    else {
      for(int i = 0; i < certificates.size(); i++) {
        for(int j = 0; j < savedCertificates.size(); j++) {
          if(certificates.get(i).getCertificateId() == savedCertificates.get(j).getCertificateId()
              && (certificates.get(i).getCertificateTitle().startsWith("HSC") || certificates
                  .get(i).getCertificateTitle().startsWith("A-Level"))) {
            createCheckbox(bodyText2, 0, certificates.get(i).getCertificateTitle());
            emptyLine(bodyText2, 1);
            break;
          }
          else if(certificates.get(i).getCertificateTitle().startsWith("HSC")
              || certificates.get(i).getCertificateTitle().startsWith("A-Level")) {
            if(j == savedCertificates.size() - 1) {
              createCheckbox(bodyText2, 1, certificates.get(i).getCertificateTitle());
              emptyLine(bodyText2, 1);
            }
          }
        }
      }
    }
    bodyText2.add(bodyChunk13);
    if(savedCertificates.size() == 0) {
      for(int i = 0; i < certificates.size(); i++) {
        if(certificates.get(i).getCertificateTitle().startsWith("SSC")
            || certificates.get(i).getCertificateTitle().startsWith("O-Level")) {
          createCheckbox(bodyText2, 1, certificates.get(i).getCertificateTitle());
          emptyLine(bodyText2, 1);
        }
      }
    }
    else {
      for(int i = 0; i < certificates.size(); i++) {
        for(int j = 0; j < savedCertificates.size(); j++) {
          if(certificates.get(i).getCertificateId() == savedCertificates.get(j).getCertificateId()
              && (certificates.get(i).getCertificateTitle().startsWith("SSC") || certificates
                  .get(i).getCertificateTitle().startsWith("O-Level"))) {
            createCheckbox(bodyText2, 0, certificates.get(i).getCertificateTitle());
            emptyLine(bodyText2, 1);
            break;
          }
          else if(certificates.get(i).getCertificateTitle().startsWith("SSC")
              || certificates.get(i).getCertificateTitle().startsWith("O-Level")) {
            if(j == savedCertificates.size() - 1) {
              createCheckbox(bodyText2, 1, certificates.get(i).getCertificateTitle());
              emptyLine(bodyText2, 1);
            }
          }
        }
      }
    }
    bodyText2.setAlignment(Element.ALIGN_LEFT);
    emptyLine(bodyText2, 1);
    document.add(bodyText2);

    Chunk report_footer1 = new Chunk();
    report_footer1.append("                                                      ");
    report_footer1.setUnderline(0.5f, -2.3f);
    Chunk report_footer2 = new Chunk();
    report_footer2.append("\nProf Dr. S.M.A Al-Mamun");
    Chunk report_footer3 = new Chunk();
    report_footer3.append("\nChairman");
    Chunk report_footer4 = new Chunk();
    report_footer4.append("\nAdmission Committee");
    Chunk report_footer5 = new Chunk();
    report_footer5.append("\nSpring 2017 Semester");
    Chunk report_footer6 = new Chunk();
    report_footer6.append("                                                      ");
    report_footer6.setUnderline(0.5f, -2f);
    Chunk report_footer7 = new Chunk();
    report_footer7.append("\nSignature of the Candidate");
    Chunk report_footer8 = new Chunk();
    report_footer8.append("\n \nDate");
    Chunk report_footer9 = new Chunk();
    report_footer9.append("                                    ");
    report_footer9.setUnderline(0.5f, -2.3f);

    Paragraph rfooter1 = new Paragraph();
    rfooter1.setFont(common);
    emptyLine(rfooter1, 4);
    rfooter1.add(report_footer1);
    rfooter1.add(report_footer2);
    rfooter1.add(report_footer3);
    rfooter1.add(report_footer4);
    rfooter1.add(report_footer5);
    rfooter1.setAlignment(Element.ALIGN_LEFT);
    document.add(rfooter1);

    Paragraph rfooter2 = new Paragraph();
    rfooter2.setFont(common);
    rfooter2.add(report_footer6);
    rfooter2.add(report_footer7);
    rfooter2.add(report_footer8);
    rfooter2.add(report_footer9);
    rfooter2.setAlignment(Element.ALIGN_RIGHT);
    document.add(rfooter2);

    document.close();
    baos.writeTo(pOutputStream);
  }

  void emptyLine(Paragraph p, int number) {
    for(int i = 0; i < number; i++) {
      p.add(new Paragraph(" "));
    }
  }

  void createCheckbox(Paragraph p, int i, String certificateName) throws DocumentException {

    PdfPTable table = new PdfPTable(2);
    table.setHorizontalAlignment(Element.ALIGN_LEFT);
    PdfPCell cell;
    table.setWidths(new float[] {0.4f, 5.0f});
    cell = new PdfPCell();
    cell.setCellEvent(new CheckboxCellEvent("cb" + i, i));
    cell.setBorder(Rectangle.BOX);
    table.addCell(cell);
    cell = new PdfPCell(new Paragraph(certificateName));
    cell.setBorder(Rectangle.NO_BORDER);
    table.addCell(cell);
    table.completeRow();
    p.add(table);
  }
}


class CheckboxCellEvent implements PdfPCellEvent {
  protected String name;
  protected int i;

  public CheckboxCellEvent(String name, int i) {
    this.name = name;
    this.i = i;
  }

  public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
    PdfWriter writer = canvases[0].getPdfWriter();
    float x = (position.getLeft() + position.getRight()) / 2;
    float y = (position.getTop() + position.getBottom()) / 2;
    Rectangle rect = new Rectangle(x - 5, y - 5, x + 5, y + 5);
    RadioCheckField checkbox = new RadioCheckField(writer, rect, name, "Yes");
    checkbox.setOptions(BaseField.READ_ONLY);
    switch(i) {
      case 0:
        checkbox.setCheckType(RadioCheckField.TYPE_CHECK);
        break;
      case 1:
        checkbox.setCheckType(RadioCheckField.TYPE_CROSS);
        break;
    }
    checkbox.setChecked(true);
    try {
      writer.addAnnotation(checkbox.getCheckField());
    } catch(Exception e) {
      throw new ExceptionConverter(e);
    }
  }
}
