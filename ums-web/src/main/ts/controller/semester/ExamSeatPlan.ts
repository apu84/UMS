module ums{

  import ITimeoutService = ng.ITimeoutService;
  interface IExamSeatPlanScope extends ng.IScope{
    semesterList:Array<ISemester>;
    seatPlanGroupList:Array<ISeatPlanGroup>;
    subGroupStorage:Array<String>;
    roomList:any;

    data:any;
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
    savedSubGroupList:any;
    totalStudentGroup1:number;
    totalStudentGroup2:number;
    totalStudentGroup3:number;
    totalNumberSubGroup:number;
    subGroupTotalNumber:number;
    groupList:Array<IGroup>;
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

    subGroup1ListTest:any;  //this is for test purpose

    groupSelected:boolean;
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
    getSubGroupInfo:Function;
    getSubGroup:Function;
    getSelectedGroupList:Function;
    getGroupInfoFromSelectedSubGroup:Function;
    deleteExistingSubGroupInfo:Function;
    createDroppable:Function;
    getSeatPlanInfo:Function;


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
  }

  export class ExamSeatPlan{
    public static $inject = ['appConstants','HttpClient','$scope','$q','notify','$timeout'];
    constructor(private appConstants: any, private httpClient: HttpClient, private $scope: IExamSeatPlanScope,private $q:ng.IQService, private notify: Notify,private $timeout:ITimeoutService) {

      var arr : { [key:number]:Array<ISeatPlanGroup>; } = {};


      $scope.groupSelected = false;
      $scope.showGroupSelectionPanel = true;
      $scope.showGroupSelection = false;
      $scope.subGroupSelected=false;
      $scope.subGroupFound = false;
      $scope.editSubGroup = false;
      $scope.cancelSubGroup = false;
      $scope.saveSubGroupInfo = false;
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
      this.initialize();

    }

    private initialize():void{
      this.getSemesterInfo().then((semesterArr:Array<ISemester>)=>{

      });
    }

    private showGroups():void{
      /*if(this.$scope.semesterId!=null && this.$scope.examType!=null && this.$scope.system!=null){
        this.$scope.showGroupSelection = true;
      }*/
      this.$scope.showGroupSelection = true;


    }

    private getSelectedGroupList(group:number):void{
      for(var g=0;g<this.$scope.groupList.length;g++){
        if(this.$scope.groupList[g].groupNumber == group){

          this.$scope.selectedGroupList=[];
          this.$scope.selectedGroupList = this.$scope.groupList[g].groupMembers;

          break;
        }
      }
    }

    private getGroupInfoFromSelectedSubGroup(groupId:number):any{


      var members:any;
      for(var j=0;j<this.$scope.selectedGroupList.length;j++){
        if(this.$scope.selectedGroupList[j].id == groupId){

          members=this.$scope.selectedGroupList[j];
          break;
        }
      }



      return members;
    }

    private createOrViewSubgroups(group:number):void{

      this.$scope.selectedGroupNo = group;
      this.getSelectedGroupList(group);


      this.getSubGroupInfo().then((subGroupArr:Array<ISubGroupDb>)=>{


        console.log('----sub group arr----');
        console.log(subGroupArr);
        if(subGroupArr.length>0){
          this.$scope.subGroupFound = true;

          this.$scope.subGroupList = [];
          var subGroupCreator:any={};
          var subGroupCounter:number = 0;
          for(var i=0;i<subGroupArr.length;i++){
            var subGroupCreator:any={};

            if(subGroupCounter!=subGroupArr[i].subGroupNo){
              subGroupCounter = subGroupArr[i].subGroupNo;
              subGroupCreator.subGroupNumber=subGroupArr[i].subGroupNo;
              subGroupCreator.subGroupTotalStudentNumber = subGroupArr[i].studentNumber;
              var members:any=this.getGroupInfoFromSelectedSubGroup(subGroupArr[i].groupId);
              var subGroupName:string="Sub Group "+subGroupCounter;
              var nameMember:any={};
              nameMember.id="";
              nameMember.programName=subGroupName
              subGroupCreator.subGroupMembers = [];
              subGroupCreator.subGroupMembers.push(nameMember);
              subGroupCreator.subGroupMembers.push(members);

              this.$scope.subGroupList.push(subGroupCreator);

            }
            else{
              for(var subGroupListIterator=0;subGroupListIterator<this.$scope.subGroupList.length;subGroupListIterator++){
                if(this.$scope.subGroupList[subGroupListIterator].subGroupNumber == subGroupCounter){
                  var members:any=this.getGroupInfoFromSelectedSubGroup(subGroupArr[i].groupId);
                  this.$scope.subGroupList[subGroupListIterator].subGroupMembers.push(members);
                  this.$scope.subGroupList[subGroupListIterator].subGroupTotalStudentNumber+= subGroupArr[i].studentNumber;
                  break;
                }
              }
            }



          }

          for(var i=0;i<this.$scope.subGroupList.length;i++){
            var studentList:Array<ISeatPlanGroup>=this.$scope.subGroupList[i].subGroupMembers;

            this.$scope.subGroupWithDeptMap[i]=studentList;

          }


          $("#sortable1,#sortable2,#sortable3,#sortable4,#sortable5,#sortable6").sortable({
            connectWith: ".connectedSortable"

          }).disableSelection();

         /* $("#sortable1").sortable("disable");
          $("#sortable2").sortable("disable");
          $("#sortable3").sortable("disable");
          $("#sortable4").sortable("disable");
          $("#sortable5").sortable("disable");
          $("#sortable6").sortable("disable");*/

          this.$scope.colForSubgroup = this.$scope.subGroupList.length;
          /*this.$scope.$apply();*/


          this.$scope.savedSubGroupList = this.$scope.subGroupList;
          this.$scope.editSubGroup = true;
          this.$scope.deleteAndCreateNewSubGroup = true;



        }
        else{

          this.$scope.subGroupList=[];

          this.$scope.saveSubGroupInfo = true;
          this.$scope.editSubGroup=false;
          this.$scope.deleteAndCreateNewSubGroup=false;
          this.$scope.cancelSubGroup = false;
          this.$scope.subGroupFound = false;
          this.$scope.showSubGroupSelectionNumber = true;

          /*this.$scope.$apply();*/

          this.createDroppable();



        }



      });







      this.$scope.subGroupSelected=true;
      this.$scope.showGroupSelectionPanel = false;

    }

    private createDroppable():void{
      $("#sortable,#droppable1,#droppable2,#droppable3,#droppable4,#dropdown5,#droppable6").sortable({
        connectWith: ".connectedSortable"

      }).disableSelection();
      var classScope = this;
      $('#droppable1').sortable({
        connectWith:".connectedSortable",
        items:"> li",

        update:function(event,ui){
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(1,result);
        },

        change:function(event,ui){
          var length = $("#droppable1").sortable('serialize');

        }
      });
      $('#droppable2').sortable({
        connectWith:".connectedSortable",
        items:"> li",

        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.$scope.subGroup2List = result;
          classScope.subGroupListChanged(2,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable3').sortable({
        connectWith:".connectedSortable",
        items:"> li",

        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(3,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable4').sortable({
        connectWith:".connectedSortable",
        items:"> li",

        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(4,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable5').sortable({
        connectWith:".connectedSortable",
        items:"> li",

        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(5,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable6').sortable({
        connectWith:".connectedSortable",
        items:"> li",

        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(6,result);

        },
        change:function(event,ui){

        }
      });
    }


    private createNewSubGroup():void{
      this.$scope.subGroupFound = false;
      this.deleteExistingSubGroupInfo();
      this.createDroppable();
      this.$scope.editSubGroup = false;
      this.$scope.cancelSubGroup = false;
      this.$scope.deleteAndCreateNewSubGroup = false;
      this.$scope.selectedGroupNo = 0;
    }

    private cancelEditedSubGroup():void{

      this.createOrViewSubgroups(this.$scope.groupNoForSeatPlanViewing);
      this.$scope.editSubGroup = true;
      this.$scope.cancelSubGroup = false;
      this.$scope.saveSubGroupInfo = false;
      this.$scope.deleteAndCreateNewSubGroup=false;

    }

    private editSavedSubGroup():void{


      console.log("Inside edit sub group");
      this.$scope.saveSubGroupInfo=true;
      this.$scope.cancelSubGroup = true;
      this.$scope.deleteAndCreateNewSubGroup = false;
      this.$scope.editSubGroup = false;


     /* $("#sortable1").sortable("enable");
      $("#sortable2").sortable("enable");
      $("#sortable3").sortable("enable");
      $("#sortable4").sortable("enable");
      $("#sortable5").sortable("enable");
      $("#sortable6").sortable("enable");*/

      $("#sortable1,#sortable2,#sortable3,#sortable4,#sortable5,#sortable6").sortable({
        connectWith: ".connectedSortable"

      }).disableSelection();

      var classScope = this;
      $('#sortable1').sortable({
        connectWith:".connectedSortable",

        update:function(event,ui){
          var result = $(this).sortable('toArray');
          console.log(result);
          classScope.subGroupListChanged(1,result);
        },

        change:function(event,ui){
          var length = $("#droppable1").sortable('serialize');

        }
      });
      $('#sortable2').sortable({
        connectWith:".connectedSortable",
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.$scope.subGroup2List = result;
          classScope.subGroupListChanged(2,result);

        },
        change:function(event,ui){

        }
      });
      $('#sortable3').sortable({
        connectWith:".connectedSortable",
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(3,result);

        },
        change:function(event,ui){

        }
      });
      $('#sortable4').sortable({
        connectWith:".connectedSortable",
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(4,result);

        },
        change:function(event,ui){

        }
      });
      $('#sortable5').sortable({
        connectWith:".connectedSortable",
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(5,result);

        },
        change:function(event,ui){

        }
      });
      $('#sortable6').sortable({
        connectWith:".connectedSortable",
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(6,result);

        },
        change:function(event,ui){

        }
      });


    }

    private subGroupListChanged(subGroupNumber:number,result:any){



      console.log("----before manipulation---");
      console.log(this.$scope.subGroupList);
      if(this.$scope.subGroupList.length ==0){
        var subGroup:any={};
        subGroup.subGroupNumber = subGroupNumber;
        for(var j=0;j< this.$scope.selectedGroupList.length;j++){

          for(var m in result){
            if(result[m]!=""){
              if(this.$scope.selectedGroupList[j].id == result[m]){
                subGroup.subGroupTotalStudentNumber= this.$scope.selectedGroupList[j].studentNumber;
                subGroup.subGroupMembers=[];
                subGroup.subGroupMembers.push(this.$scope.selectedGroupList[j]);
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
            if(this.$scope.subGroupFound==true){
              var subGroupName:string="Sub Group "+subGroupNumber;
              var nameMember:any={};
              nameMember.id="";
              nameMember.programName=subGroupName
              this.$scope.subGroupList[i].subGroupMembers.push(nameMember);
            }
            this.$scope.subGroupList[i].subGroupTotalStudentNumber = 0;

            for(var m in result){
                if(result[m] != "" ){
                  for(var j=0;j< this.$scope.selectedGroupList.length;j++){
                    if(this.$scope.selectedGroupList[j].id == result[m]){
                      this.$scope.subGroupList[i].subGroupMembers.push(this.$scope.selectedGroupList[j]);
                      this.$scope.subGroupList[i].subGroupTotalStudentNumber+= this.$scope.selectedGroupList[j].studentNumber;

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
          for(var j=0;j< this.$scope.selectedGroupList.length;j++){


            for(var m in result){
              if(result[m]!=""){
                if(this.$scope.selectedGroupList[j].id == result[m]){
                  subGroup.subGroupTotalStudentNumber= this.$scope.selectedGroupList[j].studentNumber;
                  subGroup.subGroupMembers=[];

                  subGroup.subGroupMembers.push(this.$scope.selectedGroupList[j]);
                  break;
                }
              }
            }

          }
          this.$scope.subGroupList.push(subGroup);
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

      console.log("---after maniputation---");
      console.log(this.$scope.subGroupList)

    }


    private saveSubGroup():void{

      console.log("Inside save sub group ");

      if(this.$scope.subGroupFound){
        this.deleteExistingSubGroupInfo();
      }

      this.$scope.editSubGroup=true;
      this.$scope.deleteAndCreateNewSubGroup=true;
      this.$scope.saveSubGroupInfo = false;
      var operationFailed:boolean=false;
      for(var i=0;i<this.$scope.subGroupList.length;i++){
        var position=1;
        for(var j=0;j<this.$scope.subGroupList[i].subGroupMembers.length;j++){

          var json = this.convertToJsonForSubGroup(this.$scope.subGroupList[i].subGroupNumber,
                                                  position,
                                                  this.$scope.subGroupList[i].subGroupMembers[j].id,this.$scope.subGroupList[i].subGroupMembers[j].studentNumber);
          this.postSubGroup(json);


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

      console.log('--------subgroup------');
      console.log(this.$scope.colForSubgroup);

    }

    private getGroups():void{
      this.getSeatPlanGroupInfo().then((groupArr:Array<ISeatPlanGroup>)=>{
        this.$scope.seatPlanGroupList = groupArr;
        this.$scope.seatPlanGroupListLength = this.$scope.seatPlanGroupList.length;
        for(var i=0;i<groupArr.length;i++){

          if(this.$scope.groupList.length == 0){
            var group: any={};
            group.groupNumber = 0;
            group.groupNumber += groupArr[i].groupNo;
            group.groupMembers=[];
            group.groupMembers.push(groupArr[i]);
            group.totalStudentNumber = 0;
            group.totalStudentNumber+=groupArr[i].studentNumber;
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
              this.$scope.groupList.push(group);


            }

          }

          //automation ended for group

          /*if(groupArr[i].groupNo==1){
            //group automation

            //group automation end
            this.$scope.group1List.push(this.$scope.seatPlanGroupList[i]);
            this.$scope.totalStudentGroup1 += groupArr[i].studentNumber;
          }else if(groupArr[i].groupNo==2){
            this.$scope.group2List.push(this.$scope.seatPlanGroupList[i]);
            this.$scope.totalStudentGroup2 += groupArr[i].studentNumber;
          }
          else{
            this.$scope.group3List.push(this.$scope.seatPlanGroupList[i]);
            this.$scope.totalStudentGroup3 += groupArr[i].studentNumber;
          }*/
        }

        console.log('----total group list ----');
        console.log(this.$scope.groupList);
      });
    }

    private getRoomList():void{
        this.getRoomInfo();

        this.$scope.groupSelected = true;
        this.$scope.showGroupSelectionPanel = false;

        //console.log(this.$scope.roomList[0].roomN
        //umber);

    }


    private closeSubGroupOrRoomInfoWindow():void{
      this.$scope.groupSelected = false;
      this.$scope.subGroupSelected = false;
      this.$scope.showGroupSelectionPanel = true;
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
            console.log("---inside semesterInfo---");
            console.log(this.$scope.semesterList);
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
      this.httpClient.get('/ums-webservice-common/academic/seatplan/semesterId/'+this.$scope.semesterId +'/groupNo/'+groupNo+'/type/'+this.$scope.examType, 'application/json',
          (json:any, etag:string) => {
            subGroupDb = json.entries;

            defer.resolve(subGroupDb);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

    }

    private postSubGroup(json:any):void{



      this.httpClient.post('academic/subGroup/',json,'application/json')
        .success(()=>{
          console.log("success");

        }).error((data)=>{
        console.log("insertion failure");
        console.log(data);

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


    private deleteExistingSubGroupInfo():void{
      this.httpClient.delete('academic/subGroup/semesterId/'+this.$scope.semesterId+'/groupNo/'+this.$scope.groupNoForSeatPlanViewing)
        .success(()=>{
          console.log("Successfully deleted");
        }).error((data)=>{
        console.log("Deletion failure");
        console.log(data);
      })
    }

    private convertToJsonForSubGroup(subGroupNo:number,position:number,groupId:number,studentNumber:number){
      var item={};
      item["semesterId"]=this.$scope.semesterId;
      item["groupNo"] = this.$scope.selectedGroupNo;
      item["subGroupNo"]= subGroupNo;
      item["groupId"] = groupId;
      item["position"] = position;
      item["studentNumber"] = studentNumber;
      item["examType"] = this.$scope.examType;
      return item;
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