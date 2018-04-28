package org.ums.domain.model.immutable.accounts;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.accounts.MutableGroup;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Monjur-E-Morshed on 05-Dec-17.
 */
public interface Group extends Serializable, LastModifier, EditType<MutableGroup>, Identifier<Long> {

  String getStringId();

  String getCompCode();

  String getDisplayCode();

  String getGroupCode();

  String getGroupName();

  String getMainGroup();

  String getReservedFlag();

  String getFlag();

  BigDecimal getTaxLimit();

  BigDecimal getTdsPercent();

  String getDefaultComp();

  String getStatFlag();

  String getStatUpFlag();

  Date getModifiedDate();

  String getModifiedBy();

}
