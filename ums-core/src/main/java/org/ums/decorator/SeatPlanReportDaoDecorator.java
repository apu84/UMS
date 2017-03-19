package org.ums.decorator;

import org.ums.domain.model.dto.SeatPlanReportDto;
import org.ums.domain.model.immutable.SeatPlanReport;
import org.ums.domain.model.mutable.MutableSeatPlanReport;
import org.ums.enums.ExamType;
import org.ums.manager.SeatPlanReportManager;

import java.util.List;

/**
 * Created by My Pc on 20-Aug-16.
 */
public class SeatPlanReportDaoDecorator extends
    ContentDaoDecorator<SeatPlanReport, MutableSeatPlanReport, Object, SeatPlanReportManager> implements
    SeatPlanReportManager {
  @Override
  public List<SeatPlanReportDto> getSeatPlanDataForAttendenceSheet(Integer pSemesterId, Integer pExamType,
      String pExamDate) {
    return getManager().getSeatPlanDataForAttendenceSheet(pSemesterId, pExamType, pExamDate);
  }

  @Override
  public List<SeatPlanReportDto> getSeatPlanDataForTopSheet(Integer pSemesterId, Integer pExamType, String pExamDate) {
    return getManager().getSeatPlanDataForTopSheet(pSemesterId, pExamType, pExamDate);
  }

  @Override
  public List<SeatPlanReportDto> getSeatPlanDataForSticker(Integer pSemesterId, Integer pExamType, String pExamDate,
      int pRoomId) {
    return getManager().getSeatPlanDataForSticker(pSemesterId, pExamType, pExamDate, pRoomId);
  }

  @Override
  public List<SeatPlanReport> getSeatPlanDataForSittingArrangement(int pSemesterId, ExamType pExamType) {
    return getManager().getSeatPlanDataForSittingArrangement(pSemesterId, pExamType);
  }
}
