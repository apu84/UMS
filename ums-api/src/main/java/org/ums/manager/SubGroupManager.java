package org.ums.manager;

import org.ums.domain.model.immutable.SubGroup;
import org.ums.domain.model.mutable.MutableSubGroup;

import java.util.List;

/**
 * Created by My Pc on 5/4/2016.
 */
public interface SubGroupManager extends ContentManager<SubGroup,MutableSubGroup,Integer> {
  public List<SubGroup> getByGroupNo(int pGroupNo);
  public int deleteBySemesterAndGroup(int pSemesterId,int pGroupNo);
  public List<SubGroup> getBySemesterGroupNoAndType(int pSemesterId, int pGroupNo, int pType);
  public int getSubGroupNumberOfAGroup(int pSemesterId,int pExamType,int pGroupNo);
  public List<SubGroup> getSubGroupMembers(int pSemesterId,int pExamTYpe,int pGroupNo,int pSubGroupNo);
}

//todo: add examtype option
