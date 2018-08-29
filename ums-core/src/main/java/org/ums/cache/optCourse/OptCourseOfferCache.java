package org.ums.cache.optCourse;

import org.ums.cache.ContentCache;
import org.ums.decorator.ContentDaoDecorator;
import org.ums.domain.model.immutable.optCourse.OptCourseOffer;
import org.ums.domain.model.mutable.optCourse.MutableOptCourseOffer;
import org.ums.manager.CacheManager;
import org.ums.manager.optCourse.OptCourseOfferManager;

/**
 * Created by Monjur-E-Morshed on 8/29/2018.
 */
public class OptCourseOfferCache extends
        ContentCache<OptCourseOffer, MutableOptCourseOffer, Long, OptCourseOfferManager> implements
        OptCourseOfferManager {
    CacheManager<OptCourseOffer, Long> mCacheManager;
    @Override
    protected CacheManager<OptCourseOffer, Long> getCacheManager() {
        return mCacheManager;
    }

    public OptCourseOfferCache(CacheManager<OptCourseOffer, Long> pCacheManager) {
        mCacheManager = pCacheManager;
    }
}
