package org.ums.domain.model.immutable.library;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.CourseGroup;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.enums.CourseCategory;
import org.ums.enums.CourseType;

import java.io.Serializable;

/**
 * Created by Ifti on 30-Jan-17.
 */
public interface Author extends Serializable, LastModifier, EditType<MutableAuthor>, Identifier<Integer> {

  String getFirstName();

  String getMiddleName();

  String getLastName();

  String getShortName();

  String getGender();

  String getAddress();

  int getCountryId();

  Country getCountry();
}
