package org.ums.fee.dues;

import org.ums.decorator.ContentDaoDecorator;

public class StudentDuesDaoDecorator extends
    ContentDaoDecorator<StudentDues, MutableStudentDues, Long, StudentDuesManager> implements StudentDuesManager {
}
