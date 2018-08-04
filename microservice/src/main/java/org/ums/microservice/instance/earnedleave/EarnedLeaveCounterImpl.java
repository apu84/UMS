package org.ums.microservice.instance.earnedleave;

import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.ums.configuration.UMSConfiguration;
import org.ums.manager.ContentManager;
import org.ums.microservice.AbstractService;
import org.ums.microservice.configuration.ServiceConfiguration;
import org.ums.microservice.instance.cachewarmer.CacheWarmerManagerImpl;

/**
 * Created by Monjur-E-Morshed on 31-Jul-18.
 */
public class EarnedLeaveCounterImpl extends AbstractService implements EarnedLeaveCounter {

  private static final Logger mLogger = LoggerFactory.getLogger(EarnedLeaveCounterImpl.class);

  UMSConfiguration mUMSConfiguration;

  ServiceConfiguration mServiceConfiguration;

  SecurityManager mSecurityManager;

  EarnedLeaveCounterService mEarnedLeaveCounterService;

  private ContentManager[] mContentManagers;


  public EarnedLeaveCounterImpl(UMSConfiguration pUMSConfiguration, ServiceConfiguration pServiceConfiguration, SecurityManager pSecurityManager, EarnedLeaveCounterService pEarnedLeaveCounterService, ContentManager... pContentManagers) {
    mUMSConfiguration = pUMSConfiguration;
    mServiceConfiguration = pServiceConfiguration;
    mSecurityManager = pSecurityManager;
    mEarnedLeaveCounterService = pEarnedLeaveCounterService;
    mContentManagers = pContentManagers;
  }

  @Override
  protected SecurityManager getSecurityManager() {
    return mSecurityManager;
  }

  @Override
  protected UMSConfiguration getUMSConfiguration() {
    return mUMSConfiguration;
  }

  @Override
  public void start() {
    // do nothing for now.
  }

  @Override
  @Scheduled(cron = "0 0 0 1 1/1 *") // this should execute on 1st day every month @00:00
  public void countEarnedLeave() {
    try{
      mEarnedLeaveCounterService.calculateAndUpdateEmployeesEarnedLeaveBalance();
    }catch (Exception pE){
      pE.printStackTrace();
    }
  }

}
