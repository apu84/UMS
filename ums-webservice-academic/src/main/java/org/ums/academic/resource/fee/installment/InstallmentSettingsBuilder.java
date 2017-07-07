package org.ums.academic.resource.fee.installment;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Semester;
import org.ums.fee.semesterfee.InstallmentSettings;
import org.ums.fee.semesterfee.MutableInstallmentSettings;

@Component
public class InstallmentSettingsBuilder implements Builder<InstallmentSettings, MutableInstallmentSettings> {
  @Override
  public void build(JsonObjectBuilder pBuilder, InstallmentSettings pReadOnly, UriInfo pUriInfo,
      LocalCache pLocalCache) {
    Semester semester = (Semester) pLocalCache.cache(pReadOnly::getSemester, pReadOnly.getSemesterId(), Semester.class);
    pBuilder.add("semesterName", semester.getName());
    pBuilder.add("semesterId", semester.getId());
    pBuilder.add("enabled", pReadOnly.isEnabled());
    pBuilder.add("lastModified", pReadOnly.getLastModified());
  }

  @Override
  public void build(MutableInstallmentSettings pMutable, JsonObject pJsonObject, LocalCache pLocalCache) {
    Validate.notEmpty(pJsonObject);
    Validate.notEmpty(pJsonObject.getString("semesterId"));
    Validate.notEmpty(pJsonObject.getString("enabled"));
    pMutable.setSemesterId(Integer.parseInt(pJsonObject.getString("semesterId")));
    pMutable.setEnabled(Boolean.parseBoolean(pJsonObject.getString("enabled")));
    if(pJsonObject.containsKey("id")) {
      pMutable.setId(Long.parseLong(pJsonObject.getString("id")));
    }
  }
}
