package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.common.ResourceHelper;
import org.ums.common.builder.Builder;
import org.ums.common.builder.SubGroupCCIBuilder;
import org.ums.domain.model.immutable.SubGroupCCI;
import org.ums.domain.model.mutable.MutableSubGroupCCI;
import org.ums.manager.SeatPlanManager;
import org.ums.manager.SubGroupCCIManager;
import org.ums.persistent.model.PersistentSubGroupCCI;

import javax.json.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Pc on 7/23/2016.
 */
@Component
public class SubGroupCCIResourceHelper extends ResourceHelper<SubGroupCCI,MutableSubGroupCCI,Integer>{

  @Autowired
  SubGroupCCIManager mManager;

  @Autowired
  SubGroupCCIBuilder mBuilder;

  @Autowired
  SeatPlanManager mSeatPlanManager;


  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    //int checkIfThereisAnyRecord = getContentManager().checkOccuranceBySemesterAndExamDate()
    return null;
  }

  public Response saveData(Integer pSemesterId,String pExamDate, JsonObject pJsonObject, UriInfo pUriInfo)throws Exception{
    int checkIfThereIsAnyRecord = getContentManager().checkOccuranceBySemesterAndExamDate(pSemesterId,pExamDate);
    if(checkIfThereIsAnyRecord>0){
      getContentManager().deleteBySemesterAndExamDate(pSemesterId,pExamDate);
      mSeatPlanManager.deleteBySemesterGroupExamTypeAndExamDate(pSemesterId,0,2,pExamDate);
    }

    List<MutableSubGroupCCI> subGroupCCIs = new ArrayList<>();
    JsonArray entries = pJsonObject.getJsonArray("entries");

    for(int i=0;i<entries.size();i++){
      LocalCache localCache = new LocalCache();
      JsonObject jsonObject = entries.getJsonObject(i);
      PersistentSubGroupCCI subGroupCCI = new PersistentSubGroupCCI();
      getBuilder().build(subGroupCCI,jsonObject,localCache);
      subGroupCCIs.add(subGroupCCI);
    }

    getContentManager().create(subGroupCCIs);

    URI contextURI = null;
    Response.ResponseBuilder builder = Response.created(contextURI);
    builder.status(Response.Status.CREATED);
    return builder.build();
  }

  public Response deleteBySemesterAndExamDate(Integer pSemesterId,String pExamDate)throws Exception{
    int i= getContentManager().deleteBySemesterAndExamDate(pSemesterId,pExamDate);
    mSeatPlanManager.deleteBySemesterGroupExamTypeAndExamDate(pSemesterId,0,2,pExamDate);
    return Response.noContent().build();
  }


  public JsonObject getBySemesterAndExamDate(Integer pSemesterId, String pExamDate, final Request pRequest,final UriInfo pUriInfo)throws Exception{
    List<SubGroupCCI> subGroupCCIs = getContentManager().getBySemesterAndExamDate(pSemesterId,pExamDate);
    JsonObjectBuilder object = Json.createObjectBuilder();
    JsonArrayBuilder children = Json.createArrayBuilder();
    LocalCache localCache = new LocalCache();
    for(SubGroupCCI subGroupCCI:subGroupCCIs){
      children.add(toJson(subGroupCCI,pUriInfo,localCache));
    }
    object.add("entries", children);
    localCache.invalidate();
    return object.build();

  }

  @Override
  protected SubGroupCCIManager getContentManager() {
    return mManager;
  }

  @Override
  protected Builder<SubGroupCCI, MutableSubGroupCCI> getBuilder() {
    return mBuilder;
  }

  @Override
  protected String getEtag(SubGroupCCI pReadonly) {
    return pReadonly.getLastModified();
  }
}
