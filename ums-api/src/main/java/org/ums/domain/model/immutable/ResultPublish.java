package org.ums.domain.model.immutable;

import org.ums.domain.model.common.EditType;
import org.ums.domain.model.common.Identifier;
import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.mutable.MutableResultPublish;

import java.io.Serializable;
import java.util.Date;

public interface ResultPublish extends Serializable, LastModifier, Identifier<Integer>,
    EditType<MutableResultPublish> {
  Integer getSemesterId();

  Semester getSemester() throws Exception;

  Integer getProgramId();

  Program getProgram() throws Exception;

  Date getPublishedDate();
}
