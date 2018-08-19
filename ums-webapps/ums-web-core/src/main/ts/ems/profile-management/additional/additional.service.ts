module ums{
    export class AdditionalService {
        public static $inject = ['HttpClient', '$q', 'notify'];

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }
    }

    UMS.service("additionalService", AdditionalService);
}