package org.ums.microservice.instance.earnedleave;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.Employee;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalance;
import org.ums.domain.model.immutable.common.EmployeeEarnedLeaveBalanceHistory;
import org.ums.domain.model.immutable.common.Holidays;
import org.ums.domain.model.immutable.common.LmsApplication;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalance;
import org.ums.domain.model.mutable.common.MutableEmployeeEarnedLeaveBalanceHistory;
import org.ums.domain.model.mutable.common.MutableHolidays;
import org.ums.domain.model.mutable.common.MutableLmsApplication;
import org.ums.enums.common.LeaveApplicationApprovalStatus;
import org.ums.manager.EmployeeManager;
import org.ums.manager.common.EmployeeEarnedLeaveBalanceHistoryManager;
import org.ums.manager.common.EmployeeEarnedLeaveBalanceManager;
import org.ums.manager.common.HolidaysManager;
import org.ums.manager.common.LmsApplicationManager;
import org.ums.persistent.model.common.PersistentHolidays;
import org.ums.persistent.model.common.PersistentLmsApplication;
import org.ums.util.UmsUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EarnedLeaveCounterService {
    @Autowired
    HolidaysManager mHolidaysManager;
    @Autowired
    LmsApplicationManager mLmsApplicationManager;
    @Autowired
    EmployeeEarnedLeaveBalanceManager mEmployeeEarnedLeaveBalanceManager;
    @Autowired
    EmployeeEarnedLeaveBalanceHistoryManager mEmployeeEarnedLeaveBalanceHistoryManager;
    @Autowired
    EmployeeManager mEmployeeManager;


    private List<Holidays> mHolidays;
    private int ACTIVE_STATUS=1;


    public void updateEmployeesEarnedLeaveBalance() throws  Exception{
        List<Employee> employeeList = mEmployeeManager.getAll()
                .parallelStream()
                .filter(e->e.getStatus()==ACTIVE_STATUS)
                .collect(Collectors.toList());
        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        int publicHolidays = calculatePublicHolidays(calendar);
        int weeklyHolidays = calculateTotalWeeklyHolidays(calendar);
        Map<String, EmployeeEarnedLeaveBalance> earnedLeaveBalanceMap = getEmployeeIdMapWithEarnedLeaveBalance();
        Map<String, Integer> employeesTotalLeaveTaken = calculateTotalLeavesOfEmployees(calendar);
        List<MutableEmployeeEarnedLeaveBalance> updatedEarnedLeaveBalance = new ArrayList<>();
        List<MutableEmployeeEarnedLeaveBalanceHistory> earnedLeaveBalanceHistories = new ArrayList<>();

        for(Employee employee: employeeList){
            MutableEmployeeEarnedLeaveBalance employeeEarnedLeaveBalance = (MutableEmployeeEarnedLeaveBalance) earnedLeaveBalanceMap.get(employee.getId());
            
        }

    }



    private int calculateTotalWeeklyHolidays(Calendar pCalender){
        int totalDays = 0;
        int maxDayInMonth = pCalender.getMaximum(Calendar.MONTH);
        for(int i=1; i<=maxDayInMonth; i++){
            pCalender.set(Calendar.DAY_OF_MONTH, i);
            int dayOfWeek = pCalender.get(Calendar.DAY_OF_WEEK);
            if(checkWhetherTheDayIsWithingPublicHolidays(dayOfWeek) == false)
                if(Calendar.FRIDAY == dayOfWeek){
                    totalDays++;
                }
        }
        return totalDays;
    }

    private boolean checkWhetherTheDayIsWithingPublicHolidays(int day){
        boolean found=false;
        for(Holidays holiday: mHolidays){
            if(day >= holiday.getFromDate().getDay() && day<= holiday.getToDate().getDay())
            {
                found = true;
                break;
            }
        }
        return found;
    }


    private Map<String, EmployeeEarnedLeaveBalance> getEmployeeIdMapWithEarnedLeaveBalance(){
        Map<String, EmployeeEarnedLeaveBalance> employeeIdMapWithEarnedLeaveBalance = new HashMap<>();
        List<EmployeeEarnedLeaveBalance> earnedLeaveBalanceList = mEmployeeEarnedLeaveBalanceManager.getAllEarnedLeaveBalanceOfActiveEmployees();
        employeeIdMapWithEarnedLeaveBalance = earnedLeaveBalanceList
                .parallelStream()
                .collect(Collectors.toMap(l->l.getEmployeeId(), l->l));
        return employeeIdMapWithEarnedLeaveBalance;
    }


    private int calculatePublicHolidays(Calendar pCalendar) throws Exception{
        Date fromDate = new Date();
        Date toDate = new Date();
        getFirstAndLastDate(pCalendar, fromDate, toDate);
        List<Holidays> holidays = mHolidaysManager.getHolidays(fromDate, toDate);
        mHolidays = new ArrayList<>();
        for(Holidays holiday: holidays){
            MutableHolidays holidayTmp = (PersistentHolidays)holiday;
            if(holiday.getFromDate().getMonth()> pCalendar.get(Calendar.MONTH))
                holidayTmp.setFromDate(fromDate);
            mHolidays.add(holidayTmp);
        }
        return mHolidays.stream()
                .mapToInt(e-> (e.getFromDate().getDay() - e.getToDate().getDay())+1)
                .sum();
    }


    private Map<String, Integer> calculateTotalLeavesOfEmployees(Calendar pCalendar) throws Exception{
        Date fromDate = new Date(), toDate = new Date();
        getFirstAndLastDate(pCalendar, fromDate, toDate);
        List<LmsApplication> leaveApplicationList = mLmsApplicationManager.getWithinDateRange(fromDate, toDate, LeaveApplicationApprovalStatus.APPLICATION_APPROVED);

        Map<String, Integer> employeeIdMapWithTotalDaysLeaveTaken = new HashMap<>();
        for(LmsApplication lmsApplication: leaveApplicationList){
            MutableLmsApplication lmsApplicationTmp = (PersistentLmsApplication) lmsApplication;
            int totalDays = 0;
            if(lmsApplication.getToDate().after(toDate))
                lmsApplicationTmp.setToDate(toDate);

            if(employeeIdMapWithTotalDaysLeaveTaken.containsKey(lmsApplication.getEmployeeId())){
                totalDays = employeeIdMapWithTotalDaysLeaveTaken.get(lmsApplication.getEmployeeId());
                totalDays = totalDays+ ((lmsApplicationTmp.getFromDate().getDay()- lmsApplicationTmp.getToDate().getDay())+1);
            }else{
                totalDays = (lmsApplicationTmp.getFromDate().getDay()- lmsApplicationTmp.getToDate().getDay())+1;
            }
            employeeIdMapWithTotalDaysLeaveTaken.put(lmsApplication.getEmployeeId(), totalDays);
        }
        return employeeIdMapWithTotalDaysLeaveTaken;
    }


    private void getFirstAndLastDate(Calendar pCalendar, Date pStartDate, Date pEndDate) throws Exception{
        int month = pCalendar.get(Calendar.MONTH);
        String monthStr = (month+"").length()==1?"0"+month:month+"";
        Date fromDate = UmsUtils.convertToDate("01-"+monthStr+"-"+pCalendar.get(Calendar.YEAR), "dd-MM-yyyy");
        Date toDate = UmsUtils.convertToDate(pCalendar.getMaximum(Calendar.MONTH)+"-"+pCalendar.get(Calendar.MONTH)+"-"+pCalendar.get(Calendar.YEAR), "dd-MM-yyyy");
    }


}
