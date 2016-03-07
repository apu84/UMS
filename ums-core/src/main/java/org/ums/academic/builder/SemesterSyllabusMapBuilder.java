package org.ums.academic.builder;

import org.ums.academic.model.PersistentSyllabus;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.domain.model.readOnly.SemesterSyllabusMap;
import org.ums.manager.ContentManager;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Ifti on 09-Jan-16.
 */
public class SemesterSyllabusMapBuilder  implements Builder<SemesterSyllabusMap, MutableSemesterSyllabusMap> {

  ContentManager<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> mSemesterSyllabusMapManager;

  public SemesterSyllabusMapBuilder(final ContentManager<SemesterSyllabusMap, MutableSemesterSyllabusMap, Integer> pSemesterSyllabusMapManager) {
    mSemesterSyllabusMapManager = pSemesterSyllabusMapManager;
  }

  public void build(final JsonObjectBuilder pBuilder, final SemesterSyllabusMap pSSMap, final UriInfo pUriInfo, final LocalCache pLocalCache) throws Exception {
    pBuilder.add("id",pSSMap.getId());
    pBuilder.add("year", pSSMap.getYear());
    pBuilder.add("semester", pSSMap.getSemester());
    pBuilder.add("academicSemester", pSSMap.getSyllabus().getSemester().getName());
    pBuilder.add("semesterStatus", pSSMap.getSyllabus().getSemester().getStatus().getValue());
    pBuilder.add("deptName", pSSMap.getProgram().getDepartment().getShortName());
    pBuilder.add("programName", pSSMap.getProgram().getShortName());
    pBuilder.add("syllabusId", pSSMap.getSyllabus().getId());
    pBuilder.add("syllabusName", (pSSMap.getSyllabus()).getSemester().getName());

  }

  @Override
  public void build(MutableSemesterSyllabusMap pMutableSemesterSyllabusMap, JsonObject pJsonObject, LocalCache pLocalCache) throws Exception {

    pMutableSemesterSyllabusMap.setId(pJsonObject.getInt("id"));
    PersistentSyllabus syllabus=new PersistentSyllabus();
    syllabus.setId(pJsonObject.getString("syllabusId"));
    pMutableSemesterSyllabusMap.setSyllabus(syllabus);

  }
}