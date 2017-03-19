package org.ums.manager;

import org.ums.domain.model.dto.SeatPlanReportDto;
import org.ums.domain.model.immutable.SeatPlanReport;
import org.ums.domain.model.mutable.MutableSeatPlanReport;
import org.ums.enums.ExamType;

import java.util.List;

/**
 * Created by My Pc on 20-Aug-16.
 */
public interface SeatPlanReportManager extends ContentManager<SeatPlanReport, MutableSeatPlanReport, Object> {
  List<SeatPlanReportDto> getSeatPlanDataForAttendenceSheet(Integer pSemesterId, Integer pExamType, String pExamDate);

  List<SeatPlanReportDto> getSeatPlanDataForTopSheet(Integer pSemesterId, Integer pExamType, String pExamDate);

  List<SeatPlanReportDto> getSeatPlanDataForSticker(Integer pSemesterId, Integer pExamType, String pExamDate,
      int pRoomId);

  List<SeatPlanReport> getSeatPlanDataForSittingArrangement(int pSemesterId, ExamType pExamType);

}
