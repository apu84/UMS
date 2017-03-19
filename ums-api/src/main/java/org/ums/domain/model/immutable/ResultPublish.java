package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableResultPublish;

import java.io.Serializable;
import java.util.Date;

public interface ResultPublish extends Serializable, LastModifier, Identifier<Long>, EditType<MutableResultPublish> {
  Integer getSemesterId();

  Semester getSemester();

  Integer getProgramId();

  Program getProgram();

  Date getPublishedDate();
}
