package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.readOnly.Semester;
import org.ums.manager.SemesterManager;

import java.util.List;

/**
 * Created by Ifti on 27-Dec-15.
 */
public class SemesterDaoDecorator extends ContentDaoDecorator<Semester, MutableSemester, Integer, SemesterManager> implements SemesterManager {
    @Override
    public List<Semester> getSemesters(Integer pProgramType, Integer pLimit) throws Exception {
        return getManager().getSemesters(pProgramType, pLimit);
    }
}
