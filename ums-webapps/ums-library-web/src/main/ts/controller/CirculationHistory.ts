module ums {

    export class CirculationHistory {
        public static $inject = ['$state', '$stateParams', '$q', 'notify', 'userService', 'circulationService'];

        private userId: string;
        private allCirculation: Array<ILibraryCirculation>;

        constructor(private $state: any,
                    private $stateParams: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService) {

            this.userId = $stateParams.patronId;
            this.getAllCirculations();

        }

        private getAllCirculations(): void {
            this.allCirculation = [];
            this.circulationService.getAllCirculation(this.userId).then((data: any) => {
                this.allCirculation = data;
            });
        }
    }

    UMS.controller("CirculationHistory", CirculationHistory);
}