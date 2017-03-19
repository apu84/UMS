package org.ums.domain.model.mutable;

import org.ums.domain.model.common.Mutable;
import org.ums.domain.model.immutable.ClassRoom;
import org.ums.domain.model.immutable.Department;
import org.ums.domain.model.common.MutableIdentifier;
import org.ums.enums.ClassRoomType;

/**
 * Created by Ifti on 13-Feb-16.
 */
public interface MutableClassRoom extends ClassRoom, Mutable, MutableLastModifier, MutableIdentifier<Long> {

  void setRoomNo(final String pRoomNo);

  void setDescription(final String pDescription);

  void setTotalRow(final int pTotalRow);

  void setTotalColumn(final int pTotalColumn);

  void setCapacity(final int pTotalCapacity);

  void setRoomType(final ClassRoomType pRoomType);

  void setDeptId(final String pDeptId);

  void setDept(final Department pDept);

  void setExamSeatPlan(final boolean pExamSeatPlan);

  void setLastModified(final String pLastModified);
}
