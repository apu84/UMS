module ums {
    export class FileUpload {
        public static $inject = ['HttpClient', '$q', 'notify'];

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {

        }

        public uploadFile(file: any, contentType: string, fileName: string, uploadUri: string): void {
            this.httpClient.post(uploadUri, file, contentType, fileName)
                .success(function () {
                })
                .error(function () {
                });
        }

        public uploadPhoto(formData: any): ng.IPromise<any> {
            var defer = this.$q.defer();
            var url = "profilePicture/upload";
            this.httpClient.post(url, formData, undefined)
                .success((response) => {
                    this.notify.success("Uploaded Successfully");
                    defer.resolve(response);
                }).error((data) => {
                defer.resolve(data);
            });
            return defer.promise;
        }
    }

    UMS.service("FileUpload", FileUpload);
}