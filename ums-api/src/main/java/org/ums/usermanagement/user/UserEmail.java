package org.ums.usermanagement.user;

import java.util.Optional;

public interface UserEmail<T> {
  Optional<T> getByEmail(final String pEmail);
}
