package org.ums.builder;

import org.springframework.stereotype.Component;
import org.ums.builder.Builder;
import org.ums.persistent.model.PersistentSyllabus;
import org.ums.cache.LocalCache;
import org.ums.domain.model.mutable.MutableSemesterSyllabusMap;
import org.ums.domain.model.immutable.SemesterSyllabusMap;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.UriInfo;

@Component
public class SemesterSyllabusMapBuilder implements Builder<SemesterSyllabusMap, MutableSemesterSyllabusMap> {

  public void build(final JsonObjectBuilder pBuilder, final SemesterSyllabusMap pSSMap, final UriInfo pUriInfo,
      final LocalCache pLocalCache) {
    pBuilder.add("id", pSSMap.getId());
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
  public void build(MutableSemesterSyllabusMap pMutableSemesterSyllabusMap, JsonObject pJsonObject,
      LocalCache pLocalCache) {

    pMutableSemesterSyllabusMap.setId(pJsonObject.getInt("id"));
    PersistentSyllabus syllabus = new PersistentSyllabus();
    syllabus.setId(pJsonObject.getString("syllabusId"));
    pMutableSemesterSyllabusMap.setSyllabus(syllabus);

  }
}
