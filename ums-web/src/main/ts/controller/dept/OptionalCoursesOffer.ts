///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/jquery.notify.d.ts"/>
///<reference path="../../lib/jquery.jqGrid.d.ts"/>
module ums {
  interface IExamRoutineScope extends ng.IScope {
    optional:any;
    applicationSelectionChange:Function;
    approvedSelectionChange:Function;
    removeApprovedCourse:Function;
    removeApplicationCourse:Function;
    showAppliedStudents:Function;
    showCourses:Function;
    saveCourses:Function;
    fetchRejectedStudents:Function;
    sections:ISection;
    addNewSection:Function;
    removeSection:Function;
  }
  interface ISection{
    index:number;
    courseId:string;
    section:string;
    students:Array<String>;
  }
  interface IOptCourse {
    index: number;
    id: string;
    no: string;
    title: string;
    year: number;
    semester: number;
    pairCourseId:string;
    application:boolean;
    approved:boolean;
    bgColor:string;
  }

  interface IOptStudent{
    studentId:String;
  }

  export class OptionalCoursesOffer {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q'];

    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IExamRoutineScope, private $q:ng.IQService) {

      $scope.optional = {
        optionalCourses: Array<IOptCourse>(),
        applicationCourses: Array<IOptCourse>(),
        approvedCourses: Array<IOptCourse>(),
        rejectedStudents:Array<IOptStudent>(),
        sections:Array<ISection>(),
        approvedCallForApplicationCourses:Array<IOptCourse>()
      };
      $scope.applicationSelectionChange = this.applicationSelectionChange.bind(this);
      $scope.approvedSelectionChange = this.approvedSelectionChange.bind(this);
      $scope.removeApprovedCourse = this.removeApprovedCourse.bind(this);
      $scope.removeApplicationCourse = this.removeApplicationCourse.bind(this);
      $scope.showAppliedStudents=this.showAppliedStudents.bind(this);
      $scope.showCourses=this.showCourses.bind(this);
      $scope.saveCourses=this.saveCourses.bind(this);
      $scope.fetchRejectedStudents=this.fetchRejectedStudents.bind(this);
      $scope.addNewSection=this.addNewSection.bind(this);
      $scope.removeSection=this.removeSection.bind(this);

      //$('.nav-tabs li:eq(2) a').tab('show')
    }

    private showCourses():void{
      var url1:string="https://localhost/ums-webservice-common/academic/course/optional/semester-id/11012017/program/110500/year/4/semester/1";
      var url2:string="https://localhost/ums-webservice-common/academic/course/call4Application/semester-id/11012017/program/110500/year/4/semester/1";
      var url3:string="https://localhost/ums-webservice-common/academic/course/approved/semester-id/11012017/program/110500/year/4/semester/1";

      var urlApprovedCallForApplication:string="https://localhost/ums-webservice-common/academic/course/approved-call-for-application/semester-id/11012017/program/110500/year/4/semester/1";

      this.getCourses(urlApprovedCallForApplication).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.approvedCallForApplicationCourses=optCourseArr;
      });


      this.getCourses(url2).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.applicationCourses=optCourseArr;
      });

      this.getCourses(url3).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.approvedCourses=optCourseArr;

        this.getCourses(url1).then((optCourseArr:Array<IOptCourse>)=> {
          this.$scope.optional.optionalCourses=optCourseArr;
          for(var ind in this.$scope.optional.optionalCourses){
            var course:IOptCourse=this.$scope.optional.optionalCourses[ind];
            course.approved=false;
            course.application=false;
          }

          for(var ind1 in this.$scope.optional.applicationCourses){
              var course1:IOptCourse=this.$scope.optional.applicationCourses[ind1];
            for(var ind2 in this.$scope.optional.optionalCourses){
              var course2:IOptCourse=this.$scope.optional.optionalCourses[ind2];
              if(course1.id==course2.id){
                course2.application=true;
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
                if(course2.approved=true)
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
    private applicationSelectionChange(index:number,course:IOptCourse) {

      if (course.application == true) {
        course.bgColor = "#FFFFCC";

        var index = this.getAttributeMaxValueFromArray(this.$scope.optional.applicationCourses);
        //var item = this.getNewCall4ApplicationRow(index);
        var item=course;
        item.index=index;
        this.$scope.optional.applicationCourses.splice(0, 0, item);

    }
      else {
        course.bgColor = "none";
        var application_course_arr = eval(this.$scope.optional.applicationCourses);
        var applicationCourseTargetIndex = this.findIndexFromCourseId(application_course_arr, course.id);

        this.$scope.optional.applicationCourses.splice(applicationCourseTargetIndex, 1);
      }
      if(course.application==true && course.approved==true){
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
      if(course.application==true && course.approved==true){
        course.bgColor = "#CCFFCC";
      }



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

    private removeApplicationCourse(course:IOptCourse):void {
      var application_course_arr = eval(this.$scope.optional.callForApplicationCourses);
      var applicationCourseTargetIndex = this.findIndex(application_course_arr, course.index);

      this.$scope.optional.applicationCourses.splice(applicationCourseTargetIndex, 1);


      var a=this.findIndexFromCourseId(this.$scope.optional.optionalCourses,course.id);
      this.$scope.optional.optionalCourses[a].application=false;
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


    private saveCourses():void{
      var complete_json = {};
      complete_json["approved"] = this.$scope.optional.approvedCourses;
      complete_json["callForApplication"] = this.$scope.optional.applicationCourses;

      this.httpClient.put('academic/optional/application/settings/semester-id/11012017/program/110500/year/4/semester/1', complete_json, 'application/json')
          .success(() => {
            alert("ifti");
          }).error((data) => {
          });



    }

    private showAppliedStudents(course_id){
      //$('.nav-tabs li:eq(1) a').tab('show')
    }

    private fetchRejectedStudents(course_id:string):void{
      alert(course_id);
      this.httpClient.get("https://localhost/ums-webservice-common/academic/optional/application/students/semester-id/11012017/course-id/EEE4138_S2014_110500/status/2", this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var rejectedStudentArr:Array<IOptStudent>=eval(json.entries);
            this.$scope.optional.rejectedStudents=rejectedStudentArr;
            $.plugin_dragndrop('.dragndrop').duration(150);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
    }

    private addNewSection():void {
      var index = this.getAttributeMaxValueFromArray(this.$scope.optional.sections);
      var item = this.addNewSectionRow(index);
      this.$scope.optional.sections.splice(0, 0, item);
      $.plugin_dragndrop('.dragndrop1').duration(150);
      $.plugin_dragndrop('.dragndrop2').duration(150);
    }

    private getAttributeMaxValueFromArray(array:Array<any>):number {
      var val:number = 0;
      if (array.length != 0)
        val = Math.max.apply(Math, array.map(function (o) {
          return o.index;
        })) + 1;
      return val;
    }

    private addNewSectionRow(index:number) {
      var sectionRow:ISection = {
        index: index,
        courseId:'',
      section:'',
      students:Array<String>()
      }
      return sectionRow;
    }
    private removeSection(index:number):void {
      var section_arr = eval(this.$scope.optional.sections);
      var targetIndex = this.findIndex(section_arr, index);
      this.$scope.optional.sections.splice(targetIndex, 1);
    }

    }
  UMS.controller('OptionalCoursesOffer', OptionalCoursesOffer);
}

