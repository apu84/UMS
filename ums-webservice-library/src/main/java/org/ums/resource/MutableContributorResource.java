package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.mutable.library.MutableContributor;

/**
 * Created by Ifti on 04-Feb-17.
 */
public class MutableContributorResource extends Resource {
  @Autowired
  ResourceHelper<Contributor, MutableContributor, Long> mResourceHelper;

}
