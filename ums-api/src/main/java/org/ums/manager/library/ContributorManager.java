package org.ums.manager.library;

import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.manager.ContentManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public interface ContributorManager extends
    ContentManager<Contributor, MutableContributor, Integer> {

}
