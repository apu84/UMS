package org.ums.common.report.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.List;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.common.builder.SeatPlanBuilder;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.*;
import org.ums.manager.*;
import org.ums.services.academic.SeatPlanService;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by My Pc on 5/25/2016.
 */

@Service
public class SeatPlanReportGeneratorImpl implements SeatPlanReportGenerator{

  @Autowired
  private SeatPlanManager mManager;

  @Autowired
  private SeatPlanBuilder mBuilder;

  @Autowired
  private SeatPlanService mSeatPlanService;

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
  private SpStudentManager mSpStudentManager;

  public static final String DEST = "seat_plan_report.pdf";

  @Override
  public StreamingOutput createPdf(String dest, boolean noSeatPlanInfo, int pSemesterId, int groupNo, int type) throws Exception, IOException, DocumentException,WebApplicationException {



    Document document = new Document();
    document.addTitle("Seat Plan");

    PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(dest));
    document.open();
    document.setPageSize(PageSize.A4.rotate());


    document.newPage();

    Font f = new Font(Font.FontFamily.TIMES_ROMAN,10.0f,Font.BOLD, BaseColor.BLACK);
    //to do , mSemesterManager is not working.
    Semester mSemester = mSemesterManager.get(pSemesterId);

    String semesterName =mSemester.getName();

    String examDates = "Date: ";
    java.util.List<SeatPlanGroup> seatPlanGroup = mSeatPlanGroupManager.getBySemesterGroupAndType(pSemesterId,groupNo,type) ;
    java.util.List<ExamRoutineDto> examRoutines = mExamRoutineManager.getExamRoutine(pSemesterId,type);
    java.util.List<SeatPlan> seatPlans = mSeatPlanManager.getBySemesterAndGroupAndExamType(pSemesterId,groupNo,type);
    java.util.List<SpStudent> students = mSpStudentManager.getAll();
    java.util.List<Program> programs = mProgramManager.getAll();

    java.util.List<Integer> roomsOfTheSeatPlan = new ArrayList<>();
    Map<String,SpStudent> studentIdWIthStuddentInfoMap = new HashMap<>();
    Map<String,SeatPlan> roomRowColWithSeatPlanMap = new HashMap<>();
    Map<Integer,Program> programIdWithProgramInfoMap = new HashMap<>();

    for(SeatPlan seatPlan: seatPlans){
      roomsOfTheSeatPlan.add(seatPlan.getClassRoom().getId());
      String roomRowCol = seatPlan.getClassRoom().getId()+""+seatPlan.getRowNo()+""+seatPlan.getColumnNo();
      roomRowColWithSeatPlanMap.put(roomRowCol,seatPlan);
    }

    for(SpStudent student: students){
      studentIdWIthStuddentInfoMap.put(student.getId(),student);
    }
    for(Program program: programs){
      programIdWithProgramInfoMap.put(program.getId(),program);
    }

    int routineCounter=0;
    for(ExamRoutineDto routine:examRoutines){
      SeatPlanGroup group = seatPlanGroup.get(0);

      if(routine.getProgramId() == group.getProgram().getId() && routine.getCourseYear()==group.getAcademicYear() && routine.getCourseSemester()== group.getAcademicSemester()){

        if(routineCounter==examRoutines.size()){
          examDates = examDates+routine.getExamDate();
        }else{
          if(examDates.equals("Date: ")){
            examDates=examDates+routine.getExamDate();
          }else{
            examDates=examDates+","+routine.getExamDate();
          }

        }
      }
      routineCounter+=1;

    }

