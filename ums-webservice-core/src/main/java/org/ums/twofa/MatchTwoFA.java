package org.ums.twofa;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import org.glassfish.jersey.server.ContainerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.configuration.UMSConfiguration;
import org.ums.mapper.Mapper;
import org.ums.mapper.MapperEntry;
import org.ums.resource.Resource;

@Component
@Path("/match-two-fa")
@Consumes(Resource.MIME_TYPE_JSON)
@Produces(Resource.MIME_TYPE_JSON)
public class MatchTwoFA extends Resource {
  @Autowired
  Mapper<String, MapperEntry> mMapper;

  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Autowired
  HttpClient mHttpClient;

  @Autowired
  TwoFATokenGenerator mTwoFATokenGenerator;

  @POST
  public Response matchTwoFATest(final @Context Request pRequest, final JsonObject pJsonObject) {
    String state = pJsonObject.getString(TwoFAConstants.TWO_FA_STATE_HEADER);
    String token = pJsonObject.getString(TwoFAConstants.TWO_FA_TOKEN);
    if(mMapper.contains(state) && mTwoFATokenGenerator.validateToken(getLoggedInUserId(), state, token)) {
      MapperEntry mapperEntry = mMapper.lookup(state);
      return mHttpClient
          .getClient()
          .target(UriBuilder.fromUri(mapperEntry.getUri()).scheme("https").build())
          .request(MediaType.valueOf(mapperEntry.getMediaType()))
          .header(TwoFAConstants.TWO_FA_AUTHORIZATION_HEADER,
              ((ContainerRequest) pRequest).getHeaderString(TwoFAConstants.TWO_FA_AUTHORIZATION_HEADER))
          .header(TwoFAConstants.TWO_FA_TOKEN_NAME, token)
          .build(mapperEntry.getMethod(),
              Entity.entity(mapperEntry.getEntity(), MediaType.valueOf(mapperEntry.getMediaType()))).invoke();
    }
    else {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

}
