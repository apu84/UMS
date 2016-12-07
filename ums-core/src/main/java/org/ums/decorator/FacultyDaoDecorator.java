package org.ums.decorator;

import org.ums.domain.model.immutable.Faculty;
import org.ums.domain.model.mutable.MutableFaculty;
import org.ums.manager.FacultyManager;

/**
 * Created by Monjur-E-Morshed on 06-Dec-16.
 */
public class FacultyDaoDecorator extends
    ContentDaoDecorator<Faculty, MutableFaculty, Integer, FacultyManager> implements FacultyManager {

}
