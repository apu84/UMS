package org.ums.security.bearertoken.util;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class HTTP {

  private HTTP() {}

  public static enum Status {
    OK(200), BAD_REQUEST(400), UNAUTHORIZED(401), NOT_FOUND(404), FORBIDDEN(403), INTERNAL_ERROR(
        500);

    private final int code;

    private Status(int code) {
      this.code = code;
    }

    public int toInt() {
      return code;
    }

    public String toString() {
      return Integer.toString(toInt());
    }
  }

  public static final class JSON {

    private JSON() {}

    static JsonObject fromArgs(Object... args) {
      Preconditions.checkArgument(args.length % 2 == 0,
          "There must be an even number of argument strings");
      try {
        JsonObject obj = new JsonObject();
        for(int i = 0; i < args.length; i += 2) {
          obj.add((String) args[i], new JsonPrimitive(args[i + 1].toString()));
        }
        return obj;
      } catch(Exception e) {
        throw new RuntimeException(e);
      }
    }

  }

  public static void write(ServletResponse response, String mimeType, Status returnCode,
      String output) {
    write(WebUtils.toHttp(response), mimeType, returnCode, output);
  }

  public static void write(HttpServletResponse response, String mimeType, Status returnCode,
      String output) {
    try {
      response.setContentType(mimeType);
      response.setStatus(returnCode.toInt());
      response.getWriter().println(output);
      response.getWriter().flush();
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void writeError(ServletResponse response, Status error) {
    writeError(WebUtils.toHttp(response), error);
  }

  public static void writeError(HttpServletResponse response, Status error) {
    try {
      response.sendError(error.toInt());
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void writeJSON(HttpServletResponse response, JsonObject obj) {
    write(response, MimeTypes.JSON, Status.OK, obj.toString());
  }

  public static void writeJSON(ServletResponse response, JsonObject obj) {
    write(response, MimeTypes.JSON, Status.OK, obj.toString());
  }

  public static void writeAsJSON(HttpServletResponse response, Object... args) {
    writeJSON(response, JSON.fromArgs(args));
  }

  public static void writeAsJSON(ServletResponse response, Object... args) {
    writeJSON(response, JSON.fromArgs(args));
  }

}
