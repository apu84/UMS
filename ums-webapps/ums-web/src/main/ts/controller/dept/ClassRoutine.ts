module ums {
  import ISemester = ums;
  interface IClassRoutineScope extends ng.IScope {
    semesterArr:Array<ISemester>;
    courseArr:Array<ICourse>;
    roomArr:Array<IClassRoom>;
    routineArr:Array<IClassRoutine>;
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


    addDataButtonClicked:boolean;
    addNewData:Function;

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
    dateName:string;
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
    programId:number;
    day:number;
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


      $scope.courseIdMapCourseNo={};
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
      $scope.addNewData = this.addNewData.bind(this);
      $scope.saveClassRoutine=this.saveClassRoutine.bind(this);
    }

    private addNewData():void{
      this.initializeAddVariables();
      this.$scope.addDataButtonClicked=true;
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

    private dateSelected(date:string){
      this.$scope.addedDate=date;
    }

    private startTimeSelected(startTime:string){
      this.$scope.addedStartTime = startTime;
    }

    private endTimeSelected(endTime:string){
      this.$scope.addedEndTime = endTime;
    }

    private roomNoSelected(roomNo:string){
      this.$scope.addedRoomNo = roomNo;
    }

    private sectionChanged(section:string):void{
      this.$scope.addedSection=section;
    }
    private editRoutineData(routine:IClassRoutine){
      this.$scope.courseType
      this.$scope.showSaveButton=true;
      this.$scope.addDataButtonClicked=false;
      this.initializeAddVariables();
      this.$scope.addedDate=routine.day.toString();
      this.$scope.addedCourse=routine.courseId;
      this.$scope.addedStartTime = routine.startTime;
      this.$scope.addedEndTime = routine.endTime;
      this.$scope.addedRoomNo = routine.roomNo;
      this.$scope.courseType=routine.courseType;
      this.$scope.addedSection=routine.section;



      routine.editRoutine=true;
      routine.showEditButton=false;
      routine.showAddButton=true;
      routine.showCancelButton=true;

      console.log(this.$scope.courseType);
      console.log(this.$scope.addedSection);
    }

    private fetchCOurseType(courseId:string):void{
      var courseType:string="";
      for(var i=0;i<this.$scope.courseArr.length;i++){
        if(this.$scope.courseArr[i].id==courseId){
         // this.courseType = this.$scope.courseArr[i].type;
          break;
        }
      }
    }

    private addRoutineData(routine:IClassRoutine){
      routine.day=+this.$scope.addedDate;
      routine.courseId = this.$scope.addedCourse;
      routine.section= this.$scope.addedSection;
      routine.startTime = this.$scope.addedStartTime;
      routine.endTime=this.$scope.addedEndTime;
      routine.roomNo = this.$scope.addedRoomNo;
      routine.updated=true;
      routine.status="exist";

      routine.editRoutine=false;
      routine.showAddButton=false;
      routine.showCancelButton=false;
      routine.showEditButton=true;
    }

    private cancelRoutineData(routine:IClassRoutine){
      routine.editRoutine=false;
      routine.showAddButton=false;
      routine.showCancelButton=false;
      routine.showEditButton=true;
    }

    private addData():void{
      this.$scope.showSaveButton=true;
      this.$scope.showRoutineTable=true;

      console.log(this.$scope.addedDate);
      console.log(this.$scope.addedCourse);
      console.log(this.$scope.addedSection);
      console.log(this.$scope.addedStartTime);
      console.log(this.$scope.addedEndTime);
      console.log(this.$scope.addedEndTime);
      console.log(this.$scope.addedRoomNo);
      var routine:any={};
      routine.day=+this.$scope.addedDate;
      routine.courseId = this.$scope.addedCourse;
      routine.section= this.$scope.addedSection;
      routine.startTime = this.$scope.addedStartTime;
      routine.endTime=this.$scope.addedEndTime;
      routine.roomNo = this.$scope.addedRoomNo;
      routine.updated=true;
      routine.courseType=this.$scope.courseType;

      console.log("Routine object");
      console.log(routine);
      routine.status='created';
      this.$scope.routineArr.push(routine);
    }

    private cancelData():void{
      this.$scope.addedDate="";
      this.$scope.addedCourse="";
      this.$scope.addedSection="";
      this.$scope.addedStartTime = "";
      this.$scope.addedEndTime="";
      this.$scope.courseType="";
      this.$scope.addedRoomNo="";
    }


    private courseSelected(courseId:string):void{
      this.$scope.addedSection="";
      console.log("Course id");
      this.$scope.addedCourse=courseId;
      console.log(this.$scope.addedCourse);

      this.$scope.courseType="";
      for(var i=0;i<this.$scope.courseArr.length;i++){
        if(this.$scope.courseArr[i].id==this.$scope.addedCourse){
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
          this.$scope.sessionalSections = this.appConstants.sessionalSectionsB;
        }
        else if(this.$scope.section=='C'){
          this.$scope.sessionalSections = this.appConstants.sessionalSectionsC ;
        }
        else{
          this.$scope.sessionalSections = this.appConstants.sessionalSectionsD;
        }
      }else{
        this.$scope.addedSection = this.$scope.section;
      }
      console.log("---After course changes---");

      console.log(this.$scope.courseType);

      console.log(this.$scope.sessionalSections);


    }

    private getSemesters():void{
      console.log("In the semester");

      this.$scope.semesterArr=[];
      this.semesterService.getAllSemesters().then((semesterArr:Array<any>)=>{
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

      $("#leftDiv").hide();
      $("#arrowDiv").show();
      $("#rightDiv").removeClass("orgRightClass").addClass("newRightClass");

      var programType = +this.$scope.programType;
      var year = +this.$scope.studentsYear;
      var semester = +this.$scope.studentsSemester;
      this.courseService.getCourseBySemesterProgramTypeYearSemester(this.$scope.semesterId,programType,year,semester).then((courseArr:Array<ICourse>)=>{
        this.$scope.courseIdMapCourseNo={};
        this.$scope.courseArr=[];
        this.$scope.courseArr=courseArr;
        console.log(courseArr);

        for(var i=0;i<courseArr.length;i++){
          this.$scope.courseIdMapCourseNo[courseArr[i].id] = courseArr[i].no;
        }


        console.log(this.$scope.courseIdMapCourseNo);

        this.classRoomService.getClassRooms().then((rooms:Array<IClassRoom>)=>{
          this.$scope.roomArr=[];
          this.$scope.roomArr = rooms;



          this.classRoutineService.getClassRoutineForEmployee(this.$scope.semesterId,year,semester,this.$scope.section)
              .then((routineArr:Array<IClassRoutine>)=>{
                this.$scope.routineArr=[];
                this.$scope.routineArr = routineArr;
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
      for(var i=0;i<this.$scope.dates.length;i++){
        this.$scope.dateMap[this.$scope.dates[i].id]=this.$scope.dates[i].dateName;
      }
    }

    private initializeAddVariables():void{
      this.$scope.addedDate="";
      this.$scope.addedCourse="";
      this.$scope.addedSection="";
      this.$scope.addedStartTime = "";
      this.$scope.addedEndTime="";
      this.$scope.courseType="";
      this.$scope.addedRoomNo="";
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
          item['courseId']=r.courseId;
          item['day']=r.day;
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
