package org.ums.processor.userhome;

import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Map;

public interface UserHomeProcessor {
  List<Map<String, String>> process(final Subject pCurrentSubject);

  boolean supports(final Subject pCurrentSubject);
}
