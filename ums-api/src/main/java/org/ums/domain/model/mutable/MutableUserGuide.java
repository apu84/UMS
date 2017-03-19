package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Editable;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.domain.model.immutable.UserGuide;

/**
 * Created by Ifti on 17-Dec-16.
 */
public interface MutableUserGuide extends UserGuide, Editable<Integer>, MutableIdentifier<Integer> {

  void setGuideId(Integer pGuideId);

  void setNavigationId(Integer pNavigationId);

  void setManualTitle(String pManualTitle);

  void setPdfFilePath(String pPdfFilePath);

  void setHtmlContent(String pHtmlContent);

  void setViewOrder(Integer pViewOrder);

  void setVisibility(Integer pVisibility);
}
