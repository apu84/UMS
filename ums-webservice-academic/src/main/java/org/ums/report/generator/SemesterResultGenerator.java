package org.ums.report.generator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.immutable.StudentRecord;
import org.ums.manager.StudentRecordManager;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

@Component
public class SemesterResultGenerator extends AbstractResultGenerator {
  @Autowired
  StudentRecordManager mStudentRecordManager;

  @Override
  protected List<StudentRecord> getStudentList(int pProgramId, int pSemesterId) {
    return mStudentRecordManager.getStudentRecords(pProgramId, pSemesterId);
  }

  @Override
  protected PdfPTable contentTableHeader() {
    PdfPTable contentTable = new PdfPTable(5);

    PdfPCell cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    Paragraph paragraph = new Paragraph("Student No.", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("Student Name", infoFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("GPA", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("CGPA", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    contentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph("PASS/FAIL", infoFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    contentTable.addCell(cell);

    return contentTable;
  }

  @Override
  protected void contentRow(StudentRecord pStudentRecord, PdfPTable pContentTable) {
    PdfPCell cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    Paragraph paragraph = new Paragraph(pStudentRecord.getStudentId(), tableFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    pContentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(pStudentRecord.getStudent().getFullName(), tableFont);
    paragraph.setAlignment(Element.ALIGN_LEFT);
    cell.addElement(paragraph);
    pContentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(pStudentRecord.getGPA().toString(), tableFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    pContentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph = new Paragraph(pStudentRecord.getCGPA().toString(), tableFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    pContentTable.addCell(cell);

    cell = new PdfPCell();
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setVerticalAlignment(Element.ALIGN_CENTER);
    paragraph =
        new Paragraph(pStudentRecord.getStatus() == null ? "" : pStudentRecord.getStatus().getValue(), tableFont);
    paragraph.setAlignment(Element.ALIGN_CENTER);
    cell.addElement(paragraph);
    pContentTable.addCell(cell);
  }

  @Override
  protected String getReportTitle() {
    return "SEMESTER FINAL RESULT";
  }
}
