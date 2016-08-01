package org.ums.common.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.cache.LocalCache;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.manager.ApplicationCCIManager;
import org.ums.manager.ParameterSettingManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.persistent.model.PersistentApplicationCCI;

import javax.json.*;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by My Pc on 7/17/2016.
 */
public class ApplicationCCIValidator extends AbstractValidator {

  @Autowired
  UGRegistrationResultManager mResultManager;

  @Autowired
  ApplicationCCIManager mAppManager;

  @Autowired
  ParameterSettingManager mParameterSettingManager;

  @Override
  protected void validateFields(JsonObject pJsonObject) {
    if(pJsonObject.size()==0){
      addFieldValidationException("message","Can not save empty data");
    }
  }




}
