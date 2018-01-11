module ums {
    export class CirculationCheckOut {
        public static $inject = ['$state', '$stateParams', '$q', 'notify', 'userService'];
        private user: UserView;
        private state: any;

        constructor(private $state: any,
                    private $stateParams:any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private userService: UserService) {

            this.state = $state;
            this.userService.getUser($stateParams.userId).then((data: any) => {
                this.user = data;
            });
        }

        private changeStateToPatronCheckOut(): void{
            this.state.go('circulation.checkOut.patronCheckOut', {patronId: this.user.userId});
        }

        private changeStateToPatronDetail(): void{
            this.state.go('circulation.checkOut.patronDetail', {patronId: this.user.userId});
        }

        private changeStateToPatronFine(): void{
            this.state.go('circulation.checkOut.patronFines', {patronId: this.user.userId});
        }

        private changeStateToCirculationHistory(): void{
            this.state.go('circulation.checkOut.circulationHistory', {patronId: this.user.userId});
        }
    }

    UMS.controller("CirculationCheckOut", CirculationCheckOut);
}