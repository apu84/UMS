package org.ums.common.academic.resource.helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.academic.resource.SeatPlanResource;
import org.ums.common.builder.Builder;
import org.ums.common.builder.SeatPlanBuilder;
import org.ums.common.report.generator.SeatPlanReportGenerator;
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
import javax.ws.rs.core.StreamingOutput;
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


  public StreamingOutput createOrCheckSeatPlanAndReturnRoomList(final int pSemesterId, final int groupNo, final int type, final Request pRequest, final UriInfo pUriInfo) throws Exception,IOException{
    GenericResponse<Map> genericResponse = null, previousResponse = null;


    List<SeatPlan> allSeatPlans = mManager.getAll();


    File file = new File(DEST);
    file.getParentFile().mkdirs();
    boolean noSeatPlanInfo;
    boolean seatPlanOfTheGroupFound=false;
    if(allSeatPlans.size()>0){
      List<SeatPlan> seatPlanOfTheGroup = mManager.getBySemesterAndGroupAndExamType(pSemesterId,groupNo,type);
      if(seatPlanOfTheGroup.size()>0){
        seatPlanOfTheGroupFound=true;
      }
    }

    if(seatPlanOfTheGroupFound){

        noSeatPlanInfo = false;
        return mSeatPlanReportGenerator.createPdf(DEST,noSeatPlanInfo,pSemesterId,groupNo,type);

    }else{
      List<SubGroup> subGroupOfTheGroup = mSubGroupManager.getBySemesterGroupNoAndType(pSemesterId,groupNo,type);
      if(subGroupOfTheGroup.size()>0){
        genericResponse = mSeatPlanService.generateSeatPlan(pSemesterId,groupNo,type);
        noSeatPlanInfo = false;
      }else{
        noSeatPlanInfo=true;
      }
      return mSeatPlanReportGenerator.createPdf(DEST,noSeatPlanInfo,pSemesterId,groupNo,type);

    }


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
