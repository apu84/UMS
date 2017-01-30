package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.common.Country;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.common.MutableCountry;
import org.ums.domain.model.mutable.library.MutableAuthor;

/**
 * Created by Ifti on 31-Jan-17.
 */
public class MutableCountryResource extends Resource {
  @Autowired
  ResourceHelper<Country, MutableCountry, Integer> mResourceHelper;

}
