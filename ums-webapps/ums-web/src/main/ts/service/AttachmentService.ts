module ums {
  export class AttachmentService {
    public static $inject = ['appConstants', 'HttpClient', '$q', 'notify', '$sce', '$window'];

    constructor(private appConstants: any, private httpClient: HttpClient,
                private $q: ng.IQService, private notify: Notify,
                private $sce: ng.ISCEService, private $window: ng.IWindowService) {

    }

    public fetchAttachments(applicationType: string, applicationId: string): ng.IPromise<any> {
      var url = "attachment/applicationType/" + applicationType + "/applicationId/" + applicationId;
      var defer = this.$q.defer();

      this.httpClient.get(url, this.appConstants.mimeTypeJson,
          (json: any, etag: string) => {
            var attachments: any = {};
            attachments = json.entries;
            defer.resolve(attachments);
          },
          (response: ng.IHttpPromiseCallbackArg<any>) => {
            console.error(response);
            this.notify.error("Error in getting attachments");
          });

      return defer.promise;
    }

    public downloadFile(attachmentId: string, fileName: string): void {
      var contentType: string = UmsUtil.getFileContentType("pdf");


      this.httpClient.get('attachment/downloadFile/attachmentId/' + attachmentId, undefined, (data: any, etag: string) => {

        /*var fileURL = this.$sce.trustAsResourceUrl(URL.createObjectURL(file));
        this.$window.open(fileURL);*/
        UmsUtil.writeFileContent(data, contentType, fileName);
      }, (response: any) => {
        console.error(response);
      }, 'arraybuffer');
    }


  }


  UMS.service("attachmentService", AttachmentService);
}