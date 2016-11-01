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

  interface IResultProcessStatusMonitorScope extends ng.IScope {
    programId: string;
    semesterId: string;
    taskStatus: TaskStatusResponseWrapper;
    statusByYearSemester: StatusByYearSemester;
    resultProcessesStatus: Function;
  }

  export class ResultProcessStatusMonitor implements ng.IDirective {
    private intervalPromise: ng.IPromise<any>;
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
      taskStatus: '='
    };

    private currentScope: IResultProcessStatusMonitorScope;

    public link = (scope: IResultProcessStatusMonitorScope, element: JQuery, attributes: any) => {
      this.updateStatus(scope.programId, scope.semesterId, scope.taskStatus);
      scope.resultProcessesStatus = this.resultProcessesStatus.bind(this);
    };

    public templateUrl = "./views/result/result-process-status.html";

    private getNotification(programId: string, semesterId: string, statusWrapper: TaskStatusResponseWrapper) {
      this.httpClient.poll(this.getUpdateStatusUri(programId, semesterId),
          HttpClient.MIME_TYPE_JSON,
          (response: TaskStatusResponse)=> {
            statusWrapper.taskStatus = response;
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            if (response.status === 401) {
              this.$interval.cancel(this.intervalPromise);
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

    private resultProcessesStatus(programId: string,
                                  semesterId: string,
                                  statusWrapper: TaskStatusResponseWrapper,
                                  statusByYearSemester: StatusByYearSemester): string {
      console.log(statusWrapper);
      if (statusWrapper.taskStatus) {
        if (statusWrapper.taskStatus.response.id == this.getResultProcessTaskName(programId, semesterId)) {
          if (statusWrapper.taskStatus.response.status == this.appConstants.TASK_STATUS.COMPLETED) {
            if (this.intervalPromise) {
              this.$interval.cancel(this.intervalPromise);
              this.intervalPromise = null;
            }
            return `Processed on ${statusWrapper.taskStatus.response.taskCompletionDate}`;
          }
          else if (statusWrapper.taskStatus.response.status == this.appConstants.TASK_STATUS.INPROGRESS) {
            if (!this.intervalPromise) {
              this.intervalPromise = this.$interval(()=> {
                this.getNotification(programId, semesterId, statusWrapper)
              }, 2000, 0, true);
            }
            return `Processes in progress`;
          }
          else {
            for (var yearSemester in statusByYearSemester.yearSemester) {
              if (statusByYearSemester.yearSemester.hasOwnProperty(yearSemester)) {
                if (statusByYearSemester.yearSemester[yearSemester].status
                    < this.appConstants.MARKS_SUBMISSION_STATUS.ACCEPTED_BY_COE
                    || statusByYearSemester.yearSemester[yearSemester].status
                    == this.appConstants.MARKS_SUBMISSION_STATUS.REQUESTED_FOR_RECHECK_BY_COE) {
                  return "Unprocessed";
                }
              }
            }
            return "Ready to be processed";
          }
        }
        return "Status undefined";
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
