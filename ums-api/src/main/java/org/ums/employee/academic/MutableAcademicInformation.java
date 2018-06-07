package org.ums.employee.academic;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.common.DegreeTitle;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.DegreeLevel;

public interface MutableAcademicInformation extends AcademicInformation, Editable<Long>, MutableIdentifier<Long>,
    MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setDegreeLevel(final DegreeLevel pDegreeLevel);

  void setDegreeLevelId(final Integer pDegreeLevelId);

  void setDegreeTitle(final DegreeTitle pDegreeTitle);

  void setDegreeTitleId(final Integer pDegreeTitleId);

  void setInstitute(final String pInstitute);

  void setPassingYear(final int pPassingYear);

  void setResult(final String pResult);

  void setMajor(final String pMajor);

  void setDuration(final Integer pDuration);
}
