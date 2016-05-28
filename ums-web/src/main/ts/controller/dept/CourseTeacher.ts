///<reference path="../../service/HttpClient.ts"/>
///<reference path="../../lib/jquery.notific8.d.ts"/>
///<reference path="../../lib/bootstrap.selectpicker.d.ts"/>
///<reference path="../../model/CourseTeacherSearchParamModel.ts"/>
module ums {

  interface ICourseTeacherScope extends ng.IScope {
    submit: Function;
    courseTeacherSearchParamModel:CourseTeacherSearchParamModel;
    data:any;
    loadingVisibility:boolean;
    contentVisibility:boolean;
    fetchCourseTeacherInfo:Function;
    entries: IFormattedCourseTeacherMap;
    addTeacher: Function;
    editCourseTeacher: Function;
    removeCourseTeacher: Function;
    saveCourseTeacher: Function;
    programName: string;
    departmentName: string;
    semesterName: string;
    academicYear: string;
    academicSemester: string;
    courseCategory: string;
    isEmpty: Function;
  }

  interface ITeacher {
    id?: string;
    name?: string;
    sections?: Array<string>;
    selectedSections?: Array<{id: string; name: string; uniqueId: string}>;
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
    editMode: boolean;
    updated: boolean;
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
    id?: string;
  }

  export class CourseTeacher {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify'];
    private teachersList: ITeachersMap;
    private formattedMap: IFormattedCourseTeacherMap;
    private savedCopy: IFormattedCourseTeacherMap;

    private newTeacherId: number = 0;

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $scope: ICourseTeacherScope, private $q: ng.IQService,
                private notify: Notify) {
      $scope.courseTeacherSearchParamModel = new CourseTeacherSearchParamModel(this.appConstants, this.httpClient);
      $scope.data = {
        courseCategoryOptions: appConstants.courseCategory,
        academicYearOptions: appConstants.academicYear,
        academicSemesterOptions: appConstants.academicSemester
      };

      $scope.loadingVisibility = false;
      $scope.contentVisibility = false;
      $scope.fetchCourseTeacherInfo = this.fetchCourseTeacherInfo.bind(this);
      $scope.addTeacher = this.addTeacher.bind(this);
      $scope.editCourseTeacher = this.editCourseTeacher.bind(this);
      $scope.removeCourseTeacher = this.removeCourseTeacher.bind(this);
      $scope.saveCourseTeacher = this.saveCourseTeacher.bind(this);
      $scope.isEmpty = UmsUtil.isEmpty;

      this.teachersList = {};
      this.formattedMap = {};

      //this.fetchCourseTeacherInfo();
    }


    private fetchCourseTeacherInfo(): void {
      $("#leftDiv").hide();
      $("#arrowDiv").show();

      $("#rightDiv").removeClass("orgRightClass");
      $("#rightDiv").addClass("newRightClass");


      this.$scope.loadingVisibility = true;
      this.$scope.contentVisibility = false;

      if (UmsUtil.isEmptyString(this.$scope.courseTeacherSearchParamModel.courseId)) {
        this.renderHeader();
        this.formattedMap = {};
      }

      var fetchUri: string = this.uriBuilder(this.$scope.courseTeacherSearchParamModel);

      this.httpClient.get(fetchUri,
          this.appConstants.mimeTypeJson,
          (data: ICourseTeachers, etag: string)=> {
            if (!UmsUtil.isEmptyString(this.$scope.courseTeacherSearchParamModel.courseId)) {
              this.formattedMap[this.$scope.courseTeacherSearchParamModel.courseId].updated = true;
              delete this.$scope.courseTeacherSearchParamModel['courseId'];
            }
            this.formatCourseTeacher(data.entries);
            this.$scope.loadingVisibility = false;
            this.$scope.contentVisibility = true;
          });
    }


