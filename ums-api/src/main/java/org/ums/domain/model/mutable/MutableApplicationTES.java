package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.ApplicationTES;

/**
 * Created by Rumi on 2/20/2018.
 */
public interface MutableApplicationTES extends ApplicationTES, Editable<Long>, MutableLastModifier,
    MutableIdentifier<Long> {
  void setApplicationDate(final String pApplicationDate);

  void setQuestionId(final Integer pQuestionId);

  void setQuestionDetails(final String pQuestionDetails);

  void setReviewEligibleCourses(final String pReviewEligibleCourses);

  void setSemesterName(final String pSemesterName);

  void setCourseTitle(final String pCoursetitle);

  void setCourseNo(final String pCourseNo);
}
