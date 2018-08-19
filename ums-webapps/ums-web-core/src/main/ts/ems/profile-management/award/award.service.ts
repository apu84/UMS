module ums{
    export class AwardService {
        public static $inject = ['HttpClient', '$q', 'notify'];

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }
    }

    UMS.service("awardService", AwardService);
}