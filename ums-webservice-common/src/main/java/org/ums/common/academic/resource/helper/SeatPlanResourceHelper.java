package org.ums.common.academic.resource.helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.academic.resource.SeatPlanResource;
import org.ums.common.builder.Builder;
import org.ums.common.builder.SeatPlanBuilder;
import org.ums.domain.model.dto.ExamRoutineDto;
import org.ums.domain.model.immutable.*;
import org.ums.domain.model.mutable.MutableExamRoutine;
import org.ums.domain.model.mutable.MutableSeatPlan;
import org.ums.manager.*;
import org.ums.response.type.GenericResponse;
import org.ums.services.academic.SeatPlanService;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by My Pc on 5/8/2016.
 */
@Component
public class SeatPlanResourceHelper extends ResourceHelper<SeatPlan,MutableSeatPlan,Integer> {

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

  public static final String DEST = "I:/pdf/seat_plan_report.pdf";



  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    int groupNo = pJsonObject.getInt("groupNo");
    int semesterId = pJsonObject.getInt("semesterId");
    int type = pJsonObject.getInt("type");
    String action = pJsonObject.getString("action");  //action will be of two types-> createOrView and -->createNew
    /*
    action will be of two types-> createOrView and -->createNew.
    createOrView: it will check, if there is a seatPlan already, if there is, then it just responds positively. Else, it will
                create new seatPlan and then will assure.
    create: it will create only. If there is a seatPlan already, then, it will first delete the whole seatPlan and then will create
             a new one. :)
    * */

    GenericResponse<Map> genericResponse = null, previousResponse = null;


    List<SeatPlan> allSeatPlans = mManager.getAll();

    File file = new File(DEST);
    file.getParentFile().mkdirs();


    if(allSeatPlans.size()>0){
      List<SeatPlan> seatPlanOfTheGroup = mManager.getBySemesterAndGroupAndExamType(semesterId,groupNo,type);
      if(seatPlanOfTheGroup.size()>0){
       // new SeatPlanResourceHelper().createPdf(DEST);
      }else{

      }
    }else{
      //new SeatPlanResourceHelper().createPdf(DEST);
    }

