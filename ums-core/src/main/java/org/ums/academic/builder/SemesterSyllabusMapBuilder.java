package org.ums.academic.builder;

import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableProgramType;
import org.ums.domain.model.mutable.MutableSemester;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.domain.model.regular.ProgramType;
import org.ums.domain.model.regular.Semester;
import org.ums.domain.model.regular.SemesterSyllabusMap;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;

/**
 * Created by Ifti on 09-Jan-16.
 */
public class SemesterSyllabusMapBuilder  implements Builder<SemesterSyllabusMap, MutableSemesterSyllabusMap> {

  ContentManager<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> mSemesterSyllabusMapManager;

  public SemesterSyllabusMapBuilder(final ContentManager<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> pSemesterSyllabusMapManager) {
    mSemesterSyllabusMapManager = pSemesterSyllabusMapManager;
  }

  public void build(final JsonObjectBuilder pBuilder, final SemesterSyllabusMap pSSMap, final UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {
    pBuilder.add("year", pSSMap.getYear());
    pBuilder.add("semester", pSSMap.getSemester());
  }

  @Override
  public void build(MutableSemesterSyllabusMap pMutable, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {

  }
}