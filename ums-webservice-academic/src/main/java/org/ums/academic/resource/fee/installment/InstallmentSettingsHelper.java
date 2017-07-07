package org.ums.academic.resource.fee.installment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.json.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.ums.builder.Builder;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.Parameter;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.fee.latefee.LateFee;
import org.ums.fee.latefee.LateFeeManager;
import org.ums.fee.latefee.MutableLateFee;
import org.ums.fee.latefee.PersistentLateFee;
import org.ums.fee.semesterfee.InstallmentSettings;
import org.ums.fee.semesterfee.InstallmentSettingsManager;
import org.ums.fee.semesterfee.MutableInstallmentSettings;
import org.ums.fee.semesterfee.PersistentInstallmentSettings;
import org.ums.formatter.DateFormat;
import org.ums.manager.ContentManager;
import org.ums.manager.ParameterSettingManager;
import org.ums.persistent.model.PersistentParameterSetting;
import org.ums.resource.ResourceHelper;

@Component
public class InstallmentSettingsHelper extends ResourceHelper<InstallmentSettings, MutableInstallmentSettings, Long> {
  @Autowired
  InstallmentSettingsManager mInstallmentSettingsManager;

  @Autowired
  InstallmentSettingsBuilder mInstallmentSettingsBuilder;

  @Autowired
  ParameterSettingManager mParameterSettingManager;

  @Autowired
  LateFeeManager mLateFeeManager;

