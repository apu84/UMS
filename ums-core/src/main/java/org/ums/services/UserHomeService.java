package org.ums.services;

import org.apache.shiro.subject.Subject;
import org.ums.processor.userhome.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserHomeService {
  UserInfo process(final Subject pCurrentSubject);
}
