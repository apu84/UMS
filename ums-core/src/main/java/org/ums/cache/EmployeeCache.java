package org.ums.cache;

import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.mutable.MutableEmployee;
import org.ums.manager.CacheManager;
import org.ums.manager.EmployeeManager;
import org.ums.util.CacheUtil;

import java.util.List;


public class EmployeeCache extends ContentCache<Employee,MutableEmployee,String,EmployeeManager> implements EmployeeManager {

  private CacheManager<Employee, String> mCacheManager;

  public EmployeeCache(final CacheManager<Employee, String> pCacheManager){
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<Employee, String> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(String pId) {
    return CacheUtil.getCacheKey(Employee.class,pId);
  }

  @Override
  public Employee getByEmployeeId(String pEmployeeId) {
    return getManager().getByEmployeeId(pEmployeeId);
  }

  @Override
  public List<Employee> getByDesignation(String pDesignationId) {
    return getManager().getByDesignation(pDesignationId);
  }

  @Override
  public List<Employee> getActiveTeachersOfDept(String deptId) {
    return getManager().getActiveTeachersOfDept(deptId);
  }
}
