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
    statusText: string;
  }

  interface IResultProcessStatusMonitorScope extends ng.IScope {
    programId: string;
    semesterId: string;
    taskStatus: TaskStatusResponseWrapper;
    statusByYearSemester: ResultProcessStatus;
    resultProcessStatus: Function;
    resultProcessStatusConst: {};
    processResult: Function;
    publishResult: Function;
    resultPdf: Function;
  }

  export class ResultProcessStatusMonitor implements ng.IDirective {

    constructor() {
    }

    public restrict: string = 'AE';

    public scope: any = {
      programId: '=',
      semesterId: '=',
      statusByYearSemester: '=',
      render: '='
    };

    public templateUrl = "./views/result/result-process-status.html";
    public bindToController: boolean = true;
    public controller: any = ResultProcessStatusMonitorController;
    public controllerAs: string = 'vm';
  }

  class ResultProcessStatusMonitorController {
    public static $inject = ['HttpClient',
      '$q',
      '$interval',
      'Settings',
      'appConstants',
      '$timeout'];
    private intervalPromiseMap: {[key: string]: ng.IPromise<any>};
    private PROCESS_GRADES: string = "_process_grades";
    private PROCESS_GPA_CGPA_PROMOTION: string = "_process_gpa_cgpa_promotion";
    private PUBLISH_RESULT: string = "_publish_result";

    private programId: string;
    private semesterId: string;
    private statusByYearSemester: ResultProcessStatus;
    private render: string;
    private resultProcessStatusConst: any;

    constructor(private httpClient: HttpClient,
                private $q: ng.IQService,
                private $interval: ng.IIntervalService,
                private settings: Settings,
                private appConstants: any,
                private $timeout: ng.ITimeoutService) {
      this.intervalPromiseMap = {};
      this.resultProcessStatusConst = this.appConstants.RESULT_PROCESS_STATUS;
      this.updateStatus(this.programId, this.semesterId, this.statusByYearSemester);
    }

    private getNotification(programId: string, semesterId: string, statusByYearSemester: ResultProcessStatus) {
      this.httpClient.poll(this.getUpdateStatusUri(programId, semesterId),
          HttpClient.MIME_TYPE_JSON,
          (response: TaskStatusResponse)=> {
            statusByYearSemester.taskStatus = response;
            this.$timeout(()=> {
              this.resultProcessStatus(programId, semesterId, statusByYearSemester);
            });

            if (statusByYearSemester.taskStatus.response.status == this.appConstants.TASK_STATUS.COMPLETED) {
              if (this.intervalPromiseMap[`${programId}_${semesterId}`]) {
                this.$interval.cancel(this.intervalPromiseMap[`${programId}_${semesterId}`]);
                delete this.intervalPromiseMap[`${programId}_${semesterId}`];
              }
            }
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            if (response.status !== 200) {
              this.$interval.cancel(this.intervalPromiseMap[`${programId}_${semesterId}`]);
            }
          });
    }

    private updateStatus(programId: string, semesterId: string, statusByYearSemester: ResultProcessStatus): void {
      this.httpClient.get(this.getUpdateStatusUri(programId, semesterId),
          HttpClient.MIME_TYPE_JSON,
          (response: TaskStatusResponse) => {
            statusByYearSemester.taskStatus = response;
            this.resultProcessStatus(programId, semesterId, statusByYearSemester);
          });
    }

    private resultProcessStatus(programId: string,
                                semesterId: string,
                                statusByYearSemester: ResultProcessStatus): void {
      if (statusByYearSemester.taskStatus && statusByYearSemester.taskStatus.response) {
        var resultProcessTask: boolean
            = statusByYearSemester.taskStatus.response.id == this.getResultProcessTaskName(programId, semesterId);
        var gradeProcessTask: boolean
            = statusByYearSemester.taskStatus.response.id == this.getGradeProcessTaskName(programId, semesterId);
        var resultPublishTask: boolean
            = statusByYearSemester.taskStatus.response.id == this.getResultPublishTaskName(programId, semesterId);
        if (resultProcessTask || gradeProcessTask || resultPublishTask) {
          if (statusByYearSemester.taskStatus.response.status == this.appConstants.TASK_STATUS.COMPLETED) {
            if (resultPublishTask) {
              statusByYearSemester.status = this.appConstants.RESULT_PROCESS_STATUS.RESULT_PUBLISHED.id;
              statusByYearSemester.statusText
                  = `${this.appConstants.RESULT_PROCESS_STATUS.RESULT_PUBLISHED.label} ${statusByYearSemester.taskStatus.response.taskCompletionDate}`;
              return;
            }
            else {
              statusByYearSemester.status
                  = gradeProcessTask
                  ? this.appConstants.RESULT_PROCESS_STATUS.IN_PROGRESS.id
                  : this.appConstants.RESULT_PROCESS_STATUS.PROCESSED_ON.id;
              statusByYearSemester.statusText = gradeProcessTask
                  ? this.appConstants.RESULT_PROCESS_STATUS.IN_PROGRESS.label
                  : `${this.appConstants.RESULT_PROCESS_STATUS.PROCESSED_ON.label} ${statusByYearSemester.taskStatus.response.taskCompletionDate}`;
              return;
            }
          }
          else if (statusByYearSemester.taskStatus.response.status == this.appConstants.TASK_STATUS.INPROGRESS) {
            this.startPolling(programId, semesterId, statusByYearSemester);
            if (resultPublishTask) {
              statusByYearSemester.status = this.appConstants.RESULT_PROCESS_STATUS.RESULT_PUBLISH_INPROGRESS.id;
              statusByYearSemester.statusText = this.appConstants.RESULT_PROCESS_STATUS.RESULT_PUBLISH_INPROGRESS.label;
              return;
            }
            else {
              statusByYearSemester.status = this.appConstants.RESULT_PROCESS_STATUS.IN_PROGRESS.id;
              statusByYearSemester.statusText = this.appConstants.RESULT_PROCESS_STATUS.IN_PROGRESS.label;
              return;
            }
          }
          else {
            for (var yearSemester in statusByYearSemester.yearSemester) {
              if (statusByYearSemester.yearSemester.hasOwnProperty(yearSemester)) {
                if (statusByYearSemester.yearSemester[yearSemester].status
                    < this.appConstants.MARKS_SUBMISSION_STATUS.ACCEPTED_BY_COE
                    || statusByYearSemester.yearSemester[yearSemester].status
                    == this.appConstants.MARKS_SUBMISSION_STATUS.REQUESTED_FOR_RECHECK_BY_COE) {
                  statusByYearSemester.status = this.appConstants.RESULT_PROCESS_STATUS.UNPROCESSED.id;
                  statusByYearSemester.statusText = this.appConstants.RESULT_PROCESS_STATUS.UNPROCESSED.label;
                  return;
                }
              }
            }
            statusByYearSemester.status = this.appConstants.RESULT_PROCESS_STATUS.READY_TO_BE_PROCESSED.id;
            statusByYearSemester.statusText = this.appConstants.RESULT_PROCESS_STATUS.READY_TO_BE_PROCESSED.label;
            return;
          }
        }
        statusByYearSemester.status = this.appConstants.RESULT_PROCESS_STATUS.STATUS_UNDEFINED.id;
        statusByYearSemester.statusText = this.appConstants.RESULT_PROCESS_STATUS.STATUS_UNDEFINED.label;
        return;
      }
    }

    private getResultProcessTaskName(programId: string, semesterId: string): string {
      return `${programId}_${semesterId}${this.PROCESS_GPA_CGPA_PROMOTION}`;
    }

    private getGradeProcessTaskName(programId: string, semesterId: string): string {
      return `${programId}_${semesterId}${this.PROCESS_GRADES}`;
    }

    private getResultPublishTaskName(programId: string, semesterId: string): string {
      return `${programId}_${semesterId}${this.PUBLISH_RESULT}`;
    }


    private getUpdateStatusUri(programId: string, semesterId: string): string {
      return `academic/processResult/status/program/${programId}/semester/${semesterId}`;
    }

    private getProcessResultUri(programId: string, semesterId: string): string {
      return `academic/processResult/program/${programId}/semester/${semesterId}`;
    }

    private getPublishResultUri(programId: string, semesterId: string): string {
      return `academic/publishResult/program/${programId}/semester/${semesterId}`;
    }

    private processResult(programId: string, semesterId: string, statusByYearSemester: ResultProcessStatus): void {
      this.httpClient.post(this.getProcessResultUri(programId, semesterId),
          {},
          HttpClient.MIME_TYPE_JSON);
      this.startPolling(programId, semesterId, statusByYearSemester);
    }

    private startPolling(programId: string, semesterId: string, statusByYearSemester: ResultProcessStatus): void {
      var key: string = `${programId}_${semesterId}`;
      if (!this.intervalPromiseMap[key]) {
        this.intervalPromiseMap[key] = this.$interval(()=> {
          this.getNotification(programId, semesterId, statusByYearSemester);
        }, 2000, 0, true);
      }
    }

    private publishResult(programId: string, semesterId: string, statusByYearSemester: ResultProcessStatus): void {
      this.httpClient.post(this.getPublishResultUri(programId, semesterId),
          {},
          HttpClient.MIME_TYPE_JSON);
      this.startPolling(programId, semesterId, statusByYearSemester);
    }

    private resultPdf(programId: string, semesterId: string): void {
      this.httpClient.get(`result/pdf/program/${programId}/semester/${semesterId}`, 'application/pdf',
          (data: any, etag: string) => {
            var file = new Blob([data], {type: 'application/pdf'});
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onloadend = (e) => {
              this.saveAsFile(reader.result, `result_list_${programId}_${semesterId}`);
            };
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
          }, 'arraybuffer');
    }

    private saveAsFile(url, fileName) {
      var a: any = document.createElement("a");
      document.body.appendChild(a);
      a.style = "display: none";
      a.href = url;
      a.download = fileName;
      a.click();
      window.URL.revokeObjectURL(url);
      $(a).remove();
    }
  }

  UMS.directive("resultProcessStatusMonitor",
      ()=> {
        return new ResultProcessStatusMonitor();
      });
}
