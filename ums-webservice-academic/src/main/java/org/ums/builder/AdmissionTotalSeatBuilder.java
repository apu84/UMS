package org.ums.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.AdmissionTotalSeat;
import org.ums.domain.model.mutable.MutableAdmissionTotalSeat;
import org.ums.enums.ProgramType;
import org.ums.enums.QuotaType;
import org.ums.manager.ProgramManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 03-Jan-17.
 */
@Component
public class AdmissionTotalSeatBuilder implements Builder<AdmissionTotalSeat, MutableAdmissionTotalSeat> {

  @Autowired
  ProgramManager mProgramManager;

  @Override
  public void build(JsonObjectBuilder pBuilder, AdmissionTotalSeat pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("semesterId", pReadOnly.getSemesterId());
    pBuilder.add("programId", pReadOnly.getProgramId());
    pBuilder.add("programShortName", pReadOnly.getProgram().getShortName());
    pBuilder.add("programLongName", pReadOnly.getProgram().getLongName());
    pBuilder.add("totalSeat", pReadOnly.getTotalSeat());
  }

  @Override
  public void build(MutableAdmissionTotalSeat pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    pMutable.setSemesterId(pJsonObject.getInt("semesterID"));
    pMutable.setProgramId(pJsonObject.getInt("programId"));
    pMutable.setTotalSeat(pJsonObject.getInt("totalSeat"));
  }

  public void build(MutableAdmissionTotalSeat pMutable, JsonObject pJsonObject, LocalCache pLocalCache, String pType) {
    if(pType.equals("update")) {
      pMutable.setId(pJsonObject.getInt("id"));
    }
    pMutable.setProgramType(ProgramType.get(pJsonObject.getInt("programType")));
    pMutable.setQuotaType(QuotaType.get(pJsonObject.getInt("quota")));
    pMutable.setSemesterId(pJsonObject.getInt("semesterId"));
    pMutable.setProgramId(pJsonObject.getInt("programId"));
    pMutable.setTotalSeat(pJsonObject.getInt("totalSeat"));
  }
}
