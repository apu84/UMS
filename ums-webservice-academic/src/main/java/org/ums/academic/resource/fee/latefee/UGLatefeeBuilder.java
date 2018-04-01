package org.ums.academic.resource.fee.latefee;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Semester;
import org.ums.fee.latefee.MutableLateFee;
import org.ums.fee.latefee.LateFee;
import org.ums.formatter.DateFormat;

@Component
public class UGLatefeeBuilder implements Builder<LateFee, MutableLateFee> {
  @Autowired
  @Qualifier("genericDateFormat")
  DateFormat mDateFormat;

  @Override
  public void build(JsonObjectBuilder pBuilder, LateFee pReadOnly, UriInfo pUriInfo, LocalCache pLocalCache) {
    pBuilder.add("from", mDateFormat.format(pReadOnly.getFrom()));
    pBuilder.add("to", mDateFormat.format(pReadOnly.getTo()));
    pBuilder.add("amount", pReadOnly.getFee());
    pBuilder.add("admissionTypeId", pReadOnly.getAdmissionType().getId());
    pBuilder.add("admissionTypeName", pReadOnly.getAdmissionType().getLabel());
    Semester semester = (Semester) pLocalCache.cache(pReadOnly::getSemester, pReadOnly.getSemesterId(), Semester.class);
    pBuilder.add("semesterId", semester.getId());
    pBuilder.add("semesterName", semester.getName());
    pBuilder.add("lastModified", pReadOnly.getLastModified());
  }

  @Override
  public void build(MutableLateFee pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    Validate.notNull(pJsonObject.getString("from"));
    Validate.notNull(pJsonObject.getString("to"));
    Validate.notNull(pJsonObject.getString("amount"));
    Validate.notNull(pJsonObject.getString("semesterId"));
    Validate.notNull(pJsonObject.getString("admissionTypeId"));
    pMutable.setSemesterId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setFrom(mDateFormat.parse(pJsonObject.getString("from")));
    pMutable.setTo(mDateFormat.parse(pJsonObject.getString("to")));
    pMutable.setAdmissionType(LateFee.AdmissionType.get(Integer.parseInt(pJsonObject.getString("admissionTypeId"))));
  }
}
