package org.ums.twofa;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.glassfish.jersey.server.ContainerRequest;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.configuration.UMSConfiguration;
import org.ums.mapper.Mapper;
import org.ums.mapper.MapperEntry;
import org.ums.resource.Resource;
import org.ums.usermanagement.user.User;
import org.ums.usermanagement.user.UserManager;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
  @Autowired
  TwoFATokenManager mTwoFATokenManager;
  @Autowired
  private TwoFATokenEmailSender mTwoFATokenEmailSender;
  @Autowired
  private UserManager mUserManager;

  @POST
  public Response matchTwoFATest(final @Context Request pRequest, final JsonObject pJsonObject) {
    String state = pJsonObject.getString(TwoFAConstants.TWO_FA_STATE_HEADER);
    String userProvidedPlainTextToken = pJsonObject.getString(TwoFAConstants.TWO_FA_TOKEN);
    String userProvidedHashedToken = "";
    TwoFAToken dbToken = mTwoFATokenGenerator.getTokenForValidation(getLoggedInUserId(), state);
    if(mMapper.contains(state) && dbToken != null) {
      userProvidedHashedToken = DigestUtils.sha256Hex(String.valueOf(userProvidedPlainTextToken));
      if(!userProvidedHashedToken.equals(dbToken.getOtp())) {
        mTwoFATokenManager.updateWrongTryCount(Long.valueOf(state));
        TwoFAToken token = mTwoFATokenManager.get(Long.valueOf(state));
        if(token.getTryCount() > mUMSConfiguration.getTwoFATokenAllowableWrongTry())
          throw new ExcessiveAttemptsException();
        return Response.status(Response.Status.BAD_REQUEST).build();
      }
      mTwoFATokenManager.updateRightTryCount(Long.valueOf(state));

      MapperEntry mapperEntry = mMapper.lookup(state);
      return mHttpClient
          .getClient()
          .target(UriBuilder.fromUri(mapperEntry.getUri()).scheme("https").build())
          .request(MediaType.valueOf(mapperEntry.getMediaType()))
          .header(TwoFAConstants.TWO_FA_AUTHORIZATION_HEADER,
              ((ContainerRequest) pRequest).getHeaderString(TwoFAConstants.TWO_FA_AUTHORIZATION_HEADER))
          .header(TwoFAConstants.TWO_FA_TOKEN_NAME, userProvidedPlainTextToken)
          .build(mapperEntry.getMethod(),
              Entity.entity(mapperEntry.getEntity(), MediaType.valueOf(mapperEntry.getMediaType()))).invoke();
    }
    else {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  @Path("/resend")
  @POST
  @Produces({MediaType.APPLICATION_JSON})
  public Response resendToken(final @Context Request pRequest, final JsonObject pJsonObject) {
    String state = pJsonObject.getString(TwoFAConstants.TWO_FA_STATE_HEADER);
    TwoFAToken token = mTwoFATokenManager.get(Long.valueOf(state));
    MutableTwoFAToken newToken = null;
    List<TwoFAToken> tokens = mTwoFATokenManager.getUnExpiredTokens(token.getUserId(), token.getType());
    TwoFAToken existingToken = null;
    if(tokens != null && tokens.size() > 0) {
      existingToken = tokens.get(0);
    }
    if(existingToken == null) {
      newToken = new PersistentTwoFAToken();
      SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
      newToken.setUserId(token.getUserId());
      newToken.setType(token.getType());

      Random rnd = new Random();
      int n = 100000 + rnd.nextInt(900000);
      String sha256hex = DigestUtils.sha256Hex(String.valueOf(n));
      newToken.setOtp(sha256hex);
      newToken.setId(newToken.create());
      existingToken = mTwoFATokenManager.get(newToken.getId());
      User user = mUserManager.get(getLoggedInUserId());

      mTwoFATokenEmailSender.sendEmail(String.valueOf(n), existingToken.getExpiredOn(), user.getEmail(), "IUMS",
          "One-Time Password for Online Marks Submission ");
    }

    mMapper.save(existingToken.getId().toString(), mMapper.lookup(state));
    Response.ResponseBuilder builder = Response.created(null);
    builder.status(Response.Status.CREATED);
    JSONObject json = new JSONObject();
    long seconds = (existingToken.getExpiredOn().getTime() - (new Date()).getTime()) / 1000;
    seconds = seconds < 0 ? 0 : seconds;
    json.put("state", String.valueOf(newToken != null ? newToken.getId() : existingToken.getId()));
    json.put("lifeTime", mUMSConfiguration.getTwoFATokenLifeTime());
    json.put("remainingTime", seconds);
    return Response.status(200).entity(json).build();
  }
}
