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

            var image = $("#inputFile").contents().prevObject[0].files[0];

            console.log(image);
                this.getFormData(image, id).then((formData) => {
                    this.uploadFile(formData);
                });


        }

        private getFormData(file, id): ng.IPromise<any> {
            var formData = new FormData();
            formData.append('files', file);
            console.log(file.name);
            formData.append('name', file.name);
            formData.append("id", id);
            console.log(formData);
            var defer = this.$q.defer();
            defer.resolve(formData);

            return defer.promise;

        }
        private fileUploaded(){
            console.log("In the file upload section");
            console.log(this.files);
        }


        public uploadFile(formData: any): ng.IPromise<any> {
            console.log("Files");
            console.log(this.files);
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