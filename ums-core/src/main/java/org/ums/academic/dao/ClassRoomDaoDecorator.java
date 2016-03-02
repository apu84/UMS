package org.ums.academic.dao;

import org.ums.domain.model.mutable.MutableClassRoom;
import org.ums.domain.model.mutable.MutableSyllabus;
import org.ums.domain.model.readOnly.ClassRoom;
import org.ums.domain.model.readOnly.Syllabus;
import org.ums.manager.ClassRoomManager;
import org.ums.manager.SyllabusManager;

import java.util.List;

/**
 * Created by Ifti on 13-Feb-16.
 */
public class ClassRoomDaoDecorator extends ContentDaoDecorator<ClassRoom, MutableClassRoom, Integer, ClassRoomManager> implements ClassRoomManager {
  @Override
  public List<ClassRoom> getRoomList() throws Exception {
    return getManager().getRoomList();
  }
}