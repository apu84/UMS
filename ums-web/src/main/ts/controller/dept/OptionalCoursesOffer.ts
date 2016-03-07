///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
module ums {

  interface IExamRoutineScope extends ng.IScope {
    offerCourses:any;
    callForApplicationSelectionChange:Function;
    approvedSelectionChange:Function;
    removeApprovedCourse:Function;
    removeCall4ApplicationCourse:Function;
    showAppliedStudents:Function;
  }

  interface IOptCourse {
    index: number;
    id: string;
    no: string;
    title: string;
    year: number;
    semester: number;
    pairCourseId:string;
    call4Application:boolean;
    approved:boolean;
    bgColor:string;
  }

  export class OptionalCoursesOffer {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q'];

    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IExamRoutineScope, private $q:ng.IQService) {

      $scope.offerCourses = {
        optionalCourses: Array<IOptCourse>(),
        approvedCourses: Array<IOptCourse>(),
        call4ApplicationCourses: Array<IOptCourse>()
      };
      $scope.callForApplicationSelectionChange = this.callForApplicationSelectionChange.bind(this);
      $scope.approvedSelectionChange = this.approvedSelectionChange.bind(this);
      $scope.removeApprovedCourse = this.removeApprovedCourse.bind(this);
      $scope.removeCall4ApplicationCourse = this.removeCall4ApplicationCourse.bind(this);
      $scope.showAppliedStudents=this.showAppliedStudents.bind(this);

      /*
      this.getOptionalCourses(1,1).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.offerCourses.optionalCourses=optCourseArr;
          for(var ind in this.$scope.offerCourses.optionalCourses){

            var course:IOptCourse=this.$scope.offerCourses.optionalCourses[ind];
            course.approved=false;
            course.call4Application=false;
          }
      });
      */
      //$('.nav-tabs li:eq(2) a').tab('show')
    }

    private getOptionalCourses(semester_id:number,exam_type:number):ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get("https://localhost/ums-webservice-common/academic/course/semester-id/11012015/program/110500/year/4/semester/1", this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var courseArr:Array<IOptCourse>=eval(json.entries);
            defer.resolve(courseArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
    }
    private callForApplicationSelectionChange(index:number,course:IOptCourse) {

      if (course.call4Application == true) {
      course.bgColor = "#D2B48C";

        var index = this.getAttributeMaxValueFromArray(this.$scope.offerCourses.call4ApplicationCourses);
        //var item = this.getNewCall4ApplicationRow(index);
        var item=course;
        item.index=index;
        this.$scope.offerCourses.call4ApplicationCourses.splice(0, 0, item);

    }
      else
        course.bgColor="none";



    }

    private approvedSelectionChange(index:number,course:IOptCourse) {

      console.log(course.approved);
      console.log(typeof course.approved);

      if (course.approved == true) {
        course.bgColor = "#E0FFFF";

        var index = this.getAttributeMaxValueFromArray(this.$scope.offerCourses.approvedCourses);
        //var item = this.getNewCall4ApplicationRow(index);
        var item = course;
        item.index = index;
        this.$scope.offerCourses.approvedCourses.splice(0, 0, item);
        console.log(item);

      }
      else {

      course.bgColor = "none";

        //Remove it from the  approved course list
      var approved_course_arr = eval(this.$scope.offerCourses.approvedCourses);
      var approvedCourseTargetIndex = this.findIndexFromCourseId(approved_course_arr, course.id);

      this.$scope.offerCourses.approvedCourses.splice(approvedCourseTargetIndex, 1);
    }




    }




    private getAttributeMaxValueFromArray(array:Array<any>):number {
      var val:number = 0;
      if (array.length != 0)
        val = Math.max.apply(Math, array.map(function (o) {
          return o.index;
        })) + 1;
      return val;
    }
    private removeApprovedCourse(course:IOptCourse):void {
      console.log(course);
      console.log(course.index);
      var approved_course_arr = eval(this.$scope.offerCourses.approvedCourses);
      var approvedCourseTargetIndex = this.findIndex(approved_course_arr, course.index);
      console.log(approvedCourseTargetIndex);

      this.$scope.offerCourses.approvedCourses.splice(approvedCourseTargetIndex, 1);


      var a=this.findIndexFromCourseId(this.$scope.offerCourses.optionalCourses,course.id);
      this.$scope.offerCourses.optionalCourses[a].approved=false;
      this.$scope.offerCourses.optionalCourses[a].bgColor="none";
    }

    private removeCall4ApplicationCourse(course:IOptCourse):void {
      var call4App_course_arr = eval(this.$scope.offerCourses.call4ApplicationCourses);
      var call4AppCourseTargetIndex = this.findIndex(call4App_course_arr, course.index);

      this.$scope.offerCourses.call4ApplicationCourses.splice(call4AppCourseTargetIndex, 1);


      var a=this.findIndexFromCourseId(this.$scope.offerCourses.optionalCourses,course.id);
      this.$scope.offerCourses.optionalCourses[a].call4Application=false;
      this.$scope.offerCourses.optionalCourses[a].bgColor="none";
    }


    private findIndex(source_arr:Array<any>, target_index:number):number {
      var targetIndex = -1;
      for (var i = 0; i < source_arr.length; i++) {
        if (source_arr[i].index == target_index) {
          targetIndex = i;
          break;
        }
      }
      return targetIndex;
    }

    private findIndexFromCourseId(source_arr:Array<any>, course_id:string):number {
      var targetIndex = -1;
      for (var i = 0; i < source_arr.length; i++) {
        if (source_arr[i].id == course_id) {
          targetIndex = i;
          break;
        }
      }
      return targetIndex;
    }

    private showAppliedStudents(course_id){
      //$('.nav-tabs li:eq(1) a').tab('show')
    }

  }
  UMS.controller('OptionalCoursesOffer', OptionalCoursesOffer);
}
