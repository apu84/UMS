package org.ums.services;

import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Map;

public interface UserHomeService {
  List<Map<String, String>> process(final Subject pCurrentSubject);
}
