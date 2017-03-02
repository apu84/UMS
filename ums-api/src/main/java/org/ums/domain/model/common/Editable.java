package org.ums.domain.model.common;

public interface Editable<I> {
  I create();

  void update();

  void delete();
}
