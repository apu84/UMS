package org.ums.manager;

import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;

import java.util.List;

/**
 * Created by My Pc on 3/14/2016.
 */
public interface ParameterSettingManager extends
    ContentManager<ParameterSetting, MutableParameterSetting, Long> {
  List<ParameterSetting> getBySemester(int semesterId);

  ParameterSetting getBySemesterAndParameterId(int parameterId, int semesterId);

  ParameterSetting getByParameterAndSemesterId(String parameter, int semesterId);
}
