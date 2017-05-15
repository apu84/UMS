package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.LmsType;
import org.ums.domain.model.mutable.common.MutableLmsType;
import org.ums.enums.common.EmployeeLeaveType;
import org.ums.enums.common.Gender;
import org.ums.manager.common.LmsTypeManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 06-May-17.
 */
public class LmsTypeDaoDecorator extends ContentDaoDecorator<LmsType, MutableLmsType, Integer, LmsTypeManager>
    implements LmsTypeManager {
  @Override
  public List<LmsType> getLmsTypes(EmployeeLeaveType pType, Gender pGender) {
    return getManager().getLmsTypes(pType, pGender);
  }
}
