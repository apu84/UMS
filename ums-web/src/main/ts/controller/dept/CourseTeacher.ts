///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/bootstrap.selectpicker.d.ts"/>
///<reference path="../../model/CourseTeacherSearchParamModel.ts"/>
///<reference path="../../model/CourseTeacherSearchParamModelImp.ts"/>
module ums {

  interface ICourseTeacherScope extends ng.IScope {
    submit: Function;
    courseTeacherSearchParamModel:CourseTeacherSearchParamModelImp;
    data:any;
    loadingVisibility:boolean;
    fetchCourseTeacherInfo:Function;
    entries: IFormattedCourseTeacherMap;
    addTeacher: Function;
    editCourseTeacher: Function;
    removeCourseTeacher: Function;
  }

  interface ITeacher {
    id?: string;
    name?: string;
    sections?: Array<string>;
  }

  interface ICourseTeacher {
    id: string;
    courseId: string;
    courseNo: string;
    courseTitle: string;
    courseCrHr: string;
    year: string;
    semester: string;
    syllabusId: string;
    teacherId: string;
    teacherName: string;
    section: string;
    courseOfferedByDepartmentId: string;
    courseOfferedByDepartmentName: string;
    teachers: Array<ITeacher>;
    selectedTeachers: {[key: string]: ITeacher};
    sections:Array<{section:string; uniqueId: string}>;
    editMode: boolean;
  }

  interface ICourseTeachers {
    entries : Array<ICourseTeacher>;
  }

  interface ITeachersMap {
    [key: string]: Array<ITeacher>;
  }

  interface ITeachers {
    entries : Array<ITeacher>;
  }

  interface IFormattedCourseTeacherMap {
    [courseId: string]: ICourseTeacher;
  }

  interface IPostCourseTeacherEntries {
    entries? : Array<IPostCourseTeacherModel>;
  }

  interface IPostCourseTeacherModel {
    semesterId: string;
    courseId: string;
    teacherId: string;
    section?: string;
    updateType: string;
  }

  export class CourseTeacher {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q'];
    private teachersList:ITeachersMap;
    private formattedMap:IFormattedCourseTeacherMap;
    private savedCopy:IFormattedCourseTeacherMap;

    private newTeacherId:number = 0;

    constructor(private appConstants:any, private httpClient:HttpClient,
                private $scope:ICourseTeacherScope, private $q:ng.IQService) {
      $scope.courseTeacherSearchParamModel = new CourseTeacherSearchParamModelImp(this.appConstants, this.httpClient);
      $scope.data = {
        courseCategoryOptions: appConstants.courseCategory,
        academicYearOptions: appConstants.academicYear,
        academicSemesterOptions: appConstants.academicSemester
      };

      $scope.loadingVisibility = false;
      $scope.fetchCourseTeacherInfo = this.fetchCourseTeacherInfo.bind(this);
      $scope.addTeacher = this.addTeacher.bind(this);
      $scope.editCourseTeacher = this.editCourserTeacher.bind(this);
      $scope.removeCourseTeacher = this.removeCourseTeacher.bind(this);

      $('.selectpicker').selectpicker({
        iconBase: 'fa',
        tickIcon: 'fa-check'
      });

      this.teachersList = {};
      this.formattedMap = {};
    }


    private fetchCourseTeacherInfo():void {
      $("#leftDiv").hide();
      $("#arrowDiv").show();

      $("#rightDiv").removeClass("orgRightClass");
      $("#rightDiv").addClass("newRightClass");

      this.$scope.loadingVisibility = true;
      this.formattedMap = {};
      this.httpClient.get("academic/courseTeacher/programId/" + this.$scope.courseTeacherSearchParamModel.programId
          + "/semesterId/" + this.$scope.courseTeacherSearchParamModel.semesterId + "/year/"
          + this.$scope.courseTeacherSearchParamModel.academicYearId + "/semester/" + this.$scope.courseTeacherSearchParamModel.academicSemesterId,
          this.appConstants.mimeTypeJson,
          (data:ICourseTeachers, etag:string)=> {
            this.formatCourseTeacher(data.entries);
            this.populateTeachers(this.$scope.entries);
            //this.$scope.entries = data.entries;
            this.$scope.loadingVisibility = false;
          });
    }