    return null;
  }


  public JsonObject createOrCheckSeatPlanAndReturnRoomList(final int pSemesterId, final int groupNo, final int type, final Request pRequest,final UriInfo pUriInfo) throws Exception,IOException{
    GenericResponse<Map> genericResponse = null, previousResponse = null;


    List<SeatPlan> allSeatPlans = mManager.getAll();


    File file = new File(DEST);
    file.getParentFile().mkdirs();
    boolean noSeatPlanInfo;

    if(allSeatPlans.size()>0){
      List<SeatPlan> seatPlanOfTheGroup = mManager.getBySemesterAndGroupAndExamType(pSemesterId,groupNo,type);
      if(seatPlanOfTheGroup.size()>0){
        noSeatPlanInfo = false;
        createPdf(DEST,noSeatPlanInfo,pSemesterId,groupNo,type);

      }else{
        genericResponse = mSeatPlanService.generateSeatPlan(pSemesterId,groupNo,type);
        noSeatPlanInfo = false;
        createPdf(DEST,noSeatPlanInfo,pSemesterId,groupNo,type);
      }
    }else{
      genericResponse = mSeatPlanService.generateSeatPlan(pSemesterId,groupNo,type);
      noSeatPlanInfo = false;
     createPdf(DEST,noSeatPlanInfo,pSemesterId,groupNo,type);
    }

    JsonObjectBuilder object = Json.createObjectBuilder();
    object.add("result","created");

    return object.build();
  }

  public void createPdf(String dest,boolean noSeatPlanInfo,int pSemesterId,int groupNo,int type) throws Exception,IOException,DocumentException{

    Document document = new Document();
    document.addTitle("Seat Plan");

    PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(dest));
    document.open();
    document.setPageSize(PageSize.A4.rotate());
    /*Rotate event = new Rotate();
    writer.setPageEvent(event);
    event.setOrientation(PdfPage.LANDSCAPE);*/

    document.newPage();

    Font f = new Font(Font.FontFamily.TIMES_ROMAN,10.0f,Font.BOLD, BaseColor.BLACK);
    //to do , mSemesterManager is not working.
    Semester mSemester = mSemesterManager.get(pSemesterId);

    String semesterName =mSemester.getName();

    String examDates = "Date: ";
    List<SeatPlanGroup> seatPlanGroup = mSeatPlanGroupManager.getBySemesterGroupAndType(pSemesterId,groupNo,type) ;
    List<ExamRoutineDto> examRoutines = mExamRoutineManager.getExamRoutine(pSemesterId,type);

    int routineCounter=0;
    for(ExamRoutineDto routine:examRoutines){
      SeatPlanGroup group = seatPlanGroup.get(0);
      routineCounter+=1;

      if(routine.getProgramId() == group.getProgram().getId() && routine.getCourseYear()==group.getAcademicYear() && routine.getCourseSemester()== group.getAcademicSemester()){

        if(routineCounter==examRoutines.size()){
          examDates = examDates+routine.getExamDate();
        }else{
          examDates=examDates+routine.getExamDate()+",";

        }
      }
    }

    if(noSeatPlanInfo){
      Chunk c = new Chunk("No Seat Plan Information in the database, create one and then come back again!",f);
      c.setBackground(BaseColor.WHITE);
      Paragraph p= new Paragraph(c);
      p.setAlignment(Element.ALIGN_CENTER);
      document.add(p);
    }else{
      List<ClassRoom> rooms = mRoomManager.getAll() ;


      int roomCounter=0;
      for(ClassRoom room: rooms){
        roomCounter+=1;

        int checkIfRoomExists = mSeatPlanManager.checkIfExistsByRoomSemesterGroupExamType(room.getId(),pSemesterId,groupNo,type);

        if(checkIfRoomExists>0 && room.getId()!=284 && roomCounter==3){
          String roomHeader = "Room No: "+ room.getRoomNo();
          Paragraph pRoomHeader = new Paragraph(roomHeader,FontFactory.getFont(FontFactory.TIMES_BOLD,12));
          pRoomHeader.setAlignment(Element.ALIGN_CENTER);
          document.add(pRoomHeader);

          String semesterInfo = "Semester Final Examination " + semesterName+". Capacity: "+room.getCapacity()+".";
          Paragraph pSemesterInfo = new Paragraph(semesterInfo,FontFactory.getFont(FontFactory.TIMES_BOLD,12));
          pSemesterInfo.setAlignment(Element.ALIGN_CENTER);
          document.add(pSemesterInfo);

          Paragraph pExamDates = new Paragraph(examDates,FontFactory.getFont(FontFactory.TIMES_BOLD,10));
          pExamDates.setAlignment(Element.ALIGN_CENTER);
          pExamDates.setSpacingAfter(6f);
          document.add(pExamDates);

          //for getting the summery

          List<String> deptList = new ArrayList<>();
          Map<String,List<String>> deptStudentListMap = new HashMap<>();
          Map<String,String> deptWithDeptNameMap = new HashMap<>();
          Map<String,String> deptWithYearSemesterMap = new HashMap<>();

          for(int i=1;i<=room.getTotalRow();i++){
            for(int j=1;j<=room.getTotalColumn();j++){
              int ifSeatPlanExist = mSeatPlanManager.checkIfExistsBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);

              if(ifSeatPlanExist!=0){
                List<SeatPlan> seatPlanList = mSeatPlanManager.getBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);
                SeatPlan seatPlan = seatPlanList.get(0);
                SpStudent student = mSpStudentManager.get(seatPlan.getStudent().getId());
                Program program = mProgramManager.get(student.getProgram().getId());
                String dept = program.getShortName()+" "+student.getAcademicYear()+"/"+student.getAcademicSemester();
                String deptName = program.getShortName();
                String yearSemester = student.getAcademicYear()+"/"+student.getAcademicSemester();
                if(deptList.size()==0){
                  deptList.add(dept);
                  List<String> studentList = new ArrayList<>();
                  studentList.add(student.getId());
                  deptStudentListMap.put(dept,studentList);
                  deptWithDeptNameMap.put(dept,deptName);
                  deptWithYearSemesterMap.put(dept,yearSemester);
                }else{
                  boolean foundInTheList=false;
                  for(String deptOfTheList:deptList){
                    if(deptOfTheList.equals(dept)){
                      List<String> studentList = deptStudentListMap.get(dept);
                      studentList.add(student.getId());
                      deptStudentListMap.put(dept,studentList);
                      foundInTheList=true;
                      break;
                    }
                  }
                  if(foundInTheList==false){
                    deptList.add(dept);
                    List<String> studentList = new ArrayList<>();
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

          int totalStudent = 0;
          float[] tableWithForSummery = new float[]{1,1,8,1};
          float[] columnWiths = new float[]{0.5f,0.4f,9f,1f};

          PdfPTable summaryTable = new PdfPTable(tableWithForSummery);
          summaryTable.setWidthPercentage(100);
          summaryTable.setWidths(columnWiths);
          PdfPCell deptLabelCell = new PdfPCell();
          Paragraph deptLabel = new Paragraph("DEPT",FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
          deptLabelCell.addElement(deptLabel);
          deptLabelCell.setPaddingRight(-0.5f);
          deptLabelCell.setColspan(1);
          deptLabelCell.setPaddingTop(-2f);
          summaryTable.addCell(deptLabelCell);

          PdfPCell yearSemesterLabelCell = new PdfPCell();
          Paragraph yearSemesterLabel = new Paragraph("Y/S",FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
          yearSemesterLabelCell.addElement(yearSemesterLabel);
          yearSemesterLabelCell.setPaddingRight(-0.5f);
          yearSemesterLabelCell.setColspan(1);
          yearSemesterLabelCell.setPaddingTop(-2f);
          summaryTable.addCell(yearSemesterLabelCell);

          PdfPCell studentLabelCell = new PdfPCell();
          Paragraph studentLabel = new Paragraph("STUDENT ID",FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
          studentLabel.setAlignment(Element.ALIGN_CENTER);
          studentLabelCell.addElement(studentLabel);
          studentLabelCell.setPaddingTop(-2f);
          summaryTable.addCell(studentLabelCell);

          PdfPCell totalCellLabel = new PdfPCell();
          Paragraph totalLabel = new Paragraph("TOTAL",FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
          totalCellLabel.addElement(totalLabel);
          totalCellLabel.setPaddingTop(-2f);
          summaryTable.addCell(totalCellLabel);

          int deptListCounter=0;
          for(String deptOfTheList: deptList){
            PdfPCell deptCell = new PdfPCell();
            Paragraph deptParagraph = new Paragraph(deptWithDeptNameMap.get(deptOfTheList),FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
            deptCell.addElement(deptParagraph);
            deptCell.setPaddingTop(-2f);
            summaryTable.addCell(deptCell);
            PdfPCell yearSemesterCell = new PdfPCell();
            Paragraph yearSemesterCellParagraph = new Paragraph(deptWithYearSemesterMap.get(deptOfTheList),FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
            yearSemesterCell.addElement(yearSemesterCellParagraph);
            yearSemesterCell.setPaddingTop(-2f);
            summaryTable.addCell(yearSemesterCell);
            PdfPCell studentCell = new PdfPCell();
            String studentListInString = "";
            List<String> studentList = deptStudentListMap.get(deptOfTheList);
            totalStudent+= studentList.size();
            int studentCounter=0;
            for(String studentOfTheList: studentList){
              if(studentCounter==0){
                studentListInString=studentListInString+studentOfTheList;
                studentCounter+=1;
              }
              else{
                studentListInString = studentListInString+",  "+studentOfTheList;

              }
            }

            deptListCounter+=1;
            studentListInString=studentListInString+" = "+studentList.size();


            Paragraph studentCellParagraph = new Paragraph(studentListInString,FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
            studentCell.addElement(studentCellParagraph);
            studentCell.setPaddingTop(-2f);
            summaryTable.addCell(studentCell);

            if(deptListCounter==deptList.size()){
              PdfPCell totalCell = new PdfPCell();
              Paragraph totalLabels = new Paragraph("Total:"+totalStudent,FontFactory.getFont(FontFactory.TIMES_BOLD,10));
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
              int ifSeatPlanExists = mSeatPlanManager.checkIfExistsBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);
              if(ifSeatPlanExists==0){
                PdfPCell emptyCell = new PdfPCell();
                String emptyString = "---";
                Paragraph emptyParagraph = new Paragraph(emptyString);
                emptyCell.addElement(emptyParagraph);
                table.addCell(emptyCell);
                //cellsForMainTable[cellCounter].addElement(emptyCell);
              }
              else{
                List<SeatPlan> seatPlanLists = mSeatPlanManager.getBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);

                SeatPlan seatPlan = seatPlanLists.get(0);

                SpStudent student = mSpStudentManager.get(seatPlan.getStudent().getId());
                Program program = mProgramManager.get(student.getProgram().getId());


                PdfPCell upperCell = new PdfPCell();

                PdfPCell lowerCell = new PdfPCell();

                String upperPart = program.getShortName()+" "+student.getAcademicYear()+"/"+student.getAcademicSemester();
                Paragraph upperParagraph = new Paragraph(upperPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
                upperParagraph.setSpacingBefore(-1f);
                String lowerPart = student.getId();
                Paragraph lowerParagraph = new Paragraph(lowerPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
                lowerParagraph.setSpacingBefore(-1f);
                upperCell.addElement(upperParagraph);
                upperCell.addElement(lowerParagraph);
                upperCell.setPaddingTop(-2f);

                table.addCell(upperCell);
              }


              j=j+1;
              int ifSeatPlanExist2 = mSeatPlanManager.checkIfExistsBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);

              if(ifSeatPlanExist2==0){
                table.addCell(new Phrase("---"));

              }
              else{
                List<SeatPlan> seatPlanLists2 = mSeatPlanManager.getBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);

                SeatPlan seatPlan2 = seatPlanLists2.get(0);

                SpStudent student2 = mSpStudentManager.get(seatPlan2.getStudent().getId());
                Program program2 = mProgramManager.get(student2.getProgram().getId());

                PdfPCell upperCell = new PdfPCell();
                upperCell.setColspan(10);
                PdfPCell lowerCell = new PdfPCell();
                lowerCell.setColspan(10);
                String upperPart = program2.getShortName()+" "+student2.getAcademicYear()+"/"+student2.getAcademicSemester();
                Paragraph upperParagraph = new Paragraph(upperPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
                upperParagraph.setSpacingBefore(-1f);
                String lowerPart = student2.getId();
                Paragraph lowerParagraph = new Paragraph(lowerPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,10));
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

          PdfPCell dateAndPreparedByCell = new PdfPCell();
          dateAndPreparedByCell.addElement(new Phrase("Date:",FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
          dateAndPreparedByCell.addElement(new Phrase("Prepared By",FontFactory.getFont(FontFactory.TIMES_BOLD,10)));
          dateAndPreparedByCell.setBorder(Rectangle.NO_BORDER);
          footer.addCell(dateAndPreparedByCell);
          //todo: remove border
          PdfPCell spaceCell = new PdfPCell();
          Paragraph spaceParagraph = new Paragraph(" ");
          spaceCell.addElement(spaceParagraph);
          spaceCell.setBorder(PdfPCell.NO_BORDER);
          PdfPCell checkedByCell = new PdfPCell();
          Paragraph pCheckedBy = new Paragraph("Checked By",FontFactory.getFont(FontFactory.TIMES_BOLD,10));
          checkedByCell.addElement(spaceParagraph);
          checkedByCell.addElement(pCheckedBy);
          checkedByCell.setBorder(PdfPCell.NO_BORDER);
          footer.addCell(checkedByCell);
          PdfPCell controllerCell = new PdfPCell();
          Paragraph pController = new Paragraph("Controller of Examinations",FontFactory.getFont(FontFactory.TIMES_BOLD,10));
          controllerCell.addElement(spaceParagraph);
          controllerCell.addElement(pController);
          controllerCell.setBorder(PdfPCell.NO_BORDER);
          footer.addCell(controllerCell);

          footer.setSpacingBefore(20f);

          document.add(footer);
          document.newPage();

        }

        //just for debug purpose
        /*if(roomCounter==26){
          break;
        }*/

      }
    }
    System.out.println("Report finished");
    document.close();
  }



  @Override
  protected SeatPlanManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<SeatPlan, MutableSeatPlan> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(SeatPlan pReadonly) {
    return pReadonly.getLastModified();
  }

  public class Rotate extends PdfPageEventHelper {

    protected PdfNumber orientation = PdfPage.PORTRAIT;

    public void setOrientation(PdfNumber orientation) {
      this.orientation = orientation;
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
      writer.addPageDictEntry(PdfName.ROTATE, orientation);
    }
  }

}
