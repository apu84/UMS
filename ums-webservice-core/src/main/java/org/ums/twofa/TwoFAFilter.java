package org.ums.twofa;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.ums.configuration.UMSConfiguration;
import org.ums.mapper.Mapper;
import org.ums.mapper.MapperEntry;
import org.ums.mapper.MapperEntryImpl;

@Component
@Provider
@TwoFA
public class TwoFAFilter implements ContainerRequestFilter {
  @Autowired
  Mapper<String, MapperEntry> mMapper;
  @Autowired
  TwoFATokenGenerator mTwoFATokenGenerator;
  @Autowired
  TwoFATokenManager twoFATokenManager;
  @Context
  ResourceInfo resourceInfo;
  @Autowired
  UMSConfiguration mUMSConfiguration;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String twoFAToken = requestContext.getHeaderString(TwoFAConstants.TWO_FA_TOKEN_NAME);
    Method resourceMethod = resourceInfo.getResourceMethod();
    TwoFA methodAnnotation = resourceMethod.getAnnotation(TwoFA.class);
    String type = null;
    String userId = SecurityUtils.getSubject().getPrincipal().toString();
    if(methodAnnotation != null) {
      type = methodAnnotation.type();
    }

    Response response;
    if(StringUtils.isEmpty(twoFAToken)) {
      Long id = mTwoFATokenGenerator.generateToken(userId, type).getId();
      TwoFAToken token = twoFATokenManager.get(id);
      mMapper.save(
          id.toString(),
          getMapperEntry(IOUtils.toString(requestContext.getEntityStream()), requestContext.getUriInfo()
              .getRequestUri(), requestContext.getMediaType().toString(), requestContext.getMethod()));
      long seconds = (token.getExpiredOn().getTime() - (new Date()).getTime()) / 1000;
      seconds = seconds < 0 ? 0 : seconds;
      response =
          Response.status(428).header(TwoFAConstants.TWO_FA_STATE_HEADER, id.toString())
              .header(TwoFAConstants.TWO_FA_LIFE_HEADER, mUMSConfiguration.getTwoFATokenLifeTime())
              .header(TwoFAConstants.TWO_FA_REMAINING_TIME_HEADER, seconds).build();
      requestContext.abortWith(response);
    }
    else {
      List<TwoFAToken> tokenList = twoFATokenManager.getUnExpiredTokens(userId, type);
      if(tokenList != null && tokenList.size() > 0) {
        TwoFAToken token = tokenList.get(0);
        System.out.println(token.getOtp());
      }
    }
  }

  private MapperEntry getMapperEntry(String pEntity, URI pUri, String pMediaType, String pMethod) {
    return new MapperEntryImpl(pEntity, pUri, pMediaType, pMethod);
  }
}
