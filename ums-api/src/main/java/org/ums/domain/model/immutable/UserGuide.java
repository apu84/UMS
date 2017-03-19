package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableMarksSubmissionStatus;
import org.ums.domain.model.mutable.MutableUserGuide;
import org.ums.enums.CourseMarksSubmissionStatus;
import org.ums.enums.ExamType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ifti on 17-Dec-16.
 */
public interface UserGuide extends Serializable, EditType<MutableUserGuide>, Identifier<Integer>, LastModifier {

  Integer getGuideId();

  Integer getNavigationId();

  String getManualTitle();

  String getPdfFilePath();

  String getHtmlContent();

  Integer getViewOrder();

  Integer getVisibility();

}
