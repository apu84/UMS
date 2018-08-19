module ums{
    export class TrainingService {
        public static $inject = ['HttpClient', '$q', 'notify'];

        constructor(private httpClient: HttpClient,
                    private $q: ng.IQService,
                    private notify: Notify) {
        }
    }

    UMS.service("trainingService", TrainingService);
}