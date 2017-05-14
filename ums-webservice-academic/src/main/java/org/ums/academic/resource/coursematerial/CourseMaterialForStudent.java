package org.ums.academic.resource.coursematerial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.manager.BinaryContentManager;
import org.ums.resource.Resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;

@Component
@Path("/academic/student/courseMaterial")
@Consumes(Resource.MIME_TYPE_JSON)
public class CourseMaterialForStudent extends AbstractCourseMaterialResource {
  @Autowired
  @Qualifier("courseMaterialFileManagerForStudent")
  BinaryContentManager<byte[]> mBinaryContentManager;

  @Override
  protected BinaryContentManager<byte[]> getBinaryContentManager() {
    return mBinaryContentManager;
  }
}
