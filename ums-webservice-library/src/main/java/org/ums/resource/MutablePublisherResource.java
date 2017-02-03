package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.library.Publisher;
import org.ums.domain.model.mutable.library.MutablePublisher;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class MutablePublisherResource extends Resource {
  @Autowired
  ResourceHelper<Publisher, MutablePublisher, Integer> mResourceHelper;

}
