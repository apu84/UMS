package org.ums.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.builder.SeatPlanBuilder;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.dto.SeatPlanReportDto;
import org.ums.domain.model.immutable.*;
import org.ums.manager.*;
import org.ums.services.academic.SeatPlanService;

import javax.ws.rs.WebApplicationException;
import java.io.*;
import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by My Pc on 5/25/2016.
 */

@Service
public class SeatPlanReportGeneratorImpl implements SeatPlanReportGenerator {

  @Autowired
  private SeatPlanManager mManager;

  @Autowired
  private SeatPlanBuilder mBuilder;

  @Autowired
  private SeatPlanService mSeatPlanService;

  @Autowired
  private StudentRecordManager mStudentRecordManager;

  @Autowired
  private ClassRoomManager mRoomManager;

  @Autowired
  private SemesterManager mSemesterManager;

  @Autowired
  private SeatPlanGroupManager mSeatPlanGroupManager;

  @Autowired
  private ExamRoutineManager mExamRoutineManager;

  @Autowired
  private SeatPlanManager mSeatPlanManager;

  @Autowired
  private ProgramManager mProgramManager;

  @Autowired
  private StudentManager mSpStudentManager;

  @Autowired
  private CourseManager mCourseManager;

  @Autowired
  private CourseTeacherManager mCourseTeacherManager;

  @Autowired
  private EmployeeManager mEmployeeManager;

  @Autowired
  private SeatPlanReportManager mSeatPlanReportManager;

  public static final String DEST = "seat_plan_report.pdf";

