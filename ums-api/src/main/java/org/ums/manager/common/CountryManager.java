package org.ums.manager.common;

import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.common.MutableCountry;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.manager.ContentManager;

/**
 * Created by Ifti on 31-Jan-17.
 */
public interface CountryManager extends ContentManager<Country, MutableCountry, Integer> {

}
