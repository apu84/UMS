package org.ums.common.academic.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.ums.common.Resource;
import org.ums.common.academic.resource.helper.GradeSubmissionResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * Created by ikh on 4/29/2016.
 */
public class MutableGradeSubmissionResource extends Resource {

    @Autowired
    GradeSubmissionResourceHelper mResourceHelper;



    @PUT
    public Response saveGradeSheet(final JsonObject pJsonObject) throws Exception {
        return mResourceHelper.saveGradeSheet(pJsonObject);
    }


}
