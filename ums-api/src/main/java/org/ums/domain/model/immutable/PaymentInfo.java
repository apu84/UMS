package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutablePaymentInfo;
import org.ums.enums.PaymentMode;
import org.ums.enums.PaymentType;

import java.io.Serializable;

/**
 * Created by Monjur-E-Morshed on 23-Jan-17.
 */
public interface PaymentInfo extends Serializable, EditType<MutablePaymentInfo>, LastModifier, Identifier<Integer> {

  String getReferenceId();

  int getSemesterId();

  Semester getSemester();

  PaymentType getPaymentType();

  int getAmount();

  String getPaymentDate();

  PaymentMode getPaymentMode();

}
