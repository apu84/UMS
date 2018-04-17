package org.ums.bank.branch;

import org.ums.decorator.ContentDaoDecorator;

import java.util.List;

public class BranchDaoDecorator extends ContentDaoDecorator<Branch, MutableBranch, Long, BranchManager> implements
    BranchManager {
  @Override
  public Branch getByCode(String pCode) {
    return getManager().getByCode(pCode);
  }

  @Override
  public List<Branch> getBranchesByBank(Long pBankId) {
    return getManager().getBranchesByBank(pBankId);
  }
}
