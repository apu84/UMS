module ums{

  import ITimeoutService = ng.ITimeoutService;
  import UISortableOptions = angular.ui.UISortableOptions;

  //import UISortableOptions = angular.ui.UISortableOptions;
  interface IExamSeatPlanScope extends ng.IScope{
    semesterList:Array<ISemester>;
    seatPlanGroupList:Array<ISeatPlanGroup>;
    subGroupStorage:Array<String>;
    seatPlanJsonData:Array<ISeatPlanJsonData>;
    roomList:any;


    data:any;
    classBodyBackgroundColor:string;
    mouseClickedObject:any;
    mouseClickedObjectStore:Array<any>;
    selectedSubGroupNo:string;
    subGroupTotalStudentNumber:any;
    selectedGroupTotalStudent:number;
    previousIterationNumber:number;
    tempIdList:Array<number>;
    mergeIdList:any;
    splitNumber:number;
    groupNumber:number;
    iterationNumbers:Array<number>;
    semester:ISemester
    group:Array<ISeatPlanGroup>;
    examType:number;
    system:number;
    update:number;
    seatPlanGroupListLength:number;
    semesterId:number;
    groupNoForSeatPlanViewing:number;
    selectedGroupNo:number;
    groupNoForSubGroup:number;
    subGroupNo:number;  //how many subgroup
    colForSubgroup:number; //it will be used for generating columns.
    group1List:Array<ISeatPlanGroup>;
    group2List:Array<ISeatPlanGroup>;
    group3List:Array<ISeatPlanGroup>;
    tempSubGropStudentList:Array<ISeatPlanGroup>;
    savedSubGroupList:any;
    totalStudentGroup1:number;
    totalStudentGroup2:number;
    totalStudentGroup3:number;
    totalNumberSubGroup:number;
    subGroupTotalNumber:number;
    groupIdLength:number;
    groupList:Array<IGroup>;
    tempGroupList:Array<ISeatPlanGroup>;
    tempGroupListAll:Array<ISeatPlanGroup>;
    tempGroupListForSplitInversion:Array<ISeatPlanGroup>;
    splittedGroupList:Array<ISeatPlanGroup>;
    selectedGroupList:Array<ISeatPlanGroup>;
    subGroupList:Array<ISubGroup>;
    tempSubGroupList:Array<ISubGroup>;
    subGroup1List:any;
    subGroup2List:any;
    subGroup3List:any;
    subGroup4List:any;
    subGroup5List:any;
    subGroup6List:any;
    subGroup1StudentNumber:number;
    subGroup2StudentNumber:number;
    subGroup3StudentNumber:number;
    subGroup4StudentNumber:number;
    subGroup5StudentNumber:number;
    subGroup6StudentNumber:number;
    splitId:number;
    examRoutineCCIArr:Array<IExamRoutineCCI>;

    sortableOptionsIfSubGroupNotFound:UISortableOptions<ISeatPlanGroup>;

    //cci

    examDate:string;

    //cci

    subGroup1ListTest:any;  //this is for test purpose
    confirmation:boolean;
    cciSelected:boolean;
    customeMenu:boolean;
    hideSelection:boolean;
    loadingVisibilityForCCI:boolean;
    pdfGenerator:boolean;
    recreate:boolean;
    showContextMenu:boolean;
    splitActionOccured:boolean;
    splitButtonClicked:boolean;
    reverseSplitButtonClicked: boolean;
    loadingVisibility:boolean;
    showGroupOrNot:boolean;
    recreateButtonClicked:boolean;
    groupSelected:boolean;
    editButtonClicked:boolean;
    showGroupSelectionPanel:boolean;
    showGroupSelection:boolean;
    subGroupSelected:boolean;
    showSubGroupSelectionNumber:boolean;
    subGroupFound:boolean;
    editSubGroup:boolean;
    saveSubGroupInfo:boolean;
    cancelSubGroup:boolean;
    deleteAndCreateNewSubGroup:boolean;
    cameFromEdit:boolean;   //if edited, then, delete the existing data and insert the new data
    //:boolean;
    arr :any;

    //map in javascript
    subGroupWithDeptMap:any;
    groupNumeberSelection:Function;
    mouseClickEvent:Function;
    viewGroups:Function;
    getSemesterInfo:Function;
    getSeatPlanGroupInfo:Function;
    createOrViewSeatPlan:Function;
    getGroups:Function;
    getRoomInfo:Function;
    getRoomList:Function;
    closeSubGroupOrRoomInfoWindow: Function;
    showGroups:Function;
    createOrViewSubgroups:Function;
    generateSubGroups:Function;
    subGroupListChanged:Function;
    saveSubGroup:Function;
    postSubGroup:Function;
    saveSubGroupIntoDb:Function;
    getSubGroupInfo:Function;
    getSubGroup:Function;
    getSelectedGroupList:Function;
    getGroupInfoFromSelectedSubGroup:Function;
    deleteExistingSubGroupInfo:Function;
    createDroppable:Function;
    getSeatPlanInfo:Function;
    splitACourseStudent:Function;
    splitAction:Function;
    cancelSplitAction:Function;
    revertSplitAction:Function;
    refreshSortables:Function;
    changeTotalStudentNumberForSplitAction:Function;
    mergeInitialization:Function;
    mergeGroups:Function;
    makeSortableEmpty:Function;
    makeSortableCancel:Function;
    makeSortableEnable:Function;
    getExamRoutineInfoForCCI:Function;
    generateIterationNumberArray:Function;
    splitOut:Function;
    cancelSplit:Function;
    showStatistics:Function;
    splitActionUpdate:Function;
    getSeatPlanInfoCCi:Function;
    //cci
    createCCI:Function;
    showCCI:Function;
    getApplicationCCIInfoForSubGroup:Function;
    mergeUpdate:Function;
    //cci end

    reCreate:Function;
    editSavedSubGroup:Function;
    cancelEditedSubGroup:Function;
    createNewSubGroup:Function;
    getMouseClickEvent:Function;
    viewCCI:Function;
    deleteAndRecreate:Function;
    cancelRecreation:Function;
    deleteExistingSubGroupCCI:Function;
  }

  interface IExamRoutineCCI{
    examDate:string;
    weekDay:string;
    totalStudent:number;
  }

  interface IRoom{
    id:number;
    roomNo:string;
    totalRow:number;
    totalColumn:number;
    capacity:number;
  }


  interface ISemester{
    id:number;
    name:string;
    startDate:string;
    status:number;
  }

  interface IGroup{
    groupNumber:number;
    groupMembers:Array<ISeatPlanGroup>;
    totalStudentNumber:number;
    showSubPortion:boolean;

  }

  interface ISubGroup{
    subGroupNumber:number;
    subGroupMembers:Array<ISeatPlanGroup>;
    subGroupTotalStudentNumber:number;
  }

  interface ISubGroupDb{
    id:number;
    semesterId:number;
    groupNo:number;
    subGroupNo:number;
    groupId:number;
    position:number;
    studentNumber:number;
    courseId:string;
    courseNo:string;
    examDate:string;
  }

  interface ISeatPlanJsonData{
    subGroupNo:number;
    groupId:number;
    position:number;
    studentNumber:number;
  }

  interface ISeatPlanGroup{
    id:any;
    semesterId:number;
    programId:number;
    programName:string;
    year:number;
    semester:number;
    groupNo:number;
    type:number;
    studentNumber:number;
    lastUpdated:string;
    showSubPortion:boolean;
    splitOccuranceNumber:number;
    subGroupNumber:number;
    baseId:number;
    examDate:string;
    courseNo:string;
    courseId:string;
    courseTitle:string;
    gorupId:number;
    position:number;
    backgroundColor:string;
    groupId:number;
  }



  export class ExamSeatPlan{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$timeout','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IExamSeatPlanScope,
                private $q:ng.IQService, private notify: Notify,private $timeout:ITimeoutService,
                private $sce:ng.ISCEService,private $window:ng.IWindowService) {

      var arr : { [key:number]:Array<ISeatPlanGroup>; } = {};
      $scope.previousIterationNumber=0;
      $scope.mergeIdList=[];
      $scope.loadingVisibilityForCCI = false;
      $scope.cciSelected=false;
      $scope.cciSelected = false;
      $scope.splitActionOccured = false;
      $scope.recreate = false;
      $scope.splitButtonClicked = false;
      $scope.reverseSplitButtonClicked = false;
      $scope.loadingVisibility = false;
      $scope.showGroupOrNot=false;
      $scope.groupSelected = false;
      $scope.showGroupSelectionPanel = true;
      $scope.showGroupSelection = false;
      $scope.subGroupSelected=false;
      $scope.subGroupFound = false;
      $scope.editSubGroup = false;
      $scope.cancelSubGroup = false;
      $scope.saveSubGroupInfo = false;
      $scope.editButtonClicked=false;
      $scope.recreateButtonClicked=false;
      $scope.customeMenu=false;
      $scope.pdfGenerator=false;
      $scope.deleteAndCreateNewSubGroup = false;
      $scope.showContextMenu=false;
      $scope.arr = arr;
      $scope.update = 0;
      $scope.selectedSubGroupNo="";
      $scope.groupNumber = 1;
      $scope.group1List = [];
      $scope.group2List = [];
      $scope.group3List = [];
      $scope.totalStudentGroup1=0;
      $scope.totalStudentGroup2=0;
      $scope.totalStudentGroup3=0;
      $scope.groupList=[];
      $scope.subGroupList = [];
      $scope.subGroup1List={};
      $scope.subGroup2List={};
      $scope.subGroup3List={};
      $scope.subGroup4List={};
      $scope.subGroup5List={};
      $scope.subGroup6List={};
      $scope.mouseClickedObjectStore=[];
      $scope.subGroup1StudentNumber=0;
      $scope.subGroup2StudentNumber=0;
      $scope.subGroup3StudentNumber=0;
      $scope.subGroup4StudentNumber=0;
      $scope.subGroup5StudentNumber=0;
      $scope.subGroup6StudentNumber=0;
      $scope.subGroupWithDeptMap={};
      $scope.subGroupTotalStudentNumber={};
      $scope.getSemesterInfo = this.getSemesterInfo.bind(this);
      $scope.getSeatPlanGroupInfo = this.getSeatPlanGroupInfo.bind(this);
      $scope.getGroups = this.getGroups.bind(this);
      $scope.getRoomInfo = this.getRoomInfo.bind(this);
      $scope.getRoomList = this.getRoomList.bind(this);
      $scope.closeSubGroupOrRoomInfoWindow = this.closeSubGroupOrRoomInfoWindow.bind(this);
      $scope.createOrViewSeatPlan = this.createOrViewSeatPlan.bind(this);
      $scope.showGroups = this.showGroups.bind(this);
      $scope.createOrViewSubgroups = this.createOrViewSubgroups.bind(this);
      $scope.generateSubGroups = this.generateSubGroups.bind(this);
      $scope.saveSubGroup = this.saveSubGroup.bind(this);
      $scope.postSubGroup = this.postSubGroup.bind(this);
      $scope.getSubGroupInfo = this.getSubGroupInfo.bind(this);
      $scope.getSelectedGroupList = this.getSelectedGroupList.bind(this);
      $scope.getGroupInfoFromSelectedSubGroup = this.getGroupInfoFromSelectedSubGroup.bind(this);
      $scope.deleteExistingSubGroupInfo  = this.deleteExistingSubGroupInfo.bind(this);
      $scope.editSavedSubGroup = this.editSavedSubGroup.bind(this);
      $scope.createNewSubGroup = this.createNewSubGroup.bind(this);
      $scope.getSeatPlanInfo = this.getSeatPlanInfo.bind(this);
      $scope.saveSubGroupIntoDb = this.saveSubGroupIntoDb.bind(this);
      $scope.cancelEditedSubGroup = this.cancelEditedSubGroup.bind(this);
      $scope.showGroups = this.showGroups.bind(this);
      $scope.splitACourseStudent = this.splitCourseStudent.bind(this);
      $scope.cancelSplitAction = this.cancelSplitAction.bind(this);
      $scope.getExamRoutineInfoForCCI = this.getExamRoutineCCIInfo.bind(this);
      $scope.getApplicationCCIInfoForSubGroup = this.getApplicationCCIInfoForSubGroup.bind(this);
      $scope.generateIterationNumberArray = this.generateIterationNumberArray.bind(this);
      $scope.getMouseClickEvent = this.getMouseClickEvent.bind(this);
      $scope.splitOut = this.splitOut.bind(this);
      $scope.createCCI = this.createCCI.bind(this);
      $scope.cancelSplit = this.cancelSplit.bind(this);
      $scope.showStatistics = this.showStatistics.bind(this);
      $scope.splitActionUpdate = this.splitActionUpdate.bind(this);
      $scope.mergeUpdate = this.mergeUpdate.bind(this);
      $scope.viewCCI = this.viewCCI.bind(this);
      $scope.getSeatPlanInfoCCi = this.getSeatPlanInfoCCi.bind(this);
      $scope.deleteAndRecreate = this.deleteAndRecreate.bind(this);
      $scope.cancelRecreation = this.cancelRecreation.bind(this);
      $scope.deleteExistingSubGroupCCI = this.deleteExistingSubGroupCCI.bind(this);

      this.initialize();

    }

    private initialize():void{
      this.getSemesterInfo().then((semesterArr:Array<ISemester>)=>{

      });
    }

    private generateIterationNumberArray(subGroupNo:number){


      this.$scope.saveSubGroupInfo=true;
      if(this.$scope.previousIterationNumber==0){
        this.$scope.previousIterationNumber=subGroupNo;
      }else{
        if(subGroupNo<this.$scope.previousIterationNumber){
          for(var i=this.$scope.previousIterationNumber;i>subGroupNo;i--){
            if(this.$scope.subGroupWithDeptMap[i]!=null){
              for(var j=0;j<this.$scope.subGroupWithDeptMap[i].length;j++){
                this.$scope.tempGroupList.push(this.$scope.subGroupWithDeptMap[i][j]);
              }
              this.$scope.subGroupWithDeptMap[i]=null;
              this.$scope.iterationNumbers.pop();
            }
            this.$scope.previousIterationNumber=subGroupNo;
          }
        }
        console.log("*******");
        console.log(this.$scope.iterationNumbers);
      }
      if(this.$scope.subGroupWithDeptMap==null){
        this.$scope.subGroupWithDeptMap={};

      }
      if(this.$scope.iterationNumbers==null){
        this.$scope.iterationNumbers=[];

      }
      for(var i=1;i<=subGroupNo;i++){
        if(this.$scope.subGroupWithDeptMap[i]==null){
          this.$scope.iterationNumbers.push(i);
          var subGroupWithDeptMap:Array<ISeatPlanGroup>=[];
          this.$scope.subGroupWithDeptMap[i]=subGroupWithDeptMap;
        }


      }
      this.$scope.sortableOptionsIfSubGroupNotFound={};
      this.$scope.sortableOptionsIfSubGroupNotFound.connectWith='.apps-container';
      var currentScope=this;
      this.$scope.sortableOptionsIfSubGroupNotFound={
        connectWith:'.apps-container',
        update(){
          currentScope.showStatistics();
        }
      }
      console.log(this.$scope.sortableOptionsIfSubGroupNotFound);
      this.$scope.hideSelection=false;
      this.showStatistics();

    }

    private mergeUpdate(){
      var studentNumberStore:number = this.$scope.mouseClickedObject.studentNumber;
      var studentNumber:number=0;
      for(var i=0;i<this.$scope.mouseClickedObjectStore.length;i++){
        if(this.$scope.mouseClickedObject!=this.$scope.mouseClickedObjectStore[i]){
          studentNumber+= this.$scope.mouseClickedObjectStore[i].studentNumber;
          //this.$scope.mouseClickedObjectStore[i]=null;
          for(var j=0;j<this.$scope.tempGroupList.length;j++){
            if(this.$scope.tempGroupList[j]==this.$scope.mouseClickedObjectStore[i]){
              this.$scope.tempGroupList.splice(j,1);
              break;
            }
          }
          for(var j=1;j<=this.$scope.iterationNumbers.length;j++){
            for(var k=0;k<this.$scope.subGroupWithDeptMap[j].length;k++){
              if(this.$scope.subGroupWithDeptMap[j][k]==this.$scope.mouseClickedObjectStore[i]){
                this.$scope.subGroupWithDeptMap[j].splice(k,1);
                break;
              }
            }
          }
        }
      }

      console.log("00000000000000000");
      console.log(this.$scope.mouseClickedObjectStore);
      this.$scope.mouseClickedObject.studentNumber+=studentNumber;
      this.$scope.mouseClickedObject.backgroundColor="#EA8A8A";
      this.$scope.mouseClickedObjectStore=[];
      this.$scope.mouseClickedObjectStore.push(this.$scope.mouseClickedObject);

      this.showStatistics();
      this.$scope.customeMenu=false;
    }


    private splitActionUpdate(object:any,splitNumber:number){
      console.log("in the split action");
      console.log(object);
      if(splitNumber>object.studentNumber){
        this.$window.alert("Split number must be smaller than the current student number!");
      }
      else{
        var currentStudentNumber:number = angular.copy(object.studentNumber);
        console.log(currentStudentNumber);
        console.log(splitNumber);
        //this.$scope.splitNumber=splitNumber;
        object.studentNumber = +splitNumber;
        object.showSubPortion=false;
        object.backgroundColor="#EA8A8A";

        var newObject:ISeatPlanGroup = angular.copy(object);
        newObject.studentNumber=currentStudentNumber-object.studentNumber;
        var temporaryListHolder:Array<ISeatPlanGroup>=angular.copy(this.$scope.tempGroupList);
        this.$scope.tempGroupList=[];
        this.$scope.tempGroupList.push(newObject);
       // this.$scope.tempGroupList.concat(temporaryListHolder);
        for(var x=0;x<temporaryListHolder.length;x++){
          this.$scope.tempGroupList.push(temporaryListHolder[x]);
        }
        console.log("================");
        console.log(object);

        this.showStatistics();
      }
      this.$scope.customeMenu=false;
      console.log("this is custom menu******");
      console.log(this.$scope.customeMenu);

    }

    private showStatistics(){
      for(var i=1;i<=this.$scope.iterationNumbers.length;i++){
        this.$scope.subGroupTotalStudentNumber[i]=0;
        for(var j=0;j<this.$scope.subGroupWithDeptMap[i].length;j++){
          console.log(this.$scope.subGroupWithDeptMap[i][j].studentNumber);
          this.$scope.subGroupTotalStudentNumber[i]+= this.$scope.subGroupWithDeptMap[i][j].studentNumber;
        }
      }

      console.log("in stats");
      console.log(this.$scope.subGroupTotalStudentNumber);
    }


    private getMouseClickEvent(object:any){


        this.$scope.customeMenu=false;

      var checked:boolean=false;
      if(this.$scope.mouseClickedObject!=object && this.$scope.editSubGroup==false){
        checked=true;
        this.$scope.customeMenu=true;
        this.$scope.mouseClickedObject={};

        this.$scope.mouseClickedObject=object;
        console.log("exam type:"+ this.$scope.examType);
        if(this.$scope.mouseClickedObjectStore.length==0){

          this.$scope.mouseClickedObjectStore.push(object);

        }else{

          var foundMisMatch:boolean=false;
          var doNothing:boolean=false;
          for(var i=0;i<this.$scope.mouseClickedObjectStore.length;i++){
            if(this.$scope.cciSelected){
              if(this.$scope.mouseClickedObjectStore[i].courseId!=object.courseId ){
                console.log('found mismatch');
                foundMisMatch=true;
                break;
              }
              else if(this.$scope.mouseClickedObjectStore[i]==object){
                doNothing=true;
              }
              else{

              }
            }else{
              if(this.$scope.mouseClickedObjectStore[i].id!=object.id || this.$scope.mouseClickedObjectStore[i]==object){
                console.log('found mismatch');
                foundMisMatch=true;
                break;
              }
            }

          }
          if(foundMisMatch){
            for(var i=0;i<this.$scope.mouseClickedObjectStore.length;i++){
              this.$scope.mouseClickedObjectStore[i].backgroundColor="#EA8A8A";
              console.log("deleted object");
              console.log(this.$scope.mouseClickedObjectStore[i]);
            }
            console.log("-------before deletion----");
            console.log(this.$scope.mouseClickedObjectStore);
            this.$scope.mouseClickedObjectStore=[];
            console.log("--------after deletion----");
            this.$scope.mouseClickedObjectStore.push(object);
          }else{
            if(doNothing==false){
              this.$scope.mouseClickedObjectStore.push(object);

            }


          }

        }

        console.log(this.$scope.mouseClickedObjectStore);
        object.backgroundColor="red";
        console.log(object);
      }else{
        this.$scope.customeMenu=true;
      }


     // this.$scope.customeMenu=true;


    }

    private viewCCI(examDate:string){
      console.log('in the view cci');
      this.$scope.examDate=examDate;
      this.$scope.cciSelected=true;
      this.$scope.editSubGroup=true;
      this.$scope.recreate=true;
      this.createOrViewSubgroups(4);
    }

    private createCCI(examDate:string):void{
      this.$scope.hideSelection=false;
       // for application cci, examType=4;
      this.$scope.classBodyBackgroundColor="#d9edf7"; //Active Color
      this.$scope.cciSelected=true;
      this.getApplicationCCIInfoForSubGroup(examDate).then((subGroupCCiArr:Array<ISeatPlanGroup>)=>{
        this.createOrViewSubgroups(4);
      });
    }

    private splitOut():void{
      if(this.$scope.mouseClickedObject!=null){
        this.$scope.mouseClickedObject.showSubPortion=true;
      }
      this.$scope.customeMenu=false;
    }

    private cancelSplit():void{
      this.$scope.mouseClickedObject.showSubPortion=false;
      this.$scope.customeMenu=false;
    }

    private showGroups():void{
      if(this.$scope.semesterId!=null && this.$scope.examType!=null){
        if(this.$scope.examType==1){
          this.$scope.cciSelected=false;
          this.$scope.groupList=[];
          this.$scope.showGroupSelection = true;
          this.$scope.getGroups();
          $("#groupPanel").slideDown("slow");
          console.log("into type 1");
        }
        else{
          this.$scope.cciSelected=true;
          console.log("into type 2");
          this.getExamRoutineCCIInfo().then((examRoutineArr:Array<IExamRoutineCCI>)=>{
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
              this.$scope.examRoutineCCIArr.push(examRoutine);


            }
            this.$scope.cciSelected=true;

            this.$scope.loadingVisibility=false;

            console.log("Loadng visibility");
            console.log(this.$scope.loadingVisibility);
            console.log(this.$scope.examRoutineCCIArr);

          });


        }

      }

    }

    private getSelectedGroupList(group:number):void{
      this.$scope.selectedGroupList = this.$scope.tempGroupListAll;
    }



    private cancelSplitAction(splitId:number):void{

      for(var i=0;i<this.$scope.tempGroupList.length;i++){
        if(this.$scope.tempGroupList[i].id == splitId){
          this.$scope.tempGroupList[i].showSubPortion=false;
          break;
        }
      }

      //this.$scope.$apply();
      if(!this.$scope.$$phase){
        this.$scope.$apply();
      }

    }

    private findIdMatch(id:number):boolean{
      var noMatch:boolean=true;
      for(var x=0;x<this.$scope.tempGroupListAll.length;x++){
        if(this.$scope.tempGroupListAll[x].id==id){
          noMatch=false;
        }
      }
      return noMatch;
    }









    private splitCourseStudent(menuNumber:number):void{

      //var currentScope = this;
      console.log("Split id:");
      console.log(this.$scope.splitId);
      console.log(this.$scope.tempGroupList);
      if(menuNumber==1){

        if(this.$scope.subGroupFound){
          for(var i=0;i<this.$scope.tempGroupListAll.length;i++){
            if(this.$scope.tempGroupListAll[i].id==this.$scope.splitId){
              this.$scope.tempGroupListAll[i].showSubPortion=true;
              console.log("a match found");
              break;
            }
          }
        }
        else{
          for(var i=0;i<this.$scope.tempGroupList.length;i++){
            if(this.$scope.tempGroupList[i].id==this.$scope.splitId){
              this.$scope.tempGroupList[i].showSubPortion=true;
              console.log("a match found");
              break;
            }
          }
        }


      }else{

      }


      if(!this.$scope.$$phase){
        this.$scope.$apply();
      }


    }

    private getGroupInfoFromSelectedSubGroup(groupId:number,studentNumber:number):any{

      var member:any;
      for(var j=0;j<this.$scope.tempGroupListAll.length;j++){
        if(this.$scope.tempGroupListAll[j].id == groupId){
          if(this.$scope.tempSubGropStudentList==null){
            this.$scope.tempSubGropStudentList=[];
            this.$scope.tempGroupListAll[j].studentNumber = studentNumber;
            this.$scope.tempGroupListAll[j].showSubPortion=false;
            this.$scope.tempGroupListAll[j].splitOccuranceNumber=0;
            this.$scope.tempSubGropStudentList.push(this.$scope.tempGroupListAll[j]);
            member=this.$scope.tempGroupListAll[j];
          }else{
            var foundInTheTempStore:boolean = false;
            for(var m=0;m<this.$scope.tempSubGropStudentList.length;m++){
              if(this.$scope.tempSubGropStudentList[m].id == groupId){
                foundInTheTempStore = true;
                this.$scope.tempSubGropStudentList[m].splitOccuranceNumber+=1;
                var idStr:string = groupId.toString();
                idStr=idStr+this.$scope.tempSubGropStudentList[m].splitOccuranceNumber;
                var idInt:number = +idStr;
                var newMember:any={};
                newMember.id = idInt;
                newMember.baseId=groupId;
                newMember.groupNo=this.$scope.tempGroupListAll[j].groupNo;
                newMember.lastUpdated = this.$scope.tempGroupListAll[j].lastUpdated;
                newMember.programId = this.$scope.tempGroupListAll[j].programId;
                newMember.programName = this.$scope.tempGroupListAll[j].programName;
                newMember.semester = this.$scope.tempGroupListAll[j].semester;
                newMember.year = this.$scope.tempGroupListAll[j].year;
                newMember.semesterId = this.$scope.tempGroupListAll[j].semesterId;
                newMember.showSubPortion = false;
                newMember.splitOccuranceNumber=0;
                newMember.studentNumber = studentNumber;
                this.$scope.tempGroupListAll.push(newMember);
                member = newMember;
                break;
              }
            }

            if(foundInTheTempStore==false){
              this.$scope.tempGroupListAll[j].studentNumber = studentNumber;
              this.$scope.tempGroupListAll[j].showSubPortion=false;
              this.$scope.tempGroupListAll[j].splitOccuranceNumber=0;
              this.$scope.tempSubGropStudentList.push(this.$scope.tempGroupListAll[j]);

              member=this.$scope.tempGroupListAll[j];
            }
          }



          break;
        }
      }



      return member;
    }

    private viewGroups():void{

      this.getGroups();


    }



    private createOrViewSubgroups(group:number):void{
      this.$scope.iterationNumbers=[];

      if(group==4){
        this.$scope.groupNoForSeatPlanViewing = group;
        this.$scope.tempIdList=[];
        this.$scope.subGroupWithDeptMap={};
        this.$scope.splittedGroupList=[];

        this.$scope.tempGroupListAll = angular.copy(this.$scope.tempGroupList)
      }
      else{

        //************************************************************
        this.$scope.groupNoForSeatPlanViewing = group;
        this.$scope.tempIdList=[];
        this.$scope.subGroupWithDeptMap={};
        this.$scope.tempGroupList=[];
        this.$scope.splittedGroupList=[];
        var temporaryList:any=[];
        for(var l=0;l<this.$scope.groupList.length;l++){
          if(this.$scope.groupList[l].groupNumber==group){

            for(var i=0;i<this.$scope.groupList[l].groupMembers.length;i++){
              this.$scope.groupList[l].groupMembers[i].baseId = this.$scope.groupList[l].groupMembers[i].id;
              this.$scope.groupList[l].groupMembers[i].backgroundColor="#EA8A8A";
              temporaryList.push(this.$scope.groupList[l].groupMembers[i]);
              if(i==0){
                var id:number = this.$scope.groupList[l].groupMembers[i].id;
                var idStr:string = id.toString();
                this.$scope.groupIdLength = idStr.length;
              }
              /*this.$scope.tempGroupListAll.push(this.$scope.groupList[l].groupMembers[i]);
               this.$scope.tempGroupList.push(this.$scope.groupList[l].groupMembers[i])*/;
            }
            break;
          }
        }

        this.$scope.tempGroupListAll=[];
        this.$scope.tempGroupList = angular.copy(temporaryList);
        this.$scope.tempGroupListAll = angular.copy(temporaryList);
        this.$scope.tempGroupListForSplitInversion=[];
        this.$scope.tempGroupListForSplitInversion=angular.copy(temporaryList);







        for(var i=0;i<this.$scope.groupList.length;i++){
          if(this.$scope.groupList[i].groupNumber==group){
            this.$scope.selectedGroupTotalStudent = this.$scope.groupList[i].totalStudentNumber;

            break;
          }
        }
        this.$scope.splitActionOccured = false;
        /* this.$scope.tempGroupListAll=[];*/

        var whichMenuClicked:String;


        this.$scope.selectedGroupNo = group;
        this.getSelectedGroupList(group);

       // ******************************************************
      }




      this.$scope.subGroupList = [];

      this.getSubGroupInfo().then((subGroupArr:Array<ISeatPlanGroup>)=>{

        this.$scope.classBodyBackgroundColor="#FDEEF4";   //Inactive Color
        console.log("saved data");
        console.log(subGroupArr);
        if(subGroupArr.length>0 && this.$scope.recreateButtonClicked==false){
          this.$scope.hideSelection=true;


          this.$scope.tempGroupList=[];
          this.$scope.subGroupWithDeptMap=[];
          this.$scope.iterationNumbers=[];

          var subGroupWithDeptMap:Array<ISeatPlanGroup>=[];
          var subGroupNumber:number=0;
          for(var i=0;i<subGroupArr.length;i++){
            if(subGroupArr[i].subGroupNumber==0){
              this.$scope.tempGroupList.push(subGroupArr[i]);
            }
            else{
              if(subGroupWithDeptMap.length==0 ){
                subGroupWithDeptMap.push(subGroupArr[i]);
                subGroupNumber= subGroupArr[i].subGroupNumber;
              }else{
                if(subGroupNumber==subGroupArr[i].subGroupNumber){
                  subGroupWithDeptMap.push(subGroupArr[i]);
                  if(i==subGroupArr.length-1){
                    this.$scope.subGroupWithDeptMap[subGroupNumber] = subGroupWithDeptMap;
                  }
                }
                else{
                  this.$scope.subGroupWithDeptMap[subGroupNumber] = subGroupWithDeptMap;
                  subGroupNumber=subGroupArr[i].subGroupNumber;
                  subGroupWithDeptMap=[];
                  subGroupWithDeptMap.push(subGroupArr[i]);
                  if(i==subGroupArr.length-1){
                    this.$scope.subGroupWithDeptMap[subGroupNumber] = subGroupWithDeptMap;
                  }
                }
              }
            }
          }

          for(var i=1;i<=subGroupNumber;i++){
            this.$scope.iterationNumbers.push(i);
          }
          this.$scope.sortableOptionsIfSubGroupNotFound={};
          this.$scope.sortableOptionsIfSubGroupNotFound.disabled=true;

          this.$scope.editSubGroup=true;
          this.$scope.deleteAndCreateNewSubGroup=true;
          console.log("##################################");
          console.log(this.$scope.subGroupWithDeptMap);
          //this.$scope.sortableOptionsIfSubGroupNotFound.
          this.showStatistics();
          console.log(this.$scope.tempGroupList);
          this.$scope.saveSubGroupInfo=false;

        }
        else{
          this.$scope.hideSelection=false;

          this.$scope.deleteAndCreateNewSubGroup=false;
          this.$scope.editSubGroup=false;
          this.$scope.classBodyBackgroundColor="#FAEBD7";
          this.$scope.sortableOptionsIfSubGroupNotFound={};
          this.$scope.sortableOptionsIfSubGroupNotFound.placeholder="item";
          this.$scope.sortableOptionsIfSubGroupNotFound.connectWith='.apps-container';
          //this.$scope.sortableOptionsIfSubGroupNotFound.appendTo=this.$scope.tempGroupList;
          this.$scope.showContextMenu=true;

          this.$scope.colForSubgroup=0;

          this.$scope.subGroupList=[];


          if(this.$scope.recreateButtonClicked==false){
            this.$scope.subGroupFound = false;

          }


          this.$scope.showSubGroupSelectionNumber = true;





        }


      });



      this.$scope.subGroupSelected=true;
      this.$scope.showGroupSelectionPanel = false;

      if(!this.$scope.$$phase){
        this.$scope.$apply();
      }

    }













  /*  private mouseClickEvent():void{


      setTimeout(myFunction, 2000)
      $("#ifti_div").on("contextmenu", function (event) {
        event.preventDefault();

      });
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
        $("#subGroupPanel").bind("mousedown", function (e) {
          // If the clicked element is not the menu
          if (!($(e.target).parents(".custom-menu").length > 0)) {
            // Hide it
            $(".custom-menu").hide(100);
          }
        });

        /!*with the mouse down jquery function, we are getting the event only of right button,
         * that's why the case is 3.
         * with the line: $(this).attr('id') , we are getting the id when the right mouse button click event is triggered.*!/
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









      /!*Current scope will be used in replace of 'this' of angularjs, to jquery, else, jquery will not recognize that.*!/
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

    }*/



    private createNewSubGroup():void{
      console.log("in the create");
      this.$scope.confirmation=true;


     /* this.$scope.recreateButtonClicked=true;
      this.$scope.editSubGroup=false;
      this.$scope.saveSubGroupInfo=true;
      if(this.$scope.cciSelected){
        this.createCCI(this.$scope.examDate);
      }else{
        this.createOrViewSubgroups(this.$scope.selectedGroupNo);
      }*/

    }

    private deleteAndRecreate(){
      console.log("in the delete and recreate");
      this.$scope.confirmation=false;
      if(this.$scope.cciSelected){
        this.deleteExistingSubGroupCCI();

      }
      else{
        var groupNo:number=+this.$scope.selectedGroupNo;
        this.deleteExistingSubGroupInfo(groupNo);
      }

      this.$scope.recreateButtonClicked=true;
      this.$scope.editSubGroup=false;
      this.$scope.saveSubGroupInfo=true;
      this.$scope.subGroupTotalStudentNumber={};
      this.$scope.selectedSubGroupNo="";
      this.$scope.subGroupWithDeptMap={};
      if(this.$scope.cciSelected){
        this.createCCI(this.$scope.examDate);
      }else{
        this.createOrViewSubgroups(this.$scope.selectedGroupNo);
      }

    }

    private cancelRecreation(){
      console.log(" cancel the recreation process");
      this.$scope.confirmation=false;


    }


    private unbindJqueryFunctionality():void{
      $( "#subGroupPanel").unbind( "mousedown" );
      $("#subGroupPanel li").unbind("contextmenu");
      $("#ifti_div").unbind("contextmenu");
    }

    private cancelEditedSubGroup():void{

      this.$scope.editButtonClicked=false;
      this.$scope.cancelSubGroup = false;
      this.$scope.saveSubGroupInfo = false;
      this.$scope.deleteAndCreateNewSubGroup=true;
      this.createOrViewSubgroups(this.$scope.selectedGroupNo);


    }

    private editSavedSubGroup():void{


      this.$scope.sortableOptionsIfSubGroupNotFound={};
      var currentScope = this;
      this.$scope.sortableOptionsIfSubGroupNotFound={
        connectWith:'.apps-container',
        update(){
          currentScope.showStatistics();
        },
        disabled:false
      }
      this.$scope.classBodyBackgroundColor="#d9edf7"; //Active Color
      this.$scope.editSubGroup=false;
      this.$scope.saveSubGroupInfo=true;


    }



    private saveSubGroup():void{
      var groupNo:number = this.$scope.groupNoForSeatPlanViewing;


      var json:any;

      if(this.$scope.cciSelected){
        json = this.convertToJsonForSubGroupCCI();
      }
      else{
        json = this.convertToJsonForSubGroup();

      }
      var emptySubgroupFound:boolean=false;
      console.log(this.$scope.subGroupWithDeptMap);
      for(var i=1;i<=this.$scope.iterationNumbers.length;i++){
        if(this.$scope.subGroupWithDeptMap[i].length==0){
          emptySubgroupFound=true;
          break;
        }
      }
      if(emptySubgroupFound){
        console.log("empty sub group found");
          this.$window.alert("Sub group must not be empty");
      }
      else{
        console.log("Everything ok!!!");
        this.saveSubGroupIntoDb(json).then((message:string)=>{
          this.$scope.mouseClickedObjectStore=[];
          $.notific8(message);
          this.$scope.editButtonClicked=false;
          /*if(this.$scope.subGroupFound==false){
           this.createOrViewSubgroups(this.$scope.selectedGroupNo);

           }*/
          this.$scope.showContextMenu=false;
          this.$scope.editSubGroup=true;
          this.$scope.deleteAndCreateNewSubGroup=true;
          this.$scope.saveSubGroupInfo = false;
          this.$scope.cancelSubGroup = false;
          this.$scope.recreateButtonClicked=false;
          this.$scope.selectedSubGroupNo=null;
          if(this.$scope.cciSelected){
            this.createOrViewSubgroups(4);

          }else{
            this.createOrViewSubgroups(this.$scope.selectedGroupNo);

          }

        });
      }

    }

    private generateSubGroups(group:string):void{

      if(group!=""){
        this.$scope.colForSubgroup=+group;
        this.$scope.saveSubGroupInfo = true;
        this.$scope.editSubGroup=false;
        this.$scope.deleteAndCreateNewSubGroup=false;
        this.$scope.cancelSubGroup = true;
        //$("#sortable").sortable("enable");

        if(this.$scope.recreateButtonClicked==false){
         /* this.createDroppable();*/
        }
        else{

        }
      }



    }

    private getGroups():void{
      this.$scope.loadingVisibility = true;
      this.$scope.showGroupOrNot = false;
      this.getSeatPlanGroupInfo().then((groupArr:Array<ISeatPlanGroup>)=>{

        this.$scope.showGroupOrNot = true;
        this.$scope.loadingVisibility = false;
        this.$scope.seatPlanGroupList = groupArr;
        this.$scope.seatPlanGroupListLength = this.$scope.seatPlanGroupList.length;
        for(var i=0;i<groupArr.length;i++){
          groupArr[i].showSubPortion=false;
          groupArr[i].splitOccuranceNumber=0;

          if(this.$scope.groupList.length == 0){
            var group: any={};
            group.groupNumber = 0;
            group.groupNumber += groupArr[i].groupNo;
            group.groupMembers=[];
            group.groupMembers.push(groupArr[i]);
            group.totalStudentNumber = 0;
            group.totalStudentNumber+=groupArr[i].studentNumber;
            group.showSubPortion=false;
            this.$scope.groupList.push(group);


          }
          else{
            var groupFound:boolean;
            groupFound = false;

            for(var j=0;j<this.$scope.groupList.length;j++){
              if(this.$scope.groupList[j].groupNumber==groupArr[i].groupNo){

                group.groupMembers.push(groupArr[i]);
                group.totalStudentNumber+= groupArr[i].studentNumber;
                groupFound = true;
                break;
              }
            }
            if(groupFound==false){

              var group: any={};
              group.groupNumber = 0;
              group.groupNumber += groupArr[i].groupNo;
              group.groupMembers=[];
              group.groupMembers.push(groupArr[i]);
              group.totalStudentNumber = 0;
              group.totalStudentNumber+=groupArr[i].studentNumber;
              group.showSubPortion=false;
              this.$scope.groupList.push(group);


            }

          }


        }


      });

    }

    private getRoomList():void{
      this.getRoomInfo();

      this.$scope.groupSelected = true;
      this.$scope.showGroupSelectionPanel = false;



    }




    private closeSubGroupOrRoomInfoWindow():void{
      this.$scope.showStatistics();

      this.$scope.showGroupSelectionPanel = true;
      this.$scope.subGroupSelected = false;
      this.$scope.subGroupWithDeptMap={};
      this.$scope.selectedSubGroupNo="";

      this.$scope.iterationNumbers=[];
      this.$scope.subGroupTotalStudentNumber={};
      this.$scope.previousIterationNumber=0;
      this.$scope.tempGroupList=[];
    }

    private createOrViewSeatPlan(groupNo:number):void{
      this.getSeatPlanInfo(groupNo);
    }

    private getSeatPlanGroupInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var seatPlanGroupList:Array<ISeatPlanGroup>;
      this.httpClient.get('/ums-webservice-common/academic/seatPlanGroup/semester/'+this.$scope.semesterId +'/type/'+this.$scope.examType+'/update/'+this.$scope.update, 'application/json',
          (json:any, etag:string) => {
            seatPlanGroupList = json.entries;

            defer.resolve(seatPlanGroupList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getRoomInfo():void{

      this.httpClient.get("academic/classroom/all", HttpClient.MIME_TYPE_JSON,
          (response) => {
            this.$scope.roomList = response.rows;
          });
    }


    private getSemesterInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      this.httpClient.get('/ums-webservice-common/academic/semester/all', 'application/json',
          (json:any, etag:string) => {
            this.$scope.semesterList = json.entries;

            defer.resolve(this.$scope.semesterList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getSubGroupInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var subGroupDb:Array<ISeatPlanGroup>;
      console.log("condition of cciSelected");
      console.log(this.$scope.cciSelected);
      if(this.$scope.cciSelected){
        this.httpClient.get('/ums-webservice-common/academic/subGroupCCI/semester/'+this.$scope.semesterId+'/examDate/'+this.$scope.examDate,'application/json',
            (json:any,etag:string)=>{
              subGroupDb = json.entries;
              for(var i=0;i<subGroupDb.length;i++){
                subGroupDb[i].backgroundColor="#EA8A8A";
                subGroupDb[i].showSubPortion=false;
              }

              defer.resolve(subGroupDb);
            },
            (response:ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
            });
      }
      else{
        this.httpClient.get('/ums-webservice-common/academic/subGroup/get/semesterId/'+this.$scope.semesterId +'/groupNo/'+this.$scope.selectedGroupNo+'/type/'+this.$scope.examType, 'application/json',
            (json:any, etag:string) => {
              subGroupDb = json.entries;
              console.log("#######################");
              console.log(subGroupDb);
              for(var i=0;i<subGroupDb.length;i++){
                subGroupDb[i].backgroundColor="#EA8A8A";
                subGroupDb[i].showSubPortion=false;
              }
              defer.resolve(subGroupDb);
            },
            (response:ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
            });
      }

      return defer.promise;
    }

    private getSeatPlanInfo(groupNo:number):void{
      var defer = this.$q.defer();
      var subGroupDb:string;
      this.$scope.pdfGenerator=true;
      if(this.$scope.examDate==null){
        this.$scope.examDate="null";
      }
      this.httpClient.get('/ums-webservice-common/academic/seatplan/semesterId/'+this.$scope.semesterId +'/groupNo/'+groupNo+'/type/'+this.$scope.examType+'/examDate/null',  'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
            this.$scope.pdfGenerator=false;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          },'arraybuffer');

    }

    private getSeatPlanInfoCCi(examDate:string):void{
      var defer = this.$q.defer();
      var subGroupDb:string;
      this.$scope.loadingVisibilityForCCI=true;
      var groupNo:number = 0;

      this.httpClient.get('/ums-webservice-common/academic/seatplan/semesterId/'+this.$scope.semesterId +'/groupNo/'+groupNo+'/type/'+this.$scope.examType+'/examDate/'+examDate,  'application/pdf',
          (data:any, etag:string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
            this.$scope.loadingVisibilityForCCI=false;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.$scope.loadingVisibilityForCCI=false;
          },'arraybuffer');
    }

    private getExamRoutineCCIInfo():ng.IPromise<any>{
      var defer = this.$q.defer();
      var examRoutineArr:Array<IExamRoutineCCI>=[];

      this.httpClient.get('/ums-webservice-common/academic/examroutine/exam_routine_cci/semester/'+this.$scope.semesterId +'/examtype/'+this.$scope.examType,  'application/json',
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



    private getApplicationCCIInfoForSubGroup(examDate:string):ng.IPromise<any>{
      this.$scope.examDate = examDate;
      var defer = this.$q.defer();
      this.$scope.selectedGroupTotalStudent=0;
      console.log(examDate);
      this.$scope.tempGroupList=[];
      var applicationArr:Array<ISeatPlanGroup>=[];
      this.httpClient.get('/ums-webservice-common/academic/applicationCCI/semester/'+this.$scope.semesterId+'/examDate/'+examDate, 'application/json',
          (json:any, etag:string) => {
            applicationArr = json.entries;
            this.$scope.subGroupSelected=true;

            for(var i=0;i<applicationArr.length;i++){
              this.$scope.selectedGroupTotalStudent+=applicationArr[i].studentNumber;
              applicationArr[i].showSubPortion=false;
              applicationArr[i].id=i+1;
              applicationArr[i].backgroundColor="#EA8A8A";
              this.$scope.tempGroupList.push(applicationArr[i]);
            }
            defer.resolve(this.$scope.tempGroupList);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private postSubGroup(json:any):void{


      this.httpClient.post('academic/subGroup/',json,'application/json')
          .success(()=>{


          }).error((data)=>{

        postResult:false;
      });



    }

    private postSeatPlanInfo(json:any):void{
      this.httpClient.post('academic/',json,'application/json')
          .success(()=>{
            console.log("success");
          }).error((data)=>{
        console.log("Insertion failure");
        console.log(data);
      })
    }

    private saveSubGroupIntoDb(json:any):ng.IPromise<any>{
      var defer = this.$q.defer();
      console.log(json);
      if(this.$scope.cciSelected){

        var semesterId:number = +this.$scope.semesterId;

        this.httpClient.put('academic/subGroupCCI/put/semester/'+this.$scope.semesterId+'/examDate/'+this.$scope.examDate,json,'application/json')
            .success(()=>{
              for(var i=0;i<this.$scope.examRoutineCCIArr.length;i++){
                if(this.$scope.examRoutineCCIArr[i].examDate==this.$scope.examDate){
                  this.$scope.examRoutineCCIArr[i].totalStudent=5;
                  break;
                }
              }
              defer.resolve("Sucessfully Saved Sub Group Information!");
            })
            .error((data)=>{
              console.log(data);
            });

      }else{
        this.httpClient.put('academic/subGroup/save/semester/'+this.$scope.semesterId+'/groupNo/'+this.$scope.selectedGroupNo+'/type/'+this.$scope.examType,json,'application/json')
            .success(()=>{
              defer.resolve("Sucessfully Saved Sub Group Information!");
            })
            .error((data)=>{
              console.log(data);
            });
      }

      return defer.promise;
    }


    private deleteExistingSubGroupInfo(groupNo:number):void{

      this.httpClient.delete('academic/subGroup/semesterId/'+this.$scope.semesterId+'/groupNo/'+groupNo)
          .success(()=>{
            console.log("Successfully deleted");
          }).error((data)=>{
        console.log("Deletion failure");
        console.log(data);
      })
    }

    private deleteExistingSubGroupCCI(){
      this.httpClient.delete('academic/subGroupCCI/semesterId/'+this.$scope.semesterId+'/examDate/'+this.$scope.examDate)
          .success(()=>{
              for(var i=0;i<this.$scope.examRoutineCCIArr.length;i++){
                if(this.$scope.examRoutineCCIArr[i].examDate==this.$scope.examDate){
                  this.$scope.examRoutineCCIArr[i].totalStudent=0;
                  break;
                }
              }
          }).error((data)=>{
        console.log(data);
      });
    }

    private convertToJsonForSubGroup(){
      var completeJson={};
      var jsonObj=[];
      console.log("temp group list all ---->")
      console.log(this.$scope.tempGroupList);
      if(this.$scope.tempGroupList.length>0){
        for(var i=0;i<this.$scope.tempGroupList.length;i++){
          var item={};
          item["subGroupNo"]=0;
         /* if(this.$scope.tempGroupList[i].id!=null){
            item["groupId"]=this.$scope.tempGroupList[i].id;

          }else{

          }*/
          item["groupId"]=this.$scope.tempGroupList[i].groupId;

          item["position"]=1;
          item["studentNumber"]=this.$scope.tempGroupList[i].studentNumber;
          jsonObj.push(item);
        }
      }
      if(this.$scope.iterationNumbers.length>0){
        for(var i=1;i<=this.$scope.iterationNumbers.length;i++){
          for(var j=0;j<this.$scope.subGroupWithDeptMap[i].length;j++){
            var item={};
            var item={};
            item["subGroupNo"]=i;
           /* if(this.$scope.subGroupWithDeptMap[i][j].id!=null)
            {
              item["groupId"]=this.$scope.subGroupWithDeptMap[i][j].id;

            }
            else{
            }*/
            item["groupId"]=this.$scope.subGroupWithDeptMap[i][j].groupId;

            item["position"]=1;
            item["studentNumber"]=this.$scope.subGroupWithDeptMap[i][j].studentNumber;
            jsonObj.push(item);
          }
        }
      }


      completeJson["semesterId"] = this.$scope.semesterId;
      completeJson["groupNo"] = this.$scope.selectedGroupNo;
      completeJson["examType"] = this.$scope.examType;
      completeJson["entries"] = jsonObj;
      console.log("------------------------------------->");
      console.log(completeJson);
      return completeJson;
    }


    private convertToJsonForSubGroupCCI(){
      var completeJson={};

      var jsonObject=[];

      if(this.$scope.tempGroupList.length>0){
        for(var i=0;i<this.$scope.tempGroupList.length;i++){
          var item={};
          item["semesterId"]=this.$scope.semesterId;
          item["subGroupNo"]=0;
          item["totalStudent"]=this.$scope.tempGroupList[i].studentNumber;
          item["courseId"]=this.$scope.tempGroupList[i].courseId;
          item["examDate"] = this.$scope.examDate;
          jsonObject.push(item);
        }
      }
      if(this.$scope.iterationNumbers.length>0){
        for(var i=1;i<=this.$scope.iterationNumbers.length;i++){
          for(var j=0;j<this.$scope.subGroupWithDeptMap[i].length;j++){
            var item={};
            item["semesterId"]=this.$scope.semesterId;
            item["subGroupNo"]=i;
            item["courseId"]=this.$scope.subGroupWithDeptMap[i][j].courseId;
            item["examDate"]=this.$scope.examDate;
            item["totalStudent"]=this.$scope.subGroupWithDeptMap[i][j].studentNumber;
            jsonObject.push(item);
          }
        }
      }
      completeJson["entries"] = jsonObject;
      return completeJson;

    }

    private convertToJsonForViewingSeatPlan(){
      var item={};
      item["semesterId"] = this.$scope.semesterId;
      item["groupNo"] = this.$scope.groupNoForSeatPlanViewing;
      item["type"] = this.$scope.examType;
      return item;
    }

  }

  UMS.controller("ExamSeatPlan",ExamSeatPlan);
}