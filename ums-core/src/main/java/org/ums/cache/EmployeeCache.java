package org.ums.cache;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.CacheManager;
import org.ums.manager.EmployeeManager;

import java.util.List;
import java.util.Optional;

public class EmployeeCache extends ContentCache<Employee, MutableEmployee, String, EmployeeManager> implements
    EmployeeManager {

  private CacheManager<Employee, String> mCacheManager;

  public EmployeeCache(final CacheManager<Employee, String> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Employee, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public Optional<Employee> getByEmail(final String pEmailAddress) {
    return getManager().getByEmail(pEmailAddress);
  }

  @Override
  public List<Employee> getByDesignation(String pDesignationId) {
    return getManager().getByDesignation(pDesignationId);
  }

  @Override
  public List<Employee> getActiveTeachersOfDept(String deptId) {
    return getManager().getActiveTeachersOfDept(deptId);
  }

  @Override
  public List<Employee> getEmployees(String pDeptId, String pPublicationStatus) {
    return getManager().getEmployees(pDeptId, pPublicationStatus);
  }

  @Override
  public List<Employee> getEmployees(String pDepartmentId) {
    return getManager().getEmployees(pDepartmentId);
  }

  @Override
  public String getLastEmployeeId(String pDepartmentId, int pEmployeeType) {
    return getManager().getLastEmployeeId(pDepartmentId, pEmployeeType);
  }

  @Override
  public boolean validateShortName(String pShortName) {
    return getManager().validateShortName(pShortName);
  }

}
