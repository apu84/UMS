module ums {
  import ISemester = ums;
  interface IClassRoutineScope extends ng.IScope {
    semesterArr:Array<ISemester>;
    courseArr:Array<ICourse>;
    roomArr:Array<IClassRoom>;
    routineArr:Array<IClassRoutine>;
    addedRoutine:any;
    semesterId:number;
    programTypeId:number;
    studentsYear:number;
    studentsSemester:number;
    section:string;
    semesterStatus:number;
    dates:Array<IDate>;
    times:Array<ITime>;
    sessionalSections:Array<ISessionalSections>;
    courseType:string;
    showLoader:boolean;
    showRoutineTable:boolean;

    addedDate:string;
    addedCourse:string;
    addedSection:string;
    addedStartTime:string;
    addedEndTime:string;
    addedRoomNo:string;

    //map
    courseIdMapCourseNo:any;
    dateMap:any;
    dateMapFromDateToNumber:any;
    courseNoMapCourseId:any;
    courseNoMapCourse:any;
    timeIdWithTimeMap:any;
    timeWithTimeIdMap:any;
    addDivControl:Function;

    getSemesters:Function;
    searchForRoutineData:Function;
    resetDivs:Function;
    courseSelected:Function;
    addData:Function;
    cancelData:Function;
    addRoutineData:Function;
    editRoutineData:Function;
    cancelRoutineData:Function;
    sectionChanged:Function;
    dateSelected:Function;
    startTimeSelected:Function;
    endTimeSelected:Function;
    roomNoSelected:Function;
    deleteRoutineData:Function;

    showSaveButton:boolean;
    saveClassRoutine:Function;
    data:any;
  }

  interface ISemester{
    id:number;
    name:string;
    status:number;
  }

  interface ISessionalSections{
    id:string;
    name:string;
  }

  interface ICourse{
    id:string;
    no:string;
    type:string;
  }

  interface IDate{
    id:number;
    name:string;
  }

  interface ITime{
    id:string;
    val:string;
  }
  interface IClassRoom{
    id:number;
    roomNo:string;
  }

  interface IClassRoutine{
    id:number;
    semesterId:number;
    section:string;
    courseId:string;
    courseNo:string;
    programId:number;
    day:string;
    academicYear:number;
    academicSemester:number;
    startTime:string;
    endTime:string;
    duration:string;
    roomNo:string;
    updated:boolean;
    editRoutine:boolean;
    showEditButton:boolean;
    showAddButton:boolean;
    showCancelButton:boolean;
    courseType:string;
    status:string;
  }


  export class ClassRoutine  {


    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','semesterService','courseService','classRoomService','classRoutineService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IClassRoutineScope,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService, private semesterService:SemesterService,
                private courseService:CourseService, private classRoomService:ClassRoomService, private classRoutineService:ClassRoutineService) {


      $scope.showSaveButton=false;
      $scope.showRoutineTable=false;
      $scope.addedRoutine={};
      $scope.data = {
        programTypeOptions:appConstants.programType
      };
      $scope.dates = appConstants.weekday;
      $scope.times = appConstants.timeChecker;
      $scope.courseIdMapCourseNo={};
      $scope.courseNoMapCourseId={};
      $scope.timeIdWithTimeMap={};
      $scope.timeWithTimeIdMap={};
      $scope.dateMap={};
      $scope.dateMapFromDateToNumber={};
      $scope.getSemesters = this.getSemesters.bind(this);
      $scope.searchForRoutineData = this.searchForRoutineData.bind(this);
      $scope.resetDivs = this.resetDivs.bind(this);
      $scope.courseSelected = this.courseSelected.bind(this);
      $scope.addData = this.addData.bind(this);
      $scope.cancelData = this.cancelData.bind(this);
      $scope.addRoutineData=this.addRoutineData.bind(this);
      $scope.editRoutineData  = this.editRoutineData.bind(this);
      $scope.cancelRoutineData = this.cancelRoutineData.bind(this);
      $scope.sectionChanged  = this.sectionChanged.bind(this);
      $scope.dateSelected = this.dateSelected.bind(this);
      $scope.startTimeSelected = this.startTimeSelected.bind(this);
      $scope.endTimeSelected = this.endTimeSelected.bind(this);
      $scope.roomNoSelected = this.roomNoSelected.bind(this) ;
      $scope.deleteRoutineData = this.deleteRoutinedata.bind(this);
      $scope.addDivControl = this.addDivControl.bind(this);
      $scope.saveClassRoutine=this.saveClassRoutine.bind(this);

      Utils.setValidationOptions("form-horizontal");
      this.initializeAddVariables();
      this.$scope.programTypeId=Utils.UG;
      this.getSemesters();

    }

    private addDivControl(operation:string):void{
      if(operation=="show"){
        $("#downArrowDiv").slideUp(100);
        $("#upArrowDiv").slideDown(200);
        $("#addNewDataTable").fadeIn(200);
      }
      else if(operation=="hide"){
        $("#downArrowDiv").slideDown(200);
        $("#upArrowDiv").slideUp(100);
        $("#addNewDataTable").fadeOut(200);
      }

    }

    private deleteRoutinedata(routine:IClassRoutine){
      this.$scope.showSaveButton=true;
      for(var i=0;i<this.$scope.routineArr.length;i++){
        if(this.$scope.routineArr[i]===routine){
         // this.$scope.routineArr.splice(i,1);
          this.$scope.routineArr[i].status="deleted";
          break;
        }
      }
    }

    private dateSelected(date:string,routine:IClassRoutine){
      this.$scope.addedDate=date;
      routine.day=date;
    }

    private startTimeSelected(startTime:string,routine:IClassRoutine){
      if(startTime==this.$scope.addedEndTime){
        this.notify.warn("Start time and End time can't be same",true);
        routine.startTime="";
      }else{
        this.checkIfTheTimeIsAlreadyAssigned(startTime,routine).then((foundOccurance:boolean)=>{
          if(foundOccurance==false){
            routine.startTime=startTime;
            this.getEndTimeFromStartTime(startTime,routine);
          }else{
            this.notify.error("The time is already assigned");
          }
        });

      }
    }

    private checkIfTheTimeIsAlreadyAssigned(startTime:string,routine:IClassRoutine):ng.IPromise<any>{
      var defer = this.$q.defer();
      var foundOccurance:boolean=false;
      for(var i=0;i<this.$scope.routineArr.length;i++){
        if(this.$scope.routineArr[i].day==routine.day){
          if(this.$scope.routineArr[i].startTime==startTime){
            foundOccurance=true;
            break;
          }
        }
      }

      defer.resolve(foundOccurance);

      return defer.promise;
    }

    private getEndTimeFromStartTime(startTime:string,routine:IClassRoutine):any{
      var courseType = this.$scope.courseNoMapCourse[routine.courseNo].type;


      if(courseType=="SESSIONAL"){
        routine.endTime = this.$scope.timeIdWithTimeMap[String(this.$scope.timeWithTimeIdMap[startTime]+3)];
      }else{

        routine.endTime = this.$scope.timeIdWithTimeMap[String(this.$scope.timeWithTimeIdMap[startTime]+1)];
      }
    }

    private endTimeSelected(endTime:string,routine:IClassRoutine){
      if(endTime==this.$scope.addedStartTime){
        this.notify.warn("Start Time and End Time can't be same",true);
      }else{
        routine.endTime = endTime;
      }
    }

    private roomNoSelected(roomNo:string,routine:IClassRoutine){
      routine.roomNo = roomNo;
    }

    private sectionChanged(section:string,routine:IClassRoutine):void{
      routine.section=section;
    }
    private editRoutineData(routine:IClassRoutine){
      /*//this.$scope.courseType
      this.$scope.showSaveButton=true;
      this.initializeAddVariables();
      this.$scope.addedDate=routine.day.toString();
      this.$scope.addedCourse=routine.courseId;
      this.$scope.addedStartTime = routine.startTime;
      this.$scope.addedEndTime = routine.endTime;
      this.$scope.addedRoomNo = routine.roomNo;
      this.$scope.courseType=this.fetchCourseType(routine.courseId);
      this.$scope.addedSection=routine.section;*/


      routine.editRoutine=true;
      routine.showEditButton=false;
      routine.showAddButton=true;
      routine.showCancelButton=true;

    }

    private fetchCourseType(courseId:string):string{
      var courseType:string="";
      for(var i=0;i<this.$scope.courseArr.length;i++){
        if(this.$scope.courseArr[i].id==courseId){
         courseType = this.$scope.courseArr[i].type;
          break;
        }
      }
      return courseType;
    }

    private addRoutineData(routine:IClassRoutine){
      /*routine.day=this.$scope.addedDate;
      routine.courseId = this.$scope.addedCourse;
      routine.section= this.$scope.addedSection;
      routine.startTime = this.$scope.addedStartTime;
      routine.endTime=this.$scope.addedEndTime;
      routine.roomNo = this.$scope.addedRoomNo;*/
      routine.updated=true;
      routine.status="exist";

      routine.editRoutine=false;
      routine.showAddButton=false;
      routine.showCancelButton=false;
      routine.showEditButton=true;
      this.$scope.showSaveButton=true;
    }

    private cancelRoutineData(routine:IClassRoutine){
      routine.editRoutine=false;
      routine.showAddButton=false;
      routine.showCancelButton=false;
      routine.showEditButton=true;
    }

    private addData():void{

      this.checkIfAllFieldIsAssigned().then((message)=>{
        if(message=="true"){

          this.checkForOverlap(this.$scope.addedRoutine).then((found:boolean)=>{
            if(found==false){
              this.$scope.showSaveButton=true;
              this.$scope.showRoutineTable=true;

              this.assignValueToRoutine().then((message)=>{
                this.initializeAddVariables();
              });

            }else{
              this.notify.error("Time overlapping is not allowed");
            }
          });



          //this.addNewData();
        }
        else{
          this.notify.error("Please select all the fields",true);
        }
      });


    }


    private checkForOverlap(routine:IClassRoutine):ng.IPromise<any>{
      console.log("Checking..for overlap");
      var defer = this.$q.defer();

      var foundOccurrence:boolean = false;
      for(var i=0;i<this.$scope.routineArr.length;i++){
        if(this.$scope.routineArr[i].day=routine.day){
          if(Number(this.$scope.timeWithTimeIdMap[routine.startTime])>= Number(this.$scope.timeWithTimeIdMap[this.$scope.routineArr[i].startTime]) &&
              Number(this.$scope.timeWithTimeIdMap[routine.endTime])<= Number(this.$scope.timeWithTimeIdMap[this.$scope.routineArr[i].endTime]) &&
              this.$scope.routineArr[i].roomNo== routine.roomNo){
            foundOccurrence=true;
            break;
          }
        }
      }

      defer.resolve(foundOccurrence);
      return defer.promise;
    }


    private assignValueToRoutine():ng.IPromise<any>{
      var defer = this.$q.defer();
      var routine:any={};
     /* routine.day=+this.$scope.addedDate;
      routine.courseId = this.$scope.addedCourse;
      routine.section= this.$scope.addedSection;
      routine.startTime = this.$scope.addedStartTime;
      routine.endTime=this.$scope.addedEndTime;
      routine.roomNo = this.$scope.addedRoomNo;
      routine.updated=true;
      routine.courseType=this.$scope.courseType;*/
     routine= angular.copy(this.$scope.addedRoutine);

      routine.status='created';
      this.$scope.routineArr.push(routine);
      defer.resolve("done");
      return defer.promise;
    }





    private checkIfAllFieldIsAssigned():ng.IPromise<any>{
      var defer = this.$q.defer();
      var routine=this.$scope.addedRoutine;
      if( routine.roomNo=="" ||
        routine.section=="" ||
        routine.courseId=="" ||
        routine.day==null ||
        routine.startTime=="" ||
        routine.endTime==""){
        defer.resolve("false");
      }else{
        defer.resolve("true");
      }

      return defer.promise;
    }

    private cancelData():void{
      var routine = this.$scope.addedRoutine;
      routine.courseNo="";
      routine.day = "";
      routine.startTime="";
      routine.endTime="";
      routine.roomNo="";
    }


    private courseSelected(courseNo:string,routine:IClassRoutine):void{

      this.checkIfTheCourseIsAlreadySelectedInTheSameDate(courseNo,routine).then((found:boolean)=>{
        if(found==false){
          routine.section="";
          routine.courseId=this.$scope.courseNoMapCourseId[courseNo];
          routine.courseNo = courseNo;

          //this.$scope.courseType="";
          for(var i=0;i<this.$scope.courseArr.length;i++){
            if(this.$scope.courseArr[i].id==this.$scope.courseNoMapCourseId[courseNo]){
              this.$scope.courseType = angular.copy(this.$scope.courseArr[i].type);
              break;
            }
          }


          if(this.$scope.courseType=="SESSIONAL"){
            this.$scope.sessionalSections=[];
            if(this.$scope.section=='A'){
              this.$scope.sessionalSections=this.appConstants.sessionalSectionsA;
            }
            else if(this.$scope.section=='B'){
              this.$scope.sessionalSections= this.appConstants.sessionalSectionsB;
            }
            else if(this.$scope.section=='C'){
              this.$scope.sessionalSections= this.appConstants.sessionalSectionsC ;
            }
            else{
              this.$scope.sessionalSections= this.appConstants.sessionalSectionsD;
            }
          }else{
            routine.section = this.$scope.section;
          }
        }
        else{
          this.notify.error("The course is already assigned in the current date");
        }
      });



    }

    private checkIfTheCourseIsAlreadySelectedInTheSameDate(courseNo:string,routine:IClassRoutine):ng.IPromise<any>{
      var defer = this.$q.defer();
      var occuranceFound:boolean=false;
      for(var i=0;i<this.$scope.routineArr.length;i++){
        if(this.$scope.routineArr[i].day==routine.day){
          if(this.$scope.routineArr[i].courseNo==courseNo){
            occuranceFound=true;
            break;
          }
        }
      }
      defer.resolve(occuranceFound);
      return defer.promise;
    }

    private getSemesters():void{

      this.$scope.semesterArr=[];
      this.semesterService.fetchSemesters (Utils.UG).then((semesterArr:Array<any>)=>{
        this.$scope.semesterArr = semesterArr;
        this.$scope.semesterId=semesterArr[0].id;
      });
    }


    private searchForRoutineData():void{
      this.$scope.showRoutineTable=false;
      this.$scope.showLoader=true;

      this.$scope.dates=[];
      this.$scope.times =[];

      this.initializeDateAndTime();
      this.$scope.times = this.appConstants.timeChecker;

      Utils.expandRightDiv();

      this.courseService.getCourseBySemesterProgramTypeYearSemester(this.$scope.semesterId,this.$scope.programTypeId,this.$scope.studentsYear,this.$scope.studentsSemester).then((courseArr:Array<ICourse>)=>{
        this.$scope.courseIdMapCourseNo={};
        this.$scope.courseArr=[];
        this.$scope.courseNoMapCourse={};
        //this.$scope.courseArr=courseArr;


        for(var i=0;i<courseArr.length;i++){
          this.$scope.courseArr.push((courseArr[i]));
          this.$scope.courseIdMapCourseNo[courseArr[i].id] = courseArr[i].no;
          this.$scope.courseNoMapCourseId[courseArr[i].no] = courseArr[i].id;
          this.$scope.courseNoMapCourse[courseArr[i].no]= courseArr[i];
        }



        this.classRoomService.getClassRooms().then((rooms:Array<IClassRoom>)=>{
          this.$scope.roomArr=[];
          this.$scope.roomArr = rooms;


          this.$scope.routineArr=[];
          this.classRoutineService.getClassRoutineForEmployee(this.$scope.semesterId,this.$scope.studentsYear,this.$scope.studentsSemester,this.$scope.section)
              .then((routineArr:Array<IClassRoutine>)=>{
                for(var k=0;k<routineArr.length;k++){
                  routineArr[k].day = this.$scope.dateMap[String(routineArr[k].day)];
                  routineArr[k].courseNo = this.$scope.courseIdMapCourseNo[routineArr[k].courseId];
                  this.$scope.routineArr.push(routineArr[k]);
                }


                this.$scope.showLoader=false;

                if(routineArr.length>0){
                  this.$scope.showRoutineTable=true;
                }

              });
        });

      });
    }



    private initializeDateAndTime():void{
      //this.$scope.dates = this.appConstants.weekday;
      this.createDayMaps();
      this.createTimeMaps();
      }

    private createDayMaps():void{
      this.$scope.dates = this.appConstants.weekday;

      for(var i=0;i<this.$scope.dates.length;i++){
        this.$scope.dateMap[String(i+1)] = this.$scope.dates[i].name;
        this.$scope.dateMapFromDateToNumber[this.$scope.dates[i].name] = i+1;
      }
    }


    private createTimeMaps():void{
      this.$scope.times = this.appConstants.timeChecker;
      for(var i=1;i<this.$scope.times.length;i++){
        this.$scope.timeIdWithTimeMap[String(i)] = this.$scope.times[i].val;
        this.$scope.timeWithTimeIdMap[this.$scope.times[i].val] = i;
      }
    }

    private initializeAddVariables():void{
      var routine=this.$scope.addedRoutine;
      routine.courseNo="";
      routine.startTime="";
      routine.endTime="";
      routine.section="";
      this.$scope.addedCourse="";
      this.$scope.addedSection="";
      this.$scope.addedStartTime="";
      this.$scope.addedEndTime="";
      this.$scope.courseType="";
    }

    private saveClassRoutine():void{
      this.convertToJson().then((json:any)=>{
        this.classRoutineService.saveClassRoutine(json)
            .then((message:string)=>{
              if(message=='success'){
                this.notify.success("Routine Data Successfully Saved");
                this.$scope.showSaveButton=false;

                this.searchForRoutineData();


              }else{
                this.notify.error("Failed to save routine data");
              }
            });
      });
    }

    private resetDivs() {
      $("#arrowDiv").hide();
      $("#leftDiv").show();
      $("#rightDiv").removeClass("newRightClass").addClass("orgRightClass");
      //this.decoratedScope.grid.api.resize();
    }

    private convertToJson():ng.IPromise<any>{

      var defer =this.$q.defer();
      var completeJson={};
      var jsonObject=[];

      for(var i=0;i<this.$scope.routineArr.length;i++){
        var r:IClassRoutine=this.$scope.routineArr[i];

        if(r.updated==true || r.status=='deleted' || r.status=='created'){
          var item:any={};
          if(r.id==null){
            item['id']="";
          }else{
            item['id']=r.id;
          }
          item['semesterId']=this.$scope.semesterId;
          item['courseId']=this.$scope.courseNoMapCourseId[r.courseNo];
          item['day']=Number(this.$scope.dateMapFromDateToNumber[r.day]);
          item['section']=r.section;
          item['academicYear']=this.$scope.studentsYear;
          item['academicSemester']=this.$scope.studentsSemester;
          item['startTime'] = r.startTime;
          item['endTime'] = r.endTime;
          item['roomNo']=r.roomNo;
          item['status'] = r.status;
          jsonObject.push(item);
        }

      }

      completeJson["entries"]=jsonObject;
      defer.resolve(completeJson);
      return defer.promise;

    }


  }

  UMS.controller("ClassRoutine", ClassRoutine);
}
