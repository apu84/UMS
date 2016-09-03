package org.ums.common.academic.resource.helper;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.common.builder.SeatPlanBuilder;
import org.ums.common.report.generator.SeatPlanReportGenerator;
import org.ums.domain.model.immutable.SeatPlan;
import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.immutable.SubGroupCCI;
import org.ums.domain.model.mutable.MutableSeatPlan;
import org.ums.manager.*;
import org.ums.resource.ResourceHelper;
import org.ums.response.type.GenericResponse;
import org.ums.services.academic.SeatPlanService;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;



/**
 * Created by My Pc on 5/8/2016.
 */
@Component
public class SeatPlanResourceHelper extends ResourceHelper<SeatPlan,MutableSeatPlan,Integer> {
  private static final Logger mLogger = LoggerFactory.getLogger(SeatPlanResourceHelper.class);
  @Autowired
  private SeatPlanManager mManager;

  @Autowired
  private SeatPlanBuilder mBuilder;

  @Autowired
  private SeatPlanService mSeatPlanService;

  @Autowired
  private SeatPlanReportGenerator mSeatPlanReportGenerator;

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

  @Autowired
  private SubGroupManager mSubGroupManager;

  @Autowired
  private SubGroupCCIManager mSubGroupCCIManager;

  public static final String DEST = "I:/pdf/seat_plan_report.pdf";



   public JsonObject getSeatPlanForStudentsSeatPlanView(final String pStudentId,final  Integer pSemesterId, final UriInfo mUriInfo)throws Exception{
    List<SeatPlan> seatPlans = getContentManager().getForStudent(pStudentId,pSemesterId);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(SeatPlan seatPlan: seatPlans){
      children.add(toJson(seatPlan,mUriInfo,localCache));
    }
    object.add("entries",children);
    localCache.invalidate();
    return object.build();
  }

  public JsonObject getSeatPlanForStudentAndCCIExam(final String pStudentId,final  Integer pSemesterId,final String pExamDate, final UriInfo mUriInfo)throws Exception{
    List<SeatPlan> seatPlans = getContentManager().getForStudentAndCCIExam(pStudentId,pSemesterId,pExamDate);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(SeatPlan seatPlan: seatPlans){
      children.add(toJson(seatPlan,mUriInfo,localCache));
    }
    object.add("entries",children);
    localCache.invalidate();
    return object.build();
  }


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


  public void createOrCheckSeatPlanAndReturnRoomList(final int pSemesterId, final int groupNo, final int type,final String examDate, OutputStream pOutputStream, final Request pRequest, final UriInfo pUriInfo) throws Exception,IOException{
    GenericResponse<Map> genericResponse = null, previousResponse = null;


    List<SeatPlan> allSeatPlans = mManager.getAll();


    File file = new File(DEST);
    file.getParentFile().mkdirs();
    boolean noSeatPlanInfo;
    boolean seatPlanOfTheGroupFound=false;
    if(allSeatPlans.size()>0){
      if(groupNo==0){
        List<SeatPlan> seatPlanOfTheGroup = mManager.getBySemesterAndGroupAndExamTypeAndExamDate(pSemesterId,groupNo,type,examDate);
        if(seatPlanOfTheGroup.size()>0){
          seatPlanOfTheGroupFound=true;
        }
      }
      else{
        List<SeatPlan> seatPlanOfTheGroup = mManager.getBySemesterAndGroupAndExamType(pSemesterId,groupNo,type);
        if(seatPlanOfTheGroup.size()>0){
          seatPlanOfTheGroupFound=true;
        }
      }

    }

    if(seatPlanOfTheGroupFound){

        noSeatPlanInfo = false;
         mSeatPlanReportGenerator.createPdf(DEST,noSeatPlanInfo,pSemesterId,groupNo,type,examDate,pOutputStream);

    }else{
      if(groupNo==0){
        List<SubGroupCCI> subGroupCCIs = mSubGroupCCIManager.getBySemesterAndExamDate(pSemesterId,examDate);
        if(subGroupCCIs.size()>0){
          genericResponse = mSeatPlanService.generateSeatPlan(pSemesterId,groupNo,type,examDate);
          noSeatPlanInfo = false;
        }else{
          noSeatPlanInfo=true;
        }
      }
      else{
        List<SubGroup> subGroupOfTheGroup = mSubGroupManager.getBySemesterGroupNoAndType(pSemesterId,groupNo,type);
        if(subGroupOfTheGroup.size()>0){
          genericResponse = mSeatPlanService.generateSeatPlan(pSemesterId,groupNo,type,examDate);
          noSeatPlanInfo = false;
        }else{
          noSeatPlanInfo=true;
        }
      }

       mSeatPlanReportGenerator.createPdf(DEST,noSeatPlanInfo,pSemesterId,groupNo,type,examDate,pOutputStream);

    }


  }

  public void getSeatPlanAttendenceSheetReport(Integer pProgramType,Integer pSemesterId,Integer pExamType,String pExamDate,OutputStream pOutputStream, final Request pRequest, final UriInfo mUriInfo) throws Exception,IOException{

    mSeatPlanReportGenerator.createSeatPlanAttendencePdfReport(pProgramType,pSemesterId,pExamType,pExamDate,pOutputStream);

  }



  public void getSeatPlanTopSheetReport(Integer pProgramType,Integer pSemesterId,Integer pExamType,String pExamDate,OutputStream pOutputStream, final Request pRequest, final UriInfo mUriInfo) throws Exception,IOException{

    mSeatPlanReportGenerator.createSeatPlanTopSheetPdfReport(pProgramType,pSemesterId,pExamType,pExamDate,pOutputStream);

  }




  public void getSeatPlanStudentStickerReport(Integer pProgramType,Integer pSemesterId,Integer pExamType,String pExamDate,OutputStream pOutputStream, final Request pRequest, final UriInfo mUriInfo) throws Exception,IOException{

    mSeatPlanReportGenerator.createSeatPlanStickerReport(pProgramType,pSemesterId,pExamType,pExamDate,pOutputStream);

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
