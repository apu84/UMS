package org.ums.cache;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.CacheManager;
import org.ums.manager.EmployeeManager;

import java.util.List;

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
  public List<Employee> getByDesignation(String pDesignationId) {
    return getManager().getByDesignation(pDesignationId);
  }

  @Override
  public Employee getByShortName(String pShortName) {
    return getManager().getByShortName(pShortName);
  }

  @Override
  public List<Employee> getActiveTeachersOfDept(String deptId) {
    return getManager().getActiveTeachersOfDept(deptId);
  }

  @Override
  public List<Employee> getActiveTeachers() {
    return getManager().getActiveTeachers();
  }

  @Override
  public List<Employee> getEmployees(String pDeptId, String pPublicationStatus) {
    return getManager().getEmployees(pDeptId, pPublicationStatus);
  }

  @Override
  public List<Employee> getEmployees(List<String> pEmployeeIdList) {
    return getManager().getEmployees(pEmployeeIdList);
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

  @Override
  public List<Employee> downloadEmployeeList(String pDeptList, String pEmployeeTypeList) {
    return getManager().downloadEmployeeList(pDeptList, pEmployeeTypeList);
  }

}