    private formatCourseTeacher(courseTeachers: Array<ICourseTeacher>): void {
      for (var i = 0; i < courseTeachers.length; i++) {
        if (!this.formattedMap[courseTeachers[i].courseId] || this.formattedMap[courseTeachers[i].courseId].updated) {
          this.formattedMap[courseTeachers[i].courseId] = courseTeachers[i];
          this.formattedMap[courseTeachers[i].courseId].selectedTeachers = {};
          this.formattedMap[courseTeachers[i].courseId].editMode = false;
          this.formattedMap[courseTeachers[i].courseId].updated = false;
        }
        if (courseTeachers[i].teacherId) {
          var teacher: ITeacher = {
            id: courseTeachers[i].teacherId,
            name: courseTeachers[i].teacherName,
            sections: [],
            selectedSections: []
          };
          if (!this.formattedMap[courseTeachers[i].courseId].selectedTeachers[courseTeachers[i].teacherId]) {
            this.formattedMap[courseTeachers[i].courseId].selectedTeachers[courseTeachers[i].teacherId] = teacher;
          }
          var section = {
            id: courseTeachers[i].section,
            name: courseTeachers[i].section,
            uniqueId: courseTeachers[i].id
          };
          this.formattedMap[courseTeachers[i].courseId].selectedTeachers[courseTeachers[i].teacherId].selectedSections.push(section);
          this.formattedMap[courseTeachers[i].courseId].selectedTeachers[courseTeachers[i].teacherId].sections.push(section.id);

        }

      }
      //save the fetched copy, later on it will be used to decided whats are values has been created/update/removed
      this.savedCopy = $.extend(true, {}, this.formattedMap);
      this.$scope.entries = this.formattedMap;
    }

    private populateTeachers(courseId: string): void {
      if (this.$scope.entries.hasOwnProperty(courseId)) {
        this.getTeachers(this.$scope.entries[courseId]).then(()=> {
          //do nothing

        });
      }
    }

