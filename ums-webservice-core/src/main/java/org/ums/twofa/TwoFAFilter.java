package org.ums.twofa;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String twoFAToken = requestContext.getHeaderString(TwoFAConstants.TWO_FA_TOKEN_NAME);
    Response response;
    if(StringUtils.isEmpty(twoFAToken)) {
      String state =
          mTwoFATokenGenerator.generateToken(SecurityUtils.getSubject().getPrincipal().toString()).getState();
      mMapper.save(
          state,
          getMapperEntry(IOUtils.toString(requestContext.getEntityStream()), requestContext.getUriInfo()
              .getRequestUri(), requestContext.getMediaType().toString(), requestContext.getMethod()));
      response = Response.status(Response.Status.OK).header(TwoFAConstants.TWO_FA_STATE_HEADER, state).build();
      requestContext.abortWith(response);
    }
  }

  private MapperEntry getMapperEntry(String pEntity, URI pUri, String pMediaType, String pMethod) {
    return new MapperEntryImpl(pEntity, pUri, pMediaType, pMethod);
  }
}
