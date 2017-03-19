package org.ums.domain.model.mutable.library;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.CourseGroup;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.MutableLastModifier;
import org.ums.enums.CourseCategory;
import org.ums.enums.CourseType;

/**
 * Created by Ifti on 30-Jan-17.
 */

public interface MutableAuthor extends Author, Mutable, MutableLastModifier, MutableIdentifier<Integer> {
  void setFirstName(final String pFirstName);

  void setMiddleName(final String pMiddleName);

  void setLastName(final String pLastName);

  void setShortName(final String pShortName);

  void setGender(final String pGender);

  void setAddress(final String pAddress);

  void setCountryId(final int pCountryId);

  void setCountry(final Country pCountry);

}
