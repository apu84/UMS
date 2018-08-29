package org.ums.persistent.dao.optCourse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.ums.decorator.optCourse.OptCourseOfferDaoDecorator;
import org.ums.generator.IdGenerator;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class PersistentOptCourseOfferDao extends OptCourseOfferDaoDecorator{
    private JdbcTemplate mJdbcTemplate;
    private IdGenerator mIdGenerator;

    public PersistentOptCourseOfferDao(JdbcTemplate pJdbcTemplate, IdGenerator pIdGenerator) {
        mJdbcTemplate = pJdbcTemplate;
        mIdGenerator = pIdGenerator;
    }
}
