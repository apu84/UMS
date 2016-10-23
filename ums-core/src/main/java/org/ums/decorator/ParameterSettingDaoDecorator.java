package org.ums.decorator;

import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.mutable.MutableParameterSetting;
import org.ums.manager.ParameterSettingManager;

import java.util.List;

/**
 * Created by My Pc on 3/14/2016.
 */
public class ParameterSettingDaoDecorator extends
    ContentDaoDecorator<ParameterSetting, MutableParameterSetting, String, ParameterSettingManager>
    implements ParameterSettingManager {
  @Override
  public List<ParameterSetting> getBySemester(int semesterId) {
    return getManager().getBySemester(semesterId);
  }

  @Override
  public ParameterSetting getBySemesterAndParameterId(int parameterId, int semesterId) {
    return getManager().getBySemesterAndParameterId(parameterId, semesterId);
  }

  @Override
  public ParameterSetting getByParameterAndSemesterId(String parameter, int semesterId) {
    return getManager().getByParameterAndSemesterId(parameter, semesterId);
  }
}
