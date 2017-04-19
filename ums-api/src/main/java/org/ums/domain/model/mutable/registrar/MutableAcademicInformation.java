package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.MutableLastModifier;

public interface MutableAcademicInformation extends AcademicInformation, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setDegreeName(final String pDegreeName);

  void setDegreeInstitute(final String pDegreeInstitute);

  void setDegreePassingYear(final String pDegreePassingYear);
}