    if(noSeatPlanInfo){
      Chunk c = new Chunk("No SubGroup and No Seat Plan Information in the database, create one and then come back again!",f);
      c.setBackground(BaseColor.WHITE);
      Paragraph p= new Paragraph(c);
      p.setAlignment(Element.ALIGN_CENTER);
      document.add(p);
    }else{
      java.util.List<ClassRoom> rooms = mRoomManager.getAll() ;


      int roomCounter=0;
      for(ClassRoom room: rooms){
        float fontSize;
        roomCounter+=1;
        boolean checkIfRoomExistsInSeatPlan = roomsOfTheSeatPlan.contains(room.getId());
        //int checkIfRoomExists = mSeatPlanManager.checkIfExistsByRoomSemesterGroupExamType(room.getId(),pSemesterId,groupNo,type);

        if(checkIfRoomExistsInSeatPlan && room.getId()!=284  ){
          String roomHeader = "Room No: "+ room.getRoomNo();
          Paragraph pRoomHeader = new Paragraph(roomHeader,FontFactory.getFont(FontFactory.TIMES_BOLD,12));
          pRoomHeader.setAlignment(Element.ALIGN_CENTER);
          document.add(pRoomHeader);
          String semesterInfo="";
          if(type==1){
            semesterInfo = "Semester Final Examination " + semesterName+". Capacity: "+room.getCapacity()+".";
          }
          if(type==2){
            semesterInfo = "Clearance/Improvement/Carryover Examination " + semesterName+". Capacity: "+room.getCapacity()+".";
          }
          Paragraph pSemesterInfo = new Paragraph(semesterInfo,FontFactory.getFont(FontFactory.TIMES_BOLD,12));
          pSemesterInfo.setAlignment(Element.ALIGN_CENTER);
          document.add(pSemesterInfo);

          Paragraph pExamDates = new Paragraph(examDates,FontFactory.getFont(FontFactory.TIMES_BOLD,10));
          pExamDates.setAlignment(Element.ALIGN_CENTER);
          pExamDates.setSpacingAfter(6f);
          document.add(pExamDates);

          //for getting the summery

          java.util.List<String> deptList = new ArrayList<>();
          Map<String, java.util.List<String>> deptStudentListMap = new HashMap<>();
          Map<String,String> deptWithDeptNameMap = new HashMap<>();
          Map<String,String> deptWithYearSemesterMap = new HashMap<>();

          for(int i=1;i<=room.getTotalRow();i++){
            for(int j=1;j<=room.getTotalColumn();j++){
              SeatPlan seatPlanOfTheRowAndCol = roomRowColWithSeatPlanMap.get(room.getId()+""+i+""+j) ;
              int ifSeatPlanExist = mSeatPlanManager.checkIfExistsBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);

              if(seatPlanOfTheRowAndCol!=null){
                SeatPlan seatPlan =roomRowColWithSeatPlanMap.get(room.getId()+""+i+""+j) ;
                SpStudent student = studentIdWIthStuddentInfoMap.get(seatPlan.getStudent().getId());
                Program program = programIdWithProgramInfoMap.get(student.getProgram().getId());
                String dept = program.getShortName()+" "+student.getAcademicYear()+"/"+student.getAcademicSemester();
                String deptName = program.getShortName();
                String yearSemester = student.getAcademicYear()+"/"+student.getAcademicSemester();
                if(deptList.size()==0){
                  deptList.add(dept);
                  java.util.List<String> studentList = new ArrayList<>();
                  studentList.add(student.getId());
                  deptStudentListMap.put(dept,studentList);
                  deptWithDeptNameMap.put(dept,deptName);
                  deptWithYearSemesterMap.put(dept,yearSemester);
                }else{
                  boolean foundInTheList=false;
                  for(String deptOfTheList:deptList){
                    if(deptOfTheList.equals(dept)){
                      java.util.List<String> studentList = deptStudentListMap.get(dept);
                      studentList.add(student.getId());
                      deptStudentListMap.put(dept,studentList);
                      foundInTheList=true;
                      break;
                    }
                  }
                  if(foundInTheList==false){
                    deptList.add(dept);
                    java.util.List<String> studentList = new ArrayList<>();
                    studentList.add(student.getId());
                    deptStudentListMap.put(dept,studentList);
                    deptStudentListMap.put(dept,studentList);
                    deptWithDeptNameMap.put(dept,deptName);
                    deptWithYearSemesterMap.put(dept,yearSemester);
                  }
                }
              }
            }
          }
          float summaryFontSize=10.0f;
          if(deptList.size()<6){
            fontSize=11.0f;
          }
          else if(deptList.size()==6){
            fontSize=10.0f;
          }
          else if(deptList.size()==9){
            fontSize=9.0f;
//            summaryFontSize=9.0f;
          }
          else{
            if(room.getCapacity()<=40){
              fontSize=11.0f;
            }else{
              fontSize=9.0f;

            }
          }


          int totalStudent = 0;
          float[] tableWithForSummery = new float[]{1,1,8,1};
          float[] columnWiths = new float[]{0.5f,0.4f,9f,0.6f};

          PdfPTable summaryTable = new PdfPTable(tableWithForSummery);
          summaryTable.setWidthPercentage(100);
          summaryTable.setWidths(columnWiths);
          PdfPCell deptLabelCell = new PdfPCell();
          Paragraph deptLabel = new Paragraph("DEPT",FontFactory.getFont(FontFactory.TIMES_BOLD,summaryFontSize));
          deptLabelCell.addElement(deptLabel);
          deptLabelCell.setPaddingRight(-0.5f);
          deptLabelCell.setColspan(1);
          deptLabelCell.setPaddingTop(-2f);
          summaryTable.addCell(deptLabelCell);

          PdfPCell yearSemesterLabelCell = new PdfPCell();
          Paragraph yearSemesterLabel = new Paragraph("Y/S",FontFactory.getFont(FontFactory.TIMES_BOLD,summaryFontSize));
          yearSemesterLabelCell.addElement(yearSemesterLabel);
          yearSemesterLabelCell.setPaddingRight(-0.5f);
          yearSemesterLabelCell.setColspan(1);
          yearSemesterLabelCell.setPaddingTop(-2f);
          summaryTable.addCell(yearSemesterLabelCell);

          PdfPCell studentLabelCell = new PdfPCell();
          Paragraph studentLabel = new Paragraph("STUDENT ID",FontFactory.getFont(FontFactory.TIMES_BOLD,summaryFontSize));
          studentLabel.setAlignment(Element.ALIGN_CENTER);
          studentLabelCell.addElement(studentLabel);
          studentLabelCell.setPaddingTop(-2f);
          summaryTable.addCell(studentLabelCell);

          PdfPCell totalCellLabel = new PdfPCell();
          Paragraph totalLabel = new Paragraph("TOTAL",FontFactory.getFont(FontFactory.TIMES_BOLD,summaryFontSize));
          totalCellLabel.addElement(totalLabel);
          totalCellLabel.setPaddingTop(-2f);
          summaryTable.addCell(totalCellLabel);

          int deptListCounter=0;
          for(String deptOfTheList: deptList){
            PdfPCell deptCell = new PdfPCell();
            Paragraph deptParagraph = new Paragraph(deptWithDeptNameMap.get(deptOfTheList),FontFactory.getFont(FontFactory.TIMES_ROMAN,summaryFontSize));
            deptCell.addElement(deptParagraph);
            deptCell.setPaddingTop(-2f);
            summaryTable.addCell(deptCell);
            PdfPCell yearSemesterCell = new PdfPCell();
            Paragraph yearSemesterCellParagraph = new Paragraph(deptWithYearSemesterMap.get(deptOfTheList),FontFactory.getFont(FontFactory.TIMES_ROMAN,summaryFontSize));
            yearSemesterCell.addElement(yearSemesterCellParagraph);
            yearSemesterCell.setPaddingTop(-2f);
            summaryTable.addCell(yearSemesterCell);
            PdfPCell studentCell = new PdfPCell();
            String studentListInString = "";
            java.util.List<String> studentList = deptStudentListMap.get(deptOfTheList);
            totalStudent+= studentList.size();
            int studentCounter=0;
            for(String studentOfTheList: studentList){
              if(studentCounter==0){
                studentListInString=studentListInString+studentOfTheList;
                studentCounter+=1;
              }
              else{
                studentListInString = studentListInString+","+studentOfTheList;

              }
            }

            deptListCounter+=1;
            studentListInString=studentListInString+" = "+studentList.size();

            if(studentList.size()>10){
              summaryFontSize = 9.0f;
            }

            Paragraph studentCellParagraph = new Paragraph(studentListInString,FontFactory.getFont(FontFactory.TIMES_ROMAN,summaryFontSize));
            studentCell.addElement(studentCellParagraph);
            studentCell.setPaddingTop(-2f);
            summaryTable.addCell(studentCell);

            if(deptListCounter==deptList.size()){
              PdfPCell totalCell = new PdfPCell();
              Paragraph totalLabels = new Paragraph("Total:"+totalStudent,FontFactory.getFont(FontFactory.TIMES_BOLD,summaryFontSize));
              totalCell.addElement(totalLabels);
              totalCell.setPaddingTop(-2f);
              summaryTable.addCell(totalCell);
            }else{
              PdfPCell totalCell = new PdfPCell();
              Paragraph totalLabels = new Paragraph("");
              totalCell.addElement(totalLabels);
              summaryTable.addCell(totalCell);
            }


          }
          summaryTable.setSpacingAfter(8f);
          document.add(summaryTable);
          //end of getting the summary


          for(int i=1;i<=room.getTotalRow();i++){
            Paragraph tableRow = new Paragraph();
            PdfPTable mainTable = new PdfPTable(room.getTotalColumn()/2);
            mainTable.setWidthPercentage(100);
            PdfPCell[] cellsForMainTable = new PdfPCell[room.getTotalColumn()/2];
            int cellCounter=0;
            for(int j=1;j<=room.getTotalColumn();j++){
              cellsForMainTable[cellCounter]=new PdfPCell();
              PdfPTable table = new PdfPTable(2);
              SeatPlan seatPlan = roomRowColWithSeatPlanMap.get(room.getId()+""+i+""+j);
//              int ifSeatPlanExists = mSeatPlanManager.checkIfExistsBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);
              if(seatPlan==null){
                PdfPCell emptyCell = new PdfPCell();
                String emptyString = "  ";
                Paragraph emptyParagraph = new Paragraph(emptyString);
                Paragraph emptyParagraph2 = new Paragraph("  ");
                emptyCell.addElement(emptyParagraph);
                emptyCell.addElement(emptyParagraph2);
                table.addCell(emptyCell);
                //cellsForMainTable[cellCounter].addElement(emptyCell);
              }
              else{
               /* java.util.List<SeatPlan> seatPlanLists = mSeatPlanManager.getBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);

                SeatPlan seatPlan = seatPlanLists.get(0);*/

                SpStudent student = studentIdWIthStuddentInfoMap.get(seatPlan.getStudent().getId());
                Program program = programIdWithProgramInfoMap.get(student.getProgram().getId());


                PdfPCell upperCell = new PdfPCell();

                PdfPCell lowerCell = new PdfPCell();

                String upperPart = program.getShortName()+" "+student.getAcademicYear()+"/"+student.getAcademicSemester();
                Paragraph upperParagraph = new Paragraph(upperPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,fontSize));
                upperParagraph.setSpacingBefore(-1f);
                String lowerPart = student.getId();
                Paragraph lowerParagraph = new Paragraph(lowerPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,fontSize));
                lowerParagraph.setSpacingBefore(-1f);
                upperCell.addElement(upperParagraph);
                upperCell.addElement(lowerParagraph);
                upperCell.setPaddingTop(-2f);

                table.addCell(upperCell);
              }


              j=j+1;
               SeatPlan seatPlan2 = roomRowColWithSeatPlanMap.get(room.getId()+""+i+""+j);


              if(seatPlan2==null){
                PdfPCell emptyCell = new PdfPCell();
                String emptyString = "  ";
                Paragraph emptyParagraph = new Paragraph(emptyString);
                Paragraph emptyParagraph2 = new Paragraph("  ");
                emptyCell.addElement(emptyParagraph);
                emptyCell.addElement(emptyParagraph2);
                table.addCell(emptyCell);

              }
              else{


                SpStudent student2 = studentIdWIthStuddentInfoMap.get(seatPlan2.getStudent().getId());
                Program program2 = programIdWithProgramInfoMap.get(student2.getProgram().getId());

                PdfPCell upperCell = new PdfPCell();
                upperCell.setColspan(10);
                PdfPCell lowerCell = new PdfPCell();
                lowerCell.setColspan(10);
                String upperPart = program2.getShortName()+" "+student2.getAcademicYear()+"/"+student2.getAcademicSemester();
                Paragraph upperParagraph = new Paragraph(upperPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,fontSize));
                upperParagraph.setSpacingBefore(-1f);
                String lowerPart = student2.getId();
                Paragraph lowerParagraph = new Paragraph(lowerPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,fontSize));
                lowerParagraph.setSpacingBefore(-1f);
                upperCell.addElement(upperParagraph);
                upperCell.addElement(lowerParagraph);
                upperCell.setPaddingTop(-2f);
                table.addCell(upperCell);
              }

