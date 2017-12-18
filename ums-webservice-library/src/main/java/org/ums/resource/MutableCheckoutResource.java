package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.resource.helper.CheckoutResourceHelper;

public class MutableCheckoutResource extends Resource {

  @Autowired
  CheckoutResourceHelper mHelper;
}
