package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.enums.ClassRoomType;

import java.io.Serializable;

/**
 * Created by Ifti on 13-Feb-16.
 */
public interface ClassRoom extends Serializable, LastModifier, EditType<MutableClassRoom>,
    Identifier<Integer> {
  String getRoomNo();

  String getDescription();

  int getTotalRow();

  int getTotalColumn();

  int getCapacity();

  ClassRoomType getRoomType();

  String getDeptId();

  Department getDept();

  boolean isExamSeatPlan();

  String getLastModified();

}
