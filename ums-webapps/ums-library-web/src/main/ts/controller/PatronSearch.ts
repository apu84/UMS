module ums {

    export class PatronSearch {
        public static $inject = ['$state', '$stateParams', '$scope', '$q', 'notify', 'userService', 'circulationService'];

        private patronId: string;
        private user: UserView;
        private allCirculation: Array<ILibraryCirculation>;
        private showCheckedOutItems: boolean;

        constructor(private $state: any,
                    private $stateParams: any,
                    private $scope: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService,
                    private circulationService: CirculationService) {

            this.showCheckedOutItems = false;
            this.patronId = $stateParams.patronId;
            this.getPatronDetails();
        }

        private getPatronDetails(): void{
            this.userService.getUser( this.patronId).then((data: any) => {
                this.user = data;
            });
        }

        private getCirculations(): void{
            this.showCheckedOutItems = true;
            this.allCirculation = [];
            this.circulationService.getAllCirculation(this.patronId).then((data: any) => {
                this.allCirculation = data;
            });
        }
    }

    UMS.controller("PatronSearch", PatronSearch);
}