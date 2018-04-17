package org.ums.bank.branch;

import org.ums.manager.ContentManager;

import java.util.List;

public interface BranchManager extends ContentManager<Branch, MutableBranch, Long> {
  Branch getByCode(String pCode);

  List<Branch> getBranchesByBank(Long pBankId);
}
