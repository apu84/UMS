package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.Attachment;
import org.ums.domain.model.mutable.common.MutableAttachment;
import org.ums.enums.ApplicationType;
import org.ums.manager.common.AttachmentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Jul-17.
 */
public class AttachmentDaoDecorator extends ContentDaoDecorator<Attachment, MutableAttachment, Long, AttachmentManager>
    implements AttachmentManager {
  @Override
  public List<Attachment> getAttachments(ApplicationType pApplicationType, String pApplicationId) {
    return getManager().getAttachments(pApplicationType, pApplicationId);
  }
}
