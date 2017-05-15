package org.ums.report.generator.seatPlan.support;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.domain.model.dto.SeatPlanReportDto;
import org.ums.domain.model.immutable.CourseTeacher;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.enums.ExamType;
import org.ums.manager.*;
import org.ums.util.UmsUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.ums.report.generator.seatPlan.support.ReportSupport.getStudentTypeChunk;

/**
 * Created by Monjur-E-Morshed on 13-May-17.
 */
@Component
public class TopSheetReport {

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private CourseTeacherManager mCourseTeacherManager;

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private SeatPlanReportManager mSeatPlanReportManager;

  @Autowired
  private UGRegistrationResultManager mUGRegistrationResultManager;

  public static final String DEST = "seat_plan_report.pdf";

  public void createSeatPlanTopSheetPdfReport(Integer pProgramType, Integer pSemesterId,
                                              Integer pExamType, String pExamDate, OutputStream pOutputStream) throws IOException,
      DocumentException {
    List<SeatPlanReportDto> seatPlans =
        mSeatPlanReportManager.getSeatPlanDataForTopSheet(pSemesterId, pExamType, pExamDate);

    Map<String, List<CourseTeacher>> courseWithCourseTeacherMap = mCourseTeacherManager.getCourseTeacher(pSemesterId)
        .stream()
        .collect(Collectors.groupingBy(CourseTeacher::getCourseId));

    Document document = new Document();
    document.addTitle("Seat Plan Attendence Sheet");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    /*
     * MyFooter event = new MyFooter(); writer.setPageEvent(event);
     */
    document.open();
    document.setPageSize(PageSize.A4);

    Font lightFont = FontFactory.getFont(FontFactory.TIMES, 11);
    Font duetFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 13);
    Font universityNameFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);
    Font sponSoreFont = FontFactory.getFont(FontFactory.TIMES, 8);
    Font topSheetFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 13);
    Font boldFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);

    Semester semester = mSemesterManager.get(pSemesterId);
    int seatPlanCounter = 0;
    List<UGRegistrationResult> ugRegistrationResults = new ArrayList<>();
    Map<String, UGRegistrationResult> studentIdUgRegistrationResultMap = new HashMap<>();
    Map<String, String> studentsUsageMap = new HashMap<>();

    if (pExamType == ExamType.CLEARANCE_CARRY_IMPROVEMENT.getId()) {
      String examDates = UmsUtils.formatDate(pExamDate, "mm-dd-yyyy", "dd-mm-yyyy");
      ugRegistrationResults = mUGRegistrationResultManager.getCCI(pSemesterId, UmsUtils.formatDate(pExamDate, "mm-dd-yyyy", "dd-mm-yyyy"));
      for (int i = 0; i < ugRegistrationResults.size(); i++) {
        studentIdUgRegistrationResultMap.put(ugRegistrationResults.get(i).getStudentId() + ugRegistrationResults.get(i).getCourse().getNo(), ugRegistrationResults.get(i));
      }
    }
    while (true) {

      seatPlanCounter += 1;

      SeatPlanReportDto seatPlan = seatPlans.get(0);
      if (seatPlan.getCourseNo().equals("ME 403")) {
        boolean foundxls;
        foundxls = true;
      }

      String examDate = seatPlan.getExamDate();

      /*Due date of submission is not required any more as it is done via IUMS.*/

      /*Paragraph dueDateOfSubmission = new Paragraph("DUE DATE OF SUBMISSION :", duetFont);
      dueDateOfSubmission.setAlignment(Element.ALIGN_CENTER);
      PdfPCell dueDateCell = new PdfPCell();
      dueDateCell.addElement(dueDateOfSubmission);
      document.add(dueDateOfSubmission);*/

      Paragraph universityName =
          new Paragraph("AHSANULLAH UNIVERSITY OF SCIENCE AND TECHNOLOGY", universityNameFont);
      universityName.setAlignment(Element.ALIGN_CENTER);
      PdfPCell universityNameCell = new PdfPCell();
      universityNameCell.addElement(universityName);
      document.add(universityName);

      Paragraph sponsoreName =
          new Paragraph(
              "(Sponsored by the Dhaka Ahsania Mission and approved by the Government of the People's Republic of Bangladesh)",
              sponSoreFont);
      sponsoreName.setAlignment(Element.ALIGN_CENTER);
      Phrase sponsoreNameCell = new Phrase();
      sponsoreNameCell.add(sponsoreName);
      document.add(sponsoreName);

      Chunk examName;

      if (pExamType == 1) {
        examName =
            new Chunk("TOP SHEET (FINAL EXAMINATION " + semester.getName().toUpperCase() + ")");
      } else {
        examName =
            new Chunk("TOP SHEET (Carry/Clearance/Improvement Examination, " + semester.getName()
                + ")");
      }

      examName.setUnderline(0.1f, -2f);
      Paragraph examNameP = new Paragraph(examName);
      examNameP.setAlignment(Element.ALIGN_CENTER);
      examNameP.setFont(FontFactory.getFont(FontFactory.TIMES, 14));
      PdfPCell examNameCell = new PdfPCell();
      examNameCell.addElement(examNameP);
      document.add(examNameP);
      document.add(new Paragraph(" "));
      Paragraph programP = new Paragraph(" Program :", lightFont);
      Paragraph programB = new Paragraph(seatPlan.getProgramName(), boldFont);
      Phrase programPhrase = new Phrase();
      programPhrase.add(programP);
      programPhrase.add(programB);
      PdfPCell programNameCell = new PdfPCell();
      programNameCell.addElement(programPhrase);
      document.add(programPhrase);
      float[] upperTableWidth = new float[]{7f, 3f};

      PdfPTable upperTable = new PdfPTable(upperTableWidth);
      upperTable.setPaddingTop(-10);
      upperTable.setWidthPercentage(100);
      Paragraph yearP = new Paragraph("Year :", lightFont);
      Paragraph yearB = new Paragraph(seatPlan.getCurrentYear().toString(), boldFont);
      Phrase yearPhrase = new Phrase();
      yearPhrase.add(yearP);
      yearPhrase.add(yearB);
      PdfPCell yearCell = new PdfPCell();
      yearCell.setBorder(Rectangle.NO_BORDER);
      yearCell.setPaddingTop(-8);
      yearCell.addElement(yearPhrase);
      upperTable.addCell(yearCell);

      Paragraph semesterP = new Paragraph("Semester :", lightFont);
      Paragraph semesterB = new Paragraph(seatPlan.getCurrentSemester().toString(), boldFont);
      Phrase semesterPhrase = new Phrase();
      semesterPhrase.add(semesterP);
      semesterPhrase.add(semesterB);
      PdfPCell semesterCell = new PdfPCell();
      semesterCell.setBorder(Rectangle.NO_BORDER);
      semesterCell.setPaddingTop(-8);
      semesterCell.addElement(semesterPhrase);
      upperTable.addCell(semesterCell);

      Paragraph courseNoP = new Paragraph("Course no: ", lightFont);
      Paragraph courseNoB = new Paragraph(seatPlan.getCourseNo(), boldFont);
      Phrase courseNoPhrase = new Phrase();
      courseNoPhrase.add(courseNoP);
      courseNoPhrase.add(courseNoB);
      PdfPCell courseNoCell = new PdfPCell();
      courseNoCell.setBorder(Rectangle.NO_BORDER);
      courseNoCell.addElement(courseNoPhrase);
      upperTable.addCell(courseNoCell);

      Paragraph groupP = new Paragraph("Group/Section", lightFont);
      Phrase groupPhrase = new Phrase();
      groupPhrase.add(groupP);
      PdfPCell groupCell = new PdfPCell();
      groupCell.setBorder(Rectangle.NO_BORDER);
      groupCell.addElement(groupPhrase);
      upperTable.addCell(groupCell);
      upperTable.setWidthPercentage(100);
      document.add(upperTable);

      Paragraph courseTitleP = new Paragraph(" Course Title :", lightFont);
      Paragraph courseTitleB = new Paragraph(seatPlan.getCourseTitle(), boldFont);
      Phrase courseTitlePhrase = new Phrase();
      courseTitlePhrase.add(courseTitleP);
      courseTitlePhrase.add(courseTitleB);
      PdfPCell courseTitleCell = new PdfPCell();
      courseTitleCell.addElement(courseTitlePhrase);
      document.add(courseTitlePhrase);

      Phrase notePhrase = getStudentTypeNotePhrase(pExamType, lightFont, boldFont);
      Paragraph noteParagraph = new Paragraph();
      noteParagraph.add(notePhrase);
      document.add(noteParagraph);
      Paragraph spaceP = new Paragraph(" ");
      document.add(spaceP);

      /* main data table starting */
      float[] dataTableWidth = new float[]{8f, 2f, 2f};
      PdfPTable dataTable = new PdfPTable(dataTableWidth);
      dataTable.setWidthPercentage(100);

      Paragraph registrationP =
          new Paragraph("Number of Candidates registered for the course", boldFont);
      registrationP.setAlignment(Element.ALIGN_CENTER);
      PdfPCell registrationCell = new PdfPCell();
      registrationCell.addElement(registrationP);
      dataTable.addCell(registrationCell);

      Paragraph presentParagraph = new Paragraph("Number of Candidates absent");
      presentParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
      presentParagraph.setAlignment(Element.ALIGN_CENTER);
      PdfPCell presentCell = new PdfPCell();
      presentCell.addElement(presentParagraph);
      dataTable.addCell(presentCell);

      Paragraph absentParagraph = new Paragraph("Number of Candidates reported");
      absentParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
      absentParagraph.setAlignment(Element.ALIGN_CENTER);
      PdfPCell absentCell = new PdfPCell();
      absentCell.addElement(absentParagraph);
      dataTable.addCell(absentCell);

      int rowCounter = 0;
      int studentCounter = 0;


      while (true) {
        if (seatPlans.size() != 0) {
          SeatPlanReportDto seatPlanReportDto = seatPlans.get(0);

          if (seatPlanReportDto.getCourseId().equals(seatPlan.getCourseId()) && seatPlanReportDto.getCurrentYear() == seatPlan.getCurrentYear() && seatPlanReportDto.getCurrentSemester() == seatPlan.getCurrentSemester()
              && seatPlans.size() != 0) {
            Chunk studentId = new Chunk("");
            Paragraph studentIdParagraph = new Paragraph();

            for (int i = 0; i < 6; i++) {
//              seatPlans = getUnusedStudents(seatPlans, studentsUsageMap);
              if (seatPlans.size() != 0) {
                SeatPlanReportDto seatPlanInnerReport = seatPlans.get(0);
                if (seatPlanInnerReport.getStudentId().equals("110207069")) {
                  boolean hur = true;
                }
                studentsUsageMap.put(seatPlanInnerReport.getStudentId() + seatPlanInnerReport.getCourseNo(), "used");
                if (seatPlanInnerReport.getCourseId().equals(seatPlan.getCourseId()) && seatPlanInnerReport.getCurrentYear() == seatPlan.getCurrentYear() && seatPlanInnerReport.getCurrentSemester() == seatPlan.getCurrentSemester()
                    && seatPlans.size() != 0) {
                  /*
                   * String stdIdTmp = ""; stdIdTmp = studentId; studentId = "";
                   */
                  if (seatPlans.size() != 1) {
                    if (seatPlanReportDto.getCourseNo().equals(seatPlans.get(1).getCourseNo())) {
                      studentId = getStudentTypeChunk(pExamType, studentIdUgRegistrationResultMap, studentId, seatPlanInnerReport);


                      studentIdParagraph.add(studentId);
                      studentId = new Chunk(", ");
                      studentIdParagraph.add(studentId);
                      // studentId = stdIdTmp + "" + seatPlanInnerReport.getStudentId() + ", ";

                    } else {
                      studentId = getStudentTypeChunk(pExamType, studentIdUgRegistrationResultMap, studentId, seatPlanInnerReport);


                      studentIdParagraph.add(studentId);
                      studentId = new Chunk(" ");
                      studentIdParagraph.add(studentId);

                    }
                  } else {
                    studentId = getStudentTypeChunk(pExamType, studentIdUgRegistrationResultMap, studentId, seatPlanInnerReport);


                    studentIdParagraph.add(studentId);
                    studentId = new Chunk(" ");
                    studentIdParagraph.add(studentId);

                  }

                  seatPlans.remove(0);
                  studentCounter += 1;
                  // rowCounter+=1;
                } else {
                  break;
                }
              } else {
                break;
              }

            }

            studentIdParagraph.setFont(FontFactory.getFont(FontFactory.TIMES, 11));
            Paragraph emptyParagraph = new Paragraph("  ");
            PdfPCell firstCell = new PdfPCell();
            PdfPCell middleCell = new PdfPCell();
            PdfPCell lastCell = new PdfPCell();
            firstCell.setPaddingTop(-5);
            middleCell.setPaddingTop(-5);
            lastCell.setPaddingTop(-5);
            firstCell.addElement(studentIdParagraph);
            middleCell.addElement(emptyParagraph);
            lastCell.addElement(emptyParagraph);
            dataTable.addCell(firstCell);
            dataTable.addCell(middleCell);
            dataTable.addCell(lastCell);
            rowCounter += 1;

          } else {
            Paragraph emptyParagraph = new Paragraph("  ");
            PdfPCell firstCell = new PdfPCell();
            PdfPCell middleCell = new PdfPCell();
            PdfPCell lastCell = new PdfPCell();
            firstCell.setPaddingTop(-5);
            middleCell.setPaddingTop(-5);
            lastCell.setPaddingTop(-5);
            firstCell.addElement(emptyParagraph);
            middleCell.addElement(emptyParagraph);
            lastCell.addElement(emptyParagraph);
            dataTable.addCell(firstCell);
            dataTable.addCell(middleCell);
            dataTable.addCell(lastCell);
            rowCounter += 1;
          }
        } else {
          Paragraph emptyParagraph = new Paragraph("  ");
          PdfPCell firstCell = new PdfPCell();
          PdfPCell middleCell = new PdfPCell();
          PdfPCell lastCell = new PdfPCell();
          firstCell.setPaddingTop(-5);
          middleCell.setPaddingTop(-5);
          lastCell.setPaddingTop(-5);
          firstCell.addElement(emptyParagraph);
          middleCell.addElement(emptyParagraph);
          lastCell.addElement(emptyParagraph);
          dataTable.addCell(firstCell);
          dataTable.addCell(middleCell);
          dataTable.addCell(lastCell);
          rowCounter += 1;
        }

        if (rowCounter == 23) {
          break;
        }
      }

      Paragraph totalParagraph = new Paragraph("Total :", lightFont);
      Paragraph totalNumber = new Paragraph(studentCounter + "", boldFont);
      Phrase totalPhrase = new Phrase();
      totalPhrase.add(totalParagraph);
      totalPhrase.add(totalNumber);
      PdfPCell totalCellFirst = new PdfPCell();
      totalCellFirst.addElement(totalPhrase);

      dataTable.addCell(totalCellFirst);

      PdfPCell totalCellMiddleAndLast = new PdfPCell();
      totalCellMiddleAndLast.addElement(totalParagraph);
      dataTable.addCell(totalCellMiddleAndLast);
      dataTable.addCell(totalCellMiddleAndLast);
      document.add(dataTable);

      Paragraph enclosedParagraph =
          new Paragraph(
              "Total number of the candidates whose scripts are enclosed:........................ (in words)...........................",
              boldFont);
      document.add(enclosedParagraph);

      float[] bottomTableSize = new float[]{7f, 6f};
      PdfPTable bottomTable = new PdfPTable(bottomTableSize);
      bottomTable.setPaddingTop(10);
      bottomTable.setWidthPercentage(100);

      PdfPCell dateCell = new PdfPCell();
      dateCell.addElement(new Paragraph(" "));
      dateCell.addElement(new Paragraph("Date: " + examDate, boldFont));
      dateCell.setBorder(Rectangle.NO_BORDER);
      dateCell.setPaddingTop(10);
      bottomTable.addCell(dateCell);

      PdfPCell preparedCell = new PdfPCell();
      preparedCell.addElement(new Paragraph(" "));
      preparedCell.addElement(new Paragraph("Prepared by:________Checked by:________", boldFont));
      preparedCell.setBorder(Rectangle.NO_BORDER);
      preparedCell.setPaddingTop(10);
      bottomTable.addCell(preparedCell);

      PdfPCell examineer = new PdfPCell();
      List<CourseTeacher> examiners = courseWithCourseTeacherMap.get(seatPlan.getCourseId());
      String teachersName = "";
      int teacherCounter = 0;
      Set<String> teacherNameSet = new HashSet<>();
      if (examiners != null) {
        for (CourseTeacher teacher : examiners) {
          Employee employee = mEmployeeManager.get(teacher.getTeacherId());
          teacherCounter += 1;
          if (teacherCounter < examiners.size()) {
            teacherNameSet.add(employee.getEmployeeName());
          } else {
            teacherNameSet.add(employee.getEmployeeName());
          }
        }
      }
      teachersName = String.join(",", teacherNameSet.toString());
      examineer.addElement(new Paragraph("Name of the Examiner(s): " + teachersName, lightFont));
      examineer.addElement(new Paragraph("Address & telephone no: AUST,Mobile-", lightFont));
      examineer.setBorder(Rectangle.NO_BORDER);
      bottomTable.addCell(examineer);

      PdfPCell controller = new PdfPCell();
      controller.addElement(new Paragraph("  "));
      controller.addElement(new Paragraph("  "));
      controller.addElement(new Paragraph(" ______________________________", lightFont));

      controller.addElement(new Paragraph("      Controller of Examinations", boldFont));
      controller.addElement(new Paragraph("          AUST,Dhaka-1208", boldFont));
      controller.setBorder(Rectangle.NO_BORDER);
      controller.setPaddingTop(10);
      bottomTable.addCell(controller);
      document.add(bottomTable);
      if (seatPlans.size() == 0) {
        break;
      } else {
        studentsUsageMap = new HashMap<>();
        document.newPage();
      }

    }

    document.close();
    baos.writeTo(pOutputStream);
  }

  private Phrase getStudentTypeNotePhrase(Integer pExamType, Font pLightFont, Font pBoldFont) {
    Paragraph noteParagraph = new Paragraph(" Font Style: ", pLightFont);
    Phrase notePhrase = new Phrase();
    notePhrase.add(noteParagraph);
    if (pExamType == ExamType.SEMESTER_FINAL.getId()) {
      noteParagraph = new Paragraph("Regular ", pLightFont);
      notePhrase.add(noteParagraph);
      noteParagraph = new Paragraph("Re-admission", pBoldFont);
      notePhrase.add(noteParagraph);
    } else {
      noteParagraph = new Paragraph("Clearance ", pLightFont);
      notePhrase.add(noteParagraph);
      noteParagraph = new Paragraph("Carryover ", pBoldFont);
      notePhrase.add(noteParagraph);
      Chunk noteChunk = new Chunk("Improvement ", FontFactory.getFont(FontFactory.TIMES));
      noteChunk.setBackground(BaseColor.LIGHT_GRAY);
      notePhrase.add(noteChunk);
      noteChunk = new Chunk("Special Carry ", FontFactory.getFont(FontFactory.TIMES_BOLDITALIC));
      noteChunk.setBackground(BaseColor.GREEN);
      notePhrase.add(noteChunk);
    }
    return notePhrase;
  }
}