    private getTeachers(courseTeacher: ICourseTeacher): ng.IPromise<any> {
      var defer = this.$q.defer();

      var sectionArray = [];
      sectionArray.push.apply(sectionArray, this.appConstants.theorySections);
      sectionArray.push.apply(sectionArray, this.appConstants.sessionalSections);

      courseTeacher.sections = sectionArray;

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

    private addTeacher(courseId: string): void {
      this.populateTeachers(courseId);
      this.$scope.entries[courseId].editMode = true;
      this.newTeacherId = this.newTeacherId - 1;
      this.formattedMap[courseId].selectedTeachers[this.newTeacherId] = {};
      this.formattedMap[courseId].selectedTeachers[this.newTeacherId].id = this.newTeacherId + "";
    }

    private editCourseTeacher(courseId: string): void {
      this.populateTeachers(courseId);
      this.$scope.entries[courseId].editMode = true;
    }

    private removeCourseTeacher(courseId: string, teacherId: string): void {
      console.debug(teacherId);
      if (this.formattedMap[courseId].selectedTeachers[teacherId]) {
        delete this.formattedMap[courseId].selectedTeachers[teacherId];
      } else {
        for (var teacher in this.formattedMap[courseId].selectedTeachers) {
          if (this.formattedMap[courseId].selectedTeachers.hasOwnProperty(teacher)) {
            if (this.formattedMap[courseId].selectedTeachers[teacher].id == teacherId) {
              delete this.formattedMap[courseId].selectedTeachers[teacher];
            }
          }
        }
      }
    }

    private saveCourseTeacher(courseId: string): void {

      //initialize what needs to be posted
      var savedCourseTeacher: IPostCourseTeacherEntries = {};
      savedCourseTeacher.entries = [];

      var saved: ICourseTeacher = this.savedCopy[courseId];
      var modified: ICourseTeacher = this.formattedMap[courseId];

      if (!this.validate(modified, saved)) {
        return;
      }
      for (var teacherId in saved.selectedTeachers) {
        if (saved.selectedTeachers.hasOwnProperty(teacherId)) {
          if (!modified.selectedTeachers.hasOwnProperty(teacherId)) {
            var selectedSections = saved.selectedTeachers[teacherId].selectedSections;
            for (var i = 0; i < selectedSections.length; i++) {
              savedCourseTeacher.entries.push({
                id: selectedSections[i].uniqueId,
                courseId: courseId,
                semesterId: this.$scope.courseTeacherSearchParamModel.semesterId,
                teacherId: teacherId,
                updateType: 'delete'
              });
            }

          } else {
            var modifiedTeacher: ITeacher = modified.selectedTeachers[teacherId];
            var savedTeacher: ITeacher = saved.selectedTeachers[teacherId];

            if (teacherId != modifiedTeacher.id) {
              var selectedSections = saved.selectedTeachers[teacherId].selectedSections;
              for (var i = 0; i < selectedSections.length; i++) {
                savedCourseTeacher.entries.push({
                  id: selectedSections[i].uniqueId,
                  courseId: courseId,
                  semesterId: this.$scope.courseTeacherSearchParamModel.semesterId,
                  teacherId: teacherId,
                  updateType: 'delete'
                });
              }
            }
            for (var i = 0; i < savedTeacher.selectedSections.length; i++) {
              var sectionFound: boolean = false;
              for (var j = 0; j < modifiedTeacher.sections.length; j++) {
                if (savedTeacher.selectedSections[i].id == modifiedTeacher.sections[j]) {
                  sectionFound = true;
                }
              }

              if (!sectionFound) {
                savedCourseTeacher.entries.push({
                  id: savedTeacher.selectedSections[i].uniqueId,
                  courseId: courseId,
                  semesterId: this.$scope.courseTeacherSearchParamModel.semesterId,
                  teacherId: teacherId,
                  section: '',
                  updateType: 'delete'
                });
              }
            }
          }
        }
      }

      for (var teacherId in modified.selectedTeachers) {
        if (modified.selectedTeachers.hasOwnProperty(teacherId)) {
          if (!saved.selectedTeachers.hasOwnProperty(teacherId)) {
            var modifiedSelectedSections: Array<string> = modified.selectedTeachers[teacherId].sections;
            for (var i = 0; i < modifiedSelectedSections.length; i++) {
              savedCourseTeacher.entries.push({
                courseId: courseId,
                semesterId: this.$scope.courseTeacherSearchParamModel.semesterId,
                teacherId: modified.selectedTeachers[teacherId].id,
                updateType: 'insert',
                section: modifiedSelectedSections[i]
              });
            }

          } else {
            var modifiedTeacher: ITeacher = modified.selectedTeachers[teacherId];
            var savedTeacher: ITeacher = saved.selectedTeachers[teacherId];

            if (teacherId != modifiedTeacher.id) {
              var modifiedSelectedSections: Array<string> = modified.selectedTeachers[teacherId].sections;
              for (var i = 0; i < modifiedSelectedSections.length; i++) {
                savedCourseTeacher.entries.push({
                  courseId: courseId,
                  semesterId: this.$scope.courseTeacherSearchParamModel.semesterId,
                  teacherId: modifiedTeacher.id,
                  updateType: 'insert',
                  section: modifiedSelectedSections[i]
                });
              }
            }

            for (var i = 0; i < modifiedTeacher.sections.length; i++) {
              var sectionFound: boolean = false;
              for (var j = 0; j < savedTeacher.selectedSections.length; j++) {
                if (modifiedTeacher.sections[i] == savedTeacher.selectedSections[j].id) {
                  sectionFound = true;
                }
              }
              if (!sectionFound) {
                savedCourseTeacher.entries.push({
                  courseId: courseId,
                  semesterId: this.$scope.courseTeacherSearchParamModel.semesterId,
                  teacherId: teacherId,
                  section: modifiedTeacher.sections[i],
                  updateType: 'insert'
                });
              }
            }
          }
        }
      }

      this.httpClient.post('academic/courseTeacher/', savedCourseTeacher, 'application/json')
          .success(() => {
            this.$scope.courseTeacherSearchParamModel.courseId = courseId;
            this.fetchCourseTeacherInfo();
          }).error((error) => {
            console.error(error);
          });
    }

    private renderHeader(): void {
      for (var i = 0; i < this.$scope.courseTeacherSearchParamModel.programSelector.getPrograms().length; i++) {
        if (this.$scope.courseTeacherSearchParamModel.programSelector.getPrograms()[i].id == this.$scope.courseTeacherSearchParamModel.programSelector.programId) {
          this.$scope.programName = this.$scope.courseTeacherSearchParamModel.programSelector.getPrograms()[i].longName;
        }
      }

      for (var i = 0; i < this.$scope.courseTeacherSearchParamModel.programSelector.getSemesters().length; i++) {
        if (this.$scope.courseTeacherSearchParamModel.programSelector.getSemesters()[i].id == this.$scope.courseTeacherSearchParamModel.semesterId) {
          this.$scope.semesterName = this.$scope.courseTeacherSearchParamModel.programSelector.getSemesters()[i].name;
        }
      }

      for (var i = 0; i < this.$scope.courseTeacherSearchParamModel.programSelector.getDepartments().length; i++) {
        if (this.$scope.courseTeacherSearchParamModel.programSelector.getDepartments()[i].id == this.$scope.courseTeacherSearchParamModel.programSelector.departmentId) {
          this.$scope.departmentName = this.$scope.courseTeacherSearchParamModel.programSelector.getDepartments()[i].name;
        }
      }

      for (var i = 0; i < this.$scope.data.academicYearOptions.length; i++) {
        if (this.$scope.data.academicYearOptions[i].id == this.$scope.courseTeacherSearchParamModel.academicYearId) {
          this.$scope.academicYear = this.$scope.data.academicYearOptions[i].name.indexOf('Select') == 0 ? "" : this.$scope.data.academicYearOptions[i].name;
        }
      }

      for (var i = 0; i < this.$scope.data.academicSemesterOptions.length; i++) {
        if (this.$scope.data.academicSemesterOptions[i].id == this.$scope.courseTeacherSearchParamModel.academicSemesterId) {
          this.$scope.academicSemester = this.$scope.data.academicSemesterOptions[i].name.indexOf('Select') == 0 ? "" : this.$scope.data.academicSemesterOptions[i].name;
        }
      }

      for (var i = 0; i < this.$scope.data.courseCategoryOptions.length; i++) {
        if (this.$scope.data.courseCategoryOptions[i].id == this.$scope.courseTeacherSearchParamModel.courseCategoryId) {
          this.$scope.courseCategory = this.$scope.data.courseCategoryOptions[i].name.indexOf('Select') == 0 ? "" : this.$scope.data.courseCategoryOptions[i].name;
        }
      }
    }

    private validate(modifiedVal: ICourseTeacher, saved: ICourseTeacher): boolean {
      if (UmsUtil.isEmpty(modifiedVal.selectedTeachers)) {
        if(UmsUtil.isEmpty(saved.selectedTeachers)) {
          this.notify.warn("Please select teacher/s");
          return false;
        }else {
          return true;
        }
      }

      for (var key in modifiedVal.selectedTeachers) {
        if (modifiedVal.selectedTeachers.hasOwnProperty(key)) {
          if (key < 0 && modifiedVal.selectedTeachers[key].id == null) {
            this.notify.warn("Please select teacher/s");
            return false;
          } else {
            var selectedSections = modifiedVal.selectedTeachers[key].sections;
            if (!selectedSections || selectedSections.length == 0) {
              this.notify.warn("Please select section/s");
              return false;
            }
          }
        }
      }

      return true;
    }

    private uriBuilder(param: CourseTeacherSearchParamModel): string {
      /*var fetchUri: string = "academic/courseTeacher/programId/" + '110500'
       + "/semesterId/" + '11012015' + '/year/1';*/
      var fetchUri = "academic/courseTeacher/programId/" + param.programSelector.programId + "/semesterId/" + param.semesterId;

      if (!UmsUtil.isEmptyString(param.courseId)) {
        fetchUri = fetchUri + "/courseId/" + param.courseId;
        return fetchUri;
      }

      if (!UmsUtil.isEmptyString(param.academicYearId)) {
        fetchUri = fetchUri + "/year/" + param.academicYearId;
      }
      if (!UmsUtil.isEmptyString(param.academicSemesterId)) {
        fetchUri = fetchUri + "/semester/" + param.academicSemesterId;
      }
      if (!UmsUtil.isEmptyString(param.courseCategoryId)) {
        fetchUri = fetchUri + "/category/" + param.courseCategoryId;
      }

      return fetchUri;
    }
  }

  UMS.controller('CourseTeacher', CourseTeacher);
}

