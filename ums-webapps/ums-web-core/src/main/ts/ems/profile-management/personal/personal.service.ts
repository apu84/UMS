module ums{
    export class PersonalService {
        public static $inject = ['HttpClient', '$q', 'notify'];

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }
    }

    UMS.service("personalService", PersonalService);
}