module ums {
    export class CirculationCheckOut {
        public static $inject = ['$state', '$stateParams', '$q', 'notify', 'userService'];

        private userId: string;
        private user: UserView;
        private state: any;

        constructor(private $state: any,
                    private $stateParams:any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService) {

            this.userId = $stateParams.userId;
            this.state = $state;

            this.userService.getUser(this.userId).then((data: any) => {
                this.user = data;
            });

        }

        private changeStateToPatronCheckOut(): void{
            this.state.go('circulation.checkOut.patronCheckOut', {patronId: this.userId});
        }

        private changeStateToPatronDetail(): void{
            this.state.go('circulation.checkOut.patronDetail', {patronId: this.userId});
        }

        private changeStateToPatronFine(): void{
            this.state.go('circulation.checkOut.patronFines', {patronId: this.userId});
        }

        private changeStateToCirculationHistory(): void{
            this.state.go('circulation.checkOut.circulationHistory', {patronId: this.userId});
        }
    }

    UMS.controller("CirculationCheckOut", CirculationCheckOut);
}