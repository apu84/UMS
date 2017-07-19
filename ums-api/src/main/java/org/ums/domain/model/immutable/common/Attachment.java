package org.ums.domain.model.immutable.common;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.common.MutableAttachment;
import org.ums.enums.ApplicationType;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 11-Jul-17.
 */
public interface Attachment extends Serializable, LastModifier, EditType<MutableAttachment>, Identifier<Long> {

  ApplicationType getApplicationType();

  String getApplicationId();

  String getFileName();

}
