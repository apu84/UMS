/**
 * Created by Munna on 26-Oct-16.
 */

module ums{
  interface IRoomBasedRoutine extends ng.IScope{
    loggedEmployee:Employee;
    programTypes:any;
    programTypeId:number;
    pdfFile:any;
    pdfFileByRoomId:any;
    semester:Semester;
    rooms:Array<ClassRoom>;
    roomId:number;


    showById:boolean;
    showAll:boolean;


    getActiveSemester:Function;
    resetDivs:Function;
    getRooms:Function;
    fetchAll:Function;
    fetchOne:Function;
  }

  class RoomBasedRoutine{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$sce','$window','classRoutineService','classRoomService','employeeService','semesterService'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IRoomBasedRoutine,
                private $q:ng.IQService, private notify: Notify,
                private $sce:ng.ISCEService,private $window:ng.IWindowService,private classRoutineService:ClassRoutineService,
                private classRoomService: ClassRoomService, private employeeService: EmployeeService, private semesterService: SemesterService) {

      $scope.programTypes=[
        {id:'',name:'Select program type'},
        {id:'11', name: 'Undergraduate'},
        {id:'22', name: 'Post-graduate'}
      ];

      $scope.showById=false;
      $scope.showAll=false;
      $scope.getActiveSemester = this.getActiveSemester.bind(this);
      $scope.resetDivs = this.resetDivs.bind(this);
      $scope.getRooms = this.getRooms.bind(this);
      $scope.fetchAll = this.fetchAll.bind(this);
      $scope.fetchOne = this.fetchOne.bind(this);

    }






    private getCurrentLoggedEmployeeInfo(){
      this.employeeService.getLoggedEmployeeInfo().then((employee:Employee)=>{
        this.$scope.loggedEmployee=<Employee>{};
        this.$scope.loggedEmployee = employee;
      });
    }


    private getActiveSemester(programType:string){

      this.semesterService.fetchSemesters(Number(programType)).then((semesters:Array<Semester>)=>{
        for(var i=0;i<semesters.length;i++){
          this.$scope.semester = <Semester>{};
          if(semesters[i].status==1){
            this.$scope.semester = semesters[i];
            break;
          }
        }
      });
    }

    private getRooms(){
      this.classRoomService.getClassRoomsBasedOnRoutine(this.$scope.semester.id).then((rooms:Array<ClassRoom>)=>{
        this.$scope.rooms=[];
        this.$scope.rooms = rooms;
        console.log(rooms);
      });

      $("#arrowDiv").show();
      $("#leftDiv").hide();
      $("#rightDiv").addClass("newRightClass").addClass("orgRightClass");

    }


    private fetchOne(){

      this.$scope.showAll = false;
      this.$scope.showById = false;

      console.log('Room Id');
      console.log(this.$scope.roomId);
      this.$scope.pdfFileByRoomId;
      this.classRoutineService.getRoomBasedClassRoutine(this.$scope.semester.id,this.$scope.roomId).then((file:any)=>{
        if(file!="failure"){
          this.$scope.showAll=false;
          this.$scope.showById=true;

          var pdfFile:any=file;
          this.$scope.pdfFileByRoomId = pdfFile;
        }else{
          this.notify.error("Error in generating routine report");
        }
      });
    }


    private fetchAll(roomId?:number){

      this.$scope.showAll = false;
      this.$scope.showById = false;

      console.log("In the fetch all");
      if(!roomId){
        roomId=9999;
      }
      this.$scope.pdfFile;

      this.classRoutineService.getRoomBasedClassRoutine(this.$scope.semester.id).then((file:any)=>{
        if(file!="failure"){
          this.$scope.showById=false;
          this.$scope.showAll=true;
          var pdfFile:any=file;
          this.$scope.pdfFile=pdfFile;
        }else{
          this.notify.error("Error in generating routine report");
        }
      });
    }



    private resetDivs() {
      $("#arrowDiv").hide();
      $("#leftDiv").show();
      $("#rightDiv").removeClass("newRightClass").addClass("orgRightClass");
      //this.decoratedScope.grid.api.resize();
    }



  }

  UMS.controller("RoomBasedRoutine", RoomBasedRoutine);
}