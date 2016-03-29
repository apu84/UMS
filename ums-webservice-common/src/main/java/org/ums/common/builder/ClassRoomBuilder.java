package org.ums.common.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.*;
import org.ums.domain.model.immutable.*;
import org.ums.enums.ClassRoomType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ClassRoomBuilder implements Builder<ClassRoom, MutableClassRoom> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ClassRoom pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {
    pBuilder.add("id", pReadOnly.getId());
    pBuilder.add("roomNo",pReadOnly.getRoomNo());
    pBuilder.add("description",pReadOnly.getDescription()==null?"":pReadOnly.getDescription());
    pBuilder.add("totalRow",pReadOnly.getTotalRow());
    pBuilder.add("totalColumn",pReadOnly.getTotalColumn());
    pBuilder.add("capacity",pReadOnly.getCapacity());
    pBuilder.add("examSeatPlan",pReadOnly.isExamSeatPlan());
    pBuilder.add("roomType",pReadOnly.getRoomType().getValue());
    pBuilder.add("dept_id",pReadOnly.getDeptId());
    pBuilder.add("self", pUriInfo.getBaseUriBuilder().path("academic").path("classroom")
        .path(pReadOnly.getId().toString()).build().toString());
  }

  @Override
  public void build(MutableClassRoom pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) throws Exception {
    pMutable.setId(pJsonObject.getString("id").contains("empty")?0:Integer.parseInt(pJsonObject.getString("id")));
    pMutable.setRoomNo(pJsonObject.getString("roomNo"));
    pMutable.setDescription(pJsonObject.getString("description"));
    pMutable.setTotalRow((Integer.parseInt(pJsonObject.getString("totalRow"))));
    pMutable.setTotalColumn((Integer.parseInt(pJsonObject.getString("totalColumn"))));
    pMutable.setCapacity((Integer.parseInt(pJsonObject.getString("capacity"))));
    pMutable.setRoomType(ClassRoomType.values()[Integer.parseInt(pJsonObject.getString("roomType"))]);
    pMutable.setExamSeatPlan(true);
    pMutable.setDeptId(pJsonObject.getString("dept_id"));
  }
}
