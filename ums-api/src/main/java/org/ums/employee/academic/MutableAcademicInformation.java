package org.ums.employee.academic;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.AcademicDegreeType;

public interface MutableAcademicInformation extends AcademicInformation, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setDegree(final AcademicDegreeType pDegree);

  void setDegreeId(final int pDegreeId);

  void setInstitute(final String pInstitute);

  void setPassingYear(final int pPassingYear);
}
