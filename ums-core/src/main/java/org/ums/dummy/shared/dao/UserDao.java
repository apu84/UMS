package org.ums.dummy.shared.dao;

import org.ums.dummy.shared.model.User;

import java.util.List;

public interface UserDao {
  public User get(int userId);

  public User getByName(String pUserId);

  public List<User> getAll();

  public void remove(User user);

  public void update(User user);

  public void create(User user);
}
