package org.ums.manager.common;

import org.ums.domain.model.immutable.common.Attachment;
import org.ums.domain.model.mutable.common.MutableAttachment;
import org.ums.enums.ApplicationType;
import org.ums.manager.ContentManager;

import java.util.List;

/**
 * Created by Monjur-E-Morshed on 11-Jul-17.
 */
public interface AttachmentManager extends ContentManager<Attachment, MutableAttachment, Long> {

  List<Attachment> getAttachments(ApplicationType pApplicationType, String pApplicationId);

}
