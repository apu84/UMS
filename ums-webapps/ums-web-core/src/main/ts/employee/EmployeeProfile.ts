module ums {
    class EmployeeProfile {
        public static $inject = ['$q', 'notify', '$state', '$stateParams', 'userService'];

        private state: any;
        private employeeId: string;
        private tab: string;

        constructor(private $q: ng.IQService,
                    private notify: Notify,
                    private $state : any,
                    private $stateParams: any,
                    private userService: UserService) {


            this.state = $state;
            this.tab = 'employeeProfile';

            if(this.state.id == undefined || this.state.id == null || this.state.id == ""){
                userService.fetchCurrentUserInfo().then((user: any) => {
                    this.employeeId = user.employeeId;
                });
            }
            else {
                this.employeeId = this.state.id;
            }
        }


        private redirectTo(tab: string): void{
            this.state.go(this.tab + '.' + tab, {id: this.employeeId});
        }
    }
    UMS.controller("EmployeeProfile", EmployeeProfile);
}