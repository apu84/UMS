package org.ums.decorator.library;

import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.Course;
import org.ums.domain.model.immutable.library.Author;
import org.ums.domain.model.mutable.MutableCourse;
import org.ums.domain.model.mutable.library.MutableAuthor;
import org.ums.manager.CourseManager;
import org.ums.manager.library.AuthorManager;

/**
 * Created by Ifti on 30-Jan-17.
 */
public class AuthorDaoDecorator extends ContentDaoDecorator<Author, MutableAuthor, Integer, AuthorManager> implements
    AuthorManager {

}
