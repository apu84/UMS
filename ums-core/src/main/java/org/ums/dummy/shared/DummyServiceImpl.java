package org.ums.dummy.shared;

import org.ums.dummy.shared.DummyService;

public class DummyServiceImpl implements DummyService {
  @Override
  public String getMessage() {
    return "Lots of message here; This message will change now";
  }
}