  @Autowired
  DateFormat mDateFormat;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    LocalCache localCache = new LocalCache();
    MutableInstallmentSettings mutable = new PersistentInstallmentSettings();
    getBuilder().build(mutable, pJsonObject, localCache);
    mutable.create();
    return Response.ok().build();
  }

  JsonObject getInstallmentSettings(Integer pSemesterId, UriInfo pUriInfo) {
    Optional<InstallmentSettings> settings = mInstallmentSettingsManager.getInstallmentSettings(pSemesterId);
    LocalCache localCache = new LocalCache();
    if(settings.isPresent()) {
      return toJson(settings.get(), pUriInfo, localCache);
    }
    return Json.createObjectBuilder().build();
  }

  JsonArray getInstallmentDateSettings(Integer pSemesterId) {
    JsonArrayBuilder admissionTypeEntries = Json.createArrayBuilder();
    JsonObjectBuilder admissionType = Json.createObjectBuilder();
    admissionType.add("admissionType", "regular");
    admissionType.add("admissionTypeLabel", "Regular admission");
    JsonArrayBuilder installmentEntries = Json.createArrayBuilder();
    installmentEntries.add(installmentJson(pSemesterId, Parameter.ParameterName.REGULAR_FIRST_INSTALLMENT,
        LateFee.AdmissionType.REGULAR_FIRST_INSTALLMENT));
    installmentEntries.add(installmentJson(pSemesterId, Parameter.ParameterName.REGULAR_SECOND_INSTALLMENT,
        LateFee.AdmissionType.REGULAR_SECOND_INSTALLMENT));
    admissionType.add("entries", installmentEntries);
    admissionTypeEntries.add(admissionType);

    admissionType = Json.createObjectBuilder();
    admissionType.add("admissionType", "readmission");
    admissionType.add("admissionTypeLabel", "Readmission");
    installmentEntries = Json.createArrayBuilder();
    installmentEntries.add(installmentJson(pSemesterId, Parameter.ParameterName.READMISSION_FIRST_INSTALLMENT,
        LateFee.AdmissionType.READMISSION_FIRST_INSTALLMENT));
    installmentEntries.add(installmentJson(pSemesterId, Parameter.ParameterName.READMISSION_SECOND_INSTALLMENT,
        LateFee.AdmissionType.READMISSION_SECOND_INSTALLMENT));
    admissionType.add("entries", installmentEntries);
    admissionTypeEntries.add(admissionType);

    return admissionTypeEntries.build();
  }

  @Transactional
  public Response dateSetting(Integer pSemesterId, JsonArray pJsonArray) {
    pJsonArray.forEach((admissionTypeObject) -> {
      JsonObject admissionType = (JsonObject) admissionTypeObject;
      if(admissionType.getString("admissionType").equalsIgnoreCase("regular")) {
        JsonArray entries = admissionType.getJsonArray("entries");
        entries.forEach((installmentJsonObject) -> {
          updateParameterSettings(pSemesterId, (JsonObject) installmentJsonObject);
        });
      }
    });
    return Response.ok().build();
  }

  private void updateParameterSettings(Integer pSemesterId, JsonObject installmentJson) {
    Assert.isTrue(StringUtils.isEmpty(installmentJson.getString("start")), "Required installment start date");
    Assert.isTrue(StringUtils.isEmpty(installmentJson.getString("end")), "Required installment end date");
    Parameter.ParameterName parameterName = Parameter.ParameterName.get(installmentJson.getInt("typeId"));
    ParameterSetting parameterSetting =
        mParameterSettingManager.getByParameterAndSemesterId(parameterName.getLabel(), pSemesterId);
    if(parameterSetting != null) {
      MutableParameterSetting mutableParameterSetting = parameterSetting.edit();
      mutableParameterSetting.setStartDate(mDateFormat.parse(installmentJson.getString("start")));
      mutableParameterSetting.setEndDate(mDateFormat.parse(installmentJson.getString("end")));
      mutableParameterSetting.update();
    }
    else {
      MutableParameterSetting mutableParameterSetting = new PersistentParameterSetting();
      mutableParameterSetting.setSemesterId(pSemesterId);
      mutableParameterSetting.setStartDate(mDateFormat.parse(installmentJson.getString("start")));
      mutableParameterSetting.setEndDate(mDateFormat.parse(installmentJson.getString("end")));
      mutableParameterSetting.create();
    }

    LateFee.AdmissionType admissionType = LateFee.AdmissionType.get(installmentJson.getInt("lateFeeType"));
    List<LateFee> lateFees = mLateFeeManager.getLateFees(pSemesterId, admissionType);
    if(lateFees.size() > 0) {
      lateFees.forEach((lateFee) -> {
        lateFee.edit().delete();
      });
    }

    if(installmentJson.containsKey("lateFee")) {
      List<MutableLateFee> mutableLateFees = new ArrayList<>();
      installmentJson.getJsonArray("lateFee").forEach((lateFeeObject) -> {
        JsonObject fee = (JsonObject) lateFeeObject;
        Assert.isTrue(StringUtils.isEmpty(fee.getString("start")), "Required late fee start date");
        Assert.isTrue(StringUtils.isEmpty(fee.getString("end")), "Required late fee end date");
        Assert.isTrue(StringUtils.isEmpty(fee.getString("fee")), "Required late fee amount");
        MutableLateFee mutableLateFee = new PersistentLateFee();
        mutableLateFee.setSemesterId(pSemesterId);
        mutableLateFee.setFrom(mDateFormat.parse(fee.getString("start")));
        mutableLateFee.setTo(mDateFormat.parse(fee.getString("end")));
        mutableLateFee.setFee(new BigDecimal(fee.getString("fee")));
        mutableLateFee.setAdmissionType(admissionType);
        mutableLateFees.add(mutableLateFee);
      });
      mLateFeeManager.update(mutableLateFees);
    }
  }

  private JsonObjectBuilder installmentJson(Integer pSemesterId, Parameter.ParameterName pParameterName,
      LateFee.AdmissionType pAdmissionType) {
    JsonObjectBuilder installment = Json.createObjectBuilder();
    installment.add("typeId", pParameterName.getId());
    if(pAdmissionType.getLabel().contains("first")) {
      installment.add("typeName", "First installment");
    }
    else {
      installment.add("typeName", "Second installment");
    }
    installment.add("lateFeeType", pAdmissionType.getId());
    ParameterSetting parameterSetting = null;
    try {
      parameterSetting =
          mParameterSettingManager.getBySemesterAndParameterId(pParameterName.getId().intValue(), pSemesterId);
    } catch(Exception e) {

    }
    if(parameterSetting != null) {
      installment.add("start", mDateFormat.format(parameterSetting.getStartDate()));
      installment.add("end", mDateFormat.format(parameterSetting.getEndDate()));
      List<LateFee> lateFees = mLateFeeManager.getLateFees(pSemesterId, pAdmissionType);
      if(lateFees.size() > 0) {
        JsonArrayBuilder lateFeeEntries = Json.createArrayBuilder();
        lateFees.forEach((lateFee) -> {
          JsonObjectBuilder lateFeeObject = Json.createObjectBuilder();
          lateFeeObject.add("id", lateFee.getId().toString());
          lateFeeObject.add("start", mDateFormat.format(lateFee.getFrom()));
          lateFeeObject.add("end", mDateFormat.format(lateFee.getTo()));
          lateFeeObject.add("fee", lateFee.getFee().toString());
          lateFeeEntries.add(lateFeeObject);
        });
        installment.add("lateFee", lateFeeEntries);
      }
    }
    return installment;
  }

  @Override
  protected ContentManager<InstallmentSettings, MutableInstallmentSettings, Long> getContentManager() {
    return mInstallmentSettingsManager;
  }

  @Override
  protected Builder<InstallmentSettings, MutableInstallmentSettings> getBuilder() {
    return mInstallmentSettingsBuilder;
  }

  @Override
  protected String getETag(InstallmentSettings pReadonly) {
    return pReadonly.getLastModified();
  }
}
