package org.ums.services.academic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ums.domain.model.immutable.ApplicationCCI;
import org.ums.domain.model.immutable.ParameterSetting;
import org.ums.domain.model.immutable.Student;
import org.ums.domain.model.immutable.UGRegistrationResult;
import org.ums.manager.ApplicationCCIManager;
import org.ums.manager.ParameterSettingManager;
import org.ums.manager.UGRegistrationResultManager;
import org.ums.persistent.model.PersistentApplicationCCI;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by My Pc on 7/18/2016.
 */

@Service
public class ApplicationCCIServiceImpl implements ApplicationCCIService {
  @Autowired
  UGRegistrationResultManager mResultManager;

  @Autowired
  ApplicationCCIManager mAppManager;

  @Autowired
  ParameterSettingManager mParameterSettingManager;



  @Override
   public List<PersistentApplicationCCI> validateForAnomalies(List<PersistentApplicationCCI> pApplicationCCIs, List<UGRegistrationResult> results, Student pStudent) throws Exception{
    Date date = new Date();
    ParameterSetting parameterSetting = mParameterSettingManager.getByParameterAndSemesterId("application_cci",pStudent.getSemesterId());
    Timestamp currentTimestamp = new Timestamp(date.getTime());
    DateFormat startDateFormatter;
    startDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    DateFormat endDateFormatter;
    endDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    Date startDate = startDateFormatter.parse(parameterSetting.getStartDate());
    Date endDate = endDateFormatter.parse(parameterSetting.getEndDate());
    Timestamp startDateTimeStamp = new Timestamp(startDate.getTime());
    Timestamp endDateTimeStamp = new Timestamp(endDate.getTime());

    List<ApplicationCCI> allApps = mAppManager.getAll();
    List<ApplicationCCI> savedApps = new ArrayList<>();
    if(allApps.size()>0){
      savedApps = mAppManager.getByStudentIdAndSemester(pStudent.getId(),pStudent.getSemesterId());
    }
    if(savedApps.size()>0){
      for(PersistentApplicationCCI appsIteration:pApplicationCCIs){
        appsIteration.setMessage("applied");
        break;
      }
      return pApplicationCCIs;
    }
    if(currentTimestamp.before(startDateTimeStamp) && currentTimestamp.after(endDate)){
      for(PersistentApplicationCCI appsIteration:pApplicationCCIs){
        appsIteration.setMessage("timeError");
        break;
      }

      return pApplicationCCIs;
    }
    else{
      JsonObjectBuilder object = Json.createObjectBuilder();
      JsonArrayBuilder children = Json.createArrayBuilder();
      Map<String,List<PersistentApplicationCCI>> examDateWithApplicationMap = new HashMap<>();
      List<PersistentApplicationCCI> appsList = new ArrayList<>();

      for(PersistentApplicationCCI applicationCCI:pApplicationCCIs){

        if(examDateWithApplicationMap.size()==0){
          List<PersistentApplicationCCI> appList = new LinkedList<>();
          appList.add(applicationCCI);

          examDateWithApplicationMap.put(applicationCCI.getExamDate(),appList);
        }
        else{
          boolean occuranceFound=false;
          for(int i=0;i<examDateWithApplicationMap.size();i++){
            if(examDateWithApplicationMap.get(applicationCCI.getExamDate())!=null){
              if(examDateWithApplicationMap.get(applicationCCI.getExamDate()).size()>0){
                List<PersistentApplicationCCI> appListWithOccurance =examDateWithApplicationMap.get(applicationCCI.getExamDate());
                appListWithOccurance.add(applicationCCI);
                examDateWithApplicationMap.put(applicationCCI.getExamDate(),appListWithOccurance);
                occuranceFound=true;
                break;
              }
            }

          }
          if(occuranceFound==false){
            List<PersistentApplicationCCI> appList = new LinkedList<>();
            appList.add(applicationCCI);
            examDateWithApplicationMap.put(applicationCCI.getExamDate(),appList);
          }
        }
        for(UGRegistrationResult result: results){
          if(applicationCCI.getCourseId()==result.getCourseId()){

            if(applicationCCI.getApplicationType().getValue()!=result.getExamType().getId()){
              applicationCCI.setMessage("invalid type, possible reason: unauthorized rest message");
              appsList.add(applicationCCI);
            }


          }
        }


      }

      for(PersistentApplicationCCI iteratorApp: pApplicationCCIs){
        if(examDateWithApplicationMap.get(iteratorApp.getExamDate()).size()>2){
          List<PersistentApplicationCCI> appListInMap = examDateWithApplicationMap.get(iteratorApp.getExamDate());
          for(PersistentApplicationCCI iteratorAppListofTheMap: appListInMap){

            boolean foundOccuranceInAppList=false;

            for(PersistentApplicationCCI iteratorOfAppList:appsList){
              if(iteratorAppListofTheMap.getCourseId()==iteratorOfAppList.getCourseId()){
                String message = "three "+","+iteratorAppListofTheMap.getMessage();
                iteratorOfAppList.setMessage(message);
                foundOccuranceInAppList=true;
                break;
              }
            }
            if(foundOccuranceInAppList==false){
              appsList.add(iteratorAppListofTheMap);
            }

          }
        }
      }
      return appsList;
    }



  }

}
