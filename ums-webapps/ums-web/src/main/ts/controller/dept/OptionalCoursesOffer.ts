module ums {

  interface IOptCourseOffer extends ng.IScope {
    optional:any;
    saveApplicationStatusForSingleStudent:Function;
    fetchApplicationForSingleStudent:Function;
    applicationSelectionChange:Function;
    showApplicationForStudent:Function;
    removeApplicationCourse:Function;
    approvedSelectionChange:Function;
    removeApprovedCourse:Function;
    fetchSectionInformation:Function;
    saveApplicationShifting:Function;
    saveApplicationStatus:Function;
    showAppliedStudents:Function;
    fetchApplications:Function;
    removeSection:Function;
    addNewSection:Function;
    fetchStudents:Function;
    showCourses:Function;
    resetChanges:Function;
    saveCourses:Function;
    saveSection:Function;
    goToTab:Function;
    sections:ISection;
    CrHr:ICrHr;
  }
  interface ISection {
    id:number;
    index:number;
    courseId:string;
    sectionName:string;
    type:string;
    students:Array<IOptStudent>;
  }
  interface IOptCourse {
    id: string;
    index: number;
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
    statusId:number;
    statusLabel:string;
    newStatusId:number;
    applicationTypeId:number;
    applicationTypeLabel:String;
    newStatusLabel:string;
  }
  interface IOptStudent {
    mId:String;
    studentId:String;
    studentName:String;
    statusId:number;
    statusLabel:String;
    applicationTypeId:number;
    applicationTypeLabel:String;
    newStatusId:number;
  }
  interface IOptStatistics{
    courseNumber:String;
    totalApplied:number;
  }
  interface ICrHr{
    totalCrHr : number;
    optionalCrHr: number;
    optionalTheoryCrHr: number;
    optionalSessionalCrHr: number;
  }
  interface IOption {
    id: number;
    name: string;
    shortName: string;
  }

  var map: {[key:string]: string} = {};
  map["statistics_url"] = "academic/optional/application/stat/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}";
  map["crhr_url"] =  "academic/optional/application/CrHr/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}";
  map["optional_url"] =  "academic/course/optional/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}";
  map["application_url"] =  "academic/course/call4Application/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}";
  map["approved_url"] =  "academic/course/approved/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}";
  map["approved_call4Application_url"] =  "academic/course/approved-call-for-application/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}";
  map["save_optional"] =  "academic/optional/application/settings/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/year/{YEAR}/semester/{SEMESTER}";
  map["fetch_students"] =  "academic/optional/application/students/semester-id/{SEMESTER-ID}/course/{COURSE-ID}/status/{STATUS-ID}";
  map["fetch_applications"] =  "academic/optional/application/students/semester-id/{SEMESTER-ID}/course/{COURSE-ID}/status/all";
  map["save_application_status"] =  "academic/optional/application/status/semester-id/{SEMESTER-ID}/course/{COURSE-ID}";
  map["fetch_applications_for_single_student"] =  "academic/optional/application/applied-courses/student-id/{STUDENT-ID}/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}";
  map["save_application_status_for_single_student"] =  "academic/optional/application/status/semester-id/{SEMESTER-ID}student/{STUDENT-ID}";
  map["save_application_shifting"] =  "academic/optional/application/shift/semester-id/{SEMESTER-ID}/source-course/{SOURCE-COURSE-ID}/target-course/{TARGET-COURSE-ID}";
  map["section_nonAssignedStudents_for_course"] =  "academic/optional/application/non-assigned-section/students/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/course/{COURSE-ID}";
  map["sections_info_of_course"] =  "academic/optional/application/assigned-section/students/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/course/{COURSE-ID}";
  map["delete_section"] =  "academic/optional/application/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/course/{COURSE-ID}/section/{SECTION-NAME}";
  map["save_section"] =  "academic/optional/application/semester-id/{SEMESTER-ID}/program/{PROGRAM-ID}/course/{COURSE-ID}/section/{SECTION-NAME}";

  export class OptionalCoursesOffer {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify','semesterService', 'commonService'];

    constructor(private appConstants:any, private httpClient:HttpClient, private $scope:IOptCourseOffer, private $q:ng.IQService,private notify: Notify,private semesterService: SemesterService,private commonService: CommonService) {
      $scope.optional = {
        approvedCallForApplicationCourses: Array<IOptCourse>(),
        appliedCoursesForSingleStudent: Array<IOptStudent>(),
        nonAssignedStudents: Array<IOptStudent>(),
        applicationStudents:Array<IOptStudent>(),
        approvedStudents: Array<IOptStudent>(),
        applicationCourses: Array<IOptCourse>(),
        rejectedStudents: Array<IOptStudent>(),
        approvedCourses: Array<IOptCourse>(),
        optionalCourses: Array<IOptCourse>(),
        sections: Array<ISection>(),
        statistics: Array<IOptStatistics>(),
        targetCourseIdForStudentShifting:'',
        courseIdForRejectedStudents:'',
        allStudentCourseId:'',
        sectionCourseId:'',
        semesterId:'',
        deptId:'',
        semester:'',
        program: '',
        studentId:'',
        year:'',
        semesters: Array<IOption>(),
        depts: Array<IOption>(),
        yearSemesters: Array<any>(),
        yearSemester:''
      };

      $scope.saveApplicationStatusForSingleStudent=this.saveApplicationStatusForSingleStudent.bind(this);
      $scope.applicationSelectionChange = this.applicationSelectionChange.bind(this);
      $scope.showApplicationForStudent=this.showApplicationForStudent.bind(this);
      $scope.approvedSelectionChange = this.approvedSelectionChange.bind(this);
      $scope.removeApplicationCourse = this.removeApplicationCourse.bind(this);
      $scope.removeApprovedCourse = this.removeApprovedCourse.bind(this);
      $scope.fetchSectionInformation = this.fetchSectionInformation.bind(this);
      $scope.saveApplicationShifting=this.saveApplicationShifting.bind(this);
      $scope.showAppliedStudents = this.showAppliedStudents.bind(this);
      $scope.saveApplicationStatus=this.saveApplicationStatus.bind(this);
      $scope.fetchApplicationForSingleStudent = this.fetchApplicationForSingleStudent.bind(this);
      $scope.fetchApplications=this.fetchApplications.bind(this);
      $scope.addNewSection = this.addNewSection.bind(this);
      $scope.removeSection = this.removeSection.bind(this);
      $scope.fetchStudents = this.fetchStudents.bind(this);
      $scope.showCourses = this.showCourses.bind(this);
      $scope.saveCourses = this.saveCourses.bind(this);
      $scope.resetChanges=this.resetChanges.bind(this);
      $scope.saveSection = this.saveSection.bind(this);
      $scope.goToTab=this.goToTab.bind(this);

      this.loadSemesters();
    }

    private goToTab(tabIndex:number):void{
      $('.nav-tabs li:eq('+tabIndex+') a').tab('show');
    }

    private loadSemesters(): void {
      this.semesterService.fetchSemesters(Utils.UG).then((semesters: Array<IOption>) => {
        if (semesters.length == 0) {
          semesters.splice(0, 0, this.appConstants.initSemester[0]);
        }
        this.$scope.optional.semesters = semesters;
        this.$scope.optional.semesterId = semesters[0].id;
      });

      this.commonService.fetchCurrentUser().then((departmentJson: any) => {
        this.$scope.optional.depts = [departmentJson];
        this.$scope.optional.deptId = departmentJson.id;
        this.loadPrograms();
      });
    }

    private loadPrograms(): void {
      var programArr: any;
      var controllerScope = this.$scope;
        programArr = this.appConstants.ugPrograms;
      var programJson = $.map(programArr, function (el) {
        return el
      });
      var resultPrograms: any = $.grep(programJson, function (e: any) {
        return e.deptId == controllerScope.optional.deptId;
      });

      if(resultPrograms[0] == undefined) {
        this.$scope.optional.programs = null;
        this.$scope.optional.programId = null;
      }  else {
        this.$scope.optional.programs = resultPrograms[0].programs;
        this.$scope.optional.programId = resultPrograms[0].programs[0].id;
      }

      var yearSemesterList = (this.appConstants.optionalCourseYearSemester[this.$scope.optional.programId]);
      for (var ind in yearSemesterList) {
        var yearSemester:any = yearSemesterList[ind];
        var yearSemesterStr:string = yearSemester.year+" - "+yearSemester.semester;
        var option  ={"id":yearSemesterStr,"name":yearSemesterStr};
        this.$scope.optional.yearSemesters[ind] = option;
      }
      this.$scope.optional.yearSemester = this.$scope.optional.yearSemesters[0].id;
    }

    private showCourses():void {

      this.getStatistics(this.urlPlaceholderReplace(map["statistics_url"])).then((statArr:Array<IOptStatistics>)=> {
        this.$scope.optional.statistics = statArr;
      });

      this.getCrHrInfo(this.urlPlaceholderReplace(map["crhr_url"])).then((CrHr:ICrHr)=> {
        console.log(CrHr);
        this.$scope.CrHr= CrHr;
      });


      this.getCourses(this.urlPlaceholderReplace(map["approved_call4Application_url"])).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.approvedCallForApplicationCourses = optCourseArr;
      });

      this.getCourses(this.urlPlaceholderReplace(map["application_url"])).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.applicationCourses = optCourseArr;
      });

      this.getCourses(this.urlPlaceholderReplace(map["approved_url"])).then((optCourseArr:Array<IOptCourse>)=> {
        this.$scope.optional.approvedCourses = optCourseArr;

        this.getCourses(this.urlPlaceholderReplace(map["optional_url"])).then((optCourseArr:Array<IOptCourse>)=> {
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

    private getStatistics(url:string):ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var statArr:Array<IOptStatistics> = json.entries;
            console.log(statArr);
            defer.resolve(statArr);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
      return defer.promise;

    }

    private getCrHrInfo(url:string):ng.IPromise<any> {
      var defer = this.$q.defer();
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var CrHr:ICrHr = json.CrHr;
            defer.resolve(CrHr);
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

      this.httpClient.put(this.urlPlaceholderReplace(map["save_optional"]), complete_json, 'application/json')
          .success(() => {
            $.notific8("Successfully Saved");
          }).error((data) => {
          });
    }

    private fetchApplications():void {
      var url=this.urlPlaceholderReplace(map["fetch_applications"]);
      url=url.replace("{COURSE-ID}",this.$scope.optional.allStudentCourseId);

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var appliedStudentArr:Array<IOptStudent> = json.entries;
            for (var ind in appliedStudentArr) {
              var student:IOptStudent=appliedStudentArr[ind];
              student.newStatusId = -1;
          }
              this.$scope.optional.applicationStudents = appliedStudentArr;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

    }

  private resetChanges(student_or_course:any):void{
    student_or_course.newStatusId=-1;
  }

    private saveApplicationStatus():void{

      var complete_json = {};
      var approveStudentList=Array<IOptStudent>();
      var rejectStudentList=Array<IOptStudent>();
      var removeStudentList=Array<IOptStudent>();

      var statusChangedStudentList:Array<IOptStudent>=new Array<IOptStudent>();
     for(var ind in  this.$scope.optional.applicationStudents) {
       var student:IOptStudent = this.$scope.optional.applicationStudents[ind];
       if(student.statusId!=student.newStatusId && student.newStatusId!=-1){
         statusChangedStudentList.push(student);
       }
     }

      for(var ind in statusChangedStudentList){
        var student:IOptStudent=statusChangedStudentList[ind];
        if(student.newStatusId==Utils.SCODE_APPROVED){
          approveStudentList.push(student);
        }
        else if(student.newStatusId==Utils.SCODE_REJECTED  && student.applicationTypeId==Utils.SCODE_APPROVED){
          removeStudentList.push(student);
        }
        else if(student.newStatusId==Utils.SCODE_REJECTED){
          rejectStudentList.push(student);
        }
      }
      complete_json["approve"] =approveStudentList;
      complete_json["reject"] = rejectStudentList;
      complete_json["remove"] = removeStudentList;

      var url=this.urlPlaceholderReplace(map["save_application_status"]);
      url=url.replace("{COURSE-ID}",this.$scope.optional.allStudentCourseId);


      this.httpClient.put(url, complete_json, 'application/json')
          .success((data:NotifyMessage) => {
            this.notify.show(data);
          }).error((data) => {
          });

    }

    private showAppliedStudents(course_id) {
      //$('.nav-tabs li:eq(1) a').tab('show')
    }
    private showApplicationForStudent(student:IOptStudent):void {
        $('.nav-tabs li:eq(2) a').tab('show');
        this.$scope.optional.studentId=student.studentId;
        this.fetchApplicationForSingleStudent();
    }

    private fetchApplicationForSingleStudent():void {

      var url=this.urlPlaceholderReplace(map["fetch_applications_for_single_student"]);
      url=url.replace("{STUDENT-ID}",this.$scope.optional.studentId);

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var courseArr:Array<IOptCourse> = json.entries;
            for(var ind in courseArr){
              var course:IOptCourse=courseArr[ind];
              course.newStatusId=-1;
            }
            this.$scope.optional.appliedCoursesForSingleStudent = courseArr;
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

    }

    private saveApplicationStatusForSingleStudent():void{

      var complete_json = {};
      var approveCourseList=Array<IOptCourse>();
      var rejectCourseList=Array<IOptCourse>();
      var removeCourseList=Array<IOptCourse>();

      var statusChangedCourseList:Array<IOptCourse>=new Array<IOptCourse>();
      for(var ind in  this.$scope.optional.appliedCoursesForSingleStudent) {
        var course:IOptCourse = this.$scope.optional.appliedCoursesForSingleStudent[ind];
        if(course.statusId!=course.newStatusId && course.newStatusId!=-1){
          statusChangedCourseList.push(course);
        }
      }


      for(var ind in statusChangedCourseList){
        var course:IOptCourse=statusChangedCourseList[ind];
        if(course.newStatusId==Utils.SCODE_APPROVED){
          approveCourseList.push(course);
        }
        else if(course.newStatusId==Utils.SCODE_REJECTED  && course.applicationTypeId==Utils.SCODE_APPROVED){
          removeCourseList.push(course);
        }
        else if(course.newStatusId==Utils.SCODE_REJECTED ){
          rejectCourseList.push(course);
        }
      }
      complete_json["approve"] =approveCourseList;
      complete_json["reject"] = rejectCourseList;
      complete_json["remove"] = removeCourseList;

      var url=this.urlPlaceholderReplace(map["save_application_status_for_single_student"]);
      url=url.replace("{STUDENT-ID}",this.$scope.optional.semesterId);

      this.httpClient.put(url, complete_json, 'application/json')
          .success(() => {
            $.notific8("Successfully Saved");
          }).error((data) => {
          });

    }
    private fetchStudents(course_id:string,status_id:any,type:string):void {
      var url=this.urlPlaceholderReplace(map["fetch_students"]);
      url=url.replace("{COURSE-ID}",course_id);
      url=url.replace("{STATUS-ID}",status_id);
      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var studentArr:Array<IOptStudent> = eval(json.entries);
            this.$scope.optional[type] = studentArr;
            /*
            if(type=='rejectedStudents')
              //$.plugin_dragndrop('.dragndrop').duration(150);
            else if(type=='approvedStudents'){

            }*/
          /*
            if(this.$scope.optional.courseIdForRejectedStudents==this.$scope.optional.targetCourseIdForStudentShifting){
                alert("For shifting source and target course id should be different.");
                return;
            }*/

            $.plugin_dragndrop('.dragndrop').duration(150);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });
    }

    private  saveApplicationShifting():void {
      this.validateShifting();

      var complete_json = {};
      var shiftedStudentArray = [];
      $(".jqdragndrop-drop.D > .jqselection-selectable > .dragableObject").each(function() { shiftedStudentArray.push($(this).html()) });

      if(shiftedStudentArray.length==0) return;

      complete_json["students"] =shiftedStudentArray;

      var url=this.urlPlaceholderReplace(map["save_application_shifting"]);
      url=url.replace("{SOURCE-COURSE-ID}",this.$scope.optional.courseIdForRejectedStudents);
      url=url.replace("{TARGET-COURSE-ID}",this.$scope.optional.targetCourseIdForStudentShifting);


      this.httpClient.put(url, complete_json, 'application/json')
          .success(() => {
            $.notific8("Successfully Saved");
            $(".jqdragndrop-drop.D").html("");
          }).error((data) => {
          });
  }

    private validateShifting():boolean{
      var targetCourseStudentArray = [];
      $(".badge.badge-default").each(function() { targetCourseStudentArray.push($(this).html()) });

      var shiftedStudentArray = [];
      $(".jqdragndrop-drop.D > .jqselection-selectable > .dragableObject").each(function() { shiftedStudentArray.push($(this).html()) });


      var quotedP1 = '"' + targetCourseStudentArray.join('", "') + '"';
      var quotedP2 = '"' + shiftedStudentArray.join('", "') + '"';

      console.log(quotedP1);
      console.log(quotedP2);

      var duplicateStudentArr=this.intersect(targetCourseStudentArray,shiftedStudentArray);
      console.log(duplicateStudentArr);


      if(duplicateStudentArr.length>0){
        for(var ind in duplicateStudentArr){
          var studentId=duplicateStudentArr[ind];
            $("#"+studentId).css({'background-color': 'pink'});
        }

//        alert("We found some duplicate student in a same course. Duplicate students are not allowed. Press ok to return back the duplicate students.");

        for(var ind in duplicateStudentArr) {
          var studentId=duplicateStudentArr[ind];
          document.getElementById(studentId+'').style.backgroundColor = '';

          var cloneVersion = $("#"+studentId)[0].outerHTML;

          $("#"+studentId).remove();
          $('.jqdragndrop-drop.C').html($('.jqdragndrop-drop.C').html() +cloneVersion);

        }
        $.plugin_dragndrop('.dragndrop').duration(150);

      }
      return false;
    }



    private intersect(arr1, arr2):Array<number> {
      var results:Array<number> = new Array<number>();

      for (var i = 0; i < arr1.length; i++) {
        if (arr2.indexOf(arr1[i]) !== -1) {
          results.push(arr1[i]);
        }
      }
    return results;
  }

    private fetchSectionInformation(avc:number):void {

      var  courseId= $("#sectionCourseId").val();
      if(courseId=="") return;

      var url=this.urlPlaceholderReplace(map["section_nonAssignedStudents_for_course"]);
      url=url.replace("{COURSE-ID}",courseId);

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var nonAssignedStudentsArr:Array<IOptStudent> = json.entries;
            this.$scope.optional.nonAssignedStudents = nonAssignedStudentsArr;
            $.plugin_dragndrop('.dragndrop1').duration(150);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });

      url=this.urlPlaceholderReplace(map["sections_info_of_course"]);
      url=url.replace("{COURSE-ID}",courseId);

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json:any, etag:string) => {
            var sectionArr:Array<ISection> = json.entries;
            for(var ind in sectionArr){
              sectionArr[ind].index=parseInt(ind);
              sectionArr[ind].id= parseInt(ind);
              sectionArr[ind].type="saved";
            }
            this.$scope.optional.sections = sectionArr;
            $.plugin_dragndrop('.dragndrop1').duration(150);
          },
          (response:ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          });


    }

    private addNewSection():void {
      if($("#sectionCourseId").val()=="") return;

      var index = Utils.arrayMaxIndex(this.$scope.optional.sections);
      var item = this.addNewSectionRow(index);
      this.$scope.optional.sections.splice(0, 0, item);
      $.plugin_dragndrop('.dragndrop1').duration(150);
      $.plugin_dragndrop('.dragndrop2').duration(150);
    }


    private addNewSectionRow(index:number) {
      var sectionRow:ISection;
      sectionRow = {
        index: index,
        id: index,
        courseId: '',
        sectionName: '',
        type: 'new',
        students: Array<IOptStudent>()
      };
      return sectionRow;
    }

    private removeSection(index:number):void {

     var section_arr = this.$scope.optional.sections;
      var targetIndex = Utils.findIndex(section_arr, index.toString());
      //alert(targetIndex);
      var courseId = $("#sectionCourseId").val();

      var url=this.urlPlaceholderReplace(map["delete_section"]);
      url=url.replace("{COURSE-ID}",courseId);
      url=url.replace("{SECTION-NAME}",this.$scope.optional.sections[targetIndex].sectionName);


      if (this.$scope.optional.sections[targetIndex].type != "new") {
        this.httpClient.doDelete(url)
            .success(() => {
              $.notific8("Removed Section");
            }).error((data) => {
            });



      }
      this.$scope.optional.sections.splice(targetIndex, 1);


    }

    private saveSection(section:ISection):void {
      var courseId = $("#sectionCourseId").val();

      var url=this.urlPlaceholderReplace(map["delete_section"]);
      url=url.replace("{COURSE-ID}",courseId);
      url=url.replace("{SECTION-NAME}",section.sectionName);


      var sectionStudents = [];
      $("#section"+section.index+" > .jqselection-selectable > .dragableObject").each(function() { sectionStudents.push($(this).html()) });
      var students =  sectionStudents.join(',');
      //alert(students);
      var complete_json = {};
      complete_json["students"] =students;
      console.log(complete_json);

      this.httpClient.put(url, complete_json, 'application/json')
          .success(() => {
            //alert("ifti1");
          }).error((data) => {
          });
    }


private urlPlaceholderReplace(inputUrl:string):string{
  var url=inputUrl.replace("{SEMESTER-ID}",this.$scope.optional.semesterId);
  url=url.replace("{PROGRAM-ID}",this.$scope.optional.programId);
  url=url.replace("{YEAR}",this.$scope.optional.yearSemester.split(" - ")[0]);
  url=url.replace("{SEMESTER}",this.$scope.optional.yearSemester.split(" - ")[1]);
  return url;
}

  }
  UMS.controller('OptionalCoursesOffer', OptionalCoursesOffer);
}

