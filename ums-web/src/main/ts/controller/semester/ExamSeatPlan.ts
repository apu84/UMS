module ums{

  import ITimeoutService = ng.ITimeoutService;
  import UISortableOptions = angular.ui.UISortableOptions;
  interface IExamSeatPlanScope extends ng.IScope{
    semesterList:Array<ISemester>;
    seatPlanGroupList:Array<ISeatPlanGroup>;
    subGroupStorage:Array<String>;
    seatPlanJsonData:Array<ISeatPlanJsonData>;
    roomList:any;

    data:any;
    selectedGroupTotalStudent:number;
    splitNumber:number;
    groupNumber:number;
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
    splittedGroupList:Array<ISeatPlanGroup>;
    selectedGroupList:Array<ISeatPlanGroup>;
    subGroupList:Array<ISubGroup>;
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

    subGroup1ListTest:any;  //this is for test purpose

    recreate:boolean;
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

    arr :any;

    //map in javascript
    subGroupWithDeptMap:any;
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


    editSavedSubGroup:Function;
    cancelEditedSubGroup:Function;
    createNewSubGroup:Function;
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
  }



  export class ExamSeatPlan{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$timeout','$sce','$window'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IExamSeatPlanScope,
                private $q:ng.IQService, private notify: Notify,private $timeout:ITimeoutService,
                private $sce:ng.ISCEService,private $window:ng.IWindowService ) {

      var arr : { [key:number]:Array<ISeatPlanGroup>; } = {};

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
      $scope.deleteAndCreateNewSubGroup = false;
      $scope.arr = arr;
      $scope.update = 0;
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
      $scope.subGroup1StudentNumber=0;
      $scope.subGroup2StudentNumber=0;
      $scope.subGroup3StudentNumber=0;
      $scope.subGroup4StudentNumber=0;
      $scope.subGroup5StudentNumber=0;
      $scope.subGroup6StudentNumber=0;
      $scope.subGroupWithDeptMap={};
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
      $scope.createDroppable = this.createDroppable.bind(this);
      $scope.editSavedSubGroup = this.editSavedSubGroup.bind(this);
      $scope.createNewSubGroup = this.createNewSubGroup.bind(this);
      $scope.getSeatPlanInfo = this.getSeatPlanInfo.bind(this);
      $scope.saveSubGroupIntoDb = this.saveSubGroupIntoDb.bind(this);
      $scope.cancelEditedSubGroup = this.cancelEditedSubGroup.bind(this);
      $scope.showGroups = this.showGroups.bind(this);
      $scope.splitACourseStudent = this.splitCourseStudent.bind(this);
      $scope.splitAction = this.splitAction.bind(this);
      $scope.cancelSplitAction = this.cancelSplitAction.bind(this);
      $scope.mouseClickEvent = this.mouseClickEvent.bind(this);
      this.initialize();

    }

    private initialize():void{

      this.getSemesterInfo().then((semesterArr:Array<ISemester>)=>{

      });
    }

    private showGroups():void{
      if(this.$scope.semesterId!=null && this.$scope.examType!=null){
        this.$scope.groupList=[];
        this.$scope.showGroupSelection = true;
        this.$scope.getGroups();
        $("#groupPanel").slideDown("slow");
      }

    }

    private getSelectedGroupList(group:number):void{
      this.$scope.selectedGroupList = this.$scope.tempGroupListAll;
    }

    private cancelSplitAction(splitId:number):void{
      console.log("in the cancel action");
      console.log(splitId);
    /*  for(var i=0;i<this.$scope.tempGroupList.length;i++){
        if(this.$scope.tempGroupList[i].groupNumber == this.$scope.selectedGroupNo){
          for(var j=0;j<this.$scope.tempGroupList[i].groupMembers.length;j++){
            var member = this.$scope.tempGroupList[i].groupMembers[j];

            if(member.id == splitId){
              this.$scope.tempGroupList[i].groupMembers[j].showSubPortion = false;
              break;
            }
          }
          break;
        }
      }*/

      for(var i=0;i<this.$scope.tempGroupList.length;i++){
        if(this.$scope.tempGroupList[i].id == splitId){
          this.$scope.tempGroupList[i].showSubPortion=false;
          break;
        }
      }

      //this.$scope.$apply();

    }
    private splitAction(splitNumber:number):void{
      this.$scope.splitActionOccured = true;
      this.$scope.recreate = true;
     /* for(var i=0;i<this.$scope.tempGroupList.length;i++) {
        if (this.$scope.tempGroupList[i].groupNumber == this.$scope.selectedGroupNo) {
          var tempMemberStore = this.$scope.tempGroupList[i].groupMembers;
         /!* if(this.$scope.tempGroupListAll.length==0){
            this.$scope.tempGroupListAll = this.$scope.tempGroupListAll.concat(tempMemberStore);
          }*!/
          //this.$scope.tempGroupList[i].groupMembers = [];
          var foundInTheTempGroup:boolean = false;*/
          for (var j = 0; j < this.$scope.tempGroupList.length; j++) {
            var members =this.$scope.tempGroupList[j];
            if (members.id == this.$scope.splitId) {

              console.log("Found match");
              var tempArray:Array<ISeatPlanGroup>=[];
              var memberStudentNumber :any={};
              memberStudentNumber= members.studentNumber;
              var leftStudentNumber = memberStudentNumber - splitNumber;
              var previousMember = members;
              if(this.$scope.tempGroupList[j].splitOccuranceNumber==0 && this.$scope.groupIdLength==null){
                /*When any split occurance is occured, then, in the group id, an extra number is added, so that the number can be used to show the unique id's.
                * But, the extra number is needed to be removed. That's why groupIdLength is recorded once. It will be recorded only when split is fired. Else,
                * the value will be null and it will be checked at the json making time.*/
                var idString:string = this.$scope.tempGroupList[j].id.toString();
                this.$scope.groupIdLength = idString.length;
              }
              this.$scope.tempGroupList[j].splitOccuranceNumber+=1;
              this.$scope.tempGroupList[j].studentNumber = leftStudentNumber;
              this.$scope.tempGroupList[j].showSubPortion = false;
              this.$scope.tempGroupListAll[j].splitOccuranceNumber+=1;
              this.$scope.tempGroupListAll[j].studentNumber=leftStudentNumber;
              this.$scope.tempGroupListAll[j].showSubPortion=false;
              console.log("---previous member id : "+previousMember.id);

              //var newMember = tempMemberStore[j];
              var newMember:any={};
              newMember.groupNo=this.$scope.selectedGroupNo;
              newMember.lastUpdated = members.lastUpdated;
              newMember.programId = members.programId;
              newMember.programName = members.programName;
              newMember.semester = members.semester;
              newMember.year = members.year;
              newMember.semesterId = members.semesterId;
              newMember.showSubPortion = false;
              newMember.splitOccuranceNumber=0;
              newMember.studentNumber = +splitNumber;
              newMember.showSubPortion = false;
              var id = members.id;
              var idString:string = id.toString();
              if (newMember.splitOccuranceNumber == 0) {
                idString = idString + this.$scope.tempGroupList[j].splitOccuranceNumber;
                newMember.splitOccuranceNumber = 1;
              } else {
                var idArr: Array<String> = idString.split("");
                var lastInt: number = +(idArr[idArr.length - 1]);
                lastInt = lastInt + 1;
                idString = idArr.toString();
              }
              var idNumeric = +idString;
              newMember.id = idNumeric;

              var text = newMember.programName+":"+newMember.year+"/"+newMember.semester+" ("+newMember.studentNumber+")";
              var $li = $("<li style='background-color: #0a6aa1' class='ui-state-default' />").text(text);
              $li.attr('id',newMember.id);
              $("#splittedList").append($li);
              //this.$scope.tempGroupList.push(newMember);
              //$("#sortable").append(newMember);
             /*this.createDroppable();*/

              this.$scope.tempGroupListAll.push(newMember);
              this.$scope.splittedGroupList.push(newMember);
                //this.$scope.tempGroupList[i].groupMembers.push(tempArray[k]);


              console.log("-- new member id: "+newMember.id);

            } else {
              //this.$scope.tempGroupList[i].groupMembers.push(members);
              //this.$scope.tempGroupListAll.push(this.$scope.tempGroupList[j]);
            }
            /*this.$scope.tempGroupListAll=[];
            this.$scope.tempGroupListAll = this.$scope.tempGroupList[i].groupMembers;*/
          }


      this.$scope.recreate=false;
      /*/!*$("#sortable").sortable("refresh");
      $("#sortable").sortable("refreshPositions");*!/
      console.log(this.$scope.tempGroupListAll);*/

    }


    private splitCourseStudent(menuNumber:number):void{
      console.log("splitting started");
      var currentScope = this;

      if(menuNumber==1){

        if(this.$scope.subGroupFound){
          console.log('Sub group found!!!!');
          for(var i=0;i<this.$scope.subGroupList.length;i++){
            var matchFound:boolean=false;
            for(var j=0;j<this.$scope.subGroupList[i].subGroupMembers.length;j++){
              if(this.$scope.subGroupList[i].subGroupMembers[j].id==this.$scope.splitId){
                console.log("match found of saved sub group");
                this.$scope.subGroupList[i].subGroupMembers[j].showSubPortion=true;
                matchFound=true;
                break;
              }
            }
            if(matchFound){
              break;
            }
          }
        }else{
          for(var i=0;i<this.$scope.tempGroupList.length;i++){
            if(this.$scope.tempGroupList[i].id==this.$scope.splitId){
              this.$scope.tempGroupList[i].showSubPortion=true;
            }
          }
        }
      }else{

      }
      this.$scope.$apply();

      console.log("--Splitting concluded--");

    }

    private getGroupInfoFromSelectedSubGroup(groupId:number,studentNumber:number):any{

      var member:any;
      for(var j=0;j<this.$scope.tempGroupListAll.length;j++){
        if(this.$scope.tempGroupListAll[j].id == groupId){
          console.log("found match");
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

      console.log("##############");
      console.log(this.$scope.groupList);
      this.$scope.tempGroupList=[];
      this.$scope.splittedGroupList=[];
      for(var l=0;l<this.$scope.groupList.length;l++){
        if(this.$scope.groupList[l].groupNumber==group){
          this.$scope.tempGroupListAll=[];
         for(var i=0;i<this.$scope.groupList[l].groupMembers.length;i++){

           this.$scope.tempGroupListAll.push(this.$scope.groupList[l].groupMembers[i]);
           this.$scope.tempGroupList.push(this.$scope.groupList[l].groupMembers[i]);

         }
          break;
        }
      }

      $("#splittedList").enableSelection();
      console.log("*** temp group list all***");
      console.log(this.$scope.tempGroupListAll);
      for(var i=0;i<this.$scope.groupList.length;i++){
        if(this.$scope.groupList[i].groupNumber==group){
          this.$scope.selectedGroupTotalStudent = this.$scope.groupList[i].totalStudentNumber;
          console.log("--total student--:"+this.$scope.selectedGroupTotalStudent);
          break;
        }
      }
      this.$scope.splitActionOccured = false;
     /* this.$scope.tempGroupListAll=[];*/

      var whichMenuClicked:String;


      this.$scope.selectedGroupNo = group;
      this.getSelectedGroupList(group);

      console.log("Re create button condition before");
      console.log(this.$scope.recreateButtonClicked);
      this.$scope.subGroupList = [];

      this.getSubGroupInfo().then((subGroupArr:Array<ISubGroupDb>)=>{

        console.log("subgroup arr");
        console.log(subGroupArr);

        if(subGroupArr.length>0 && this.$scope.recreateButtonClicked==false){
          console.log("well, recreateButton is not clicked!");
          this.$scope.subGroupFound = true;



          var subGroupCreator:any={};
          var subGroupCounter:number = 0;
          for(var i=0;i<subGroupArr.length;i++){
            var subGroupCreator:any={};

            if(subGroupCounter!=subGroupArr[i].subGroupNo){
              subGroupCounter = subGroupArr[i].subGroupNo;
              subGroupCreator.subGroupNumber=subGroupArr[i].subGroupNo;
              subGroupCreator.subGroupTotalStudentNumber = subGroupArr[i].studentNumber;
              var members:any=this.getGroupInfoFromSelectedSubGroup(subGroupArr[i].groupId,subGroupArr[i].studentNumber);

              subGroupCreator.subGroupMembers = [];

              subGroupCreator.subGroupMembers.push(members);

              this.$scope.subGroupList.push(subGroupCreator);

            }
            else{
              for(var subGroupListIterator=0;subGroupListIterator<this.$scope.subGroupList.length;subGroupListIterator++){
                if(this.$scope.subGroupList[subGroupListIterator].subGroupNumber == subGroupCounter){
                  var members:any=this.getGroupInfoFromSelectedSubGroup(subGroupArr[i].groupId,subGroupArr[i].studentNumber);
                  this.$scope.subGroupList[subGroupListIterator].subGroupMembers.push(members);
                  this.$scope.subGroupList[subGroupListIterator].subGroupTotalStudentNumber+= subGroupArr[i].studentNumber;
                  break;
                }
              }
            }



          }

          this.$scope.tempGroupListAll=[];
          this.$scope.tempGroupList=[];
          for(var i=0;i<this.$scope.subGroupList.length;i++){
            var studentList:Array<ISeatPlanGroup>=this.$scope.subGroupList[i].subGroupMembers;

           for(var k=0;k<studentList.length;k++){
             this.$scope.tempGroupListAll.push(studentList[k]);
             this.$scope.tempGroupList.push(studentList[k]);
           }
            this.$scope.subGroupWithDeptMap[i]=studentList;

          }

          console.log("tempgroupListAll--->");
          console.log(this.$scope.tempGroupListAll);




          $("#sortable1,#sortable2,#sortable3,#sortable4,#sortable5,#sortable6").sortable({
            connectWith: ".connectedSortable"

          }).disableSelection();

          $(".connectedSortable").css("background-color","antiquewhite");

          this.$scope.colForSubgroup = this.$scope.subGroupList.length;
          /*this.$scope.$apply();*/


          this.$scope.savedSubGroupList = this.$scope.subGroupList;
          if(this.$scope.editButtonClicked==false){
            this.$scope.editSubGroup = true;
          }
          this.$scope.deleteAndCreateNewSubGroup = true;

          this.mouseClickEvent();

        }
        else{


          this.$scope.colForSubgroup=0;

          this.$scope.subGroupList=[];

          this.$scope.saveSubGroupInfo = true;
          this.$scope.editSubGroup=false;
          this.$scope.deleteAndCreateNewSubGroup=false;
          this.$scope.cancelSubGroup = false;
          this.$scope.subGroupFound = false;
          this.$scope.showSubGroupSelectionNumber = true;



          /*this.$scope.$apply();*/

          this.createDroppable();

          this.mouseClickEvent();

        }

       /* $("#ifti_div").on("contextmenu", function (event) {
          event.preventDefault();
          console.log("ifti");
          console.log($( "#subGroupPanel" ).find( "li" ));
        });
        //console.log($( "#subGroupPanel" ).find( "li" ));

        //setTimeout(myFunction, 2000)

        function myFunction() {
          // Trigger action when the contexmenu is about to be shown
          $(".ui-state-default").on("contextmenu", function (event) {

            console.log("Inside 'contextmenu' ....................");
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

        }





// If the document is clicked somewhere
        $("#subGroupPanel").bind("mousedown", function (e) {
          console.log("Inside 'mousedown' ....................");
          // If the clicked element is not the menu
          if (!($(e.target).parents(".custom-menu").length > 0)) {
            console.log("inside ......................")
            // Hide it
            $(".custom-menu").hide(100);
          }
        });

        /!*with the mouse down jquery function, we are getting the event only of right button,
         * that's why the case is 3.
         * with the line: $(this).attr('id') , we are getting the id when the right mouse button click event is triggered.*!/
        $(".connectedSortable li").mousedown(function(event){
          switch(event.which){
            case 3:
              var id = $(this).attr('id');
              currentScope.$scope.splitId = +id;

              console.log($(this).attr('id'));
          }
        });

        /!*Current scope will be used in replace of 'this' of angularjs, to jquery, else, jquery will not recognize that.*!/
        var currentScope = this;
// If the menu element is clicked
        $(".custom-menu li").click(function(){

          // This is the triggered action name
          switch($(this).attr("data-action")) {

            case "split": currentScope.$scope.splitButtonClicked=true;
              console.log('split button before function call');
              console.log(currentScope.$scope.splitButtonClicked);
              currentScope.splitCourseStudent(1);
              console.log("Split button is clicked");break;
            case "revertSplit": currentScope.$scope.reverseSplitButtonClicked=true;console.log("Reverse button is clicked!");break;
          }
          $(".custom-menu").hide(100);
        });
*/

      });



      this.$scope.subGroupSelected=true;
      this.$scope.showGroupSelectionPanel = false;

    }

    private mouseClickEvent():void{


      setTimeout(myFunction, 2000)
      $("#ifti_div").on("contextmenu", function (event) {
        event.preventDefault();
        console.log("ifti");
        console.log($( "#subGroupPanel" ).find( "li" ));
      });
      //console.log($( "#subGroupPanel" ).find( "li" ));



      function myFunction() {
        // Trigger action when the contexmenu is about to be shown
        $(".connectedSortable li").on("contextmenu", function (event) {

          console.log("Inside 'contextmenu' ....................");
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
          console.log("Inside 'mousedown' ....................");
          // If the clicked element is not the menu
          if (!($(e.target).parents(".custom-menu").length > 0)) {
            console.log("inside .....???.................")
            // Hide it
            $(".custom-menu").hide(100);
          }
        });

        /*with the mouse down jquery function, we are getting the event only of right button,
         * that's why the case is 3.
         * with the line: $(this).attr('id') , we are getting the id when the right mouse button click event is triggered.*/
        $("#subGroupPanel li").mousedown(function(event){
          console.log("In the list");
          switch(event.which){
            case 3:
              var id = $(this).attr('id');
              console.log($(this));
              currentScope.$scope.splitId = +id;

              console.log($(this).attr('id'));
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
            console.log('split button before function call');
            console.log(currentScope.$scope.splitButtonClicked);
            currentScope.splitCourseStudent(1);
            console.log("Split button is clicked");break;
          case "revertSplit": currentScope.$scope.reverseSplitButtonClicked=true;console.log("Reverse button is clicked!");break;
        }
        $(".custom-menu").hide(100);
      });

    }
    private createDroppable():void{
      $("#sortable,#droppable1,#droppable2,#droppable3,#droppable4,#dropdown5,#droppable6,#splittedList").sortable({
        connectWith: ".connectedSortable"

      }).disableSelection();
      var classScope = this;
      $("#sortable").sortable({
        cursor:"move"
      });
      $('#droppable1').sortable({
        cursor: "move",
        connectWith:".connectedSortable",
        items:"> li",
        over:function(event,ui){
          $("#droppable1").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#droppable1").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#droppable1").css("background-color","antiquewhite");
        },
        update:function(event,ui){
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(1,result);
        },

        change:function(event,ui){
        }
      });
      $('#droppable2').sortable({
        cursor: "move",

        connectWith:".connectedSortable",
        items:"> li",
        over:function(event,ui){
          $("#droppable2").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#droppable2").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#droppable2").css("background-color","antiquewhite");
        },

        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.$scope.subGroup2List = result;
          classScope.subGroupListChanged(2,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable3').sortable({
        cursor: "move",

        connectWith:".connectedSortable",
        items:"> li",

        over:function(event,ui){
          $("#droppable3").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#droppable3").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#droppable3").css("background-color","antiquewhite");
        },
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(3,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable4').sortable({
        cursor: "move",

        connectWith:".connectedSortable",
        items:"> li",
        over:function(event,ui){
          $("#droppable4").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#droppable4").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#droppable4").css("background-color","antiquewhite");
        },
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(4,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable5').sortable({
        cursor: "move",

        connectWith:".connectedSortable",
        items:"> li",
        over:function(event,ui){
          $("#droppable5").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#droppable5").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#droppable5").css("background-color","antiquewhite");
        },
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(5,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable6').sortable({
        cursor: "move",

        connectWith:".connectedSortable",
        items:"> li",
        over:function(event,ui){
          $("#droppable6").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#droppable6").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#droppable6").css("background-color","antiquewhite");
        },
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(6,result);

        },
        change:function(event,ui){

        }
      });

      $("#sortable").sortable("refresh");

    }


    private createNewSubGroup(groupNo:number):void{

      this.$scope.recreateButtonClicked=true;
      this.$scope.subGroupFound = false;
      this.$scope.editSubGroup = false;
      this.$scope.cancelSubGroup = false;
      this.$scope.deleteAndCreateNewSubGroup = false;
      this.$scope.selectedGroupNo = groupNo;
      this.$scope.showSubGroupSelectionNumber=true;
      this.createOrViewSubgroups(groupNo);
      this.$scope.colForSubgroup=0;
      this.$scope.groupNoForSubGroup = groupNo;
    }

    private cancelEditedSubGroup():void{

      this.$scope.editButtonClicked=false;
      this.$scope.cancelSubGroup = false;
      this.$scope.saveSubGroupInfo = false;
      this.$scope.deleteAndCreateNewSubGroup=true;
      this.createOrViewSubgroups(this.$scope.selectedGroupNo);


    }

    private editSavedSubGroup(groupNo:number):void{
      $(".connectedSortable").css("background-color","skyblue");
      console.log("Edit button clicked");
      if(this.$scope.subGroupFound==false){
        this.createOrViewSubgroups(groupNo);

      }
      this.$scope.editButtonClicked=true;
      this.$scope.saveSubGroupInfo=true;
      this.$scope.cancelSubGroup = true;
      this.$scope.deleteAndCreateNewSubGroup = false;
      this.$scope.editSubGroup = false;




      $("#sortable,#sortable1,#sortable2,#sortable3,#sortable4,#sortable5,#sortable6,#splittedList").sortable({
        connectWith: ".connectedSortable"
      });

      $("#sortable").sortable("enable");
      $("#sortable1").sortable("enable");
      $("#sortable2").sortable("enable");
      $("#sortable3").sortable("enable");
      $("#sortable4").sortable("enable");
      $("#sortable5").sortable("enable");
      $("#sortable6").sortable("enable");


      var classScope = this;
      $("#sortable").sortable({
        connectWith:".connectedSortable",
        cursor: "move",

        items:"> li",
        over:function(event,ui){
          $("#sortable").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#sortable").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#sortable").css("background-color","antiquewhite");
        }
      });

      $('#sortable1').sortable({
        connectWith:".connectedSortable",
        cursor: "move",

        items:"> li",
        over:function(event,ui){
          $("#sortable1").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#sortable1").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#sortable1").css("background-color","antiquewhite");
        },
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(1,result);

        },

        change:function(event,ui){


        }
      });
      $('#sortable2').sortable({
        connectWith:".connectedSortable",
        cursor: "move",

        items:"> li",

        over:function(event,ui){
          $("#sortable2").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#sortable2").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#sortable2").css("background-color","antiquewhite");
        },
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(2,result);

        },
        change:function(event,ui){

        }
      });
      $('#sortable3').sortable({
        connectWith:".connectedSortable",
        cursor: "move",

        items:"> li",

        over:function(event,ui){
          $("#sortable3").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#sortable3").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#sortable3").css("background-color","antiquewhite");
        },
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(3,result);

        },
        change:function(event,ui){

        }
      });
      $('#sortable4').sortable({
        connectWith:".connectedSortable",
        cursor: "move",

        items:"> li",

        over:function(event,ui){
          $("#sortable4").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#sortable4").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#sortable4").css("background-color","antiquewhite");
        },
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(4,result);

        },
        change:function(event,ui){

        }
      });
      $('#sortable5').sortable({
        connectWith:".connectedSortable",
        cursor: "move",

        items:"> li",
        over:function(event,ui){
          $("#sortable5").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#sortable5").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#sortable5").css("background-color","antiquewhite");
        },
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(5,result);

        },
        change:function(event,ui){

        }
      });
      $('#sortable6').sortable({
        connectWith:".connectedSortable",
        cursor: "move",

        items:"> li",
        over:function(event,ui){
          $("#sortable6").css("background-color","aqua");
        },
        receive:function(event,ui){
          $("#sortable6").css("background-color","antiquewhite");
        },

        out:function(event,ui){
          $("#sortable6").css("background-color","antiquewhite");
        },
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(6,result);

        },
        change:function(event,ui){

        }
      });

      this.$scope.editSubGroup = false;
      this.$scope.$apply();

    }

    private subGroupListChanged(subGroupNumber:number,result:any){

      console.clear();
      console.log("############################");
      console.log(this.$scope.tempGroupListAll);
      console.log("result_--");
      console.log(result);

      if(this.$scope.subGroupList.length ==0){
        var subGroup:any={};
        subGroup.subGroupNumber = subGroupNumber;

        for(var j=0;j< this.$scope.tempGroupListAll.length;j++){

          for(var m in result){
            if(result[m]!=""){
              if(this.$scope.tempGroupListAll[j].id == +result[m]){
                subGroup.subGroupTotalStudentNumber= this.$scope.tempGroupListAll[j].studentNumber;
                subGroup.subGroupMembers=[];
                subGroup.subGroupMembers.push(this.$scope.tempGroupListAll[j]);
                break;
              }
            }
          }

        }
        this.$scope.subGroupList.push(subGroup);
      }
      else{
        var subGroupFound= false;
        for( var i=0;i< this.$scope.subGroupList.length;i++){
          if(this.$scope.subGroupList[i].subGroupNumber == subGroupNumber){
            this.$scope.subGroupList[i].subGroupMembers = [];
            /*if(this.$scope.subGroupFound==true){
             var subGroupName:string="Sub Group "+subGroupNumber;
             var nameMember:any={};
             nameMember.id="";
             nameMember.programName=subGroupName
             this.$scope.subGroupList[i].subGroupMembers.push(nameMember);
             }*/
            this.$scope.subGroupList[i].subGroupTotalStudentNumber = 0;

            for(var m in result){
              if(result[m] != "" ){
                for(var j=0;j< this.$scope.tempGroupListAll.length;j++){
                  if(this.$scope.tempGroupListAll[j].id == +result[m]){
                    this.$scope.subGroupList[i].subGroupMembers.push(this.$scope.tempGroupListAll[j]);
                    this.$scope.subGroupList[i].subGroupTotalStudentNumber+= this.$scope.tempGroupListAll[j].studentNumber;

                    break;
                  }
                }
              }
            }

            subGroupFound = true;

            break;
          }
        }
        if(subGroupFound==false){
          var subGroup:any={};
          subGroup.subGroupNumber = subGroupNumber;


          for(var m in result){
            if(result[m]!=""){
                for(var j=0;j< this.$scope.tempGroupListAll.length;j++){

                  if(this.$scope.tempGroupListAll[j].id == +result[m]){
                  subGroup.subGroupTotalStudentNumber= this.$scope.tempGroupListAll[j].studentNumber;
                  subGroup.subGroupMembers=[];

                  subGroup.subGroupMembers.push(this.$scope.tempGroupListAll[j]);
                  break;
                }
              }
            }

          }
          console.log(subGroup);
          this.$scope.subGroupList.push(subGroup);
          console.log(this.$scope.subGroupList);
        }
      }


      for(var i=0;i<this.$scope.subGroupList.length;i++){
        if(this.$scope.subGroupList[i].subGroupNumber==1){
          this.$scope.subGroup1StudentNumber = this.$scope.subGroupList[i].subGroupTotalStudentNumber;
        }
        else if(this.$scope.subGroupList[i].subGroupNumber==2){
          this.$scope.subGroup2StudentNumber = this.$scope.subGroupList[i].subGroupTotalStudentNumber;
        }
        else if(this.$scope.subGroupList[i].subGroupNumber==3){
          this.$scope.subGroup3StudentNumber = this.$scope.subGroupList[i].subGroupTotalStudentNumber;
        }
        else if(this.$scope.subGroupList[i].subGroupNumber==4){
          this.$scope.subGroup4StudentNumber = this.$scope.subGroupList[i].subGroupTotalStudentNumber;
        }
        else if(this.$scope.subGroupList[i].subGroupNumber==5){
          this.$scope.subGroup5StudentNumber = this.$scope.subGroupList[i].subGroupTotalStudentNumber;
        }
        else {
          this.$scope.subGroup6StudentNumber = this.$scope.subGroupList[i].subGroupTotalStudentNumber;
        }
      }

      this.$scope.$apply();



    }


    private saveSubGroup(groupNo:number):void{




      var operationFailed:boolean=false;
      this.$scope.recreateButtonClicked=false;
      var seatPlanJsonDataList:Array<ISeatPlanJsonData>=[];
      for(var i=0;i<this.$scope.subGroupList.length;i++){
        var position=1;
        for(var j=0;j<this.$scope.subGroupList[i].subGroupMembers.length;j++){
          var seatPlanJsonData:any={};
          var seatPlanJsonData:any={};
          seatPlanJsonData.subGroupNo =this.$scope.subGroupList[i].subGroupNumber;
          seatPlanJsonData.position = position;
          seatPlanJsonData.groupId = this.$scope.subGroupList[i].subGroupMembers[j].id;
          seatPlanJsonData.studentNumber =this.$scope.subGroupList[i].subGroupMembers[j].studentNumber;
          /* var json = this.convertToJsonForSubGroup(this.$scope.subGroupList[i].subGroupNumber,
           position,
           this.$scope.subGroupList[i].subGroupMembers[j].id,this.$scope.subGroupList[i].subGroupMembers[j].studentNumber);*/
          //this.postSubGroup(json);

          seatPlanJsonDataList.push(seatPlanJsonData);
          position+=1;


        }

        /*if(operationFailed==true){

         break;
         }*/

        if(i==this.$scope.subGroupList.length-1 && this.$scope.subGroupFound==false){
          $("#droppable1").sortable("disable");
          $("#droppable2").sortable("disable");
          $("#droppable3").sortable("disable");
          $("#droppable4").sortable("disable");
          $("#droppable5").sortable("disable");
          $("#droppable6").sortable("disable");

        }
        else{
          $("#sortable1").sortable("disable");
          $("#sortable2").sortable("disable");
          $("#sortable3").sortable("disable");
          $("#sortable4").sortable("disable");
          $("#sortable5").sortable("disable");
          $("#sortable6").sortable("disable");
        }
      }

      var json = this.convertToJsonForSubGroup(seatPlanJsonDataList);
      this.saveSubGroupIntoDb(json).then((message:string)=>{
        $.notific8(message);
        this.$scope.editButtonClicked=false;
        /*if(this.$scope.subGroupFound==false){
         this.createOrViewSubgroups(this.$scope.selectedGroupNo);

         }*/

        this.$scope.editSubGroup=true;
        this.$scope.deleteAndCreateNewSubGroup=true;
        this.$scope.saveSubGroupInfo = false;
        this.createOrViewSubgroups(this.$scope.selectedGroupNo);

        $(".connectedSortable").css("background-color","antiquewhite");
      });
    }

    private generateSubGroups(group:number):void{
      /*
       * colForSubGroup==9999 means, invalid
       * */
      var totalSubgroups =Math.floor(12/group) ;
      if(group>6){
        alert("Subgroup can be no more than six.");
        this.$scope.colForSubgroup=9999;

      }else if(group<=0){
        alert("Please generate sub groups");
        this.$scope.colForSubgroup=9999;

      }else if(group==null){
        alert("Please generate sub groups");
        this.$scope.colForSubgroup=9999;
      }
      else{
        this.$scope.colForSubgroup=group;
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

      $("#splittedList").empty();

      console.log(" -- close button clicked---");
      this.$scope.groupSelected = false;
      this.$scope.subGroupSelected = false;
      this.$scope.showGroupSelectionPanel = true;
      this.$scope.recreateButtonClicked=false;
      $( "#subGroupPanel").unbind( "mousedown" );
      $("#subGroupPanel li").unbind("contextmenu");
      $("#ifti_div").unbind("contextmenu");
      //$("#subGroupPanel li").off();
    //$(".connectedSortable li").off();

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
      var subGroupDb:Array<ISubGroupDb>;
      this.httpClient.get('/ums-webservice-common/academic/subGroup/get/semesterId/'+this.$scope.semesterId +'/groupNo/'+this.$scope.selectedGroupNo+'/type/'+this.$scope.examType, 'application/json',
          (json:any, etag:string) => {
            subGroupDb = json.entries;

            defer.resolve(subGroupDb);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private getSeatPlanInfo(groupNo:number):void{
      var defer = this.$q.defer();
      var subGroupDb:string;
      this.httpClient.get('/ums-webservice-common/academic/seatplan/semesterId/'+this.$scope.semesterId +'/groupNo/'+groupNo+'/type/'+this.$scope.examType, 'application/pdf',
          (data:any, etag:string) => {
            var file=new Blob([data],{type:'application/pdf'});
            var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
            this.$window.open(fileURL);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

    }

    private postSubGroup(json:any):void{


      this.httpClient.post('academic/subGroup/',json,'application/json')
          .success(()=>{


          }).error((data)=>{
        console.log("insertion failure");
        //console.log(data);

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
      this.httpClient.put('academic/subGroup/save/semester/'+this.$scope.semesterId+'/groupNo/'+this.$scope.selectedGroupNo+'/type/'+this.$scope.examType,json,'application/json')
          .success(()=>{
            defer.resolve("Sucessfully Saved Sub Group Information!");
          })
          .error((data)=>{

          });
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

    private convertToJsonForSubGroup(seatPlanJsonData:Array<ISeatPlanJsonData>){
      var completeJson={};
      var jsonObj=[];
      for(var i=0;i<seatPlanJsonData.length;i++){
        var item={};
        item["subGroupNo"] = seatPlanJsonData[i].subGroupNo;
        if(this.$scope.groupIdLength!=null){
          var groupId:string = seatPlanJsonData[i].groupId.toString();
          if(groupId.length>this.$scope.groupIdLength){
            console.log("----*******----");
            console.log("groupId:"+groupId);
            var groupIdToStringArray = groupId.split("");

            console.log("groupIdToStringArray:"+groupIdToStringArray);
            var idStr:string="";
            for(var k=0;k<groupIdToStringArray.length-1;k++){
              idStr= idStr+ groupIdToStringArray[k];
            }

            console.log('idStr:'+idStr);
            var id:number = +idStr;
            console.log("id:"+id);
            item["groupId"] = id;
          }else{
            item["groupId"] = seatPlanJsonData[i].groupId;
          }

        }else{
          item["groupId"] = seatPlanJsonData[i].groupId;
        }
        item["position"] = seatPlanJsonData[i].position;
        item["studentNumber"] = seatPlanJsonData[i].studentNumber;

        jsonObj.push(item);
      }
      completeJson["semesterId"] = this.$scope.semesterId;
      completeJson["groupNo"] = this.$scope.selectedGroupNo;
      completeJson["examType"] = this.$scope.examType;
      completeJson["entries"] = jsonObj;
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