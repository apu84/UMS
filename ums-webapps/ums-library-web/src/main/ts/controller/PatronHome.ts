module ums {

    export class PatronHome {
        public static $inject = ['HttpClient', '$scope', '$q', 'notify', 'userService'];
        private userId: string;
        private files: any = {};

        constructor(private httpClient: HttpClient, private $scope: any,
                    private $q: ng.IQService, private notify: Notify, private userService: UserService) {

            this.userService.fetchCurrentUserInfo().then((data: any)=> {
                this.userId = data.employeeId;
            });
        }

        private uploadImage() {
            var id = this.userId;
            var image = $("#userPhoto").contents().prevObject[0].files[0];
            this.getFormData(image, id).then((formData) => {
                this.uploadFile(formData);
            });
        }

        private getFormData(file, id): ng.IPromise<any> {
            var formData = new FormData();
            formData.append('files', file);
            formData.append('name', file.name);
            formData.append("id", id);
            var defer = this.$q.defer();
            defer.resolve(formData);
            return defer.promise;
        }

        public uploadFile(formData: any): ng.IPromise<any> {
            var defer = this.$q.defer();
            var url = "profilePicture/upload";
            this.httpClient.post(url, formData, undefined)
                .success((response) => {
                    defer.resolve(response);
                }).error((data) => {
                console.error(data);
            });
            return defer.promise;
        }

    }

    UMS.controller("PatronHome", PatronHome);
}