  @Override
  public void createPdf(String dest, boolean noSeatPlanInfo, int pSemesterId, int groupNo,
      int type, String examDate, OutputStream pOutputStream) throws IOException, DocumentException,
      WebApplicationException {

    Document document = new Document();
    document.addTitle("Seat Plan");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    MyFooter event = new MyFooter();
    writer.setPageEvent(event);
    document.open();
    document.setPageSize(PageSize.A4.rotate());

    document.newPage();

    Font f = new Font(Font.FontFamily.TIMES_ROMAN, 10.0f, Font.BOLD, BaseColor.BLACK);
    // to do , mSemesterManager is not working.
    Semester mSemester = mSemesterManager.get(pSemesterId);

    String semesterName = mSemester.getName();

    String examDates = "Date: ";
    java.util.List<SeatPlanGroup> seatPlanGroup =
        mSeatPlanGroupManager.getBySemesterGroupAndType(pSemesterId, groupNo, type);
    java.util.List<ExamRoutineDto> examRoutines =
        mExamRoutineManager.getExamRoutine(pSemesterId, type);
    java.util.List<SeatPlan> seatPlans;
    if(groupNo == 0) {
      seatPlans =
          mSeatPlanManager.getBySemesterAndGroupAndExamTypeAndExamDate(pSemesterId, groupNo, type,
              examDate);
    }
    else {
      seatPlans = mSeatPlanManager.getBySemesterAndGroupAndExamType(pSemesterId, groupNo, type);

    }
    java.util.List<Student> students;
    java.util.List<Integer> roomsOfTheSeatPlan = new ArrayList<>();
    Map<String, Student> studentIdWIthStuddentInfoMap = new HashMap<>();
    Map<String, SeatPlan> roomRowColWithSeatPlanMap = new HashMap<>();
    Map<Integer, Program> programIdWithProgramInfoMap = new HashMap<>();
    if(groupNo != 0) {
      //students = mSpStudentManager.getRegisteredStudents(groupNo, pSemesterId, type);
      studentIdWIthStuddentInfoMap = mSpStudentManager.getRegisteredStudents(groupNo, pSemesterId, type)
          .parallelStream()
          .collect(Collectors.toMap(Student::getId, Function.identity()));
    }
    else {
      //students = mSpStudentManager.getStudentBySemesterIdAndExamDateForCCI(pSemesterId, examDate);
      studentIdWIthStuddentInfoMap = mSpStudentManager.getStudentBySemesterIdAndExamDateForCCI(pSemesterId, examDate)
          .parallelStream()
          .collect(Collectors.toMap(Student::getId, Function.identity()));
    }
    programIdWithProgramInfoMap = mProgramManager.getAll().stream()
    .collect(Collectors.toMap(Program::getId, Function.identity()));


    long startTime = System.currentTimeMillis();
    for(SeatPlan seatPlan : seatPlans) {
      roomsOfTheSeatPlan.add(seatPlan.getClassRoomId());
      /*
       * StringBuilder sb = new StringBuilder();
       * sb.append(seatPlan.getClassRoom().getId()).append(seatPlan
       * .getRowNo()).append(seatPlan.getColumnNo());
       */
      /* String roomRowCol =sb.toString(); */
      roomRowColWithSeatPlanMap.put(seatPlan.getClassRoomId() + "" + seatPlan.getRowNo() + ""
          + seatPlan.getColumnNo(), seatPlan);
    }
    long endTime = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    /*for(Student student : students) {
      studentIdWIthStuddentInfoMap.put(student.getId(), student);
    }*/
    /*for(Program program : programs) {
      programIdWithProgramInfoMap.put(program.getId(), program);
    }*/

    int routineCounter = 0;
    if(examDate.equals("null")) {
      for(ExamRoutineDto routine : examRoutines) {
        SeatPlanGroup group = seatPlanGroup.get(0);

        if(routine.getProgramId() == group.getProgramId()
            && routine.getCourseYear() == group.getAcademicYear()
            && routine.getCourseSemester() == group.getAcademicSemester()) {

          if(routineCounter == examRoutines.size()) {
            examDates = examDates + routine.getExamDate();
          }
          else {
            if(examDates.equals("Date: ")) {
              examDates = examDates + routine.getExamDate();
            }
            else {
              examDates = examDates + ", " + routine.getExamDate();
            }

          }
        }
        routineCounter += 1;

      }
    }
    else {
      // examDate="";
      examDates = "Date: " + examDate;
    }

    long startTimeOfTheMainAlgorithm = System.currentTimeMillis();

    if(noSeatPlanInfo) {
      Chunk c =
          new Chunk(
              "No SubGroup and No Seat Plan Information in the database, create one and then come back again!",
              f);
      c.setBackground(BaseColor.WHITE);
      Paragraph p = new Paragraph(c);
      p.setAlignment(Element.ALIGN_CENTER);
      document.add(p);
    }
    else {
      java.util.List<ClassRoom> rooms = mRoomManager.getAll();

      int roomCounter = 0;
      for(ClassRoom room : rooms) {
        float fontSize;
        roomCounter += 1;
        boolean checkIfRoomExistsInSeatPlan = roomsOfTheSeatPlan.contains(room.getId());
        // int checkIfRoomExists =
        // mSeatPlanManager.checkIfExistsByRoomSemesterGroupExamType(room.getId(),pSemesterId,groupNo,type);

        if(checkIfRoomExistsInSeatPlan && room.getId() != 284) {
          long startTimeInRoom = System.currentTimeMillis();
          String roomHeader = "Room No: " + room.getRoomNo();
          Paragraph pRoomHeader =
              new Paragraph(roomHeader, FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
          pRoomHeader.setAlignment(Element.ALIGN_CENTER);
          document.add(pRoomHeader);
          String semesterInfo = "";
          if(type == 1) {
            semesterInfo =
                "Semester Final Examination " + semesterName + ". Capacity: " + (room.getCapacity()+2)
                    + ".";
          }
          if(type == 2) {
            semesterInfo =
                "Clearance/Improvement/Carryover Examination " + semesterName + ". Capacity: "
                    + (room.getCapacity()+2) + ".";
          }
          Paragraph pSemesterInfo =
              new Paragraph(semesterInfo, FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
          pSemesterInfo.setAlignment(Element.ALIGN_CENTER);
          document.add(pSemesterInfo);

          Paragraph pExamDates =
              new Paragraph(examDates, FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
          pExamDates.setAlignment(Element.ALIGN_CENTER);
          pExamDates.setSpacingAfter(6f);
          document.add(pExamDates);

          // for getting the summery

          java.util.List<String> deptList = new ArrayList<>();
          Map<String, java.util.List<String>> deptStudentListMap = new HashMap<>();
          Map<String, String> deptWithDeptNameMap = new HashMap<>();
          Map<String, String> deptWithYearSemesterMap = new HashMap<>();

          for(int i = 1; i <= (room.getTotalRow()+1); i++) {
            for(int j = 1; j <= room.getTotalColumn(); j++) {

              /*if(j<room.getTotalColumn()-2){
                if(i==1)
                  i=2;
              }*/

              SeatPlan seatPlanOfTheRowAndCol =
                  roomRowColWithSeatPlanMap.get(room.getId() + "" + i + "" + j);
              int ifSeatPlanExist;
              /*if(groupNo == 0) {
                ifSeatPlanExist =
                    mSeatPlanManager.checkIfExistsBySemesterGroupTypeExamDateRoomRowAndCol(
                        pSemesterId, groupNo, type, examDate, room.getId(), i, j);
              }
              else {
                ifSeatPlanExist =
                    mSeatPlanManager.checkIfExistsBySemesterGroupTypeRoomRowAndCol(pSemesterId,
                        groupNo, type, room.getId(), i, j);

              }*/
              if(seatPlanOfTheRowAndCol != null) {
                SeatPlan seatPlan = seatPlanOfTheRowAndCol; // roomRowColWithSeatPlanMap.get(room.getId()+""+i+""+j)
                // ;
                Student student = studentIdWIthStuddentInfoMap.get(seatPlan.getStudent().getId());
                Program program;
                String dept;
                String deptName;
                if(groupNo == 0) {
                  dept =
                      student.getProgramShortName() + " " + student.getCurrentYear() + "/"
                          + student.getCurrentAcademicSemester();
                  deptName = student.getProgramShortName();
                }
                else {
                  program = programIdWithProgramInfoMap.get(student.getProgram().getId());
                  dept =
                      program.getShortName().replace("BSc in ", "") + " "
                          + student.getCurrentYear() + "/" + student.getCurrentAcademicSemester();
                  deptName = program.getShortName().replace("BSc in ", "");

                }
                String yearSemester =
                    student.getCurrentYear() + "/" + student.getCurrentAcademicSemester();
                if(deptList.size() == 0) {
                  deptList.add(dept);
                  java.util.List<String> studentList = new ArrayList<>();
                  if(groupNo == 0) {
                    if(student.getApplicationType() == 3) {
                      studentList.add(student.getId() + "(C)");
                    }
                    else if(student.getApplicationType() == 5) {
                      studentList.add(student.getId() + "(I)");

                    }
                    else {
                      studentList.add(student.getId());

                    }
                  }
                  else {
                    studentList.add(student.getId());

                  }
                  deptStudentListMap.put(dept, studentList);
                  deptWithDeptNameMap.put(dept, deptName);
                  deptWithYearSemesterMap.put(dept, yearSemester);
                }
                else {
                  boolean foundInTheList = false;
                  for(String deptOfTheList : deptList) {
                    if(deptOfTheList.equals(dept)) {
                      java.util.List<String> studentList = deptStudentListMap.get(dept);
                      if(groupNo == 0) {
                        if(student.getApplicationType() == 3) {
                          studentList.add(student.getId() + "(C)");
                        }
                        else if(student.getApplicationType() == 5) {
                          studentList.add(student.getId() + "(I)");

                        }
                        else {
                          studentList.add(student.getId());

                        }
                      }
                      else {
                        studentList.add(student.getId());

                      }
                      deptStudentListMap.put(dept, studentList);
                      foundInTheList = true;
                      break;
                    }
                  }
                  if(foundInTheList == false) {
                    deptList.add(dept);
                    java.util.List<String> studentList = new ArrayList<>();
                    if(groupNo == 0) {
                      if(student.getApplicationType() == 3) {
                        studentList.add(student.getId() + "(C)");
                      }
                      else if(student.getApplicationType() == 5) {
                        studentList.add(student.getId() + "(I)");

                      }
                      else {
                        studentList.add(student.getId());

                      }
                    }
                    else {
                      studentList.add(student.getId());

                    }
                    deptStudentListMap.put(dept, studentList);
                    deptStudentListMap.put(dept, studentList);
                    deptWithDeptNameMap.put(dept, deptName);
                    deptWithYearSemesterMap.put(dept, yearSemester);
                  }
                }
              }
            }
          }
          float summaryFontSize = 10.0f;
          if(deptList.size() < 6) {
            fontSize = 11.0f;
          }
          else if(deptList.size() == 6) {
            fontSize = 11.0f;

          }
          else if(deptList.size() == 9) {
            fontSize = 11.0f;
            // summaryFontSize=9.0f;
          }
          else {
            if(room.getCapacity() <= 40) {
              fontSize = 11.0f;
            }
            else {
              fontSize = 11.0f;

            }
          }

          int totalStudent = 0;
          float[] tableWithForSummery = new float[] {1, 1, 8, 1};
          float[] columnWiths = new float[] {0.5f, 0.4f, 9f, 0.6f};

          PdfPTable summaryTable = new PdfPTable(tableWithForSummery);
          summaryTable.setWidthPercentage(100);
          summaryTable.setWidths(columnWiths);
          PdfPCell deptLabelCell = new PdfPCell();
          Paragraph deptLabel =
              new Paragraph("DEPT", FontFactory.getFont(FontFactory.TIMES_BOLD, summaryFontSize));
          deptLabelCell.addElement(deptLabel);
          deptLabelCell.setPaddingRight(-0.5f);
          deptLabelCell.setColspan(1);
          deptLabelCell.setPaddingTop(-2f);
          summaryTable.addCell(deptLabelCell);

          PdfPCell yearSemesterLabelCell = new PdfPCell();
          Paragraph yearSemesterLabel =
              new Paragraph("Y/S", FontFactory.getFont(FontFactory.TIMES_BOLD, summaryFontSize));
          yearSemesterLabelCell.addElement(yearSemesterLabel);
          yearSemesterLabelCell.setPaddingRight(-0.5f);
          yearSemesterLabelCell.setColspan(1);
          yearSemesterLabelCell.setPaddingTop(-2f);
          summaryTable.addCell(yearSemesterLabelCell);

          PdfPCell studentLabelCell = new PdfPCell();
          Paragraph studentLabel =
              new Paragraph("STUDENT ID", FontFactory.getFont(FontFactory.TIMES_BOLD,
                  summaryFontSize));
          studentLabel.setAlignment(Element.ALIGN_CENTER);
          studentLabelCell.addElement(studentLabel);
          studentLabelCell.setPaddingTop(-2f);
          summaryTable.addCell(studentLabelCell);

          PdfPCell totalCellLabel = new PdfPCell();
          Paragraph totalLabel =
              new Paragraph("TOTAL", FontFactory.getFont(FontFactory.TIMES_BOLD, summaryFontSize));
          totalCellLabel.addElement(totalLabel);
          totalCellLabel.setPaddingTop(-2f);
          summaryTable.addCell(totalCellLabel);

          int deptListCounter = 0;
          for(String deptOfTheList : deptList) {
            PdfPCell deptCell = new PdfPCell();
            Paragraph deptParagraph =
                new Paragraph(deptWithDeptNameMap.get(deptOfTheList), FontFactory.getFont(
                    FontFactory.TIMES_ROMAN, summaryFontSize));
            deptCell.addElement(deptParagraph);
            deptCell.setPaddingTop(-2f);
            summaryTable.addCell(deptCell);
            PdfPCell yearSemesterCell = new PdfPCell();
            Paragraph yearSemesterCellParagraph =
                new Paragraph(deptWithYearSemesterMap.get(deptOfTheList), FontFactory.getFont(
                    FontFactory.TIMES_ROMAN, summaryFontSize));
            yearSemesterCell.addElement(yearSemesterCellParagraph);
            yearSemesterCell.setPaddingTop(-2f);
            summaryTable.addCell(yearSemesterCell);
            PdfPCell studentCell = new PdfPCell();
            String studentListInString = "";
            java.util.List<String> studentList = deptStudentListMap.get(deptOfTheList);
            Collections.sort(studentList);
            totalStudent += studentList.size();
            int studentCounter = 0;
            for(String studentOfTheList : studentList) {
              if(studentCounter == 0) {
                studentListInString = studentListInString + studentOfTheList;
                studentCounter += 1;
              }
              else {
                if(studentList.size() > 10) {
                  studentListInString = studentListInString + "," + studentOfTheList;

                }
                else {
                  studentListInString = studentListInString + ", " + studentOfTheList;

                }

              }
            }

            deptListCounter += 1;
            studentListInString = studentListInString + " = " + studentList.size();

            if(studentList.size() > 10) {
              summaryFontSize = 9.0f;
            }

            Paragraph studentCellParagraph =
                new Paragraph(studentListInString, FontFactory.getFont(FontFactory.TIMES_ROMAN,
                    summaryFontSize));
            studentCell.addElement(studentCellParagraph);
            studentCell.setPaddingTop(-2f);
            summaryTable.addCell(studentCell);

            if(deptListCounter == deptList.size()) {
              PdfPCell totalCell = new PdfPCell();
              Paragraph totalLabels =
                  new Paragraph("" + totalStudent, FontFactory.getFont(FontFactory.TIMES_BOLD,
                      summaryFontSize));
              totalLabels.setAlignment(Element.ALIGN_CENTER);
              totalCell.addElement(totalLabels);
              totalCell.setPaddingTop(-2f);
              summaryTable.addCell(totalCell);
            }
            else {
              PdfPCell totalCell = new PdfPCell();
              Paragraph totalLabels = new Paragraph("");
              totalCell.addElement(totalLabels);
              summaryTable.addCell(totalCell);
            }

          }
          summaryTable.setSpacingAfter(8f);
          document.add(summaryTable);
          // end of getting the summary

          for(int i = 1; i <= room.getTotalRow()+1; i++) {
            Paragraph tableRow = new Paragraph();
            PdfPTable mainTable = new PdfPTable(room.getTotalColumn() / 2);
            mainTable.setWidthPercentage(100);
            PdfPCell[] cellsForMainTable = new PdfPCell[room.getTotalColumn() / 2];
            int cellCounter = 0;
            for(int j = 1; j <= room.getTotalColumn(); j++) {
              cellsForMainTable[cellCounter] = new PdfPCell();
              PdfPTable table = new PdfPTable(2);
              SeatPlan seatPlan = roomRowColWithSeatPlanMap.get(room.getId() + "" + i + "" + j);
              // int ifSeatPlanExists =
              // mSeatPlanManager.checkIfExistsBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);
              if(seatPlan == null) {
                PdfPCell emptyCell = new PdfPCell();
                String emptyString = "  ";
                Paragraph emptyParagraph = new Paragraph(emptyString);
                Paragraph emptyParagraph2 = new Paragraph("  ");
                emptyParagraph.setPaddingTop(-5f);
                emptyParagraph2.setPaddingTop(-10f);
                emptyCell.addElement(emptyParagraph);
                emptyCell.addElement(emptyParagraph2);
                if(i==1)
                  emptyCell.setBorder(Rectangle.NO_BORDER);

                table.addCell(emptyCell);
                // cellsForMainTable[cellCounter].addElement(emptyCell);
              }
              else {
                /*
                 * java.util.List<SeatPlan> seatPlanLists =
                 * mSeatPlanManager.getBySemesterGroupTypeRoomRowAndCol
                 * (pSemesterId,groupNo,type,room.getId(),i,j);
                 * 
                 * SeatPlan seatPlan = seatPlanLists.get(0);
                 */

                Student student = studentIdWIthStuddentInfoMap.get(seatPlan.getStudent().getId());
                Program program = programIdWithProgramInfoMap.get(student.getProgram().getId());

                PdfPCell upperCell = new PdfPCell();

                PdfPCell lowerCell = new PdfPCell();

                String upperPart =
                    program.getShortName().replace("BSc in ", "") + " " + student.getCurrentYear()
                        + "/" + student.getCurrentAcademicSemester();
                Paragraph upperParagraph =
                    new Paragraph(upperPart, FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize));
                upperParagraph.setPaddingTop(-5f);
                String lowerPart = student.getId();
                Paragraph lowerParagraph =
                    new Paragraph(lowerPart, FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize));
                lowerParagraph.setPaddingTop(-10f);
                upperCell.addElement(upperParagraph);
                upperCell.addElement(lowerParagraph);
                upperCell.setPaddingTop(-5f);

                table.addCell(upperCell);
              }

              j = j + 1;
              SeatPlan seatPlan2 = roomRowColWithSeatPlanMap.get(room.getId() + "" + i + "" + j);

              if(seatPlan2 == null) {
                PdfPCell emptyCell = new PdfPCell();
                String emptyString = "  ";
                Paragraph emptyParagraph = new Paragraph(emptyString);
                Paragraph emptyParagraph2 = new Paragraph("  ");
                emptyParagraph.setPaddingTop(-5f);
                emptyParagraph2.setPaddingTop(-10f);
                emptyCell.addElement(emptyParagraph);
                emptyCell.addElement(emptyParagraph2);
                if(i==1)
                  emptyCell.setBorder(Rectangle.NO_BORDER);
                table.addCell(emptyCell);

              }
              else {

                Student student2 = studentIdWIthStuddentInfoMap.get(seatPlan2.getStudent().getId());
                Program program2 = programIdWithProgramInfoMap.get(student2.getProgram().getId());

                PdfPCell upperCell = new PdfPCell();
                upperCell.setColspan(10);
                PdfPCell lowerCell = new PdfPCell();
                lowerCell.setColspan(10);
                String upperPart =
                    program2.getShortName().replace("BSc in ", "") + " "
                        + student2.getCurrentYear() + "/" + student2.getCurrentAcademicSemester();
                Paragraph upperParagraph =
                    new Paragraph(upperPart, FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize));
                upperParagraph.setPaddingTop(-5f);
                String lowerPart = student2.getId();
                Paragraph lowerParagraph =
                    new Paragraph(lowerPart, FontFactory.getFont(FontFactory.TIMES_ROMAN, fontSize));
                lowerParagraph.setPaddingTop(-10f);
                upperCell.addElement(upperParagraph);
                upperCell.addElement(lowerParagraph);
                upperCell.setPaddingTop(-5f);
                table.addCell(upperCell);
              }
              table.setPaddingTop(-2f);
              cellsForMainTable[cellCounter].addElement(table);
              cellsForMainTable[cellCounter].setBorder(PdfPCell.NO_BORDER);
              mainTable.addCell(cellsForMainTable[cellCounter]);

              cellCounter += 1;
            }


            tableRow.add(mainTable);
            tableRow.setSpacingAfter(3f);

            document.add(tableRow);
          }

          PdfPTable footer = new PdfPTable(3);
          float footerFontSize;
          if(deptList.size() >= 9) {
            footerFontSize = 10.0f;
          }
          else {
            footerFontSize = 12.0f;

          }

          PdfPCell dateAndPreparedByCell = new PdfPCell();
          dateAndPreparedByCell.addElement(new Phrase("Date:", FontFactory.getFont(
              FontFactory.TIMES_BOLD, footerFontSize)));
          dateAndPreparedByCell.addElement(new Phrase("Prepared By", FontFactory.getFont(
              FontFactory.TIMES_BOLD, footerFontSize)));
          dateAndPreparedByCell.setBorder(Rectangle.NO_BORDER);
          footer.addCell(dateAndPreparedByCell);
          // todo: remove border
          PdfPCell spaceCell = new PdfPCell();
          Paragraph spaceParagraph = new Paragraph(" ");
          spaceCell.addElement(spaceParagraph);
          spaceCell.setBorder(PdfPCell.NO_BORDER);
          PdfPCell checkedByCell = new PdfPCell();
          Paragraph pCheckedBy =
              new Paragraph("Checked By", FontFactory.getFont(FontFactory.TIMES_BOLD,
                  footerFontSize));
          checkedByCell.addElement(spaceParagraph);
          checkedByCell.addElement(pCheckedBy);
          checkedByCell.setBorder(PdfPCell.NO_BORDER);
          footer.addCell(checkedByCell);
          PdfPCell controllerCell = new PdfPCell();
          Paragraph pController =
              new Paragraph("Controller of Examinations", FontFactory.getFont(
                  FontFactory.TIMES_BOLD, footerFontSize));
          controllerCell.addElement(spaceParagraph);
          controllerCell.addElement(pController);
          controllerCell.setBorder(PdfPCell.NO_BORDER);
          footer.addCell(controllerCell);

          if(room.getCapacity() <= 40) {
            footer.setSpacingBefore(40f);

          }
          else {
            footer.setSpacingBefore(15.0f);

          }

          /* document.add(footer); */
          long endTimeInRoom = System.currentTimeMillis();
          long totalTimeInRoom = endTimeInRoom - startTimeInRoom;
          document.newPage();

        }

        // just for debug purpose
        /*
         * if(roomCounter==26){ break; }
         */

      }
    }

    long endTimeOfTheMainAlgorithm = System.currentTimeMillis();
    long totalTimeOfTHeMainAlgorithm = endTimeOfTheMainAlgorithm - startTimeOfTheMainAlgorithm;

    double totalTimeTaken = totalTimeOfTHeMainAlgorithm/60000;
    long exp = totalTimeOfTHeMainAlgorithm;
    document.close();
    baos.writeTo(pOutputStream);
  }

