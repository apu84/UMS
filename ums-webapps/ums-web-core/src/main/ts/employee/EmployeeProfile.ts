module ums {
    class EmployeeProfile {
        public static $inject = ['$q', 'notify', '$state', '$stateParams', 'userService'];

        private state: any;
        private stateParams: any;
        private employeeId: string;
        private tab: string;

        constructor(private $q: ng.IQService,
                    private notify: Notify,
                    private $state : any,
                    private $stateParams: any,
                    private userService: UserService) {


            this.state = $state;
            this.stateParams = $stateParams;

            console.log("Current state name ---------- " + this.state.current.name);
            console.log("Current state url ---------- " + this.state.current.url);
            console.log("stateParams ID:  ---------  " +  this.stateParams.id);

            if(this.stateParams.id == undefined || this.stateParams.id == null || this.stateParams.id == ""){
                console.log("in if");
                userService.fetchCurrentUserInfo().then((user: any) => {
                    this.tab = 'employeeProfile';
                    this.employeeId = user.employeeId;
                    //this.initialRouting();
                });
            }
            else {
                console.log("in else");
                this.tab = 'employeeInformation.employeeProfile';
                this.employeeId = this.stateParams.id;
                //this.initialRouting();
            }
        }

        private initialRouting() : void{
            if (this.state.current.url === '/employeeProfile') {
                this.redirectTo('personal');
            }
            else {
                this.redirectTo(this.state.current.url.split('/')[1]);
            }
        }

        public redirectTo(tab: string): void{
            this.state.go(this.tab + '.' + tab, {id: this.employeeId});
        }
    }

    UMS.controller("EmployeeProfile", EmployeeProfile);
}