              cellsForMainTable[cellCounter].addElement(table);
              cellsForMainTable[cellCounter].setBorder(PdfPCell.NO_BORDER);
              mainTable.addCell(cellsForMainTable[cellCounter]);

              cellCounter+=1;
            }

            tableRow.add(mainTable);
            tableRow.setSpacingAfter(8f);

            document.add(tableRow);
          }

          PdfPTable footer = new PdfPTable(3);
          float footerFontSize;
          if(deptList.size()>=9){
            footerFontSize=10.0f;
          }
          else{
            footerFontSize = 12.0f;

          }

          PdfPCell dateAndPreparedByCell = new PdfPCell();
          dateAndPreparedByCell.addElement(new Phrase("Date:",FontFactory.getFont(FontFactory.TIMES_BOLD,footerFontSize)));
          dateAndPreparedByCell.addElement(new Phrase("Prepared By",FontFactory.getFont(FontFactory.TIMES_BOLD,footerFontSize)));
          dateAndPreparedByCell.setBorder(Rectangle.NO_BORDER);
          footer.addCell(dateAndPreparedByCell);
          //todo: remove border
          PdfPCell spaceCell = new PdfPCell();
          Paragraph spaceParagraph = new Paragraph(" ");
          spaceCell.addElement(spaceParagraph);
          spaceCell.setBorder(PdfPCell.NO_BORDER);
          PdfPCell checkedByCell = new PdfPCell();
          Paragraph pCheckedBy = new Paragraph("Checked By",FontFactory.getFont(FontFactory.TIMES_BOLD,footerFontSize));
          checkedByCell.addElement(spaceParagraph);
          checkedByCell.addElement(pCheckedBy);
          checkedByCell.setBorder(PdfPCell.NO_BORDER);
          footer.addCell(checkedByCell);
          PdfPCell controllerCell = new PdfPCell();
          Paragraph pController = new Paragraph("Controller of Examinations",FontFactory.getFont(FontFactory.TIMES_BOLD,footerFontSize));
          controllerCell.addElement(spaceParagraph);
          controllerCell.addElement(pController);
          controllerCell.setBorder(PdfPCell.NO_BORDER);
          footer.addCell(controllerCell);

          if(room.getCapacity()<=40){
            footer.setSpacingBefore(40f);

          }else{
            footer.setSpacingBefore(15.0f);

          }

          document.add(footer);
          document.newPage();

        }

        //just for debug purpose
        /*if(roomCounter==26){
          break;
        }*/

      }
    }

    document.close();
    return new StreamingOutput() {
      @Override
      public void write(OutputStream outputStream) throws IOException, WebApplicationException {
        FileInputStream inputStream = new FileInputStream(dest);
        int nextByte=0;
        while((nextByte  = inputStream.read()) != -1 ){
          outputStream.write(nextByte);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
      }
    };
  }
}
