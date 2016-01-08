package org.ums.common.academic.resource.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.ums.academic.builder.Builder;
import org.ums.academic.model.PersistentStudent;
import org.ums.academic.model.PersistentUser;
import org.ums.cache.LocalCache;
import org.ums.common.academic.resource.ResourceHelper;
import org.ums.common.academic.resource.StudentResource;
import org.ums.domain.model.mutable.MutableRole;
import org.ums.domain.model.mutable.MutableStudent;
import org.ums.domain.model.mutable.MutableUser;
import org.ums.domain.model.regular.Role;
import org.ums.domain.model.regular.Student;
import org.ums.manager.BinaryContentManager;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.List;

@Component
public class StudentResourceHelper extends ResourceHelper<Student, MutableStudent, String> {
  @Autowired
  @Qualifier("studentManager")
  private ContentManager<Student, MutableStudent, String> mManager;

  @Autowired
  @Qualifier("roleManager")
  ContentManager<Role, MutableRole, Integer> mRoleManager;

  @Autowired
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Autowired
  private List<Builder<Student, MutableStudent>> mBuilders;

  @Autowired
  private TransactionTemplate mTransactionTemplate;

  @Override
  public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
    //TODO: Three separate task done here; new student, student photo and new user, needs to be bundled with in a transaction

    return mTransactionTemplate.execute(new TransactionCallback<Response>() {
      Response.ResponseBuilder builder = null;

      @Override
      public Response doInTransaction(TransactionStatus status) {
        try {
          MutableStudent mutableStudent = new PersistentStudent();
          LocalCache localCache = new LocalCache();
          for (Builder<Student, MutableStudent> builder : mBuilders) {
            builder.build(mutableStudent, pJsonObject, localCache);
          }
          mutableStudent.commit(false);

          try {
            String encodingPrefix = "base64,", data = pJsonObject.getString("imageData");
            int contentStartIndex = data.indexOf(encodingPrefix) + encodingPrefix.length();
            byte[] imageData = Base64.getDecoder().decode(data.substring(contentStartIndex));
            mBinaryContentManager.create(imageData, pJsonObject.getString("id"), BinaryContentManager.Domain.PICTURE);

          } catch (IOException e) {
            throw new WebApplicationException("Error while uploading file. Please try again !!");
          }

          MutableUser studentUser = new PersistentUser();
          studentUser.setId(pJsonObject.getString("id"));
          //TODO: Use a password generator to generate temporary password
          studentUser.setTemporaryPassword("testPassword".toCharArray());
          //TODO: Use role name to fetch a particular role, say for Student it should be "student"
          studentUser.setRole(mRoleManager.get(11));
          studentUser.setActive(true);
          studentUser.commit(false);

          URI contextURI = pUriInfo.getBaseUriBuilder().path(StudentResource.class).path(StudentResource.class, "get").build(mutableStudent.getId());
          builder = Response.created(contextURI);
          builder.status(Response.Status.CREATED);

        } catch (Exception e) {
          throw new WebApplicationException("Exception while creating new student", e);
        }
        return builder == null ? null : builder.build();
      }
    });
  }

  @Override
  protected ContentManager<Student, MutableStudent, String> getContentManager() {
    return mManager;
  }

  @Override
  protected List<Builder<Student, MutableStudent>> getBuilders() {
    return mBuilders;
  }

  @Override
  protected String getEtag(Student pReadonly) {
    return pReadonly.getLastModified();
  }
}
