package org.ums.manager.common;

import org.ums.domain.model.immutable.common.LmsType;
import org.ums.domain.model.mutable.common.MutableLmsType;
import org.ums.enums.common.EmployeeLeaveType;
import org.ums.enums.common.Gender;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 03-May-17.
 */
public interface LmsTypeManager extends ContentManager<LmsType, MutableLmsType, Integer> {
  List<LmsType> getLmsTypes(EmployeeLeaveType pType, Gender pGender);
}
