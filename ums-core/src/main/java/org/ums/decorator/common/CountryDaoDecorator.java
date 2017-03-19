package org.ums.decorator.common;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.common.MutableCountry;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.manager.common.CountryManager;
import org.ums.manager.library.AuthorManager;

/**
 * Created by Ifti on 31-Jan-17.
 */
public class CountryDaoDecorator extends ContentDaoDecorator<Country, MutableCountry, Integer, CountryManager>
    implements CountryManager {

}
