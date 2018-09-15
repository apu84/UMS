package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.enums.ClassRoomType;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class ClassRoomBuilder implements Builder<ClassRoom, MutableClassRoom> {
  @Override
  public void build(JsonObjectBuilder pBuilder, ClassRoom pReadOnly, UriInfo pUriInfo, final LocalCache pLocalCache) {
    pBuilder.add("id", pReadOnly.getId().toString());
    pBuilder.add("roomNo", pReadOnly.getRoomNo());
    pBuilder.add("description", pReadOnly.getDescription() == null ? "" : pReadOnly.getDescription());
    pBuilder.add("totalRow", pReadOnly.getTotalRow());
    pBuilder.add("totalColumn", pReadOnly.getTotalColumn());
    pBuilder.add("capacity", pReadOnly.getCapacity());
    pBuilder.add("examSeatPlan", pReadOnly.isExamSeatPlan());
    pBuilder.add("roomType", pReadOnly.getRoomType().getValue());
    pBuilder.add("dept_id", pReadOnly.getDeptId());
    pBuilder.add("self",
        pUriInfo.getBaseUriBuilder().path("academic").path("classroom").path(pReadOnly.getId().toString()).build()
            .toString());
  }

  @Override
  public void build(MutableClassRoom pMutable, JsonObject pJsonObject, final LocalCache pLocalCache) {
    pMutable.setRoomNo(pJsonObject.getString("roomNo"));
    pMutable.setDescription(pJsonObject.getString("description"));
    pMutable.setTotalRow((Integer.parseInt(pJsonObject.getString("totalRow"))));
    pMutable.setTotalColumn((Integer.parseInt(pJsonObject.getString("totalColumn"))));
    pMutable.setCapacity((Integer.parseInt(pJsonObject.getString("totalRow")) * Integer.parseInt(pJsonObject
        .getString("totalColumn"))) + 2);
    pMutable.setRoomType(ClassRoomType.get(Integer.parseInt(pJsonObject.getString("roomType"))));
    if(pJsonObject.getString("examSeatPlan").equals("0")) {
      pMutable.setExamSeatPlan(false);
    }
    else {
      pMutable.setExamSeatPlan(true);
    }
    pMutable.setDeptId(pJsonObject.getString("dept_id"));
  }
}
