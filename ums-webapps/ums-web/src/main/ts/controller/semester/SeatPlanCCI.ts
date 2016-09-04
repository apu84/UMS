module ums{
  import ITimeoutService = ng.ITimeoutService;
  import UISortableOptions = angular.ui.UISortableOptions;

  interface ISeatPlanCCIScope extends ng.IScope{
    selectedSubGroupNo:number;
    splitId:number;
    subGroupExamDate:string;
    semesterId:number;
    totalStudent:number;
    semesterList:Array<ISemester>;
    examRoutineCCIArr:Array<IExamRoutineCCI>;
    applicationCCIArr:Array<IApplicationCCI>;
    tempGroupList:Array<IApplicationCCI>;
    tempGroupListAll:Array<IApplicationCCI>;
    subGroupMap:any;
    sortableOptions:UISortableOptions<IApplicationCCI>;
    subGroupList:Array<ISubGroup>;

    showCCIInfo:boolean;
    subGroupPortionSelected:boolean;
    recreate:boolean;
    subGroupFound:boolean;
    splitActionOccured:boolean;
    recreateButtonClicked:boolean;
    createButtonClicked:boolean;
    showContextMenu:boolean;
    splitButtonClicked:boolean;
    reverseSplitButtonClicked:boolean;
    showSelectedSubGrupPortions:boolean;


    getSemesterInfo:Function;
    showApplicationCCIInfo:Function;
    getExamRoutineInfoForCCI:Function;
    getApplicationCCIInfoForSubGroup:Function;
    create:Function;
    getGroupInfoFromSelectedSubGroup:Function;
    mouseClickEvent:Function;
    showSubGroups:Function;

  }

  interface ISubGroupDb{
    id:number;
    semesterId:number;
    subGroupNo:number;
    position:number;
    courseId:string;
    groupId:number;
    studentNumber:number;
  }

  interface ISubGroup{
    subGroupNumber:number;
    subGroupMembers:Array<IApplicationCCI>;
    subGroupTotalStudentNumber:number;
  }

  interface ISemester{
    id:number;
    name:string;
    startDate:string;
    status:number;
  }

  interface IExamRoutineCCI{
    examDate:string;
    examDateOriginal:string;
    weekDay:string;
    totalStudent:number;
  }

  interface IApplicationCCI{
    id:number;
    examDate:string;
    courseNo:string;
    courseId:string;
    courseTitle:string;
    totalStudent:number;
    year:number;
    semester:number;
    showSubPortion:boolean;
  }

  export class SeatPlanCCI{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$timeout','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: ISeatPlanCCIScope,
                private $q:ng.IQService, private notify: Notify,private $timeout:ITimeoutService,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

      $scope.sortableOptions={};
      $scope.showSelectedSubGrupPortions=false;
      $scope.subGroupMap={};
      $scope.showContextMenu=false;
      $scope.createButtonClicked=false;
      $scope.recreate=false;
      $scope.subGroupFound=false;
      $scope.subGroupPortionSelected=false;
      $scope.showCCIInfo=false;
      $scope.recreateButtonClicked=false;
      $scope.getSemesterInfo=this.getSemesterInfo.bind(this);
      $scope.getExamRoutineInfoForCCI = this.getExamRoutineCCIInfo.bind(this);
      $scope.showApplicationCCIInfo = this.showApplicationCCIInfo.bind(this);
      $scope.create = this.create.bind(this);
      $scope.mouseClickEvent = this.mouseClickEvent.bind(this);
      $scope.showSubGroups = this.showSubGroups.bind(this);

    }

    private showApplicationCCIInfo(semesterId:number){
      this.$scope.semesterId= semesterId;
      this.getExamRoutineCCIInfo(semesterId).then((examRoutineArr:Array<IExamRoutineCCI>)=>{
        this.$scope.examRoutineCCIArr=[];
        var weekday:any={};
        weekday[0]=  "Sunday";
        weekday[1] = "Monday";
        weekday[2] = "Tuesday";
        weekday[3] = "Wednesday";
        weekday[4] = "Thursday";
        weekday[5] = "Friday";
        weekday[6] = "Saturday";
        var d= new Date();
        for(var i=0;i<examRoutineArr.length;i++){
          console.log(examRoutineArr[i].examDate);
          var examRoutine:any={};
          examRoutine.examDate=examRoutineArr[i].examDate;
          console.log(new Date(examRoutineArr[i].examDate));
          var d = new Date(examRoutineArr[i].examDate);
          console.log(d.getDay());
          examRoutine.weekDay = weekday[new Date(examRoutineArr[i].examDate).getDay()];
          examRoutine.totalStudent = examRoutineArr[i].totalStudent;
          examRoutine.examDateOriginal=examRoutineArr[i].examDateOriginal;
          this.$scope.examRoutineCCIArr.push(examRoutine);


        }

        this.$scope.showCCIInfo=true;

      });
    }

    private create(pExamDate:string){
      this.mouseClickEvent();
      this.$scope.showContextMenu=true;
      this.$scope.createButtonClicked=true;
      this.$scope.tempGroupList=[];
      this.$scope.subGroupExamDate=pExamDate;
      this.getApplicationCCIInfoForSubGroup(pExamDate).then((arr:Array<IApplicationCCI>)=>{
          this.$scope.tempGroupList=arr;


      });
    }

    private showSubGroups(subGroupNo:number){
      this.$scope.showSelectedSubGrupPortions=true;
      this.$scope.sortableOptions.connectWith = ".apps-container";
      this.$scope.sortableOptions.placeholder="list";
      console.log("this is the sub gorup");
    }


    private createSubGroup(){

    }

    private mergeInitialization(id:string){

    }

    private splitCourseStudent(mouseClickEvent:number){

    }

    private revertSplitAction(){

    }

    private mergeGroups(){

    }
    private mouseClickEvent():void{


      setTimeout(myFunction, 2000)

      //console.log($( "#subGroupPanel" ).find( "li" ));



      function myFunction() {
        // Trigger action when the contexmenu is about to be shown
        $("#subGroupPanel li").on("contextmenu", function (event) {

          // Avoid the real one
          event.preventDefault();

          // Show contextmenu
          $(".custom-menu").finish().toggle(100).

          // In the right position (the mouse)
          css({

            top: event.pageY - $("#topbar").height()+"px" ,
            left: event.pageX - $("#sidebar").width()+"px"
          });

        });


// If the document is clicked somewhere
        $("#subGroupPanel li").bind("mousedown", function (e) {
          // If the clicked element is not the menu
          if (!($(e.target).parents(".custom-menu").length > 0)) {
            // Hide it
            $(".custom-menu").hide(100);
          }
        });

        /*with the mouse down jquery function, we are getting the event only of right button,
         * that's why the case is 3.
         * with the line: $(this).attr('id') , we are getting the id when the right mouse button click event is triggered.*/
        var classScope = this;
        $("#subGroupPanel li").mousedown(function(event){
          switch(event.which){
            case 1:
              var ids = $(this).attr('id');
              var idNum = +ids;
              currentScope.mergeInitialization(ids);
              break;
            case 3:
              var id = $(this).attr('id');

              currentScope.$scope.splitId = +id;
              break;
          }
        });

      }









      /*Current scope will be used in replace of 'this' of angularjs, to jquery, else, jquery will not recognize that.*/
      var currentScope = this;
// If the menu element is clicked
      $(".custom-menu li").click(function(){

        // This is the triggered action name
        switch($(this).attr("data-action")) {

          case "split": currentScope.$scope.splitButtonClicked=true;
            currentScope.splitCourseStudent(1);
            break;
          case "revertSplit":
            currentScope.$scope.reverseSplitButtonClicked=true;
            currentScope.revertSplitAction();
            break;
          case "merge":
            currentScope.mergeGroups();
            break;
        }
        $(".custom-menu").hide(100);
      });

    }

    private getExamRoutineCCIInfo(semesterId:number):ng.IPromise<any>{
      var defer = this.$q.defer();
      var examRoutineArr:Array<IExamRoutineCCI>=[];

      this.httpClient.get('/ums-webservice-academic/academic/examroutine/exam_routine_cci/semester/'+semesterId +'/examtype/'+2,  'application/json',
          (json:any, etag:string) => {
            examRoutineArr = json.entries;
            defer.resolve(examRoutineArr);
            console.log(examRoutineArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;

    }

    private getSubGroupInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var subGroupDb:Array<ISubGroupDb>=[];
      this.httpClient.get('/ums-webservice-academic/academic/subGroupCCI/semester/'+this.$scope.semesterId +'/examDate/'+this.$scope.subGroupExamDate, 'application/json',
          (json:any, etag:string) => {
            subGroupDb = json.entries;

            defer.resolve(subGroupDb);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            defer.resolve(subGroupDb);
          });
      return defer.promise;
    }

    private getSemesterInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.$scope.semesterList=[];
      this.httpClient.get('/ums-webservice-academic/academic/semester/all', 'application/json',
          (json:any, etag:string) => {
            this.$scope.semesterList = json.entries;
            console.log("################");
            console.log(this.$scope.semesterList);
            defer.resolve(this.$scope.semesterList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getApplicationCCIInfoForSubGroup(examDate:string):ng.IPromise<any>{
      var defer = this.$q.defer();
      this.$scope.totalStudent=0;
      console.log(examDate);
      this.$scope.applicationCCIArr=[];
      var applicationArr:Array<IApplicationCCI>=[];
      this.httpClient.get('/ums-webservice-academic/academic/applicationCCI/semester/'+this.$scope.semesterId+'/examDate/'+examDate, 'application/json',
          (json:any, etag:string) => {
            applicationArr = json.entries;
            this.$scope.subGroupPortionSelected=true;

            for(var i=0;i<applicationArr.length;i++){
              this.$scope.totalStudent+=applicationArr[i].totalStudent;
              applicationArr[i].showSubPortion=false;
              applicationArr[i].id=i+1;
              this.$scope.applicationCCIArr.push(applicationArr[i]);
            }
            defer.resolve(this.$scope.applicationCCIArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

  }

  UMS.controller('SeatPlanCCI',SeatPlanCCI);
}