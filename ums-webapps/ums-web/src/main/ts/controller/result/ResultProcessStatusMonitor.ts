module ums {

  interface TaskStatus {
    id: string,
    lastModified: string,
    status: string,
    taskName: string,
    progressDescription: string,
    taskCompletionDate: string
  }

  interface TaskStatusResponse {
    message?: string;
    response?: TaskStatus;
    responseType?: string;
  }

  interface TaskStatusResponseWrapper {
    taskStatus: TaskStatusResponse;
  }

  interface ResultProcessStatus extends StatusByYearSemester {
    status: number;
  }

  interface IResultProcessStatusMonitorScope extends ng.IScope {
    programId: string;
    semesterId: string;
    taskStatus: TaskStatusResponseWrapper;
    statusByYearSemester: StatusByYearSemester;
    resultProcessStatus: Function;
    resultProcessStatusConst: {};
    processResult: Function;
  }

  export class ResultProcessStatusMonitor implements ng.IDirective {
    private intervalPromiseMap: {[key:string]: ng.IPromise<any>} = {};
    private PROCESS_GRADES: string = "_process_grades";
    private PROCESS_GPA_CGPA_PROMOTION: string = "_process_gpa_cgpa_promotion";

    constructor(private httpClient: HttpClient,
                private $q: ng.IQService,
                private $interval: ng.IIntervalService,
                private settings: Settings,
                private appConstants: any) {

    }

    public restrict: string = 'AE';

    public scope: any = {
      programId: '=',
      semesterId: '=',
      statusByYearSemester: '=',
      taskStatus: '=',
      render: '='
    };

    public link = (scope: IResultProcessStatusMonitorScope, element: JQuery, attributes: any) => {
      this.updateStatus(scope.programId, scope.semesterId, scope.taskStatus);
      scope.resultProcessStatus = this.resultProcessStatus.bind(this);
      scope.resultProcessStatusConst = this.appConstants.RESULT_PROCESS_STATUS;
      scope.processResult = this.processResult.bind(this);
    };

    public templateUrl = "./views/result/result-process-status.html";

    private getNotification(programId: string, semesterId: string, statusWrapper: TaskStatusResponseWrapper) {
      this.httpClient.poll(this.getUpdateStatusUri(programId, semesterId),
          HttpClient.MIME_TYPE_JSON,
          (response: TaskStatusResponse)=> {
            statusWrapper.taskStatus = response;
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            if (response.status !== 200) {
              this.$interval.cancel(this.intervalPromiseMap[`${programId}_${semesterId}`]);
            }
          });
    }

    private updateStatus(programId: string, semesterId: string, taskStatusWrapper: TaskStatusResponseWrapper): void {
      this.httpClient.get(this.getUpdateStatusUri(programId, semesterId),
          HttpClient.MIME_TYPE_JSON,
          (response: TaskStatusResponse) => {
            taskStatusWrapper.taskStatus = response;
          });
    }

    private resultProcessStatus(programId: string,
                                semesterId: string,
                                statusWrapper: TaskStatusResponseWrapper,
                                statusByYearSemester: ResultProcessStatus): string {
      if (statusWrapper && statusWrapper.taskStatus && statusWrapper.taskStatus.response) {
        var resultProcessTask: boolean
            = statusWrapper.taskStatus.response.id == this.getResultProcessTaskName(programId, semesterId);
        var gradeProcessTask: boolean
            = statusWrapper.taskStatus.response.id == this.getGradeProcessTaskName(programId, semesterId);

        if (resultProcessTask || gradeProcessTask) {
          if (statusWrapper.taskStatus.response.status == this.appConstants.TASK_STATUS.COMPLETED) {
            if (this.intervalPromiseMap[`${programId}_${semesterId}`] && resultProcessTask) {
              this.$interval.cancel(this.intervalPromiseMap[`${programId}_${semesterId}`]);
              delete this.intervalPromiseMap[`${programId}_${semesterId}`];
            }
            statusByYearSemester.status
                = gradeProcessTask
                ? this.appConstants.RESULT_PROCESS_STATUS.IN_PROGRESS.id
                : this.appConstants.RESULT_PROCESS_STATUS.PROCESSED_ON.id;
            return gradeProcessTask
                ? this.appConstants.RESULT_PROCESS_STATUS.IN_PROGRESS.label
                : `${this.appConstants.RESULT_PROCESS_STATUS.PROCESSED_ON.label} ${statusWrapper.taskStatus.response.taskCompletionDate}`;
          }
          else if (statusWrapper.taskStatus.response.status == this.appConstants.TASK_STATUS.INPROGRESS) {
            this.startPolling(programId, semesterId, statusWrapper);
            statusByYearSemester.status = this.appConstants.RESULT_PROCESS_STATUS.IN_PROGRESS.id;
            return this.appConstants.RESULT_PROCESS_STATUS.IN_PROGRESS.label;
          }
          else {
            for (var yearSemester in statusByYearSemester.yearSemester) {
              if (statusByYearSemester.yearSemester.hasOwnProperty(yearSemester)) {
                if (statusByYearSemester.yearSemester[yearSemester].status
                    < this.appConstants.MARKS_SUBMISSION_STATUS.ACCEPTED_BY_COE
                    || statusByYearSemester.yearSemester[yearSemester].status
                    == this.appConstants.MARKS_SUBMISSION_STATUS.REQUESTED_FOR_RECHECK_BY_COE) {
                  statusByYearSemester.status = this.appConstants.RESULT_PROCESS_STATUS.UNPROCESSED.id;
                  return this.appConstants.RESULT_PROCESS_STATUS.UNPROCESSED.label;
                }
              }
            }
            statusByYearSemester.status = this.appConstants.RESULT_PROCESS_STATUS.READY_TO_BE_PROCESSED.id;
            return this.appConstants.RESULT_PROCESS_STATUS.READY_TO_BE_PROCESSED.label;
          }
        }
        statusByYearSemester.status = this.appConstants.RESULT_PROCESS_STATUS.STATUS_UNDEFINED.id;
        return this.appConstants.RESULT_PROCESS_STATUS.STATUS_UNDEFINED.label;
      }
    }

    private getResultProcessTaskName(programId: string, semesterId: string): string {
      return `${programId}_${semesterId}${this.PROCESS_GPA_CGPA_PROMOTION}`;
    }

    private getGradeProcessTaskName(programId: string, semesterId: string): string {
      return `${programId}_${semesterId}${this.PROCESS_GRADES}`;
    }

    private getUpdateStatusUri(programId: string, semesterId: string): string {
      return `academic/processResult/status/program/${programId}/semester/${semesterId}`;
    }

    private getProcessResultUri(programId: string, semesterId: string): string {
      return `academic/processResult/program/${programId}/semester/${semesterId}`;
    }

    private processResult(programId: string, semesterId: string,  statusWrapper: TaskStatusResponseWrapper): void {
      this.httpClient.post(this.getProcessResultUri(programId, semesterId),
          {},
          HttpClient.MIME_TYPE_JSON);
      this.startPolling(programId, semesterId, statusWrapper);
    }

    private startPolling(programId: string, semesterId: string, statusWrapper: TaskStatusResponseWrapper): void {
      if (!this.intervalPromiseMap[`${programId}_${semesterId}`]) {
        this.intervalPromiseMap[`${programId}_${semesterId}`] = this.$interval(()=> {
          this.getNotification(programId, semesterId, statusWrapper);
        }, 2000, 0, true);
      }
    }
  }

  UMS.directive("resultProcessStatusMonitor",
      ['HttpClient',
        '$q',
        '$interval',
        'Settings',
        'appConstants',
        (httpClient, $q, $interval, settings: Settings, appConstants: any)=> {
          return new ResultProcessStatusMonitor(httpClient, $q, $interval, settings, appConstants);
        }]);
}
