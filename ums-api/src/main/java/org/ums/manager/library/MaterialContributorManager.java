package org.ums.manager.library;

import org.ums.domain.model.immutable.library.MaterialContributor;
import org.ums.domain.model.mutable.library.MutableMaterialContributor;
import org.ums.manager.ContentManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public interface MaterialContributorManager extends
    ContentManager<MaterialContributor, MutableMaterialContributor, Integer> {

}
