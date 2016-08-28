package org.ums.common.academic.resource;

import org.springframework.stereotype.Component;
import org.ums.common.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Ifti on 02-Jan-16.
 */
@Component
@Path("/report/password")
@Consumes(Resource.MIME_TYPE_JSON)
public class UserPasswordReportResource {

  private static final String FILE_PATH = "e:\\1.pdf";


  @POST
  @Consumes("application/json")
  @Produces("application/pdf")
  public StreamingOutput getUserPasswordReport() throws Exception {
    File file = new File(FILE_PATH);
    return new StreamingOutput() {
      public void write(OutputStream output) throws IOException, WebApplicationException {

        byte[] bFile = new byte[(int) file.length()];
        FileInputStream fileInputStream=null;
        try {
          //convert file into array of bytes
          fileInputStream = new FileInputStream(file);
          fileInputStream.read(bFile);
          fileInputStream.close();
          System.out.println("Done");
        }catch(Exception e){
          e.printStackTrace();
        }

        try {
          output.write(bFile);
        } catch (Exception e) {
          throw new WebApplicationException(e);
        }
      }
    };
  }


}
