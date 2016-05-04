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

    arr :any;

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

  interface ISeatPlanGroup{
    id:number;
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

    private createOrViewSubgroups(group:number):void{


      this.$scope.selectedGroupNo = group;
      for(var g=0;g<this.$scope.groupList.length;g++){
        if(this.$scope.groupList[g].groupNumber == group){

          this.$scope.selectedGroupList=[];
          this.$scope.selectedGroupList = this.$scope.groupList[g].groupMembers;

          break;
        }
      }


      $("#sortable,#droppable1,#droppable2,#droppable3,#droppable4,#dropdown5,#droppable6").sortable({
        connectWith: ".connectedSortable"

      }).disableSelection();
      var classScope = this;
      $('#droppable1').sortable({
        connectWith:".connectedSortable",

        update:function(event,ui){
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(1,result);
        },

        change:function(event,ui){
          var length = $("#droppable1").sortable('serialize');
          console.log(length);

        }
      });
      $('#droppable2').sortable({
        connectWith:".connectedSortable",
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
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(3,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable4').sortable({
        connectWith:".connectedSortable",
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(4,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable5').sortable({
        connectWith:".connectedSortable",
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(5,result);

        },
        change:function(event,ui){

        }
      });
      $('#droppable6').sortable({
        connectWith:".connectedSortable",
        update:function(event,ui) {
          var result = $(this).sortable('toArray');
          classScope.subGroupListChanged(6,result);

        },
        change:function(event,ui){

        }
      });



      console.log(group);
      this.$scope.subGroupSelected=true;
      this.$scope.showGroupSelectionPanel = false;

    }

    private subGroupListChanged(subGroupNumber:number,result:any){

      console.log("IN the change method");



      if(this.$scope.subGroupList.length ==0){
        var subGroup:any={};
        subGroup.subGroupNumber = subGroupNumber;
        console.log(result);
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
            this.$scope.subGroupList[i].subGroupTotalStudentNumber = 0;

            for(var m in result){
                if(result[m] != ""){
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

      console.log(this.$scope.subGroupList);

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
      console.log(this.$scope.subGroup1StudentNumber);

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

    private createOrViewSeatPlan():void{
        this.getRoomList();
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
  }

  UMS.controller("ExamSeatPlan",ExamSeatPlan);
}