  class MyFooter extends PdfPageEventHelper {
    Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);

    public void onEndPage(PdfWriter writer, Document document) {
      PdfContentByte cb = writer.getDirectContent();
      Paragraph pDate = new Paragraph("Date:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
      Paragraph pPreparedBy =
          new Paragraph("Prepared by:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
      Paragraph pCheckedBy =
          new Paragraph("Checked by:", FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
      Paragraph pController =
          new Paragraph("Controller of Examinations", FontFactory.getFont(FontFactory.TIMES_BOLD,
              12));

      PdfPCell dateAndPreparedByCell = new PdfPCell();
      dateAndPreparedByCell.addElement(new Phrase("Date:", FontFactory.getFont(
          FontFactory.TIMES_BOLD, 12)));
      dateAndPreparedByCell.addElement(new Phrase("Prepared By", FontFactory.getFont(
          FontFactory.TIMES_BOLD, 12)));
      dateAndPreparedByCell.setBorder(Rectangle.NO_BORDER);
      Phrase leftPhrase = new Phrase();
      leftPhrase.add(pDate);
      Phrase leftPhraseBottom = new Phrase();
      leftPhraseBottom.add(pPreparedBy);

      Phrase middlePhraseBottom = new Phrase();
      middlePhraseBottom.add(pCheckedBy);

      Phrase rightPhraseButtom = new Phrase();
      rightPhraseButtom.add(pController);

      ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, leftPhrase, (document.left()) / 3,
          document.bottom() - 1, 0);

      ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, leftPhraseBottom, (document.left()) / 3,
          document.bottom() - 20, 0);

      ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, middlePhraseBottom,
          (document.right()) / 2, document.bottom() - 20, 0);

      ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, rightPhraseButtom,
          (document.right()) / 1, document.bottom() - 20, 0);

    }
  }

  @Override
  public void createSeatPlanAttendenceReport(Integer pProgramType, Integer pSemesterId,
      Integer pExamType, String pExamDate, OutputStream pOutputStream) throws IOException,
      DocumentException {
    /*
     * java.util.List<ExamRoutineDto> examRoutines = new ArrayList<>(); List<SeatPlan> seatPlans =
     * new ArrayList<>(); String universityName = new
     * String("Ahsanullah University of Science and Technology") ; String attendenceSheet = new
     * String("ATTENDANCE SHEET"); String original = new String("ORIGINAL"); String duplicate = new
     * String("DUPLICATE"); String roomNo=new String("ROOM NO..."); String year=new
     * String("Year: "); String semester=new String("Semester: "); String courseNo=new
     * String("Course No: "); String courseTitle=new String("Course Title: "); Semester
     * semesterManager = mSemesterManager.get(pSemesterId); String examName = new
     * String("Semester Final Examination, "+semesterManager.getName()); String studentNo = new
     * String("Student No."); String signatureOfTheStudents = new
     * String("Signature of the examinee"); String numberOfTheExamineesRegistered=new
     * String("* Number of the examinees registered: "); String numberOfTheExamineesAbsent = new
     * String("* Number of the examinees absent: "); String numberofTheExamineesPresent = new
     * String("* Number of the examinees present: "); String signatureOfTheInvigilator = new
     * String("Signature of the Invigilator"); String nameOfTheInvigilator=new
     * String("Name of the Invigilator");
     *//*
        * String tr="<tr>"; String trEnd="</tr>"; String td="<td>"; String tdEnd="</td>";
        *//*
           * if(pExamDate.equals("NULL")){ examRoutines=mExamRoutineManager.
           * getExamRoutineBySemesterAndExamTypeOrderByExamDateAndProgramIdAndCourseId
           * (pSemesterId,pExamType); seatPlans =
           * mSeatPlanManager.getSeatPlanOrderByExamDateAndCourseAndYearAndSemesterAndStudentId
           * (pSemesterId,pExamType); }else{
           * 
           * } Document document = new Document(); document.addTitle("Seat Plan Attendence Sheet");
           * 
           * ByteArrayOutputStream baos = new ByteArrayOutputStream(); PdfWriter writer =
           * PdfWriter.getInstance(document,baos);
           *//*
              * MyFooter event = new MyFooter(); writer.setPageEvent(event);
              *//*
                 * document.open(); document.setPageSize(PageSize.A4);
                 * 
                 * 
                 * int routineCounter=0; for(ExamRoutineDto routine: examRoutines){
                 * routineCounter+=1; if(routineCounter==200){ break; } Course course =
                 * mCourseManager.get(routine.getCourseId());
                 * 
                 * while(true){ boolean courseMismatchfound=false; SeatPlan seatPlan =
                 * seatPlans.get(0); int studentYear = seatPlan.getStudent().getAcademicYear(); int
                 * studentSemester = seatPlan.getStudent().getAcademicSemester(); int courseYear =
                 * course.getYear(); int courseSemester = course.getSemester(); int studentProgramId
                 * = seatPlan.getStudent().getProgram().getId(); int routineProgramId =
                 * routine.getProgramId(); if(studentYear==courseYear &&
                 * studentSemester==courseSemester && studentProgramId==routineProgramId){
                 * 
                 * PdfPTable table = new PdfPTable(2); table.setWidthPercentage(110); PdfPTable
                 * tableOne = new PdfPTable(1); PdfPTable tableTwo = new PdfPTable(1); Paragraph
                 * universityParagraph = new Paragraph(universityName);
                 * universityParagraph.setAlignment(Element.ALIGN_CENTER);
                 * universityParagraph.setFont(FontFactory.getFont(FontFactory.TIMES,12)); PdfPCell
                 * cellUniversityName= new PdfPCell();
                 * cellUniversityName.addElement(universityParagraph);
                 * cellUniversityName.setPaddingBottom(5); tableOne.addCell(cellUniversityName);
                 * tableTwo.addCell(cellUniversityName);
                 * 
                 * Paragraph originalParagraph = new Paragraph(original);
                 * originalParagraph.setAlignment(Element.ALIGN_CENTER);
                 * originalParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD,14));
                 * PdfPCell originalCell = new PdfPCell();
                 * originalCell.addElement(originalParagraph); originalCell.setPaddingTop(-3);
                 * tableOne.addCell(originalCell);
                 * 
                 * Paragraph duplicateParagraph= new Paragraph(duplicate);
                 * duplicateParagraph.setAlignment(Element.ALIGN_CENTER);
                 * duplicateParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD,14));
                 * PdfPCell duplicateCell = new PdfPCell();
                 * duplicateCell.addElement(duplicateParagraph); duplicateCell.setPaddingTop(-3);
                 * tableTwo.addCell(duplicateCell);
                 * 
                 * PdfPCell upperCell=new PdfPCell();
                 * 
                 * Paragraph attendanceParagraph=new Paragraph(attendenceSheet);
                 * attendanceParagraph.setAlignment(Element.ALIGN_CENTER);
                 * attendanceParagraph.setFont(FontFactory.getFont(FontFactory.TIMES,14)); PdfPCell
                 * attendanceCell = new PdfPCell(attendanceParagraph);
                 * attendanceCell.setBorder(Rectangle.NO_BORDER);
                 * upperCell.addElement(attendanceParagraph);
                 *//*
                    * tableOne.addCell(attendanceCell); tableTwo.addCell(attendanceCell);
                    *//*
                       * 
                       * 
                       * ClassRoom room = mRoomManager.get(seatPlan.getClassRoomId()); Paragraph
                       * roomNoParagraph= new Paragraph(roomNo+ room.getRoomId());
                       * roomNoParagraph.setAlignment(Element.ALIGN_CENTER);
                       * roomNoParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD));
                       * PdfPCell roomCell = new PdfPCell(roomNoParagraph);
                       * roomCell.setBorder(Rectangle.NO_BORDER);
                       *//*
                          * tableOne.addCell(roomCell); tableTwo.addCell(roomCell);
                          *//*
                             * upperCell.addElement(roomNoParagraph);
                             * 
                             * 
                             * Paragraph date = new Paragraph("Date: "+routine.getExamDate());
                             * date.setAlignment(Element.ALIGN_CENTER);
                             * date.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD)); PdfPCell
                             * dateCell = new PdfPCell(date);
                             * dateCell.setBorder(Rectangle.NO_BORDER);
                             *//*
                                * tableOne.addCell(dateCell); tableTwo.addCell(dateCell);
                                *//*
                                   * upperCell.addElement(date);
                                   * 
                                   * 
                                   * Paragraph examParagraph = new Paragraph(examName);
                                   * examParagraph.setAlignment(Element.ALIGN_CENTER);
                                   * examParagraph.
                                   * setFont(FontFactory.getFont(FontFactory.TIMES_BOLD,14));
                                   * PdfPCell examCell = new PdfPCell(examParagraph);
                                   *//*
                                      * tableOne.addCell(examCell); tableTwo.addCell(examCell);
                                      *//*
                                         * upperCell.addElement(examParagraph);
                                         * 
                                         * Paragraph yearSemesterParagraph = new
                                         * Paragraph(year+seatPlan.getStudent().getAcademicYear()+
                                         * "                             "
                                         * +"                "+"    "+
                                         * semester+seatPlan.getStudent().getAcademicSemester());
                                         * yearSemesterParagraph.setAlignment(Element.ALIGN_LEFT);
                                         * yearSemesterParagraph
                                         * .setFont(FontFactory.getFont(FontFactory.TIMES_BOLD,12));
                                         * PdfPCell yearSemesterCell = new
                                         * PdfPCell(yearSemesterParagraph);
                                         *//*
                                            * tableOne.addCell(yearSemesterCell);
                                            * tableTwo.addCell(yearSemesterCell);
                                            *//*
                                               * upperCell.addElement(yearSemesterParagraph);
                                               * 
                                               * Paragraph departmentParagraph = new
                                               * Paragraph("Department: "
                                               * +seatPlan.getStudent().getProgram
                                               * ().getShortName());
                                               * departmentParagraph.setAlignment
                                               * (Element.ALIGN_LEFT);
                                               * departmentParagraph.setFont(FontFactory
                                               * .getFont(FontFactory.TIMES_BOLD,12)); PdfPCell
                                               * departmentCell = new PdfPCell(departmentParagraph);
                                               *//*
                                                  * tableOne.addCell(departmentCell);
                                                  * tableTwo.addCell(departmentCell);
                                                  *//*
                                                     * upperCell.addElement(departmentParagraph);
                                                     * 
                                                     * 
                                                     * Paragraph courseNoParagraph = new
                                                     * Paragraph(courseNo+ course.getNo());
                                                     * courseNoParagraph
                                                     * .setAlignment(Element.ALIGN_LEFT);
                                                     * courseNoParagraph
                                                     * .setFont(FontFactory.getFont(
                                                     * FontFactory.TIMES_BOLD,12)); PdfPCell
                                                     * courseNoCell = new
                                                     * PdfPCell(courseNoParagraph);
                                                     *//*
                                                        * tableOne.addCell(courseNoCell);
                                                        * tableTwo.addCell(courseNoCell);
                                                        *//*
                                                           * upperCell.addElement(courseNoParagraph);
                                                           * 
                                                           * Paragraph courseTitleParagrah = new
                                                           * Paragraph
                                                           * (courseTitle+course.getTitle());
                                                           * courseTitleParagrah
                                                           * .setAlignment(Element.ALIGN_LEFT);
                                                           * courseTitleParagrah
                                                           * .setFont(FontFactory.
                                                           * getFont(FontFactory.TIMES_BOLD,12));
                                                           * PdfPCell courseTitleCell = new
                                                           * PdfPCell(courseTitleParagrah);
                                                           *//*
                                                              * tableOne.addCell(courseTitleCell);
                                                              * tableTwo.addCell(courseTitleCell);
                                                              *//*
                                                                 * upperCell.addElement(
                                                                 * courseTitleParagrah);
                                                                 * upperCell.setPaddingBottom(10);
                                                                 * 
                                                                 * tableOne.addCell(upperCell);
                                                                 * tableTwo.addCell(upperCell);
                                                                 * 
                                                                 * float[] attendenceSheetColSpan =
                                                                 * {4,7}; PdfPTable
                                                                 * attendanceSheetTable = new
                                                                 * PdfPTable
                                                                 * (attendenceSheetColSpan);
                                                                 * attendanceSheetTable
                                                                 * .setWidthPercentage(100);
                                                                 * PdfPCell sttudentNoCell = new
                                                                 * PdfPCell(new
                                                                 * Paragraph(studentNo,FontFactory
                                                                 * .getFont
                                                                 * (FontFactory.TIMES_BOLD,12)));
                                                                 * PdfPCell signatureCell = new
                                                                 * PdfPCell(new
                                                                 * Paragraph(signatureOfTheStudents
                                                                 * ,FontFactory
                                                                 * .getFont(FontFactory.TIMES_BOLD
                                                                 * ,12)));
                                                                 * attendanceSheetTable.addCell
                                                                 * (sttudentNoCell);
                                                                 * attendanceSheetTable
                                                                 * .addCell(signatureCell); Integer
                                                                 * seatPlanRoomId =
                                                                 * seatPlan.getClassRoom().getId();
                                                                 * int counter=0; int
                                                                 * studentCounter=0; while(true){
                                                                 * SeatPlan seatPlanInner =
                                                                 * seatPlans.get(0);
                                                                 * if(seatPlanInner
                                                                 * .getClassRoom().getId
                                                                 * ().equals(seatPlanRoomId) &&
                                                                 * counter!=20 &&
                                                                 * seatPlan.getStudent
                                                                 * ().getAcademicYear()==courseYear
                                                                 * && seatPlan.getStudent().
                                                                 * getAcademicSemester
                                                                 * ()==courseSemester){ PdfPCell
                                                                 * innerCellOne = new PdfPCell(new
                                                                 * Paragraph
                                                                 * (seatPlanInner.getStudent
                                                                 * ().getId())); PdfPCell
                                                                 * innerCellTwo = new PdfPCell(new
                                                                 * Paragraph("  "));
                                                                 * attendanceSheetTable
                                                                 * .addCell(innerCellOne);
                                                                 * attendanceSheetTable
                                                                 * .addCell(innerCellTwo);
                                                                 * seatPlans.remove(0);
                                                                 * studentCounter+=1; counter+=1;
                                                                 * }else{ if(counter==20){ break;
                                                                 * }else{ PdfPCell innerCellOne =
                                                                 * new PdfPCell(new
                                                                 * Paragraph("   ")); PdfPCell
                                                                 * innerCellTwo = new PdfPCell(new
                                                                 * Paragraph("  "));
                                                                 * attendanceSheetTable
                                                                 * .addCell(innerCellOne);
                                                                 * attendanceSheetTable
                                                                 * .addCell(innerCellTwo);
                                                                 * counter+=1; } } }
                                                                 * 
                                                                 * PdfPCell attendenceCellTable =
                                                                 * new
                                                                 * PdfPCell(attendanceSheetTable);
                                                                 * tableOne
                                                                 * .addCell(attendenceCellTable);
                                                                 * tableTwo
                                                                 * .addCell(attendenceCellTable);
                                                                 * 
                                                                 * 
                                                                 * Paragraph
                                                                 * numberOfExamineeParagraph = new
                                                                 * Paragraph
                                                                 * (numberOfTheExamineesRegistered
                                                                 * +studentCounter); Paragraph
                                                                 * numberOfAbsentParagraph= new
                                                                 * Paragraph
                                                                 * (numberOfTheExamineesAbsent);
                                                                 * PdfPCell studentInfoCell = new
                                                                 * PdfPCell();
                                                                 * studentInfoCell.addElement
                                                                 * (numberOfExamineeParagraph);
                                                                 * studentInfoCell
                                                                 * .addElement(numberOfAbsentParagraph
                                                                 * );
                                                                 * tableOne.addCell(studentInfoCell
                                                                 * );
                                                                 * tableTwo.addCell(studentInfoCell
                                                                 * );
                                                                 * 
                                                                 * Paragraph numberOfPresent = new
                                                                 * Paragraph
                                                                 * (numberofTheExamineesPresent);
                                                                 * PdfPCell presentCell = new
                                                                 * PdfPCell();
                                                                 * presentCell.addElement
                                                                 * (numberOfPresent);
                                                                 * tableOne.addCell(presentCell);
                                                                 * tableTwo.addCell(presentCell);
                                                                 * 
                                                                 * Paragraph underLine = new
                                                                 * Paragraph
                                                                 * ("___________________________");
                                                                 * underLine
                                                                 * .setAlignment(Element.ALIGN_RIGHT
                                                                 * ); Paragraph
                                                                 * signatureInvigilatorParagraph =
                                                                 * new
                                                                 * Paragraph(signatureOfTheInvigilator
                                                                 * ); signatureInvigilatorParagraph.
                                                                 * setAlignment
                                                                 * (Element.ALIGN_RIGHT);
                                                                 * 
                                                                 * PdfPCell invigilatorSignatureCell
                                                                 * = new PdfPCell();
                                                                 * invigilatorSignatureCell
                                                                 * .addElement(underLine);
                                                                 * invigilatorSignatureCell
                                                                 * .addElement
                                                                 * (signatureInvigilatorParagraph);
                                                                 * invigilatorSignatureCell
                                                                 * .setBorder(Rectangle.NO_BORDER);
                                                                 * invigilatorSignatureCell
                                                                 * .setPaddingTop(15);
                                                                 * tableOne.addCell
                                                                 * (invigilatorSignatureCell);
                                                                 * tableTwo
                                                                 * .addCell(invigilatorSignatureCell
                                                                 * );
                                                                 * 
                                                                 * Paragraph
                                                                 * invigilatorNameParagraph = new
                                                                 * Paragraph(nameOfTheInvigilator);
                                                                 * invigilatorNameParagraph
                                                                 * .setAlignment
                                                                 * (Element.ALIGN_RIGHT); PdfPCell
                                                                 * nameCell = new PdfPCell();
                                                                 * nameCell.addElement(underLine);
                                                                 * nameCell
                                                                 * .addElement(invigilatorNameParagraph
                                                                 * ); nameCell.setBorder(Rectangle.
                                                                 * NO_BORDER);
                                                                 * nameCell.setPaddingTop(8);
                                                                 * tableOne.addCell(nameCell);
                                                                 * tableTwo.addCell(nameCell);
                                                                 * 
                                                                 * PdfPCell cellOne = new
                                                                 * PdfPCell(tableOne);
                                                                 * cellOne.setBorder
                                                                 * (Rectangle.NO_BORDER); PdfPCell
                                                                 * cellTwo = new PdfPCell(tableTwo);
                                                                 * cellTwo
                                                                 * .setBorder(Rectangle.NO_BORDER);
                                                                 * cellOne.setPaddingRight(5);
                                                                 * cellTwo.setPaddingLeft(5);
                                                                 * table.addCell(cellOne);
                                                                 * table.addCell(cellTwo);
                                                                 * 
                                                                 * document.add(table);
                                                                 * document.newPage(); //break;
                                                                 * 
                                                                 * } else{ break; }
                                                                 * 
                                                                 * 
                                                                 * }
                                                                 * 
                                                                 * 
                                                                 * 
                                                                 * 
                                                                 * 
                                                                 * }
                                                                 * 
                                                                 * document.close();
                                                                 * baos.writeTo(pOutputStream);
                                                                 */
  }

  @Override
  public void createSeatPlanAttendencePdfReport(Integer pProgramType, Integer pSemesterId,
      Integer pExamType, String pExamDate, OutputStream pOutputStream) throws IOException,
      DocumentException {

    java.util.List<SeatPlanReportDto> seatPlanReports = new ArrayList<>();
    String universityName = new String("Ahsanullah University of Science and Technology");
    String attendenceSheet = new String("ATTENDANCE SHEET");
    String original = new String("ORIGINAL");
    String duplicate = new String("DUPLICATE");
    String roomNo = new String("ROOM NO...");
    String year = new String("Year: ");
    String semester = new String("Semester: ");
    String courseNo = new String("Course No: ");
    String courseTitle = new String("Course Title: ");
    Semester semesterManager = mSemesterManager.get(pSemesterId);
    String examName = "";
    if(pExamType == 1) {
      examName = "Semester Final Examination, " + semesterManager.getName();
    }
    else {
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

    seatPlanReports =
        mSeatPlanReportManager.getSeatPlanDataForAttendenceSheet(pSemesterId, pExamType, pExamDate);

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
    while(true) {
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
      roomNoParagraph.setAlignment(Element.ALIGN_CENTER);
      roomNoParagraph.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD));
      PdfPCell roomCell = new PdfPCell(roomNoParagraph);
      roomCell.setBorder(Rectangle.NO_BORDER);
      /*
       * tableOne.addCell(roomCell); tableTwo.addCell(roomCell);
       */
      upperCell.addElement(roomNoParagraph);

      Paragraph date = new Paragraph("Date: " + seatPlanReportDto.getExamDate());
      date.setAlignment(Element.ALIGN_CENTER);
      date.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD));
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
      Paragraph semesterP =
          new Paragraph("            " + "              " + "     " + semester + "  ", lightFont);
      Paragraph semesterB =
          new Paragraph(seatPlanReportDto.getCurrentSemester().toString(), boldFont);
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
      Paragraph departmentParagraph =
          new Paragraph(" " + seatPlanReportDto.getProgramName(), boldFont);
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
      Paragraph courseTitleParagrah =
          new Paragraph("" + seatPlanReportDto.getCourseTitle(), boldFont);
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
      PdfPCell sttudentNoCell =
          new PdfPCell(new Paragraph(studentNo, FontFactory.getFont(FontFactory.TIMES_BOLD, 12)));
      sttudentNoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      Paragraph studentsSignutureSection = new Paragraph("Signature of the examinee");
      studentsSignutureSection.setAlignment(Element.ALIGN_CENTER);
      studentsSignutureSection.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
      PdfPCell signatureCell =
          new PdfPCell(new Paragraph(signatureOfTheStudents, FontFactory.getFont(
              FontFactory.TIMES_BOLD, 12)));
      signatureCell.setHorizontalAlignment(Element.ALIGN_CENTER);
      attendanceSheetTable.addCell(sttudentNoCell);
      attendanceSheetTable.addCell(signatureCell);
      String classRoomNo = seatPlanReportDto.getRoomNo();
      String courseNoOfStudent = seatPlanReportDto.getCourseNo();
      int counter = 0;
      int studentCounter = 0;
      while(true) {
        SeatPlanReportDto seatPlanInner = new SeatPlanReportDto();
        if(seatPlanReports.size() != 0) {
          seatPlanInner = seatPlanReports.get(0);
          if(seatPlanInner.getRoomNo().equals(classRoomNo)
              && seatPlanInner.getCourseNo().equals(courseNoOfStudent) && counter != 20
              && seatPlanReports.size() != 0) {
            PdfPCell innerCellOne = new PdfPCell();
            if(seatPlanInner.getStudentType().equals("RA")) {
              innerCellOne.addElement(new Paragraph(seatPlanInner.getStudentId(), FontFactory
                  .getFont(FontFactory.TIMES_BOLD)));
            }
            else {
              innerCellOne.addElement(new Paragraph(seatPlanInner.getStudentId(), FontFactory
                  .getFont(FontFactory.TIMES)));
            }
            innerCellOne.setPaddingTop(-4);
            PdfPCell innerCellTwo = new PdfPCell(new Paragraph("  "));
            attendanceSheetTable.addCell(innerCellOne);
            attendanceSheetTable.addCell(innerCellTwo);
            seatPlanReports.remove(0);
            studentCounter += 1;
            counter += 1;
          }
          else {
            if(counter == 20) {
              break;
            }
            else {
              PdfPCell innerCellOne = new PdfPCell(new Paragraph("   "));
              PdfPCell innerCellTwo = new PdfPCell(new Paragraph("  "));
              attendanceSheetTable.addCell(innerCellOne);
              attendanceSheetTable.addCell(innerCellTwo);
              counter += 1;
            }
          }
        }
        else {

          if(counter == 20) {
            break;
          }
          else {
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

      Paragraph numberOfExamineeParagraph =
          new Paragraph(numberOfTheExamineesRegistered + studentCounter);
      Paragraph numberOfAbsentParagraph = new Paragraph(numberOfTheExamineesAbsent);
      PdfPCell studentInfoCell = new PdfPCell();
      studentInfoCell.addElement(numberOfExamineeParagraph);
      studentInfoCell.addElement(numberOfAbsentParagraph);
      studentInfoCell.setPaddingTop(-3);
      studentInfoCell.setPaddingBottom(4);
      tableOne.addCell(studentInfoCell);
      tableTwo.addCell(studentInfoCell);

      Paragraph numberOfPresent = new Paragraph(numberofTheExamineesPresent);
      PdfPCell presentCell = new PdfPCell();
      presentCell.setPaddingTop(-3);
      presentCell.setPaddingBottom(4);
      presentCell.addElement(numberOfPresent);
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

      if(seatPlanReports.size() == 0) {
        break;
      }

    }

    document.close();
    baos.writeTo(pOutputStream);

    long endTime = System.currentTimeMillis();
    long totalTime = endTime - startTime;

    System.out.println(totalTime);
  }

  @Override
  public void createSeatPlanTopSheetPdfReport(Integer pProgramType, Integer pSemesterId,
      Integer pExamType, String pExamDate, OutputStream pOutputStream) throws IOException,
      DocumentException {
    List<SeatPlanReportDto> seatPlans =
        mSeatPlanReportManager.getSeatPlanDataForTopSheet(pSemesterId, pExamType, pExamDate);

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
    while(true) {

      seatPlanCounter += 1;

      SeatPlanReportDto seatPlan = seatPlans.get(0);

      String examDate = seatPlan.getExamDate();

      Paragraph dueDateOfSubmission = new Paragraph("DUE DATE OF SUBMISSION :", duetFont);
      dueDateOfSubmission.setAlignment(Element.ALIGN_CENTER);
      PdfPCell dueDateCell = new PdfPCell();
      dueDateCell.addElement(dueDateOfSubmission);
      document.add(dueDateOfSubmission);

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

      if(pExamType == 1) {
        examName =
            new Chunk("TOP SHEET (FINAL EXAMINATION " + semester.getName().toUpperCase() + ")");
      }
      else {
        examName =
            new Chunk("TOP SHEET (CARRY/CLEARANCE/IMPROVEMENT EXAMINATION " + semester.getName()
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
      float[] upperTableWidth = new float[] {7f, 3f};

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

      /* main data table starting */
      float[] dataTableWidth = new float[] {8f, 2f, 2f};
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

      while(true) {
        if(seatPlans.size() != 0) {
          SeatPlanReportDto seatPlanReportDto = seatPlans.get(0);
          if(seatPlanReportDto.getCourseNo().equals(seatPlan.getCourseNo())
              && seatPlans.size() != 0) {
            Chunk studentId = new Chunk("");
            Paragraph studentIdParagraph = new Paragraph();

            for(int i = 0; i < 6; i++) {
              if(seatPlans.size() != 0) {
                SeatPlanReportDto seatPlanInnerReport = seatPlans.get(0);
                if(seatPlanInnerReport.getCourseNo().equals(seatPlan.getCourseNo())
                    && seatPlans.size() != 0) {
                  /*
                   * String stdIdTmp = ""; stdIdTmp = studentId; studentId = "";
                   */
                  if(seatPlans.size() != 1) {
                    if(seatPlanReportDto.getCourseNo().equals(seatPlans.get(1).getCourseNo())) {
                      if(seatPlanInnerReport.getStudentType().equals("RA")) {
                        studentId =
                            new Chunk(seatPlanInnerReport.getStudentId(),
                                FontFactory.getFont(FontFactory.TIMES_BOLD));

                      }
                      else {
                        studentId =
                            new Chunk(seatPlanInnerReport.getStudentId(),
                                FontFactory.getFont(FontFactory.TIMES));
                      }

                      studentIdParagraph.add(studentId);
                      studentId = new Chunk(", ");
                      studentIdParagraph.add(studentId);
                      // studentId = stdIdTmp + "" + seatPlanInnerReport.getStudentId() + ", ";

                    }
                    else {
                      if(seatPlanInnerReport.getStudentType().equals("RA")) {
                        studentId =
                            new Chunk(seatPlanInnerReport.getStudentId(),
                                FontFactory.getFont(FontFactory.TIMES_BOLD));

                      }
                      else {
                        studentId =
                            new Chunk(seatPlanInnerReport.getStudentId(),
                                FontFactory.getFont(FontFactory.TIMES));
                      }

                      studentIdParagraph.add(studentId);
                      studentId = new Chunk(" ");
                      studentIdParagraph.add(studentId);

                    }
                  }
                  else {
                    if(seatPlanInnerReport.getStudentType().equals("RA")) {
                      studentId =
                          new Chunk(seatPlanInnerReport.getStudentId(),
                              FontFactory.getFont(FontFactory.TIMES_BOLD));

                    }
                    else {
                      studentId =
                          new Chunk(seatPlanInnerReport.getStudentId(),
                              FontFactory.getFont(FontFactory.TIMES));
                    }

                    studentIdParagraph.add(studentId);
                    studentId = new Chunk(" ");
                    studentIdParagraph.add(studentId);

                  }

                  seatPlans.remove(0);
                  studentCounter += 1;
                  // rowCounter+=1;
                }
                else {
                  break;
                }
              }
              else {
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

          }
          else {
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
        }
        else {
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

        if(rowCounter == 23) {
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

      float[] bottomTableSize = new float[] {7f, 6f};
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
      List<CourseTeacher> examiners =
          mCourseTeacherManager.getCourseTeacher(pSemesterId, seatPlan.getCourseId());
      String teachersName = "";
      int teacherCounter = 0;
      Set<String> teacherNameSet = new HashSet<>();
      for(CourseTeacher teacher : examiners) {
        Employee employee = mEmployeeManager.get(teacher.getTeacherId());
        teacherCounter += 1;
        if(teacherCounter < examiners.size()) {
          teacherNameSet.add(employee.getEmployeeName());
        }
        else {
          teacherNameSet.add(employee.getEmployeeName());
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
      if(seatPlans.size() == 0) {
        break;
      }
      else {
        document.newPage();
      }

    }

    document.close();
    baos.writeTo(pOutputStream);
  }

  @Override
  public void createSeatPlanStickerReport(Integer pProgramType, Integer pSemesterId,
      Integer pExamType, String pExamDate, OutputStream pOutputStream) throws IOException,
      DocumentException {

    List<SeatPlanReportDto> seatPlans =
        mSeatPlanReportManager.getSeatPlanDataForSticker(pSemesterId, pExamType, pExamDate);
    Semester semester = mSemesterManager.get(pSemesterId);

    Document document = new Document();
    document.addTitle("Seat Plan Attendence Sheet");

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    /*
     * MyFooter event = new MyFooter(); writer.setPageEvent(event);
     */
    document.open();
    document.setPageSize(PageSize.A4);

    int totalRow;
    if(pExamType == 1) {
      totalRow = 5;
    }
    else {
      totalRow = 4;
    }

    Font lightFont = FontFactory.getFont(FontFactory.TIMES, 11);
    Font duetFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 13);
    Font universityNameFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);
    Font sponSoreFont = FontFactory.getFont(FontFactory.TIMES, 8);
    Font topSheetFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 13);
    Font boldFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 11);
    PdfPTable masterTable = new PdfPTable(2);
    masterTable.setWidthPercentage(108);

    int rowCounter = 0;
    while(true) {
      PdfPCell masterLeftCell = new PdfPCell();
      PdfPCell masterRightCell = new PdfPCell();
      PdfPTable leftTable = new PdfPTable(1);
      PdfPTable rightTable = new PdfPTable(1);
      PdfPCell leftCell = new PdfPCell();
      PdfPCell rightCell = new PdfPCell();
      leftCell.setPaddingRight(13.0f);
      rightCell.setPaddingLeft(13.0f);
      boolean dataEnd = false;
      for(int i = 1; i <= 2; i++) {
        if(seatPlans.size() != 0) {
          SeatPlanReportDto seatPlanReportDto = seatPlans.get(0);
          Paragraph paragraph = new Paragraph();
          String roomInfo = seatPlanReportDto.getRoomNo();
          paragraph.add(roomInfo);
          paragraph.setAlignment(Element.ALIGN_RIGHT);
          paragraph.setFont(boldFont);
          if(i == 1) {
            leftCell.addElement(paragraph);
          }
          else {
            rightCell.addElement(paragraph);
          }

          String semesterInfo;
          if(pExamType == 1) {
            semesterInfo = "Semester Final Examination, " + semester.getName();
          }
          else {
            semesterInfo = "Carry/Clearance/Improvement Examination, " + semester.getName();
          }

          paragraph = new Paragraph(semesterInfo, boldFont);
          paragraph.setAlignment(Element.ALIGN_CENTER);
          if(i == 1) {
            leftCell.addElement(paragraph);
          }
          else {
            rightCell.addElement(paragraph);
          }

          paragraph = new Paragraph("  ");
          if(i == 1) {
            leftCell.addElement(paragraph);
          }
          else {
            rightCell.addElement(paragraph);
          }

          String yearSemester =
              "  Year :" + seatPlanReportDto.getCurrentYear() + "           "
                  + "            Semester: " + seatPlanReportDto.getCurrentSemester();
          paragraph = new Paragraph(yearSemester, boldFont);
          if(i == 1) {
            leftCell.addElement(paragraph);
          }
          else {
            rightCell.addElement(paragraph);
          }

          String department = " Department :" + seatPlanReportDto.getProgramName();
          paragraph = new Paragraph(department, boldFont);
          if(i == 1) {
            leftCell.addElement(paragraph);
          }
          else {
            rightCell.addElement(paragraph);
          }

          paragraph = new Paragraph(" Student Id: " + seatPlanReportDto.getStudentId(), boldFont);
          if(i == 1) {
            leftCell.addElement(paragraph);
          }
          else {
            rightCell.addElement(paragraph);
          }

          paragraph = new Paragraph(" ");
          if(i == 1) {
            leftCell.addElement(paragraph);
          }
          else {
            rightCell.addElement(paragraph);
          }

          seatPlans.remove(0);

        }
        else {
          dataEnd = true;
          break;
        }
      }

      leftTable.addCell(leftCell);
      rightTable.addCell(rightCell);

      leftTable.setWidthPercentage(90);
      rightTable.setWidthPercentage(90);
      masterLeftCell.addElement(leftTable);
      masterRightCell.addElement(rightTable);
      masterRightCell.setPaddingLeft(7);

      masterLeftCell.setBorder(Rectangle.NO_BORDER);
      masterRightCell.setBorder(Rectangle.NO_BORDER);

      masterLeftCell.setPaddingBottom(30f);
      masterRightCell.setPaddingBottom(30f);
      masterTable.addCell(masterLeftCell);
      masterTable.addCell(masterRightCell);

      rowCounter += 1;

      if(rowCounter == totalRow || dataEnd == true) {
        document.add(masterTable);
        rowCounter = 0;
        masterTable = new PdfPTable(2);
        masterTable.setWidthPercentage(108);
      }

      if(seatPlans.size() == 0) {
        break;
      }
      else {
        document.newPage();

      }

    }

    document.close();
    baos.writeTo(pOutputStream);

  }
}
