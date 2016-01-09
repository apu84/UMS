package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.regular.Semester;
import org.ums.manager.SemesterManager;

import java.util.List;

/**
 * Created by Ifti on 27-Dec-15.
 */
public class SemesterDaoDecorator extends ContentDaoDecorator<Semester, MutableSemester, Integer> implements SemesterManager {
    private SemesterManager mManager;

    @Override
    public SemesterManager getManager() {
        return mManager;
    }

    @Override
    public List<Semester> getSemesters(Integer pProgramType, Integer pLimit) throws Exception {
        return getManager().getSemesters(pProgramType, pLimit);
    }
}
