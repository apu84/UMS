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
    fetchSectionInformation:Function;
    saveSection:Function;
    searchApplication:Function;
  }
  interface ISection {
    index:number;
    courseId:string;
    sectionName:string;
    type:string;
    students:Array<IOptStudent>;
  }
  interface IOptCourse {
    index: number;
    id: string;
    courseId:string;
    no: string;
    courseNo:string;
    title: string;
    courseTitle:string;
    year: number;
    semester: number;
    pairCourseId:string;
    application:boolean;
    approved:boolean;
    bgColor:string;
    status_id:number;
    status_label:string;
  }

  interface IOptStudent {
    mId:String;
    studentId:String;
    studentName:String;
  }
  var map = new Map();
  map.set("optional_url", "academic/course/optional/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}");
  map.set("application_url", "academic/course/call4Application/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}");
  map.set("approved_url", "academic/course/approved/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}");
  map.set("approved_call4Application_url", "academic/course/approved-call-for-application/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}");
  map.set("save_optional", "academic/optional/application/settings/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}");


  export class OptionalCoursesOffer {

    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q'];

    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IExamRoutineScope, private $q:ng.IQService) {

      $scope.optional = {
        optionalCourses: Array<IOptCourse>(),
        applicationCourses: Array<IOptCourse>(),
        approvedCourses: Array<IOptCourse>(),
        approvedCallForApplicationCourses: Array<IOptCourse>(),
        rejectedStudents: Array<IOptStudent>(),
        nonAssignedStudents: Array<IOptStudent>(),
        sections: Array<ISection>(),
        appliedCoursesForSingleStudent: Array<IOptStudent>(),
        program: '',
        semesterId:'',
        year:'',
        semester:''
      };

      $scope.applicationSelectionChange = this.applicationSelectionChange.bind(this);
      $scope.approvedSelectionChange = this.approvedSelectionChange.bind(this);
      $scope.removeApplicationCourse = this.removeApplicationCourse.bind(this);
      $scope.removeApprovedCourse = this.removeApprovedCourse.bind(this);
      $scope.fetchSectionInformation = this.fetchSectionInformation.bind(this);
      $scope.fetchRejectedStudents = this.fetchRejectedStudents.bind(this);
      $scope.showAppliedStudents = this.showAppliedStudents.bind(this);
      $scope.searchApplication = this.searchApplication.bind(this);
      $scope.addNewSection = this.addNewSection.bind(this);
      $scope.removeSection = this.removeSection.bind(this);
      $scope.showCourses = this.showCourses.bind(this);
      $scope.saveCourses = this.saveCourses.bind(this);
      $scope.saveSection = this.saveSection.bind(this);

      //$('.nav-tabs li:eq(2) a').tab('show')
    }

    private showCourses():void {

      this.getCourses(this.urlPlaceholderReplace(map.get("approved_call4Application_url"))).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.approvedCallForApplicationCourses = optCourseArr;
      });

      this.getCourses(this.urlPlaceholderReplace(map.get("application_url"))).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.applicationCourses = optCourseArr;
      });

      this.getCourses(this.urlPlaceholderReplace(map.get("approved_url"))).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.approvedCourses = optCourseArr;

        this.getCourses(this.urlPlaceholderReplace(map.get("optional_url"))).then((optCourseArr:Array<IOptCourse>)=> {
          this.$scope.optional.optionalCourses = optCourseArr;
          for (var ind in this.$scope.optional.optionalCourses) {
            var course:IOptCourse = this.$scope.optional.optionalCourses[ind];
            course.approved = false;
            course.application = false;
          }

          for (var ind1 in this.$scope.optional.applicationCourses) {
            var course1:IOptCourse = this.$scope.optional.applicationCourses[ind1];
            for (var ind2 in this.$scope.optional.optionalCourses) {
              var course2:IOptCourse = this.$scope.optional.optionalCourses[ind2];
              if (course1.id == course2.id) {
                course2.application = true;
                course2.bgColor = Utils.APPLICATION;
              }
            }
          }

          for (var ind1 in this.$scope.optional.approvedCourses) {
            var course1:IOptCourse = this.$scope.optional.approvedCourses[ind1];
            for (var ind2 in this.$scope.optional.optionalCourses) {
              var course2:IOptCourse = this.$scope.optional.optionalCourses[ind2];
              if (course1.id == course2.id) {
                course2.approved = true;
                if (course2.application == true)
                  course2.bgColor = Utils.APPROVED_APPLICATION;
                else
                  course2.bgColor = Utils.APPROVED;
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
            var courseArr:Array<IOptCourse> = eval(json.entries);
            defer.resolve(courseArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;
    }

    private applicationSelectionChange(index:number, course:IOptCourse) {
      this.manageSelectionChange(this.$scope.optional.applicationCourses,"application",index,course,course.application);
    }
    private approvedSelectionChange(index:number, course:IOptCourse) {
      this.manageSelectionChange(this.$scope.optional.approvedCourses,"approved",index,course,course.approved);
    }

    private manageSelectionChange( courses:Array<IOptCourse>,columnType:string, index:number, course:IOptCourse,operation:boolean) {
      var courseIndex=-1;
      var pairCourseIndex=-1;
      if (operation== true) {
        courseIndex = Utils.arrayMaxIndex(courses);
        var item = course;
        item.index = courseIndex;
        courses.splice(0, 0, item);

        if(item.pairCourseId!=""){
          pairCourseIndex=Utils.findIndex(this.$scope.optional.optionalCourses,item.pairCourseId);
          if(columnType=="application") this.$scope.optional.optionalCourses[pairCourseIndex].application =true;
          if(columnType=="approved") this.$scope.optional.optionalCourses[pairCourseIndex].approved =true;
          courses.splice(0, 0,  this.$scope.optional.optionalCourses[pairCourseIndex]);
        }

      }
      else {
        var application_course_arr = courses;
        courseIndex = Utils.findIndex(application_course_arr, course.id);
        this.$scope.optional.applicationCourses.splice(courseIndex, 1);

        if(course.pairCourseId!=""){
          pairCourseIndex=Utils.findIndex(this.$scope.optional.optionalCourses,course.pairCourseId);
          if(columnType=="application") this.$scope.optional.optionalCourses[pairCourseIndex].application =false;
          if(columnType=="approved") this.$scope.optional.optionalCourses[pairCourseIndex].approved =false;

          pairCourseIndex=Utils.findIndex(courses,course.pairCourseId);
          courses.splice(pairCourseIndex,1);

        }
      }
      this.manageRowColor(index,pairCourseIndex);
    }


    private removeApplicationCourse(course:IOptCourse):void {
      this.manageRemoveCourse( this.$scope.optional.applicationCourses,"application",course);
    }

    private removeApprovedCourse(course:IOptCourse):void {
      this.manageRemoveCourse( this.$scope.optional.approvedCourses,"approved",course);
    }

    private manageRemoveCourse( courses:Array<IOptCourse>,columnType:string,course:IOptCourse):void {

      var courseIndex = Utils.findIndex(courses, course.id);
      courses.splice(courseIndex, 1);
      var optionalCourseIndex = Utils.findIndex(this.$scope.optional.optionalCourses, course.id);
      if(columnType=="application") this.$scope.optional.optionalCourses[optionalCourseIndex].application =false;
      if(columnType=="approved") this.$scope.optional.optionalCourses[optionalCourseIndex].approved =false;

      this.manageRowColor(optionalCourseIndex);

      if(course.pairCourseId!=""){
        courseIndex = Utils.findIndex(courses, course.pairCourseId);
        courses.splice(courseIndex, 1);

        optionalCourseIndex = Utils.findIndex(this.$scope.optional.optionalCourses, course.pairCourseId);
        if(columnType=="application") this.$scope.optional.optionalCourses[optionalCourseIndex].application =false;
        if(columnType=="approved") this.$scope.optional.optionalCourses[optionalCourseIndex].approved =false;

        this.manageRowColor(optionalCourseIndex);
      }

    }


    private manageRowColor(...rowIndexArr: number[]):void{
      for (var i = 0; i < rowIndexArr.length; i++) {
        var rowIndex=rowIndexArr[i];
        if(rowIndex!=-1) {
          if (this.$scope.optional.optionalCourses[rowIndex].approved == true && this.$scope.optional.optionalCourses[rowIndex].application == true)
            this.$scope.optional.optionalCourses[rowIndex].bgColor = Utils.APPROVED_APPLICATION;
          else if (this.$scope.optional.optionalCourses[rowIndex].approved == false && this.$scope.optional.optionalCourses[rowIndex].application == false)
            this.$scope.optional.optionalCourses[rowIndex].bgColor = Utils.NONE;
          else if (this.$scope.optional.optionalCourses[rowIndex].approved == true)
            this.$scope.optional.optionalCourses[rowIndex].bgColor = Utils.APPROVED;
          else if (this.$scope.optional.optionalCourses[rowIndex].application == true)
            this.$scope.optional.optionalCourses[rowIndex].bgColor = Utils.APPLICATION;
        }
      }
    }



    private saveCourses():void {
      var complete_json = {};
      complete_json["approved"] = this.$scope.optional.approvedCourses;
      complete_json["callForApplication"] = this.$scope.optional.applicationCourses;

      this.httpClient.put(this.urlPlaceholderReplace(map.get("save_optional")), complete_json, 'application/json')
          .success(() => {
            $.notific8("Successfully Saved");
          }).error((data) => {
          });
    }

    private showAppliedStudents(course_id) {
      //$('.nav-tabs li:eq(1) a').tab('show')
    }

    private fetchRejectedStudents(course_id:string):void {
      alert(course_id);
      this.httpClient.get("https://localhost/ums-webservice-common/academic/optional/application/students/semester-id/11012017/course-id/EEE4138_S2014_110500/status/2", this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var rejectedStudentArr:Array<IOptStudent> = eval(json.entries);
            this.$scope.optional.rejectedStudents = rejectedStudentArr;
            $.plugin_dragndrop('.dragndrop').duration(150);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
    }

    private fetchSectionInformation(avc:number):void {

      var courseId = $("#sectionCourseId").val();

      this.httpClient.get("https://localhost/ums-webservice-common/academic/optional/application/non-assigned-section/students/semester-id/11012017/program/110500/course-id/EEE4154_S2014_110500", this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var nonAssignedStudentsArr:Array<IOptStudent> = json.entries;
            this.$scope.optional.nonAssignedStudents = nonAssignedStudentsArr;
            $.plugin_dragndrop('.dragndrop1').duration(150);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      this.httpClient.get("https://localhost/ums-webservice-common/academic/optional/application/assigned-section/students/semester-id/11012017/program/110500/course-id/EEE4154_S2014_110500", this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var sectionArr:Array<IOptStudent> = json.entries;
            this.$scope.optional.sections = sectionArr;
            $.plugin_dragndrop('.dragndrop1').duration(150);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });


    }

    private addNewSection():void {
      var index = Utils.arrayMaxIndex(this.$scope.optional.sections);
      var item = this.addNewSectionRow(index);
      this.$scope.optional.sections.splice(0, 0, item);
      $.plugin_dragndrop('.dragndrop1').duration(150);
      $.plugin_dragndrop('.dragndrop2').duration(150);
    }


    private addNewSectionRow(index:number) {
      var sectionRow:ISection = {
        index: index,
        courseId: '',
        sectionName: '',
        type: 'new',
        students: Array<IOptStudent>()
      }
      return sectionRow;
    }

    private removeSection(index:number):void {
      var section_arr = eval(this.$scope.optional.sections);
      var targetIndex = Utils.findIndex(section_arr, index.toString());

      console.log(this.$scope.optional.sections[targetIndex]);
      if (this.$scope.optional.sections[targetIndex].type != "new") {
        this.httpClient.delete("https://localhost/ums-webservice-common/academic/optional/application/semester-id/11012017/program/110500/course/EEE4154_S2014_110500/section/Section A")
            .success(() => {
              alert("ifti");
            }).error((data) => {
            });


      }
      this.$scope.optional.sections.splice(targetIndex, 1);


    }

    private saveSection():void {
      var complete_json = {};

      this.httpClient.put("https://localhost/ums-webservice-common/academic/optional/application/semester-id/11012017/program/110500/course/EEE4154_S2014_110500/section/Section A", complete_json, 'application/json')
          .success(() => {
            alert("ifti1");
          }).error((data) => {
          });
    }

    private searchApplication():void {

      this.httpClient.get("https://localhost/ums-webservice-common/academic/optional/application/applied-courses/student-id/150105001/semester-id/11012017/program/1", this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var courseArr:Array<IOptCourse> = eval(json.entries);
            this.$scope.optional.appliedCoursesForSingleStudent = courseArr;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });


    }
private urlPlaceholderReplace(inputUrl:string):string{
  var url=inputUrl.replace("{SEMESTER-ID}",this.$scope.optional.semesterId);
  url=url.replace("{PROGRAM-ID}",this.$scope.optional.program);
  url=url.replace("{YEAR}",this.$scope.optional.year);
  url=url.replace("{SEMESTER}",this.$scope.optional.semester);
  return url;
}

  }
  UMS.controller('OptionalCoursesOffer', OptionalCoursesOffer);
}

