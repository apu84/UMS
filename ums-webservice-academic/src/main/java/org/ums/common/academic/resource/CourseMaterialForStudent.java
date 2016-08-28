package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import org.ums.manager.BinaryContentManager;

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
