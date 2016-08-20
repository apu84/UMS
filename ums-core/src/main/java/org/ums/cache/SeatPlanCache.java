package org.ums.cache;

import org.ums.domain.model.common.LastModifier;
import org.ums.domain.model.immutable.SeatPlan;
import org.ums.domain.model.mutable.MutableSeatPlan;
import org.ums.manager.CacheManager;
import org.ums.manager.SeatPlanManager;
import org.ums.util.CacheUtil;

import java.util.List;

/**
 * Created by My Pc on 5/8/2016.
 */
public class SeatPlanCache extends ContentCache<SeatPlan, MutableSeatPlan, Integer, SeatPlanManager> implements SeatPlanManager {

  private CacheManager mCacheManager;

  public SeatPlanCache(final CacheManager pCacheManager) {
    mCacheManager = pCacheManager;
  }

  @Override
  protected CacheManager<SeatPlan, Integer> getCacheManager() {
    return mCacheManager;
  }

  @Override
  protected String getCacheKey(Integer pId) {
    return CacheUtil.getCacheKey(SeatPlan.class, pId);
  }

  @Override
  public int createSeatPlanForCCI(List<MutableSeatPlan> pSeatPlans)throws Exception {
    return getManager().createSeatPlanForCCI(pSeatPlans);
  }

  @Override
  public List<SeatPlan> getBySemesterAndGroupAndExamType(int pSemesterId, int pGropNo, int pExamType) throws Exception{
    List<SeatPlan> readOnlys = getManager().getBySemesterAndGroupAndExamType(pSemesterId, pGropNo, pExamType);

    for(SeatPlan readOnly: readOnlys){
      getCacheManager().put(getCacheKey(readOnly.getId()),readOnly);
    }
    return readOnlys;
  }

  @Override
  public List<SeatPlan> getByRoomSemesterGroupExamType(int pRoomId, int pSemesterId, int pGroupNo, int pExamType) throws Exception{
    List<SeatPlan> readOnlys = getManager().getByRoomSemesterGroupExamType(pRoomId, pSemesterId, pGroupNo, pExamType);
    for(SeatPlan readOnly: readOnlys){
      getCacheManager().put(getCacheKey(readOnly.getId()),readOnly);
    }
    return readOnlys;
  }

  @Override
  public int deleteBySemesterGroupExamType(int pSemesterId, int pGroupNo, int pExamType) throws Exception{
   return  getManager().deleteBySemesterGroupExamType(pSemesterId, pGroupNo, pExamType);

  }

  @Override
  public int deleteBySemesterGroupExamTypeAndExamDate(int pSemesterId, int pGroupNo, int pExamType, String pExamDate) throws Exception{
    return getManager().deleteBySemesterGroupExamTypeAndExamDate(pSemesterId,pGroupNo,pExamType,pExamDate);
  }

  @Override
  public List<SeatPlan> getBySemesterGroupTypeRoomRowAndCol(int pSemesterId, int pGroupNo, int pType, int pRoomId, int pRow, int pCol) throws Exception{
    List<SeatPlan> readOnlys = getManager().getBySemesterGroupTypeRoomRowAndCol(pSemesterId, pGroupNo, pType, pRoomId, pRow, pCol);
    for(SeatPlan readOnly: readOnlys){
      getCacheManager().put(getCacheKey(readOnly.getId()),readOnly);
    }
    return readOnlys;
  }

  @Override
  public int checkIfExistsBySemesterGroupTypeRoomRowAndCol(int pSemesterId, int pGroupNo, int pType, int pRoomId, int pRow, int pCol) throws Exception{
    return getManager().checkIfExistsBySemesterGroupTypeRoomRowAndCol(pSemesterId, pGroupNo, pType, pRoomId, pRow, pCol);
  }

