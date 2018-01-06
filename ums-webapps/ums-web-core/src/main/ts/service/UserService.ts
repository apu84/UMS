/**
 * Created by My Pc on 29-May-17.
 */

module ums {
    export class UserService {
        public static $inject = ['HttpClient', '$q', 'notify', '$sce', '$window'];

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService, private notify: Notify,
                    private $sce: ng.ISCEService, private $window: ng.IWindowService) {

        }

        public fetchCurrentUserInfo(): ng.IPromise<any> {
            var url = "users/current";
            var defer = this.$q.defer();

            this.httpClient.get(url, 'application/json',
                (json: any, etag: string) => {
                    var user: any = json;
                    defer.resolve(user);
                },
                (response: ng.IHttpPromiseCallbackArg<any>) => {
                    this.notify.error("Error in getting user data");
                });

            return defer.promise;
        }

        public getUser(id: string): ng.IPromise<any> {
            var defer = this.$q.defer();
            if (id == undefined || id == null || id == "") {
                this.notify.error("Invalid UserId");
                defer.resolve("Invalid User Id");
            }
            else if (id.length == 9 || id.length == 6) {

                var url = "user/view/id/" + id;

                this.httpClient.get(url, 'application/json',
                    (json: any, etag: string) => {
                        var user: any = json.entries;
                        defer.resolve(user);
                    },
                    (response: ng.IHttpPromiseCallbackArg<any>) => {
                        defer.resolve(response);
                    });
            }
            else{
                this.notify.error("Invalid UserId");
                defer.resolve("Invalid User Id");
            }
            return defer.promise;
        }

    }

    UMS.service("userService", UserService);
}