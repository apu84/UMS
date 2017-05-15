package org.ums.report.generator.seatPlan.support;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.SeatPlanReportDto;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.enums.ExamType;
import org.ums.manager.SeatPlanReportManager;
import org.ums.manager.SemesterManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.util.UmsUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ums.report.generator.seatPlan.support.ReportSupport.getStudentTypeChunk;

/**
 * Created by Monjur-E-Morshed on 13-May-17.
 */
@Component
public class AttendanceSheetReport {

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private SeatPlanReportManager mSeatPlanReportManager;

  @Autowired
  private UGRegistrationResultManager mUGRegistrationResultManager;

  public static final String DEST = "seat_plan_report.pdf";

  public void createSeatPlanAttendencePdfReport(Integer pProgramType, Integer pSemesterId, Integer pExamType,
                                                String pExamDate, OutputStream pOutputStream) throws IOException, DocumentException {

    java.util.List<SeatPlanReportDto> seatPlanReports = new ArrayList<>();
    String universityName = new String("Ahsanullah University of Science and Technology");
    String attendenceSheet = new String("ATTENDANCE SHEET");
    String original = new String("ORIGINAL");
    String duplicate = new String("DUPLICATE");
    String roomNo = new String("ROOM NO: ");
    String year = new String("Year: ");
    String semester = new String("Semester: ");
    String courseNo = new String("Course No: ");
    String courseTitle = new String("Course Title: ");
    Semester semesterManager = mSemesterManager.get(pSemesterId);
    String examName = "";
    if (pExamType == 1) {
      examName = "Semester Final Examination, " + semesterManager.getName();
    } else {
      examName = "Carry/Clearance/Improvement Examination, " + semesterManager.getName();
    }
    String studentNo = new String("Student No.");
    String signatureOfTheStudents = new String("Signature of the examinee");
    String numberOfTheExamineesRegistered = new String("* Number of the examinees registered: ");
    String numberOfTheExamineesAbsent = new String("* Number of the examinees absent: ");
    String numberofTheExamineesPresent = new String("* Number of the examinees present: ");
    String signatureOfTheInvigilator = new String("Signature of the Invigilator");
    String nameOfTheInvigilator = new String("Name of the Invigilator");
    /*
     * String tr="<tr>"; String trEnd="</tr>"; String td="<td>"; String tdEnd="</td>";
     */

    seatPlanReports = mSeatPlanReportManager.getSeatPlanDataForAttendenceSheet(pSemesterId, pExamType, pExamDate);

    Document document = new Document();
    document.addTitle("Seat Plan Attendence Sheet");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    /*
     * MyFooter event = new MyFooter(); writer.setPageEvent(event);
     */
    document.open();
    document.setPageSize(PageSize.A4);

    int routineCounter = 0;

    long startTime = System.currentTimeMillis();
    Map<String, String> studentsUsageMap = new HashMap<>();

    List<UGRegistrationResult> ugRegistrationResults = new ArrayList<>();
    Map<String, UGRegistrationResult> studentIdUgRegistrationResultMap = new HashMap<>();
    if (pExamType == ExamType.CLEARANCE_CARRY_IMPROVEMENT.getId()) {
      ugRegistrationResults =
          mUGRegistrationResultManager.getCCI(pSemesterId, UmsUtils.formatDate(pExamDate, "mm-dd-yyyy", "dd-mm-yyyy"));
      for (int i = 0; i < ugRegistrationResults.size(); i++) {

        studentIdUgRegistrationResultMap.put(ugRegistrationResults.get(i).getStudentId()
            + ugRegistrationResults.get(i).getCourse().getNo(), ugRegistrationResults.get(i));
      }
    }

    while (true) {
      // seatPlanReports = getUnusedStudents(seatPlanReports, studentsUsageMap);
      SeatPlanReportDto seatPlanReportDto = seatPlanReports.get(0);

      routineCounter += 1;

      PdfPTable table = new PdfPTable(2);
      table.setWidthPercentage(110);
      PdfPTable tableOne = new PdfPTable(1);
      PdfPTable tableTwo = new PdfPTable(1);
      Paragraph universityParagraph = new Paragraph(universityName);
      universityParagraph.setAlignment(Element.ALIGN_CENTER);
      universityParagraph.setFont(FontFactory.getFont(FontFactory.TIMES, 12));
      PdfPCell cellUniversityName = new PdfPCell();
      cellUniversityName.addElement(universityParagraph);
      cellUniversityName.setPaddingTop(-5);
      cellUniversityName.setPaddingBottom(5);
      cellUniversityName.setVerticalAlignment(Element.ALIGN_CENTER);
      tableOne.addCell(cellUniversityName);
      tableTwo.addCell(cellUniversityName);

      Paragraph originalParagraph = new Paragraph(original);
      originalParagraph.setAlignment(Element.ALIGN_CENTER);
      originalParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 14));
      PdfPCell originalCell = new PdfPCell();
      originalCell.addElement(originalParagraph);
      originalCell.setPaddingTop(-5);
      tableOne.addCell(originalCell);

