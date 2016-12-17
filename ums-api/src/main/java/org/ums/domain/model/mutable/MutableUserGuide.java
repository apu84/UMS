package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.MarksSubmissionStatus;
import org.ums.domain.model.immutable.Semester;
import org.ums.domain.model.immutable.UserGuide;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.ExamType;

import java.util.Date;

/**
 * Created by Ifti on 17-Dec-16.
 */
public interface MutableUserGuide extends UserGuide, Mutable, MutableIdentifier<Integer> {

  void setGuideId(Integer pGuideId);

  void setNavigationId(Integer pNavigationId);

  void setManualTitle(String pManualTitle);

  void setFilePath(String pFilePath);

  void setViewOrder(Integer pViewOrder);

  void setVisibility(Integer pVisibility);
}
