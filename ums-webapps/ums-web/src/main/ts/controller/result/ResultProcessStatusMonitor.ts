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
    message: string;
    response: TaskStatus;
    responseType: string;
  }

  interface IResultProcessStatusMonitorScope extends ng.IScope {
    programId: string;
    semesterId: string;
    taskStatus: TaskStatus;
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
      statusByYearSemester: '='
    };

    public link = (scope: IResultProcessStatusMonitorScope, element: JQuery, attributes: any) => {
      this.updateStatus(scope.programId, scope.semesterId, scope);
      scope.resultProcessesStatus = this.resultProcessesStatus.bind(this);
    };

    public templateUrl = "./views/result/result-process-status.html";

    private getNotification() {
      this.httpClient.poll("notification/10/", HttpClient.MIME_TYPE_JSON,
          (response: NotificationEntries)=> {

          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            if (response.status === 401) {
              this.$interval.cancel(this.intervalPromise);
            }
          });
    }

    private updateStatus(programId: string, semesterId: string, scope: any): void {
      this.httpClient.get(`academic/processResult/status/program/${programId}/semester/${semesterId}`,
          HttpClient.MIME_TYPE_JSON,
          (response: TaskStatusResponse) => {
            scope.taskStatus = response;
          });
    }

    private resultProcessesStatus(programId: string,
                                  semesterId: string,
                                  status: TaskStatusResponse,
                                  statusByYearSemester: StatusByYearSemester): string {
      if (status) {
        if (status.response.id == this.getResultProcessTaskName(programId, semesterId)) {
          if (status.response.status == this.appConstants.TASK_STATUS.COMPLETED) {
            return `Processed on ${status.response.taskCompletionDate}`;
          }
          else if (status.response.id  == this.appConstants.TASK_STATUS.INPROGRESS) {
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
