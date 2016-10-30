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

  interface IResultProcessStatusMonitorScope {
    programId: string;
    semesterId: string;
    taskStatus: TaskStatus;
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
      programId: '@',
      semesterId: '@'
    };

    public link = (scope: IResultProcessStatusMonitorScope, element: JQuery, attributes: any) => {
      this.updateStatus();
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

    private updateStatus(): void {
      this.httpClient.get(`processResult/status/program/${this.scope.programId}/semester/${this.scope.semesterId}`,
          HttpClient.MIME_TYPE_JSON,
          (response: TaskStatusResponse) => {
            this.scope.taskStatus = response;
          });
    }

    private isResultProcessed(status: TaskStatus): boolean {
      return status.taskName == this.getResultProcessTaskName() && status.status == this.appConstants.TASK_STATUS.COMPLETED;
    }

    private getResultProcessTaskName(): string {
      return `${this.scope.programId}_${this.scope.semesterId}_${this.PROCESS_GPA_CGPA_PROMOTION}`;
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
