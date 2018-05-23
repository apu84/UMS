///<reference path="GenericModel.ts"/>

module ums {
  export enum FieldViewTypes {
    selected,
    hidden,
    readonly,
    none
  }

  interface FieldView {
    [key: string]: FieldViewTypes;
  }

  export class ProgramSelectorModel {
    getProgramTypes: Function;
    getDepartments: Function;
    getPrograms: Function;
    getSemesters: Function;
    private setProgramTypes: Function;
    private setDepartments: Function;
    private setPrograms: Function;
    private setSemesters: Function;

    private getAppConstants: Function;
    private getHttpClient: Function;

    private isAllDepartmentEnabled: Function;
    private isAllProgramEnabled: Function;

    private isSemesterEnabled: Function;
    private enableSemester: Function;

    fieldView: () => FieldView;
    fieldViewTypes: () => any;

    programId: string;
    departmentId: string;
    programTypeId: string;
    semesterId: string;

    constructor(pAppConstants: any,
                pHtpClient: HttpClient,
                pEnableSemester?: boolean,
                pEnableAllDepartment?: boolean,
                pEnableAllProgram?: boolean) {

      var appConstants = pAppConstants;
      var httpClient = pHtpClient;

      var programTypes = appConstants.programType;
      var departments = appConstants.initDept;
      var programs = appConstants.initProgram;
      var semesters = appConstants.initSemester;

      var fieldView: FieldView = {
        programType: FieldViewTypes.none,
        department: FieldViewTypes.none,
        program: FieldViewTypes.none,
        semester: FieldViewTypes.none
      };

      this.getProgramTypes = () => {
        return programTypes;
      };

      this.getDepartments = () => {
        return departments;
      };

      this.getPrograms = () => {
        return programs;
      };

      this.getSemesters = () => {
        return semesters;
      };

      this.setProgramTypes = () => {
        return programTypes;
      };

      this.setDepartments = (pDepartments: any) => {
        departments = pDepartments;
      };

      this.setPrograms = (pPrograms: any) => {
        programs = pPrograms;
      };

      this.setSemesters = (pSemesters: any) => {
        semesters = pSemesters;
      };

      this.getAppConstants = (): Constants => {
        return appConstants;
      };

      this.getHttpClient = () => {
        return httpClient;
      };

      this.isSemesterEnabled = () => {
        return pEnableSemester;
      };

      this.isAllDepartmentEnabled = () => {
        return pEnableAllDepartment;
      };

      this.isAllProgramEnabled = () => {
        return pEnableAllProgram;
      };

      this.fieldView = () => {
        return fieldView;
      };

      this.fieldViewTypes = () => {
        return FieldViewTypes;
      };

      this.loadSemester();
    }

    public setDepartment(departmentId: string, fieldViewType?: FieldViewTypes): void {
      this.departmentId = departmentId;
      if (fieldViewType) {
        this.fieldView()['department'] = fieldViewType;
      }
      this.loadPrograms();
    }

    public setProgram(programId: string, fieldViewType?: FieldViewTypes): void {
      this.programId = programId;
      if (fieldViewType) {
        this.fieldView()['program'] = fieldViewType;
      }
    }

    public setProgramType(programTypeId: string, fieldViewType?: FieldViewTypes): void {
      this.programTypeId = programTypeId;
      if (fieldViewType) {
        this.fieldView()['programType'] = fieldViewType;
      }
      this.loadDepartments();
      this.loadSemester();
    }

    public enableSemesterOption(enable: boolean): void {
      this.enableSemester(enable);
    }




    public loadDepartments(): void {
      if (this.programTypeId === this.getAppConstants().programTypeEnum.UG) {
        this.setDepartments(this.getAppConstants().ugDept);
      }
      else if (this.programTypeId === this.getAppConstants().programTypeEnum.PG) {
        this.setDepartments(this.getAppConstants().pgDept);
      }
      else {
        this.setDepartments(this.getAppConstants().initDept);
      }

      if (this.isAllDepartmentEnabled()) {
        if (this.getDepartments()[this.getDepartments().length - 1].id != this.getAppConstants().deptAll.id) {
          this.getDepartments().push({
            id: this.getAppConstants().deptAll.id,
            name: this.getAppConstants().deptAll.label
          });
        }
      }

      this.loadPrograms();
    }

    public loadPrograms(): void {
      this.setPrograms(this.getAppConstants().initProgram);
      if (this.departmentId
          && this.departmentId !== this.getAppConstants().deptAll.id) {
        var programsArr: any;

        if (this.programTypeId === this.getAppConstants().programTypeEnum.UG) {
          programsArr = this.getAppConstants().ugPrograms;
        }
        else if (this.programTypeId === this.getAppConstants().pgPrograms) {
          programsArr = this.getAppConstants().pgPrograms;
        }
        if(programsArr && programsArr.length > 0) {
          var programsJson = $.map(programsArr, (el) => {
            return el;
          });

          var resultPrograms: any = $.grep(programsJson, (e: any) => {
            return e.deptId == this.departmentId;
          });

          this.setPrograms(resultPrograms[0].programs);
        }
      }

      this.programId = this.getPrograms()[0].id;

      if (this.isAllProgramEnabled() && this.departmentId === this.getAppConstants().deptAll.id) {
        if (this.getPrograms()[this.getPrograms().length - 1].id != this.getAppConstants().programAll.id) {
          this.getPrograms().push({
            id: this.getAppConstants().programAll.id,
            longName: this.getAppConstants().programAll.label
          });
        }
      }
    }

    public loadSemester(): void {
      if (this.programTypeId && this.programTypeId !== this.getAppConstants().Empty) {
        this.getHttpClient().get('academic/semester/program-type/' + this.programTypeId + "/limit/10/status/"+Utils.SEMESTER_FETCH_ALL,
            HttpClient.MIME_TYPE_JSON,
            (json: any, etag: string) => {
              this.setSemesters(json.entries);
              if (this.isSemesterEnabled() && json.entries.length > 0){
                this.semesterId = (this.getSemesters()[0]).id + '';
              }
            },
            (response: ng.IHttpPromiseCallbackArg<any>) => {
              console.error(response);
            });
      }
    }
  }
}
