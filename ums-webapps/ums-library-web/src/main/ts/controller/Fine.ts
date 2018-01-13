module ums{

    export class Fine{
        public static $inject = ['$state', '$stateParams', '$q', 'notify', 'userService', 'circulationService'];

        private userId: string;

        constructor(private $state: any,
                    private $stateParams:any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService) {

            this.userId = $stateParams.patronId;
        }

    }

    UMS.controller("Fine", Fine);
}