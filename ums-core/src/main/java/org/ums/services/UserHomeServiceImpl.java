package org.ums.services;

import org.apache.shiro.subject.Subject;
import org.ums.processor.userhome.UserHomeProcessor;

import java.util.List;
import java.util.Map;

public class UserHomeServiceImpl implements UserHomeService {
  private List<UserHomeProcessor> mUserHomeProcessors;

  public List<UserHomeProcessor> getUserHomeProcessors() {
    return mUserHomeProcessors;
  }

  public void setUserHomeProcessors(List<UserHomeProcessor> pUserHomeProcessors) {
    mUserHomeProcessors = pUserHomeProcessors;
  }

  @Override
  public List<Map<String, String>> process(Subject pCurrentSubject) {
    for(UserHomeProcessor homeProcessor : mUserHomeProcessors) {
      if(homeProcessor.supports(pCurrentSubject)) {
        return homeProcessor.process(pCurrentSubject);
      }
    }
    return null;
  }
}