    private formatCourseTeacher(courseTeachers:Array<ICourseTeacher>):void {
      for (var i = 0; i < courseTeachers.length; i++) {
        if (!this.formattedMap[courseTeachers[i].courseId]) {
          this.formattedMap[courseTeachers[i].courseId].editMode = false;
          this.formattedMap[courseTeachers[i].courseId] = courseTeachers[i];
          this.formattedMap[courseTeachers[i].courseId].selectedTeachers = {};
        }
        if (courseTeachers[i].teacherId) {
          var teacher:ITeacher = {
            id: courseTeachers[i].teacherId,
            name: courseTeachers[i].teacherName,
            sections: []
          };
          if (!this.formattedMap[courseTeachers[i].courseId].selectedTeachers[courseTeachers[i].teacherId]) {
            this.formattedMap[courseTeachers[i].courseId].selectedTeachers[courseTeachers[i].teacherId] = teacher;
          }

          this.formattedMap[courseTeachers[i].courseId].selectedTeachers[courseTeachers[i].teacherId].sections.push(courseTeachers[i].section);

        }

      }
      console.debug("%o", this.formattedMap);
      //save the fetched copy, later on it will be used to decided whats are values has been created/update/removed
      this.savedCopy = $.extend(true, {}, this.formattedMap);
      console.debug("Saved copy %o", this.formattedMap);

      this.$scope.entries = this.formattedMap;
    }

    private populateTeachers(courseTeachers:IFormattedCourseTeacherMap):void {
      for (var courseId in courseTeachers) {
        if (courseTeachers.hasOwnProperty(courseId)) {
          this.getTeachers(courseTeachers[courseId]).then(()=> {
            //do nothing
          });
        }
      }
    }

    private getTeachers(courseTeacher:ICourseTeacher):ng.IPromise<any> {
      var defer = this.$q.defer();
      courseTeacher.sections = this.appConstants.theorySections;
      if (this.teachersList[courseTeacher.courseOfferedByDepartmentId]) {
        courseTeacher.teachers = this.teachersList[courseTeacher.courseOfferedByDepartmentId];
        defer.resolve(null);
      } else {
        this.httpClient.get("academic/teacher/department/" + courseTeacher.courseOfferedByDepartmentId, this.appConstants.mimeTypeJson,
            (data:ITeachers, etag:string) => {
              this.teachersList[courseTeacher.courseOfferedByDepartmentId] = data.entries;
              courseTeacher.teachers = this.teachersList[courseTeacher.courseOfferedByDepartmentId];
              defer.resolve(null);
            });
      }

      return defer.promise;
    }

    private addTeacher(courseId:string):void {
      this.newTeacherId = this.newTeacherId - 1;
      this.formattedMap[courseId].selectedTeachers[this.newTeacherId] = {id: this.newTeacherId + ""};
    }

    private editCourserTeacher(courseId:string):void {
      this.formattedMap[courseId].editMode = true;
    }

    private removeCourseTeacher(courseId:string, teacherId:string):void {
      delete this.formattedMap[courseId].selectedTeachers[teacherId];
    }

    private saveCourseTeacher(courseId:string):void {
      //initialize what needs to be posted
      var savedCourseTeacher:IPostCourseTeacherEntries = {};
      savedCourseTeacher.entries = [];

      var saved:ICourseTeacher = this.savedCopy[courseId];
      var modified:ICourseTeacher = this.formattedMap[courseId];

      for (var teacherId in saved.selectedTeachers) {
        if (saved.selectedTeachers.hasOwnProperty(teacherId)) {
          if (!modified.selectedTeachers.hasOwnProperty(teacherId)) {
            savedCourseTeacher.entries.push({
              courseId: courseId,
              semesterId: this.$scope.courseTeacherSearchParamModel.semesterId,
              teacherId: teacherId,
              updateType: 'delete'
            });
          } else {
            var modifiedTeacher:ITeacher = modified.selectedTeachers[teacherId];
            var savedTeacher:ITeacher = saved.selectedTeachers[teacherId];
            var sectionFound:boolean = false;
            for (var i = 0; i < savedTeacher.sections.length; i++) {
              for (var j = 0; j < modifiedTeacher.sections.length; j++) {
              }
              if (savedTeacher.sections[i] == modifiedTeacher.sections[i]) {
                sectionFound = true;
              }
            }
            if (!sectionFound) {
              savedCourseTeacher.entries.push({
                courseId: courseId,
                semesterId: this.$scope.courseTeacherSearchParamModel.semesterId,
                teacherId: teacherId,
                section: '',
                updateType: 'delete',
                id: savedTeacher.id
              });
            }

          }
        }
      }
    }
  }
  UMS.controller('CourseTeacher', CourseTeacher);
}

