package org.ums.domain.model.mutable.registrar;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.registrar.AcademicInformation;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.common.AcademicDegreeType;

public interface MutableAcademicInformation extends AcademicInformation, Editable<Integer>, MutableIdentifier<Integer>,
    MutableLastModifier {

  void setEmployeeId(final String pEmployeeId);

  void setDegree(final AcademicDegreeType pDegree);

  void setDegreeId(final int pDegreeId);

  void setInstitute(final String pInstitute);

  void setPassingYear(final int pPassingYear);
}