      Paragraph duplicateParagraph = new Paragraph(duplicate);
      duplicateParagraph.setAlignment(Element.ALIGN_CENTER);
      duplicateParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 14));
      PdfPCell duplicateCell = new PdfPCell();
      duplicateCell.addElement(duplicateParagraph);
      duplicateCell.setPaddingTop(-5);
      tableTwo.addCell(duplicateCell);

      PdfPCell upperCell = new PdfPCell();

      Paragraph attendanceParagraph = new Paragraph(attendenceSheet);
      attendanceParagraph.setAlignment(Element.ALIGN_CENTER);
      attendanceParagraph.setFont(FontFactory.getFont(FontFactory.TIMES, 14));
      attendanceParagraph.setPaddingTop(-5);
      PdfPCell attendanceCell = new PdfPCell(attendanceParagraph);
      attendanceCell.setBorder(Rectangle.NO_BORDER);
      upperCell.addElement(attendanceParagraph);
      /*
       * tableOne.addCell(attendanceCell); tableTwo.addCell(attendanceCell);
       */

      Paragraph roomNoParagraph = new Paragraph(roomNo + seatPlanReportDto.getRoomNo());
      getCenterAlignmentAndBoldTimesFont(roomNoParagraph);
      PdfPCell roomCell = new PdfPCell(roomNoParagraph);
      roomCell.setBorder(Rectangle.NO_BORDER);
      /*
       * tableOne.addCell(roomCell); tableTwo.addCell(roomCell);
       */
      upperCell.addElement(roomNoParagraph);

      Paragraph date = new Paragraph("Date: " + seatPlanReportDto.getExamDate());
      getCenterAlignmentAndBoldTimesFont(date);
      PdfPCell dateCell = new PdfPCell(date);
      dateCell.setBorder(Rectangle.NO_BORDER);
      /*
       * tableOne.addCell(dateCell); tableTwo.addCell(dateCell);
       */
      upperCell.addElement(date);

      Paragraph examParagraph = new Paragraph(examName);
      examParagraph.setAlignment(Element.ALIGN_CENTER);
      examParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 14));
      PdfPCell examCell = new PdfPCell(examParagraph);
      /*
       * tableOne.addCell(examCell); tableTwo.addCell(examCell);
       */
      upperCell.addElement(examParagraph);
      upperCell.addElement(new Paragraph(" "));

      /*
       * two private variables are declared for defining two types of fonts, one is for light times
       * new roman font, and the other is for bold times new roman.
       */

      Font lightFont = FontFactory.getFont(FontFactory.TIMES, 12);

      Font boldFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);

      Paragraph yearP = new Paragraph(year, lightFont);
      Paragraph yearB = new Paragraph(seatPlanReportDto.getCurrentYear().toString(), boldFont);
      Paragraph semesterP = new Paragraph("            " + "              " + "     " + semester + "  ", lightFont);
      Paragraph semesterB = new Paragraph(seatPlanReportDto.getCurrentSemester().toString(), boldFont);
      /*
       * Paragraph yearSemesterParagraph = new Paragraph();
       * yearSemesterParagraph.add(yearP+" "+yearB);
       *//*
          * yearSemesterParagraph.add(yearP); yearSemesterParagraph.add(yearB);
          *//*
             * yearSemesterParagraph.add(semesterP); yearSemesterParagraph.add(semesterB);
             */

      Phrase yearSemesterPhrase = new Phrase();
      yearSemesterPhrase.add(yearP);
      yearSemesterPhrase.add(yearB);
      yearSemesterPhrase.add(semesterP);
      yearSemesterPhrase.add(semesterB);
      // PdfPCell yearSemesterCell = new PdfPCell(yearSemesterParagraph);
      /*
       * tableOne.addCell(yearSemesterCell); tableTwo.addCell(yearSemesterCell);
       */
      upperCell.addElement(yearSemesterPhrase);

      Paragraph departmentP = new Paragraph("Department: ", lightFont);
      Paragraph departmentParagraph = new Paragraph(" " + seatPlanReportDto.getProgramName(), boldFont);
      // departmentParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD,12));
      PdfPCell departmentCell = new PdfPCell(departmentParagraph);
      /*
       * tableOne.addCell(departmentCell); tableTwo.addCell(departmentCell);
       */
      Phrase departmentPhrase = new Phrase();
      departmentPhrase.add(departmentP);
      departmentPhrase.add(departmentParagraph);
      upperCell.addElement(departmentPhrase);

      Paragraph courseP = new Paragraph("Course No: ", lightFont);
      Paragraph courseNoParagraph = new Paragraph("" + seatPlanReportDto.getCourseNo(), boldFont);
      // courseNoParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD,12));
      PdfPCell courseNoCell = new PdfPCell(courseNoParagraph);
      /*
       * tableOne.addCell(courseNoCell); tableTwo.addCell(courseNoCell);
       */
      Phrase courseNoPhrase = new Phrase();
      courseNoPhrase.add(courseP);
      courseNoPhrase.add(courseNoParagraph);
      upperCell.addElement(courseNoPhrase);

      Paragraph courseTitleP = new Paragraph(courseTitle, lightFont);
      Paragraph courseTitleParagrah = new Paragraph("" + seatPlanReportDto.getCourseTitle(), boldFont);
      Phrase courseTitlePhrase = new Phrase();
      courseTitlePhrase.add(courseTitleP);
      courseTitlePhrase.add(courseTitleParagrah);
      // courseTitleParagrah.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD,12));
      PdfPCell courseTitleCell = new PdfPCell(courseTitleParagrah);
      /*
       * tableOne.addCell(courseTitleCell); tableTwo.addCell(courseTitleCell);
       */
      upperCell.addElement(courseTitlePhrase);
      upperCell.setPaddingBottom(10);

      tableOne.addCell(upperCell);
      tableTwo.addCell(upperCell);

      float[] attendenceSheetColSpan = {4, 7};
      PdfPTable attendanceSheetTable = new PdfPTable(attendenceSheetColSpan);
      attendanceSheetTable.setWidthPercentage(100);
      PdfPCell sttudentNoCell = new PdfPCell(new Paragraph(studentNo, FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
      sttudentNoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      Paragraph studentsSignutureSection = new Paragraph("Signature of the examinee");
      studentsSignutureSection.setAlignment(Element.ALIGN_CENTER);
      studentsSignutureSection.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
      PdfPCell signatureCell =
          new PdfPCell(new Paragraph(signatureOfTheStudents, FontFactory.getFont(FontFactory.TIMES_BOLD, 10)));
      signatureCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      attendanceSheetTable.addCell(sttudentNoCell);
      attendanceSheetTable.addCell(signatureCell);
      String classRoomNo = seatPlanReportDto.getRoomNo();
      String courseIdOfStudent = seatPlanReportDto.getCourseId();
      String programOfStudent = seatPlanReportDto.getProgramName();
      int currYearOfStudent = seatPlanReportDto.getCurrentYear();
      int currSemesterOfStudent = seatPlanReportDto.getCurrentSemester();
      int counter = 0;
      int studentCounter = 0;

      while (true) {

        SeatPlanReportDto seatPlanInner = new SeatPlanReportDto();
        // seatPlanReports = getUnusedStudents(seatPlanReports, studentsUsageMap);
        // studentsUsageMap.put(seatPlanReportDto.getStudentId(), "used");

        if (seatPlanReports.size() != 0) {
          seatPlanInner = seatPlanReports.get(0);
          studentsUsageMap.put(seatPlanInner.getStudentId(), "used");
          if (seatPlanInner.getRoomNo().equals(classRoomNo) && seatPlanInner.getCourseId().equals(courseIdOfStudent)
              && seatPlanInner.getProgramName().equals(programOfStudent)
              && seatPlanInner.getCurrentYear() == currYearOfStudent
              && seatPlanInner.getCurrentSemester() == currSemesterOfStudent && counter != 18
              && seatPlanReports.size() != 0) {
            PdfPCell innerCellOne = new PdfPCell();
            /*
             * if(seatPlanInner.getStudentType().equals("RA")) { innerCellOne.addElement(new
             * Paragraph(seatPlanInner.getStudentId(), FontFactory
             * .getFont(FontFactory.TIMES_BOLD))); } else { innerCellOne.addElement(new
             * Paragraph(seatPlanInner.getStudentId(), FontFactory .getFont(FontFactory.TIMES))); }
             */
            Chunk studentId = new Chunk();
            studentId = getStudentTypeChunk(pExamType, studentIdUgRegistrationResultMap, studentId, seatPlanInner);
            innerCellOne.addElement(studentId);
            innerCellOne.setPaddingTop(-4);
            PdfPCell innerCellTwo = new PdfPCell(new Paragraph("  "));
            innerCellOne.setPaddingBottom(4f);
            attendanceSheetTable.addCell(innerCellOne);
            attendanceSheetTable.addCell(innerCellTwo);
            seatPlanReports.remove(0);
            studentCounter += 1;
            counter += 1;
          } else {
            if (counter > 18) {
              break;
            } else {
              PdfPCell innerCellOne = new PdfPCell(new Paragraph("   "));
              PdfPCell innerCellTwo = new PdfPCell(new Paragraph("  "));
              attendanceSheetTable.addCell(innerCellOne);
              attendanceSheetTable.addCell(innerCellTwo);
              counter += 1;
            }
          }
        } else {

          if (counter > 18) {
            break;
          } else {
            PdfPCell innerCellOne = new PdfPCell(new Paragraph("   "));
            PdfPCell innerCellTwo = new PdfPCell(new Paragraph("  "));
            attendanceSheetTable.addCell(innerCellOne);
            attendanceSheetTable.addCell(innerCellTwo);
            counter += 1;
          }

        }

      }

      PdfPCell attendenceCellTable = new PdfPCell(attendanceSheetTable);
      tableOne.addCell(attendenceCellTable);
      tableTwo.addCell(attendenceCellTable);

      /*
       * Paragraph numberOfExamineeParagraph = new Paragraph(numberOfTheExamineesRegistered +
       * studentCounter);
       */
      Paragraph numberOfPrewsent = new Paragraph(numberofTheExamineesPresent);
      Paragraph numberOfAbsentParagraph = new Paragraph(numberOfTheExamineesAbsent);
      PdfPCell studentInfoCell = new PdfPCell();
      studentInfoCell.addElement(numberOfPrewsent);
      studentInfoCell.addElement(numberOfAbsentParagraph);
      studentInfoCell.setPaddingTop(-3);
      studentInfoCell.setPaddingBottom(4);
      tableOne.addCell(studentInfoCell);
      tableTwo.addCell(studentInfoCell);

      /* Paragraph numberOfPresent = new Paragraph(numberofTheExamineesPresent); */
      Paragraph numberOfExamineeParagraph = new Paragraph(numberOfTheExamineesRegistered + studentCounter);
      PdfPCell presentCell = new PdfPCell();
      presentCell.setPaddingTop(-3);
      presentCell.setPaddingBottom(4);
      presentCell.addElement(numberOfExamineeParagraph);
      tableOne.addCell(presentCell);
      tableTwo.addCell(presentCell);

      Paragraph underLine = new Paragraph("___________________________");
      underLine.setAlignment(Element.ALIGN_RIGHT);
      Paragraph signatureInvigilatorParagraph = new Paragraph(signatureOfTheInvigilator);
      signatureInvigilatorParagraph.setAlignment(Element.ALIGN_RIGHT);

      PdfPCell invigilatorSignatureCell = new PdfPCell();
      invigilatorSignatureCell.addElement(underLine);
      invigilatorSignatureCell.addElement(signatureInvigilatorParagraph);
      invigilatorSignatureCell.setBorder(Rectangle.NO_BORDER);
      invigilatorSignatureCell.setPaddingTop(40);
      tableOne.addCell(invigilatorSignatureCell);
      tableTwo.addCell(invigilatorSignatureCell);

      Paragraph invigilatorNameParagraph = new Paragraph(nameOfTheInvigilator);
      invigilatorNameParagraph.setAlignment(Element.ALIGN_RIGHT);
      PdfPCell nameCell = new PdfPCell();
      nameCell.addElement(underLine);
      nameCell.addElement(invigilatorNameParagraph);
      nameCell.setBorder(Rectangle.NO_BORDER);
      nameCell.setPaddingTop(8);
      tableOne.addCell(nameCell);
      tableTwo.addCell(nameCell);

      PdfPCell cellOne = new PdfPCell(tableOne);
      cellOne.setBorder(Rectangle.NO_BORDER);
      PdfPCell cellTwo = new PdfPCell(tableTwo);
      cellTwo.setBorder(Rectangle.NO_BORDER);
      cellOne.setPaddingRight(13);
      cellTwo.setPaddingLeft(13);
      table.addCell(cellOne);
      table.addCell(cellTwo);

      document.add(table);
      document.newPage();
      // break;

      if (seatPlanReports.size() == 0) {
        break;
      }

    }

    document.close();
    baos.writeTo(pOutputStream);

    long endTime = System.currentTimeMillis();
    long totalTime = endTime - startTime;

    System.out.println(totalTime);
  }

  private void getCenterAlignmentAndBoldTimesFont(Paragraph pHeaderParagraph) {
    pHeaderParagraph.setAlignment(Element.ALIGN_CENTER);
    pHeaderParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD));
  }

}
