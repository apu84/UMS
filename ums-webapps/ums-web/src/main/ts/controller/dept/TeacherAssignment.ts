module ums {
  export interface ITeacherAssignmentScope extends ng.IScope {
    submit: Function;
    teacherSearchParamModel: TeacherAssignmentSearchParamModel;
    data:any;
    loadingVisibility:boolean;
    contentVisibility:boolean;
    fetchTeacherInfo:Function;
    entries: IFormattedTeacherMap<IAssignedTeacher>;
    addTeacher: Function;
    editTeacher: Function;
    removeTeacher: Function;
    saveTeacher: Function;
    saveTeachers: Function;
    programName: string;
    departmentName: string;
    semesterName: string;
    academicYear: string;
    academicSemester: string;
    courseCategory: string;
    isEmpty: Function;
    showRightSide: boolean;
    showSaveButton: boolean;
  }

  export interface IAssignedTeacher {
    id: string;
    courseType:number;
    courseId: string;
    courseNo: string;
    courseTitle: string;
    courseCrHr: string;
    year: string;
    semester: string;
    syllabusId: string;
    courseOfferedByDepartmentId: string;
    courseOfferedByDepartmentName: string;
    courseOfferedToDepartmentId: string;
    courseOfferedToDepartmentName: string;
    teachers: Array<ITeacher>;

    editMode: boolean;
    updated: boolean;
    modified: boolean;
  }

  interface IAssignedTeachers {
    entries : Array<IAssignedTeacher>;
  }

  interface ITeachersMap {
    [key: string]: ITeachers;
  }

  interface IFormattedTeacherMap<T> {
    [courseId: string]: T;
  }

  export interface IPostTeacherEntries {
    entries? : Array<IPostAssignedTeacherModel>;
  }

  export interface IPostAssignedTeacherModel {
    semesterId: string;
    courseId: string;
    teacherId: string;
    updateType: string;
    id?: string;
  }

  export class TeacherAssignment<T extends IAssignedTeacher> {
    public teachersList: ITeachersMap;
    public formattedMap: IFormattedTeacherMap<T>;
    public savedCopy: IFormattedTeacherMap<T>;

    constructor(public appConstants: any,
                public httpClient: HttpClient,
                public $scope: ITeacherAssignmentScope,
                public $q: ng.IQService,
                public notify: Notify,
                public teacherService: TeacherService) {
      $scope.teacherSearchParamModel = new TeacherAssignmentSearchParamModel(this.appConstants, this.httpClient, true);
      $scope.teacherSearchParamModel.programSelector.setProgramType(this.appConstants.programTypeEnum.UG,
          FieldViewTypes.selected);

      $scope.data = {
        courseCategoryOptions: appConstants.courseCategory,
        academicYearOptions: appConstants.academicYear,
        academicSemesterOptions: appConstants.academicSemester
      };

      $scope.loadingVisibility = false;
      $scope.contentVisibility = false;
      $scope.fetchTeacherInfo = this.fetchTeacherInfo.bind(this);
      $scope.addTeacher = this.addTeacher.bind(this);
      $scope.editTeacher = this.editTeacher.bind(this);
      $scope.removeTeacher = this.removeTeacher.bind(this);
      $scope.saveTeacher = this.saveTeacher.bind(this);
      $scope.saveTeachers = this.saveTeachers.bind(this);
      $scope.isEmpty = UmsUtil.isEmpty;

      this.teachersList = {};
      this.formattedMap = {};

      $scope.showRightSide = false;
      $scope.showSaveButton = false;
      // this.fetchTeacherInfo();
    }


    public fetchTeacherInfo(): void {
      $("#leftDiv").hide(100);
      $("#arrowDiv").show(50);

      this.$scope.loadingVisibility = true;
      this.$scope.contentVisibility = false;

      if (UmsUtil.isEmptyString(this.$scope.teacherSearchParamModel.courseId)) {
        this.renderHeader();
        this.formattedMap = {};
      }

      var fetchUri: string = this.uriBuilder(this.$scope.teacherSearchParamModel);

      this.httpClient.get(fetchUri,
          this.appConstants.mimeTypeJson,
          (data: IAssignedTeachers, etag: string)=> {
            if (!UmsUtil.isEmptyString(this.$scope.teacherSearchParamModel.courseId)) {
              this.formattedMap[this.$scope.teacherSearchParamModel.courseId].updated = true;
              this.formattedMap[this.$scope.teacherSearchParamModel.courseId].modified = false;
              this.formatTeacher(data.entries, this.$scope.teacherSearchParamModel['courseId']);
              delete this.$scope.teacherSearchParamModel['courseId'];
            } else {
              this.formatTeacher(data.entries);
            }
            this.$scope.showSaveButton = this.saveButtonVisibility();
            this.$scope.loadingVisibility = false;
            this.$scope.contentVisibility = true;
          });

      this.$scope.showRightSide = true;
    }

    //override
    public formatTeacher(teachers: Array<IAssignedTeacher>, courseId?: string): void {
      throw new Error("Method not implemented");
    }


    public populateTeachers(courseId: string): void {
      if (this.$scope.entries.hasOwnProperty(courseId)) {
        this.getTeachers(this.$scope.entries[courseId]);
      }
    }

    private getTeachers(assignedTeacher: IAssignedTeacher): void {

      this.decorateTeacher(assignedTeacher);
      assignedTeacher.teachers = [];

      if (!this.teachersList[assignedTeacher.courseOfferedByDepartmentId]) {
        this.listTeachers(assignedTeacher.courseOfferedByDepartmentId)
            .then((teachers: ITeachers) => {
              assignedTeacher.teachers.push.apply(assignedTeacher.teachers, teachers.entries);
              this.teachersList[assignedTeacher.courseOfferedByDepartmentId] = teachers;
            });
      }
      else {
        assignedTeacher.teachers = this.teachersList[assignedTeacher.courseOfferedByDepartmentId].entries;
      }

      if (assignedTeacher.courseOfferedByDepartmentId != assignedTeacher.courseOfferedToDepartmentId) {
        if (!this.teachersList[assignedTeacher.courseOfferedToDepartmentId]) {
          this.listTeachers(assignedTeacher.courseOfferedToDepartmentId)
              .then((teachers: ITeachers) => {
                assignedTeacher.teachers.push.apply(assignedTeacher.teachers, teachers.entries);
                this.teachersList[assignedTeacher.courseOfferedToDepartmentId] = teachers;
              });
        }
        else {
          assignedTeacher.teachers = this.teachersList[assignedTeacher.courseOfferedToDepartmentId].entries;
        }
      }
    }

    private listTeachers(departmentId: string): ng.IPromise<ITeachers> {
      if (this.teachersList[departmentId]) {
        return this.$q.when(this.teachersList[departmentId]);
      }
      else {
        var defer = this.$q.defer();
        this.teacherService
            .getByDepartment(departmentId)
            .then((response: ITeachers) => {
              defer.resolve(response);
            });
        return defer.promise;
      }
    }

    public decorateTeacher(assignedTeacher: IAssignedTeacher): void {
      throw new Error("Method not implemented");
    }

    public addTeacher(courseId: string): void {
      throw new Error("Method not implemented");
    }

    public editTeacher(courseId: string): void {
      throw new Error("Method not implemented");
    }

    public removeTeacher(courseId: string, teacherId: string): void {
      throw new Error("Method not implemented");
    }

    public saveTeacher(courseId: string): void {
      throw new Error('Method not implemented');
    }

    public saveTeachers(): void {
      throw new Error('Method not implemented');
    }

    public postTeacher(save: IPostExaminerEntries): ng.IHttpPromise<any> {
      return this.httpClient.post(this.getPostUri(), save, 'application/json');
    }

    private renderHeader(): void {
      for (var i = 0; i < this.$scope.teacherSearchParamModel.programSelector.getPrograms().length; i++) {
        if (this.$scope.teacherSearchParamModel.programSelector.getPrograms()[i].id == this.$scope.teacherSearchParamModel.programSelector.programId) {
          this.$scope.programName = this.$scope.teacherSearchParamModel.programSelector.getPrograms()[i].shortName;
        }
      }

      for (var i = 0; i < this.$scope.teacherSearchParamModel.programSelector.getSemesters().length; i++) {
        if (this.$scope.teacherSearchParamModel.programSelector.getSemesters()[i].id == this.$scope.teacherSearchParamModel.programSelector.semesterId) {
          this.$scope.semesterName = this.$scope.teacherSearchParamModel.programSelector.getSemesters()[i].name;
        }
      }

      for (var i = 0; i < this.$scope.teacherSearchParamModel.programSelector.getDepartments().length; i++) {
        if (this.$scope.teacherSearchParamModel.programSelector.getDepartments()[i].id == this.$scope.teacherSearchParamModel.programSelector.departmentId) {
          this.$scope.departmentName = this.$scope.teacherSearchParamModel.programSelector.getDepartments()[i].name;
        }
      }

      for (var i = 0; i < this.$scope.data.academicYearOptions.length; i++) {
        if (this.$scope.data.academicYearOptions[i].id == this.$scope.teacherSearchParamModel.academicYearId) {
          this.$scope.academicYear = this.$scope.data.academicYearOptions[i].name.indexOf('Select') == 0 ? "" : this.$scope.data.academicYearOptions[i].name;
        }
      }

      for (var i = 0; i < this.$scope.data.academicSemesterOptions.length; i++) {
        if (this.$scope.data.academicSemesterOptions[i].id == this.$scope.teacherSearchParamModel.academicSemesterId) {
          this.$scope.academicSemester = this.$scope.data.academicSemesterOptions[i].name.indexOf('Select') == 0 ? "" : this.$scope.data.academicSemesterOptions[i].name;
        }
      }

      for (var i = 0; i < this.$scope.data.courseCategoryOptions.length; i++) {
        if (this.$scope.data.courseCategoryOptions[i].id == this.$scope.teacherSearchParamModel.courseCategoryId) {
          this.$scope.courseCategory = this.$scope.data.courseCategoryOptions[i].name.indexOf('Select') == 0 ? "All" : this.$scope.data.courseCategoryOptions[i].name;
        }
      }
    }

    public validate(modifiedVal: IAssignedTeacher, saved: IAssignedTeacher): boolean {
      throw new Error("Method not implemented");
    }

    private uriBuilder(param: CourseTeacherSearchParamModel): string {
      // var fetchUri: string = this.getBaseUri() + "/programId/" + '110500' + "/semesterId/" + '11012016' + '/year/1';
      var fetchUri = this.getBaseUri() + "/programId/" + param.programSelector.programId + "/semesterId/" + param.programSelector.semesterId;

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

    public getBaseUri(): string {
      return "";
    }

    public getPostUri(): string {
      return "";
    }

    public saveButtonVisibility(): boolean {
      for (var courseId in this.$scope.entries) {
        if (this.$scope.entries.hasOwnProperty(courseId) && this.$scope.entries[courseId].editMode) {
          return true
        }
      }
      return false;
    }
  }
}