  @Override
  public int checkIfExistsByRoomSemesterGroupExamType(int pRoomId, int pSemesterId, int pGroupNo, int pExamType) throws Exception {
    /*long pCacheKey = generateCacheKeyForRoomSemesterGroupExamType(pRoomId, pSemesterId, pGroupNo, pExamType);
    String cacheKey = CacheUtil.getCacheKey(SeatPlan.class, pCacheKey);
    Object pReadonly = getCacheManager().get(cacheKey);
    if (pReadonly == null) {
      pReadonly = getManager().checkIfExistsByRoomSemesterGroupExamType(pRoomId, pSemesterId, pGroupNo, pExamType);
      TemporaryContainer temporaryContainer = new TemporaryContainer(pReadonly);
      getCacheManager().put(cacheKey, temporaryContainer);
    }
    return (Integer) ((TemporaryContainer)pReadonly).getContain();*/
    return getManager().checkIfExistsByRoomSemesterGroupExamType(pRoomId,pSemesterId,pGroupNo,pExamType);
  }


  @Override
  public List<SeatPlan> getForStudent(String pStudentId, Integer pSemesterId) throws Exception{
    List<SeatPlan> readOnlys = getManager().getForStudent(pStudentId,pSemesterId);
    for(SeatPlan readOnly: readOnlys){
      getCacheManager().put(getCacheKey(readOnly.getId()),readOnly);
    }
    return readOnlys;
  }

  @Override
  public List<SeatPlan> getBySemesterAndGroupAndExamTypeAndExamDate(int pSemesterId, int pGropuNo, int pExamType, String pExamDate) throws Exception{
    List<SeatPlan> readOnlys = getManager().getBySemesterAndGroupAndExamTypeAndExamDate(pSemesterId,pGropuNo,pExamType,pExamDate);
    for(SeatPlan readOnly: readOnlys){
      getCacheManager().put(getCacheKey(readOnly.getId()),readOnly);
    }
    return readOnlys;
  }

  @Override
  public int checkIfExistsBySemesterGroupTypeExamDateRoomRowAndCol(int pSemesterId, int pGroupNo, int pType, String pExamDate, int pRoomId, int pRow, int pCol) throws Exception{
    return getManager().checkIfExistsBySemesterGroupTypeExamDateRoomRowAndCol(pSemesterId,pGroupNo,pType,pExamDate,pRoomId,pRow,pCol);

  }

  @Override
  public List<SeatPlan> getForStudentAndCCIExam(String pStudentId, Integer pSemesterid, String pExamDate) throws Exception{
    List<SeatPlan> readOnlys = getManager().getForStudentAndCCIExam(pStudentId,pSemesterid,pExamDate);
    for(SeatPlan readOnly: readOnlys){
      getCacheManager().put(getCacheKey(readOnly.getId()),readOnly);
    }
    return readOnlys;
  }

  @Override
  public List<SeatPlan> getSeatPlanOrderByExamDateAndCourseAndYearAndSemesterAndStudentId(Integer pSemesterId, Integer pExamType)throws Exception {
    List<SeatPlan> readOnlys= getManager().getSeatPlanOrderByExamDateAndCourseAndYearAndSemesterAndStudentId(pSemesterId,pExamType);
    for(SeatPlan readOnly: readOnlys){
      getCacheManager().put(getCacheKey(readOnly.getId()),readOnly);
    }
    return readOnlys;
  }

  protected long generateCacheKeyForRoomSemesterGroupExamType(int pRoomId, int pSemesterId, int pGroupNo, int pExamType) throws Exception{
    StringBuilder builder = new StringBuilder();
    builder.append(pRoomId).append(pSemesterId).append(pGroupNo).append(pExamType);
    return Long.parseLong(builder.toString());
  }



  class TemporaryContainer implements LastModifier {
    private Object contain;

    public TemporaryContainer(Object pContain) {
      contain = pContain;
    }

    public Object getContain() {
      return contain;
    }

    @Override
    public String getLastModified() {
      return "";
    }
  }
}
