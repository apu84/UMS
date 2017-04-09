package org.ums.domain.model.mutable.registrar.Employee;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.Employee.AcademicInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

import java.util.Date;

public interface MutableAcademicInformation extends AcademicInformation, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setEmployeeId(final int pEmployeeId);

  void setDegreeName(final String pDegreeName);

  void setDegreeInstitute(final String pDegreeInstitute);

  void setDegreePassingYear(final String pDegreePassingYear);
}
