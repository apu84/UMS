package org.ums.decorator;

import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.mutable.MutableSubGroup;
import org.ums.manager.SubGroupManager;

import java.util.List;

/**
 * Created by My Pc on 5/4/2016.
 */
public class SubGroupDaoDecorator extends ContentDaoDecorator<SubGroup,MutableSubGroup,Integer,SubGroupManager> implements SubGroupManager {
  @Override
  public List<SubGroup> getByGroupNo(int pGroupNo) {
    return getManager().getByGroupNo(pGroupNo);
  }

  @Override
  public int deleteBySemesterAndGroup(int pSemesterId, int pGroupNo) {
    return getManager().deleteBySemesterAndGroup(pSemesterId,pGroupNo);
  }

  @Override
  public List<SubGroup> getBySemesterGroupNoAndType(int pSemesterId, int pGroupNo, int pType) {
    return getManager().getBySemesterGroupNoAndType(pSemesterId,pGroupNo, pType);
  }

  @Override
  public int getSubGroupNumberOfAGroup(int pSemesterId, int pExamType, int pGroupNo) {
    return getManager().getSubGroupNumberOfAGroup(pSemesterId,pExamType,pGroupNo);
  }

  @Override
  public List<SubGroup> getSubGroupMembers(int pSemesterId, int pExamTYpe, int pGroupNo, int pSubGroupNo) {
    return getManager().getSubGroupMembers(pSemesterId,pExamTYpe,pGroupNo,pSubGroupNo);
  }
}
