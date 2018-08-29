package org.ums.academic.resource.optCourse.optCourseHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ums.academic.resource.optCourse.optCourseBuilder.OptCourseOfferBuilder;
import org.ums.builder.Builder;
import org.ums.domain.model.immutable.optCourse.OptCourseOffer;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseOffer;
import org.ums.manager.ContentManager;
import org.ums.manager.optCourse.OptCourseOfferManager;
import org.ums.resource.ResourceHelper;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
@Component
public class OptCourseOfferResourceHelper extends ResourceHelper<OptCourseOffer, MutableOptCourseOffer, Long> {
    @Autowired
    OptCourseOfferBuilder mBuilder;
    @Autowired
    OptCourseOfferManager mManager;

    @Override
    public Response post(JsonObject pJsonObject, UriInfo pUriInfo) throws Exception {
        return null;
    }

    @Override
    protected ContentManager<OptCourseOffer, MutableOptCourseOffer, Long> getContentManager() {
        return mManager;
    }

    @Override
    protected Builder<OptCourseOffer, MutableOptCourseOffer> getBuilder() {
        return mBuilder;
    }

    @Override
    protected String getETag(OptCourseOffer pReadonly) {
        return null;
    }
}
