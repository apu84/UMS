package org.ums.common.builder;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableStudent;

import javax.json.JsonObject;

@Component
@Qualifier("StudentProfileBuilder")
public class StudentProfileBuilder extends StudentBuilder {
  @Override
  public void build(final MutableStudent pMutableStudent,
                    final JsonObject pJsonObject,
                    final LocalCache pLocalCache) throws Exception {
    pMutableStudent.setId(pJsonObject.getString("id"));
    pMutableStudent.setPresentAddress(pJsonObject.getString("presentAddress"));
    pMutableStudent.setPermanentAddress(pJsonObject.getString("permanentAddress"));
    pMutableStudent.setMobileNo(pJsonObject.getString("mobileNo"));
    pMutableStudent.setPhoneNo(pJsonObject.getString("phoneNo"));
    pMutableStudent.setEmail(pJsonObject.getString("email"));
  }
}
