package org.ums.cache;

import org.ums.domain.model.dto.SemesterSyllabusMapDto;
import org.ums.domain.model.immutable.SemesterSyllabusMap;
import org.ums.domain.model.immutable.Syllabus;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.manager.CacheManager;
import org.ums.manager.SemesterSyllabusMapManager;
import org.ums.util.CacheUtil;

import java.util.List;

public class SemesterSyllabusMapCache
    extends
    ContentCache<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer, SemesterSyllabusMapManager>
    implements SemesterSyllabusMapManager {
  private CacheManager<SemesterSyllabusMap, Integer> mCacheManager;

  public SemesterSyllabusMapCache(final CacheManager<SemesterSyllabusMap, Integer> pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SemesterSyllabusMap, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  public List<SemesterSyllabusMap> getMapsByProgramSemester(Integer pProgramId, Integer pSemesterId) {
    return getManager().getMapsByProgramSemester(pProgramId, pSemesterId);
  }

  @Override
  public void copySyllabus(SemesterSyllabusMapDto pSemesterSyllabusMapDto) {
    getManager().copySyllabus(pSemesterSyllabusMapDto);
  }

  @Override
  public List<Syllabus> getSyllabusForSemester(Integer pProgramId, Integer pSemesterId) {
    return getManager().getSyllabusForSemester(pProgramId, pSemesterId);
  }

  @Override
  public Syllabus getSyllabusForSemester(Integer pProgramId, Integer pSemesterId, Integer pYear,
      Integer pSemester) {
    return getManager().getSyllabusForSemester(pProgramId, pSemesterId, pYear, pSemester);
  }
}
