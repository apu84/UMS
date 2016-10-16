module ums {
  import ISemester = ums;
  interface IClassRoutineScope extends ng.IScope {
    semesterArr:Array<ISemester>;
    courseArr:Array<ICourse>;
    roomArr:Array<IClassRoom>;
    routineArr:Array<IClassRoutine>;
    addedRoutine:any;
    semesterId:number;
    programType:number;
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

      $scope.courseIdMapCourseNo={};
      $scope.courseNoMapCourseId={};
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
        routine.startTime=startTime;
        var courseType = this.$scope.courseNoMapCourse[routine.courseNo].type;

          var globalCounter:number;
        if(courseType=="SESSIONAL"){
          globalCounter=3;
        }else{
          globalCounter=1;
        }
          var count=0;
          var found=false;

          for(var i=0;i<this.$scope.times.length;i++){
            if(this.$scope.times[i].val== startTime){
              var found=true;
              count=1;
            }
            else if(found && count !=globalCounter){
              count+=1;
            }else{
              console.log("Found match");
              routine.endTime = this.$scope.times[i].id;
              break;
            }

          }

          console.log("In the start time selected");
        console.log(routine);


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

      console.log(routine);

      routine.editRoutine=true;
      routine.showEditButton=false;
      routine.showAddButton=true;
      routine.showCancelButton=true;

      console.log(this.$scope.courseType);
      console.log(this.$scope.addedSection);
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

      console.log("After edit");
      console.log(routine);
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
          this.$scope.showSaveButton=true;
          this.$scope.showRoutineTable=true;

          this.assignValueToRoutine().then((message)=>{
            this.initializeAddVariables();
          });



          //this.addNewData();
        }
        else{
          this.notify.error("Please select all the fields",true);
        }
      });


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

      console.log("Routine object");
      console.log(routine);
      routine.status='created';
      this.$scope.routineArr.push(routine);
      defer.resolve("done");
      return defer.promise;
    }





    private checkIfAllFieldIsAssigned():ng.IPromise<any>{
      console.log("Added class routine object");
      console.log(this.$scope.addedRoutine);
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
      routine.section="";
      console.log("Course id");
      routine.courseId=this.$scope.courseNoMapCourseId[courseNo];
      routine.courseNo = courseNo;

      //this.$scope.courseType="";
      for(var i=0;i<this.$scope.courseArr.length;i++){
        if(this.$scope.courseArr[i].id==this.$scope.courseNoMapCourseId[courseNo]){
          this.$scope.courseType = angular.copy(this.$scope.courseArr[i].type);
          console.log("In the course type selection");
          console.log(this.$scope.courseArr[i]);
          break;
        }
      }

      console.log("Out side the loop");
      console.log(this.$scope.courseType);
      console.log(this.$scope.courseArr);

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
      console.log(routine);


    }

    private getSemesters():void{
      console.log("In the semester");

      this.$scope.semesterArr=[];
      this.semesterService.fetchSemesters (Utils.UG).then((semesterArr:Array<any>)=>{
        this.$scope.semesterArr = semesterArr;
        console.log(semesterArr);
      });
    }


    private searchForRoutineData():void{
      this.$scope.showRoutineTable=false;
      this.$scope.showLoader=true;

      this.$scope.dates=[];
      this.$scope.times =[];

      this.initializeDate();
      this.$scope.times = this.appConstants.timeChecker;

      Utils.expandRightDiv();

      var programType = +this.$scope.programType;
      var year = +this.$scope.studentsYear;
      var semester = +this.$scope.studentsSemester;
      this.courseService.getCourseBySemesterProgramTypeYearSemester(this.$scope.semesterId,programType,year,semester).then((courseArr:Array<ICourse>)=>{
        this.$scope.courseIdMapCourseNo={};
        this.$scope.courseArr=[];
        this.$scope.courseNoMapCourse={};
        //this.$scope.courseArr=courseArr;
        console.log(courseArr);


        for(var i=0;i<courseArr.length;i++){
          this.$scope.courseArr.push((courseArr[i]));
          this.$scope.courseIdMapCourseNo[courseArr[i].id] = courseArr[i].no;
          this.$scope.courseNoMapCourseId[courseArr[i].no] = courseArr[i].id;
          this.$scope.courseNoMapCourse[courseArr[i].no]= courseArr[i];
        }


        console.log(this.$scope.courseIdMapCourseNo);

        this.classRoomService.getClassRooms().then((rooms:Array<IClassRoom>)=>{
          this.$scope.roomArr=[];
          this.$scope.roomArr = rooms;


          this.$scope.routineArr=[];
          this.classRoutineService.getClassRoutineForEmployee(this.$scope.semesterId,year,semester,this.$scope.section)
              .then((routineArr:Array<IClassRoutine>)=>{
                console.log("---first---");
                console.log(routineArr);
                for(var k=0;k<routineArr.length;k++){
                  routineArr[k].day = this.$scope.dateMap[String(routineArr[k].day)];
                  console.log("date");
                  console.log(this.$scope.dateMap[routineArr[k].day]);
                  routineArr[k].courseNo = this.$scope.courseIdMapCourseNo[routineArr[k].courseId];
                  this.$scope.routineArr.push(routineArr[k]);
                  console.log(this.$scope.routineArr[k]);
                }


                this.$scope.showLoader=false;

                if(routineArr.length>0){
                  this.$scope.showRoutineTable=true;
                }

              });
        });

      });
    }



    private initializeDate():void{
      this.$scope.dates = this.appConstants.weekday;
      this.$scope.dateMap={};
      var date = this.$scope.dateMap;
      date["1"]="Saturday";
      date["2"]="Sunday";
      date["3"]="Monday";
      date["4"] = "Tuesday";
      date["5"] = "Wednesday";
      date["6"]="Thursday";

      var dateStr = this.$scope.dateMapFromDateToNumber;
      dateStr["Saturday"]=1;
      dateStr["Sunday"]=2;
      dateStr["Monday"]=3;
      dateStr["Tuesday"]=4;
      dateStr["Wednesday"]=5;
      dateStr["Thursday"]=6;
      }

    private initializeAddVariables():void{
      var routine=this.$scope.addedRoutine;
      routine.courseId="";
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
          console.log(item);
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
