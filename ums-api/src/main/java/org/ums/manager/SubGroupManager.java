package org.ums.manager;

import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.mutable.MutableSubGroup;

import java.util.List;

/**
 * Created by My Pc on 5/4/2016.
 */
public interface SubGroupManager extends ContentManager<SubGroup, MutableSubGroup, Integer> {
  List<SubGroup> getByGroupNo(int pGroupNo);

  int deleteBySemesterGroupAndType(int pSemesterId, int pGroupNo, int pType);

  List<SubGroup> getBySemesterAndExamType(int pSemesterId, int pExamType);

  List<SubGroup> getBySemesterGroupNoAndType(int pSemesterId, int pGroupNo, int pType);

  int getSubGroupNumberOfAGroup(int pSemesterId, int pExamType, int pGroupNo);

  List<SubGroup> getSubGroupMembers(int pSemesterId, int pExamTYpe, int pGroupNo, int pSubGroupNo);

  int checkBySemesterGroupNoAndType(int pSemesterId, int pGroupNo, int pType);

  int checkForHalfFinishedSubGroupsBySemesterGroupNoAndType(int pSemesterId, int pGroupNo, int pType);
}

// todo: add examtype option
