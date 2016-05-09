package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.builder.Builder;
import org.ums.common.builder.SeatPlanBuilder;
import org.ums.domain.model.immutable.SeatPlan;
import org.ums.domain.model.mutable.MutableSeatPlan;
import org.ums.manager.ContentManager;
import org.ums.manager.SeatPlanManager;
import org.ums.response.type.GenericResponse;

import javax.json.JsonObject;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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



  @Override
  protected Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    int groupNo = pJsonObject.getInt("groupNo");
    int semesterId = pJsonObject.getInt("semesterId");
    String action = pJsonObject.getString("action");  //action will be of two types-> createOrView and -->createNew
    /*
    action will be of two types-> createOrView and -->createNew.
    createOrView: it will check, if there is a seatPlan already, if there is, then it just responds positively. Else, it will
                create new seatPlan and then will assure.
    create: it will create only. If there is a seatPlan already, then, it will first delete the whole seatPlan and then will create
             a new one. :)
    * */

    return null;
  }


  public JsonObject createOrCheckSeatPlanAndReturnRoomList(final int pSemesterId, final int type, final int groupNo, final Request pRequest,final UriInfo pUriInfo) throws Exception{
    GenericResponse<Map> genericResponse = null, previousResponse = null;

    List<SeatPlan> allSeatPlans = mManager.getAll();

    if(allSeatPlans.size()>0){
      List<SeatPlan> seatPlanOfTheGroup = mManager.getBySemesterAndGroupAndExamType(pSemesterId,groupNo,type);
      if(seatPlanOfTheGroup.size()>0){

      }else{

      }
    }else{

    }
    return null;
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
}
