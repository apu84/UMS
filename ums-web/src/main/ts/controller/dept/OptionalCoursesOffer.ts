///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
module ums {

  interface IExamRoutineScope extends ng.IScope {
    optional:any;
    offeredSelectionChange:Function;
    approvedSelectionChange:Function;
    removeApprovedCourse:Function;
    removeOfferedCourse:Function;
    showAppliedStudents:Function;
    showCourses:Function;
  }

  interface IOptCourse {
    index: number;
    id: string;
    no: string;
    title: string;
    year: number;
    semester: number;
    pairCourseId:string;
    offered:boolean;
    approved:boolean;
    bgColor:string;
  }

  export class OptionalCoursesOffer {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q'];

    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IExamRoutineScope, private $q:ng.IQService) {

      $scope.optional = {
        optionalCourses: Array<IOptCourse>(),
        offeredCourses: Array<IOptCourse>(),
        approvedCourses: Array<IOptCourse>()

      };
      $scope.offeredSelectionChange = this.offeredSelectionChange.bind(this);
      $scope.approvedSelectionChange = this.approvedSelectionChange.bind(this);
      $scope.removeApprovedCourse = this.removeApprovedCourse.bind(this);
      $scope.removeOfferedCourse = this.removeOfferedCourse.bind(this);
      $scope.showAppliedStudents=this.showAppliedStudents.bind(this);
      $scope.showCourses=this.showCourses.bind(this);




      //$('.nav-tabs li:eq(2) a').tab('show')
    }


    private showCourses():void{
      var url1:string="https://localhost/ums-webservice-common/academic/course/optional/semester-id/11012015/program/110500/year/4/semester/1";
      var url2:string="https://localhost/ums-webservice-common/academic/course/offered/semester-id/11012015/program/110500/year/4/semester/1";
      var url3:string="https://localhost/ums-webservice-common/academic/course/approved/semester-id/11012015/program/110500/year/4/semester/1";

      this.getCourses(url2).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.offeredCourses=optCourseArr;
      });

      this.getCourses(url3).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.approvedCourses=optCourseArr;

        this.getCourses(url1).then((optCourseArr:Array<IOptCourse>)=> {
          this.$scope.optional.optionalCourses=optCourseArr;
          for(var ind in this.$scope.optional.optionalCourses){
            var course:IOptCourse=this.$scope.optional.optionalCourses[ind];
            course.approved=false;
            course.offered=false;
          }

          for(var ind1 in this.$scope.optional.offeredCourses){
              var course1:IOptCourse=this.$scope.optional.offeredCourses[ind1];
            for(var ind2 in this.$scope.optional.optionalCourses){
              var course2:IOptCourse=this.$scope.optional.optionalCourses[ind2];
              if(course1.id==course2.id){
                course2.offered=true;
                course2.bgColor="#FFFFCC";
              }
            }
          }

          for(var ind1 in this.$scope.optional.approvedCourses){
            var course1:IOptCourse=this.$scope.optional.approvedCourses[ind1];
            for(var ind2 in this.$scope.optional.optionalCourses){
              var course2:IOptCourse=this.$scope.optional.optionalCourses[ind2];
              if(course1.id==course2.id){
                course2.approved=true;
                if(course2.offered=true)
                    course2.bgColor="#CCFFCC";
                else
                    course2.bgColor="#E0FFFF";
              }
            }
          }

        });


      });


    }

    private getCourses(url:string):ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var courseArr:Array<IOptCourse>=eval(json.entries);
            defer.resolve(courseArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      return defer.promise;
    }
    private offeredSelectionChange(index:number,course:IOptCourse) {


      if (course.offered == true) {
        course.bgColor = "#FFFFCC";

        var index = this.getAttributeMaxValueFromArray(this.$scope.optional.offeredCourses);
        //var item = this.getNewCall4ApplicationRow(index);
        var item=course;
        item.index=index;
        this.$scope.optional.offeredCourses.splice(0, 0, item);

    }
      else {
        course.bgColor = "none";
        var offered_course_arr = eval(this.$scope.optional.offeredCourses);
        var offeredCourseTargetIndex = this.findIndexFromCourseId(offered_course_arr, course.id);

        this.$scope.optional.offeredCourses.splice(offeredCourseTargetIndex, 1);
      }
      if(course.offered==true && course.approved==true){
        course.bgColor = "#CCFFCC";
      }
    }

    private approvedSelectionChange(index:number,course:IOptCourse) {

      console.log(course.approved);
      console.log(typeof course.approved);

      if (course.approved == true) {
        course.bgColor = "#E0FFFF";

        var index = this.getAttributeMaxValueFromArray(this.$scope.optional.approvedCourses);
        //var item = this.getNewCall4ApplicationRow(index);
        var item = course;
        item.index = index;
        this.$scope.optional.approvedCourses.splice(0, 0, item);
        console.log(item);

      }
      else {

      course.bgColor = "none";

        //Remove it from the  approved course list
      var approved_course_arr = eval(this.$scope.optional.approvedCourses);
      var approvedCourseTargetIndex = this.findIndexFromCourseId(approved_course_arr, course.id);

      this.$scope.optional.approvedCourses.splice(approvedCourseTargetIndex, 1);
    }
      if(course.offered==true && course.approved==true){
        course.bgColor = "#CCFFCC";
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
      var approved_course_arr = eval(this.$scope.optional.approvedCourses);
      var approvedCourseTargetIndex = this.findIndex(approved_course_arr, course.index);
      console.log(approvedCourseTargetIndex);

      this.$scope.optional.approvedCourses.splice(approvedCourseTargetIndex, 1);


      var a=this.findIndexFromCourseId(this.$scope.optional.optionalCourses,course.id);
      this.$scope.optional.optionalCourses[a].approved=false;
      this.$scope.optional.optionalCourses[a].bgColor="none";
    }

    private removeOfferedCourse(course:IOptCourse):void {
      var call4App_course_arr = eval(this.$scope.optional.offeredCourses);
      var call4AppCourseTargetIndex = this.findIndex(call4App_course_arr, course.index);

      this.$scope.optional.offeredCourses.splice(call4AppCourseTargetIndex, 1);


      var a=this.findIndexFromCourseId(this.$scope.optional.optionalCourses,course.id);
      this.$scope.optional.optionalCourses[a].offeredCourses=false;
      this.$scope.optional.optionalCourses[a].bgColor="none";
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
