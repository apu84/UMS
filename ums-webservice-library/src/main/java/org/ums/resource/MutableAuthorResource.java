package org.ums.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.library.MutableAuthor;

/**
 * Created by Ifti on 30-Jan-17.
 */
public class MutableAuthorResource extends Resource {
  @Autowired
  ResourceHelper<Author, MutableAuthor, Integer> mResourceHelper;

}
