package org.ums.resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.resource.helper.SupplierResourceHelper;
import org.ums.util.UmsUtils;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ifti on 04-Feb-17.
 */

@Component
@Path("supplier")
@Produces(Resource.MIME_TYPE_JSON)
@Consumes(Resource.MIME_TYPE_JSON)
public class SupplierResource extends MutableSupplierResource {
  @Autowired
  SupplierResourceHelper mResourceHelper;

  @GET
  @Path("/all")
  public JsonObject getAll() throws Exception {
    return mResourceHelper.getAll(mUriInfo);
  }

  @GET
  @Path(PATH_PARAM_OBJECT_ID)
  public Response get(final @Context Request pRequest, final @PathParam("object-id") Long pObjectId) throws Exception {
    return mResourceHelper.get(pObjectId, pRequest, mUriInfo);
  }

  @GET
  @Path("/all/ipp/{item-per-page}/page/{page}/order/{order}/filter/{filter}")
  public JsonObject getAllForPagination(final @Context Request pRequest,
      final @PathParam("item-per-page") int pItemPerPage, final @PathParam("page") int pPage,
      final @PathParam("order") String pOrder, final @PathParam("filter") String pFilter) throws Exception {
    System.out.println(pFilter);

    String abc = "{\"rules\":" + pFilter + "}";
    JSONParser parser = new JSONParser();
    Object obj = parser.parse(abc);

    JSONObject filters = (JSONObject) obj;
    JSONArray rules = (JSONArray) filters.get("rules");

    String groupOperation = "and";
    String where = "";
    List<String> whereArray = new ArrayList<String>();

    for(Object rule_ : rules) {
      JSONObject rule = (JSONObject) rule_;
      String fieldName = (String) rule.get("fieldName");
      String fieldData = (String) rule.get("fieldValue");
      String op = (String) rule.get("operator");
      String fieldOperation = "";

      if(op.equalsIgnoreCase("eq")) {

        if(fieldName.contains("date"))
          fieldOperation = " = to_date('" + fieldData + "','dd-MM-YYYY')";
        else
          fieldOperation = " = '" + fieldData + "'";
      }
      else if(op.equalsIgnoreCase("ne"))
        fieldOperation = " != '" + fieldData + "'";

      else if(op.equalsIgnoreCase("lt")) {
        if(fieldName.contains("date"))
          fieldOperation = " < to_date('" + fieldData + "','dd-MM-YYYY')";
        else
          fieldOperation = " < '" + fieldData + "'";
      }
      else if(op.equalsIgnoreCase("gt")) {
        if(fieldName.contains("date"))
          fieldOperation = " > to_date('" + fieldData + "','dd-MM-YYYY')";
        else
          fieldOperation = " > '" + fieldData + "'";
      }

      else if(op.equalsIgnoreCase("le")) {
        if(fieldName.contains("date"))
          fieldOperation = " <= to_date('" + fieldData + "','dd-MM-YYYY')";
        else
          fieldOperation = " <= '" + fieldData + "'";
      }
      else if(op.equalsIgnoreCase("ge")) {
        if(fieldName.contains("date"))
          fieldOperation = " >= to_date('" + fieldData + "','dd-MM-YYYY')";
        else
          fieldOperation = " >= '" + fieldData + "'";
      }
      else if(op.equalsIgnoreCase("eqMonth")) {

        fieldOperation = " = " + fieldData + "";
      }
      else if(op.equalsIgnoreCase("eqYear")) {

        fieldOperation = " = '" + fieldData + "'";
      }

      else if(op.equalsIgnoreCase("nu"))
        fieldOperation = " = '' ";
      else if(op.equalsIgnoreCase("nn"))
        fieldOperation = " != '' ";
      else if(op.equalsIgnoreCase("in"))
        fieldOperation = " IN (" + fieldData + ")";
      else if(op.equalsIgnoreCase("ni"))
        fieldOperation = " NOT IN '" + fieldData + "";
      else if(op.equalsIgnoreCase("bw"))
        fieldOperation = " LIKE '" + fieldData + "%'";
      else if(op.equalsIgnoreCase("bn"))
        fieldOperation = " NOT LIKE '" + fieldData + "%'";
      else if(op.equalsIgnoreCase("ew"))
        fieldOperation = " LIKE '%" + fieldData + "'";
      else if(op.equalsIgnoreCase("en"))
        fieldOperation = " Not LIKE '%" + fieldData + "'";
      else if(op.equalsIgnoreCase("cn"))
        fieldOperation = " LIKE '%" + fieldData + "%'";
      else if(op.equalsIgnoreCase("nc"))
        fieldOperation = " NOT LIKE '%" + fieldData + "%'";

      if(fieldOperation != "") {
        whereArray.add(fieldName + " " + fieldOperation);
      }
    }
    String[] array = whereArray.toArray(new String[whereArray.size()]);
    if(whereArray.size() > 0) {
      where += UmsUtils.join(" " + groupOperation + " ", array);
    }
    else {
      where = "";
    }
    where = where.equals("") ? "" : " Where " + where;
    System.out.println(where);

    return mResourceHelper.getAllForPagination(pItemPerPage, pPage, pOrder, where, mUriInfo);
  }
}
