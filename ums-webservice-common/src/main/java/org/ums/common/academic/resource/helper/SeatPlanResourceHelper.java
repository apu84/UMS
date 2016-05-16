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

    Font f = new Font(Font.FontFamily.TIMES_ROMAN,14.0f,Font.BOLD, BaseColor.BLACK);
    //to do , mSemesterManager is not working.
    Semester mSemester = mSemesterManager.get(pSemesterId);

    String semesterName =mSemester.getName();

    String examDates = "";
    List<SeatPlanGroup> seatPlanGroup = mSeatPlanGroupManager.getBySemesterGroupAndType(pSemesterId,groupNo,type) ;
    List<ExamRoutineDto> examRoutines = mExamRoutineManager.getExamRoutine(pSemesterId,type);

    int routineCounter=0;
    for(ExamRoutineDto routine:examRoutines){
      SeatPlanGroup group = seatPlanGroup.get(0);
      routineCounter+=1;
      if(routineCounter==26) break;
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

        List<SeatPlan> seatPlanForRoomValidity = mSeatPlanManager.getByRoomSemesterGroupExamType(room.getId(),pSemesterId,groupNo,type);

        if(seatPlanForRoomValidity.size()>0){
          String roomHeader = "Room No: "+ room.getRoomNo();
          Paragraph pRoomHeader = new Paragraph(roomHeader,FontFactory.getFont(FontFactory.TIMES_BOLD,14));
          pRoomHeader.setAlignment(Element.ALIGN_CENTER);
          document.add(pRoomHeader);

          String semesterInfo = "Semester Final Examination " + semesterName+". Capacity: "+room.getCapacity()+".";
          Paragraph pSemesterInfo = new Paragraph(semesterInfo,FontFactory.getFont(FontFactory.TIMES_BOLD,14));
          pSemesterInfo.setAlignment(Element.ALIGN_CENTER);
          document.add(pSemesterInfo);

          Paragraph pExamDates = new Paragraph(examDates,FontFactory.getFont(FontFactory.TIMES_BOLD,14));
          pExamDates.setAlignment(Element.ALIGN_CENTER);
          pExamDates.setSpacingAfter(6f);
          document.add(pExamDates);

          for(int i=1;i<=room.getTotalRow();i++){
            Paragraph tableRow = new Paragraph();
            PdfPTable mainTable = new PdfPTable(room.getTotalColumn()/2);
            mainTable.setWidthPercentage(100);
            PdfPCell[] cellsForMainTable = new PdfPCell[room.getTotalColumn()/2];
            int cellCounter=0;
            for(int j=1;j<=room.getTotalColumn();j++){
              cellsForMainTable[cellCounter]=new PdfPCell();
              PdfPTable table = new PdfPTable(2);
              SeatPlan seatPlan = mSeatPlanManager.getBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);
              if(seatPlan==null){
                PdfPCell emptyCell = new PdfPCell();
                emptyCell.setColspan(10);
                String emptyString = "";
                Paragraph emptyParagraph = new Paragraph(emptyString);
                emptyCell.addElement(emptyParagraph);
                table.addCell(emptyCell);
                cellsForMainTable[cellCounter].addElement(emptyCell);
              }
              else{
                SpStudent student = mSpStudentManager.get(seatPlan.getStudent().getId());
                Program program = mProgramManager.get(student.getProgram().getId());


                PdfPCell upperCell = new PdfPCell();

                PdfPCell lowerCell = new PdfPCell();

                String upperPart = program.getShortName()+" "+student.getAcademicYear()+"/"+student.getAcademicSemester();
                Paragraph upperParagraph = new Paragraph(upperPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,14));
                String lowerPart = student.getId();
                Paragraph lowerParagraph = new Paragraph(lowerPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,14));
                upperCell.addElement(upperParagraph);
                upperCell.addElement(lowerParagraph);
                table.addCell(upperCell);
              }


              j=j+1;
              SeatPlan seatPlan2 = mSeatPlanManager.getBySemesterGroupTypeRoomRowAndCol(pSemesterId,groupNo,type,room.getId(),i,j);
              if(seatPlan2==null){
                table.addCell(new Phrase(""));

              }
              else{
                SpStudent student2 = mSpStudentManager.get(seatPlan2.getStudent().getId());
                Program program2 = mProgramManager.get(student2.getProgram().getId());

                PdfPCell upperCell = new PdfPCell();
                upperCell.setColspan(10);
                PdfPCell lowerCell = new PdfPCell();
                lowerCell.setColspan(10);
                String upperPart = program2.getShortName()+" "+student2.getAcademicYear()+"/"+student2.getAcademicSemester();
                Paragraph upperParagraph = new Paragraph(upperPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,14));
                String lowerPart = student2.getId();
                Paragraph lowerParagraph = new Paragraph(lowerPart,FontFactory.getFont(FontFactory.TIMES_ROMAN,14));
                upperCell.addElement(upperParagraph);
                upperCell.addElement(lowerParagraph);
                table.addCell(upperCell);
              }

              cellsForMainTable[cellCounter].addElement(table);
              cellsForMainTable[cellCounter].setBorder(PdfPCell.NO_BORDER);
              mainTable.addCell(cellsForMainTable[cellCounter]);

              cellCounter+=1;
            }

            tableRow.add(mainTable);

            document.add(tableRow);
          }

          PdfPTable footer = new PdfPTable(3);

          PdfPCell dateAndPreparedByCell = new PdfPCell();
          dateAndPreparedByCell.addElement(new Phrase("Date:",FontFactory.getFont(FontFactory.TIMES_ROMAN,14)));
          dateAndPreparedByCell.addElement(new Phrase("Prepared By",FontFactory.getFont(FontFactory.TIMES_ROMAN,14)));
          dateAndPreparedByCell.setBorder(Rectangle.NO_BORDER);
          footer.addCell(dateAndPreparedByCell);
          //todo: remove border
          PdfPCell checkedByCell = new PdfPCell();
          Paragraph pCheckedBy = new Paragraph("Checked By",FontFactory.getFont(FontFactory.TIMES_ROMAN,14));
          checkedByCell.addElement(pCheckedBy);
          checkedByCell.setBorder(PdfPCell.NO_BORDER);
          footer.addCell(checkedByCell);
          PdfPCell controllerCell = new PdfPCell();
          Paragraph pController = new Paragraph("Controller of Examinations",FontFactory.getFont(FontFactory.TIMES_ROMAN,14));
          controllerCell.addElement(pController);
          controllerCell.setBorder(PdfPCell.NO_BORDER);
          footer.addCell(controllerCell);

          footer.setSpacingBefore(20f);
          footer.setSpacingAfter(10f);

          document.add(footer);
          document.newPage();

        }

        //just for debug purpose
        if(roomCounter==2)
          break;

      }
    }


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
