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
  }

  interface ITeacher {
    id: string;
    name: string;
    sections?: Array<{id: string; name: string}>;
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
    sections:Array<{id: string; name: string}>;
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


  interface  IFormattedCourseTeacherMap {
    [courseId: string]: ICourseTeacher;
  }

  export class CourseTeacher {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q'];
    private teachersList: ITeachersMap;
    private formattedMap: IFormattedCourseTeacherMap;

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $scope: ICourseTeacherScope, private $q: ng.IQService) {
      $scope.courseTeacherSearchParamModel = new CourseTeacherSearchParamModelImp(this.appConstants, this.httpClient);
      $scope.data = {
        courseCategoryOptions: appConstants.courseCategory,
        academicYearOptions: appConstants.academicYear,
        academicSemesterOptions: appConstants.academicSemester
      };

      $scope.loadingVisibility=false;
      $scope.fetchCourseTeacherInfo = this.fetchCourseTeacherInfo.bind(this);
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
          (data: ICourseTeachers, etag: string)=> {
            this.formatCourseTeacher(data.entries);
            this.populateTeachers(this.$scope.entries);
            //this.$scope.entries = data.entries;
            this.$scope.loadingVisibility = false;
          });
    }

    private formatCourseTeacher(courseTeachers: Array<ICourseTeacher>): void {
      for (var i = 0; i < courseTeachers.length; i++) {
        if (!this.formattedMap[courseTeachers[i].courseId]) {
          this.formattedMap[courseTeachers[i].courseId] = courseTeachers[i];
          this.formattedMap[courseTeachers[i].courseId].selectedTeachers = {};
        }
        if (courseTeachers[i].teacherId) {
          var teacher: ITeacher = {
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
      console.debug("%o",this.formattedMap);
      this.$scope.entries = this.formattedMap;
    }

    private populateTeachers(courseTeachers: IFormattedCourseTeacherMap): void {
      for (var courseId in courseTeachers) {
        this.getTeachers(courseTeachers[courseId]).then(()=> {

        });
      }
    }

    private getTeachers(courseTeacher: ICourseTeacher): ng.IPromise<any> {
      var defer = this.$q.defer();
      courseTeacher.sections = this.appConstants.theorySections;
      if (this.teachersList[courseTeacher.courseOfferedByDepartmentId]) {
        courseTeacher.teachers = this.teachersList[courseTeacher.courseOfferedByDepartmentId];
        defer.resolve(null);
      } else {
        this.httpClient.get("academic/teacher/department/" + courseTeacher.courseOfferedByDepartmentId, this.appConstants.mimeTypeJson,
            (data: ITeachers, etag: string) => {
              this.teachersList[courseTeacher.courseOfferedByDepartmentId] = data.entries;
              courseTeacher.teachers = this.teachersList[courseTeacher.courseOfferedByDepartmentId];
              defer.resolve(null);
            });
      }

      return defer.promise;
    }
  }
  UMS.controller('CourseTeacher', CourseTeacher);
}

