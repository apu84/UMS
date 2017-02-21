package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.library.Contributor;
import org.ums.domain.model.immutable.library.MaterialContributor;
import org.ums.domain.model.mutable.library.MutableContributor;
import org.ums.domain.model.mutable.library.MutableMaterialContributor;
import org.ums.manager.library.ContributorManager;
import org.ums.manager.library.MaterialContributorManager;

/**
 * Created by Ifti on 17-Feb-17.
 */
public class MaterialContributorDaoDecorator
    extends
    ContentDaoDecorator<MaterialContributor, MutableMaterialContributor, Integer, MaterialContributorManager>
    implements MaterialContributorManager {

}
