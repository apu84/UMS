///<reference path="../../model/master_data/Semester.ts"/>
///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
module ums {

  interface INewSemesterScope extends ng.IScope {
    submit: Function;
    semester:Semester;
    data:any;
  }

  export class NewSemester {
    public static $inject = ['appConstants', 'HttpClient','$scope', 'notify'];
    constructor(private appConstants:any,private httpClient:HttpClient,private $scope:INewSemesterScope,private notify: Notify) {
      var currentYear:number=(new Date()).getFullYear();
      var nextYear:number=currentYear+1;
      $scope.data = {
        programTypeOptions:appConstants.programType,
        semesterTypeOptions:appConstants.semesterType,
        semesterStatusOptions:[{id: '2', name: 'Newly Created'}],
        year:[{id:"",name:"Select a Year"},{id:currentYear,name:currentYear},{id:nextYear,name:nextYear}]
      };
      /*
      setTimeout(function () {
        $('.make-switch').bootstrapSwitch();
        $('#TheCheckBox').bootstrapSwitch();
      }, 50);
      */

      $('.datepicker-default').datepicker();
      $('.datepicker-default').on('change', function(){
        $('.datepicker').hide();
      });
      $scope.submit = this.submit.bind(this);



    }
    private submit():void {

      //$.notify("Access granted", { position:"top",autoHide:false });
      this.$scope. semester.semesterName=$("#semesterType option:selected").text()+","+this.$scope.semester.year;
      var semesterId=this.$scope.semester.programTypeId+this.$scope.semester.semesterTypeId+this.$scope.semester.year;
      this.$scope. semester.semesterId=+semesterId;
      this.$scope.semester.statusId=2;
      var notify=this.notify;
      this.httpClient.post('academic/semester/', this.$scope.semester, 'application/json')
          .success(() => {
            //TODO: Move $.notific8 Common messaging service
            notify.success("Successfully created a new semester.");
            this.$scope. semester.programTypeId="";
            this.$scope. semester.semesterTypeId="";
            this.$scope. semester.year="";
            this.$scope. semester.startDate="";
            this.$scope. semester.endDate="";
          }).error((data) => {
      });

    }

  }
  UMS.controller('NewSemester', NewSemester);
}

