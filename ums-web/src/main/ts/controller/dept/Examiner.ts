module ums {
  export interface IExaminer extends IAssignedTeacher {
    preparerId: string;
    preparerName: string;
    scrutinizerId: string;
    scrutinizerName: string;
    deleted: boolean;
  }

  export interface IPostExaminerModel {
    semesterId: string;
    courseId: string;
    preparerId?: string;
    scrutinizerId?: string;
    updateType: string;
    id?: string;
  }

  export interface IPostExaminerEntries {
    entries? : Array<IPostExaminerModel>;
  }

  export class Examiner extends TeacherAssignment<IExaminer> {
    public static $inject = ['appConstants', 'HttpClient', '$scope', '$q', 'notify'];

    constructor(appConstants: any, httpClient: HttpClient,
                $scope: ITeacherAssignmentScope, $q: ng.IQService,
                notify: Notify) {
      super(appConstants, httpClient, $scope, $q, notify);
    }

    public formatTeacher(examiners: Array<IExaminer>, courseId?: string): void {
      for (var i = 0; i < examiners.length; i++) {
        if (!this.formattedMap[examiners[i].courseId] || this.formattedMap[examiners[i].courseId].updated) {
          this.formattedMap[examiners[i].courseId] = examiners[i];
          this.formattedMap[examiners[i].courseId].editMode = false;
          this.formattedMap[examiners[i].courseId].updated = false;
          this.formattedMap[examiners[i].courseId].deleted = false;

          // console.debug('%o',this.formattedMap[examiners[i].courseId]);
        }
        //save the fetched copy, later on it will be used to decided whats are values has been created/update/removed
        this.savedCopy = $.extend(true, {}, this.formattedMap);
        this.$scope.entries = this.formattedMap;
      }

    }

    public decorateTeacher(assignedTeacher: IExaminer): void {
      // do nothing
    }

    public addTeacher(courseId: string): void {
      this.populateTeachers(courseId);
      this.formattedMap[courseId].editMode = true;
    }

    public editTeacher(courseId: string): void {
      this.populateTeachers(courseId);
      this.formattedMap[courseId].editMode = true;
    }

    public removeTeacher(courseId: string): void {
      this.formattedMap[courseId]['preparerId'] = '-1';
      delete this.formattedMap[courseId]['preparerName'];
      this.formattedMap[courseId]['scrutinizerId'] = '-1';
      delete this.formattedMap[courseId]['scrutinizerName'];
      this.formattedMap[courseId].deleted = true;
    }

    public saveTeacher(courseId: string): void {
      //initialize what needs to be posted
      var savedExaminer: IPostExaminerEntries = {};
      savedExaminer.entries = [];

      var saved: IExaminer = this.savedCopy[courseId];
      var modified: IExaminer = this.formattedMap[courseId];

      if (!this.validate(modified, saved)) {
        return;
      }
      if (saved.id) {
        var updateType = 'update';
        if (modified.preparerId == '-1' && modified.scrutinizerId == '-1') {
          updateType = 'delete';
        }
        savedExaminer.entries.push({
          id: saved.id,
          courseId: courseId,
          programId: this.$scope.teacherSearchParamModel.programSelector.programId,
          semesterId: this.$scope.teacherSearchParamModel.semesterId,
          preparerId: modified.preparerId,
          scrutinizerId: modified.scrutinizerId,
          updateType: updateType
        });
      } else {
        savedExaminer.entries.push({
          courseId: courseId,
          programId: this.$scope.teacherSearchParamModel.programSelector.programId,
          semesterId: this.$scope.teacherSearchParamModel.semesterId,
          preparerId: modified.preparerId,
          scrutinizerId: modified.scrutinizerId,
          updateType: 'insert'
        });
      }
      this.postTeacher(savedExaminer, courseId);
    }


    public validate(modifiedVal: IExaminer, saved: IExaminer): boolean {

      if (UmsUtil.isEmptyString(modifiedVal.preparerId)) {
        this.notify.warn("Please select Gradesheet Preparer");
        return false;
      }
      if (UmsUtil.isEmptyString(modifiedVal.scrutinizerId)) {
        this.notify.warn("Please select Gradesheet Scrutinizer");
        return false;
      }

      return true;
    }

    public getBaseUri(): string {
      return "academic/examiner";
    }

    public getPostUri(): string {
      return 'academic/examiner/';
    }
  }
  UMS.controller('Examiner', Examiner);
}